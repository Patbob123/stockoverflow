package use_case.singlestock;

import entities.PriceBar;

import java.util.List;
import java.util.Locale;

public class CompareTwoStocksInteractor implements CompareTwoStocksInputBoundary {

    private static final int TRADING_DAYS = 252;

    private final StockPriceDataAccessInterface priceGateway;
    private final RiskFreeRateDataAccessInterface riskFreeGateway;
    private final CompareTwoStocksOutputBoundary outputBoundary;

    public CompareTwoStocksInteractor(StockPriceDataAccessInterface priceGateway,
                                      RiskFreeRateDataAccessInterface riskFreeGateway,
                                      CompareTwoStocksOutputBoundary outputBoundary) {
        this.priceGateway = priceGateway;
        this.riskFreeGateway = riskFreeGateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(CompareTwoStocksInputData inputData) {
        String t1 = inputData.getTicker1();
        String t2 = inputData.getTicker2();
        double rfAnnual = inputData.getRiskFreeAnnual();

        if (rfAnnual <= 0) {
            rfAnnual = riskFreeGateway.getCurrentRiskFreeRate();
        }

        StockMetrics m1 = analyzeOne(t1, rfAnnual);
        StockMetrics m2 = analyzeOne(t2, rfAnnual);

        String report = buildReport(m1, m2, rfAnnual);

        CompareTwoStocksOutputData output =
                new CompareTwoStocksOutputData(t1, t2, rfAnnual, report);

        outputBoundary.present(output);
    }

    private StockMetrics analyzeOne(String ticker, double rfAnnual) {
        List<PriceBar> series = priceGateway.getDailySeries(ticker, 400);

        if (series.isEmpty()) {
            throw new RuntimeException(
                    "No price data found for '" + ticker + "'.\n" +
                            "This symbol may not be supported by the free data providers or may require a premium plan.");
        }
        if (series.size() < 2) {
            throw new RuntimeException(
                    "Not enough historical data for '" + ticker + "': only " +
                            series.size() + " day(s).");
        }

        PriceBar latest = series.get(0);
        double[] returns = dailyLogReturns(series);
        Stats stats = computeStats(returns, rfAnnual);

        StockMetrics m = new StockMetrics();
        m.ticker = ticker;
        m.days = series.size();
        m.lastClose = latest.getClose();
        m.meanAnnual = stats.meanAnnual;
        m.stdAnnual = stats.stdAnnual;
        m.sharpeAnnual = stats.sharpeAnnual;
        return m;
    }

    /** series is newest-first; compute log(P_t / P_{t-1}) */
    private double[] dailyLogReturns(List<PriceBar> series) {
        int n = series.size();
        double[] r = new double[n - 1];
        for (int i = 0; i < n - 1; i++) {
            double pt   = series.get(i).getClose();
            double pt_1 = series.get(i + 1).getClose();
            r[i] = Math.log(pt / pt_1);
        }
        return r;
    }

    private static class Stats {
        double meanDaily;
        double stdDaily;
        double meanAnnual;
        double stdAnnual;
        double sharpeAnnual;
    }

    private Stats computeStats(double[] returns, double rfAnnual) {
        int n = returns.length;

        double sum = 0.0;
        for (double r : returns) sum += r;
        double mean = sum / n;

        double sumSq = 0.0;
        for (double r : returns) {
            double d = r - mean;
            sumSq += d * d;
        }

        double var = sumSq / n;
        double std = Math.sqrt(var);

        Stats s = new Stats();
        s.meanDaily = mean;
        s.stdDaily = std;

        double trading = (double) TRADING_DAYS;
        s.meanAnnual = mean * trading;
        s.stdAnnual = std * Math.sqrt(trading);

        if (s.stdAnnual == 0.0) {
            s.sharpeAnnual = 0.0;
        } else {
            s.sharpeAnnual = (s.meanAnnual - rfAnnual) / s.stdAnnual;
        }
        return s;
    }

    private static class StockMetrics {
        String ticker;
        int days;
        double lastClose;
        double meanAnnual;
        double stdAnnual;
        double sharpeAnnual;
    }

    private String buildReport(StockMetrics m1, StockMetrics m2, double rfAnnual) {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Compare ").append(m1.ticker)
                .append(" vs ").append(m2.ticker).append(" ===\n\n");
        sb.append(String.format(Locale.US,
                "Risk-free (annual): %.2f%%%n%n", 100.0 * rfAnnual));

        sb.append(String.format(Locale.US,
                "%-8s %6s %12s %12s %10s%n",
                "Ticker", "Days", "Last Close", "AnnVol(%)", "Sharpe"));
        sb.append(String.format(Locale.US,
                "%-8s %6d %12.2f %12.2f %10.3f%n",
                m1.ticker, m1.days, m1.lastClose,
                100.0 * m1.stdAnnual, m1.sharpeAnnual));
        sb.append(String.format(Locale.US,
                "%-8s %6d %12.2f %12.2f %10.3f%n",
                m2.ticker, m2.days, m2.lastClose,
                100.0 * m2.stdAnnual, m2.sharpeAnnual));

        String better;
        if (m1.sharpeAnnual > m2.sharpeAnnual) {
            better = m1.ticker + String.format(" (%.3f vs %.3f)",
                    m1.sharpeAnnual, m2.sharpeAnnual);
        } else if (m2.sharpeAnnual > m1.sharpeAnnual) {
            better = m2.ticker + String.format(" (%.3f vs %.3f)",
                    m2.sharpeAnnual, m1.sharpeAnnual);
        } else {
            better = "Tie (both Sharpe = " +
                    String.format(Locale.US, "%.3f", m1.sharpeAnnual) + ")";
        }

        sb.append("\nBest Sharpe: ").append(better).append("\n");

        return sb.toString();
    }
}
