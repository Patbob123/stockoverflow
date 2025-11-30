package use_case.singlestock;

import entities.StatisticsCalculator;
import entities.Stock;
import use_case.APIDataAccessInterface;

import java.util.*;

public class CompareTwoStocksInteractor implements CompareTwoStocksInputBoundary {

    private static final int TRADING_DAYS = 252;
    private final APIDataAccessInterface apiDataAccess;
    private final CompareTwoStocksOutputBoundary outputBoundary;
    private final StatisticsCalculator statsCalculator;

    public CompareTwoStocksInteractor(APIDataAccessInterface apiDataAccess,
                                      CompareTwoStocksOutputBoundary outputBoundary) {
        this.apiDataAccess = apiDataAccess;
        this.outputBoundary = outputBoundary;
        this.statsCalculator = new StatisticsCalculator();
    }

    @Override
    public void execute(CompareTwoStocksInputData inputData) {
        String t1 = inputData.getTicker1();
        String t2 = inputData.getTicker2();
        double rfAnnual = inputData.getRiskFreeAnnual();

        if (rfAnnual <= 0) {
            rfAnnual = apiDataAccess.getRiskFreeRate();
        }

        try {
            StockMetricsDTO m1 = analyzeOne(t1, rfAnnual);
            StockMetricsDTO m2 = analyzeOne(t2, rfAnnual);

            String report = buildReport(m1, m2, rfAnnual);
            CompareTwoStocksOutputData output = new CompareTwoStocksOutputData(t1, t2, rfAnnual, report);
            outputBoundary.present(output);

        } catch (Exception e) {
            throw new RuntimeException("Comparison Error: " + e.getMessage());
        }
    }


    private StockMetricsDTO analyzeOne(String ticker, double rfAnnual) {
        Stock stock = apiDataAccess.getStock(ticker);
        if (stock == null || stock.getHistoricalPrices().isEmpty()) {
            throw new RuntimeException("No data for: " + ticker);
        }

        List<Double> prices = new ArrayList<>(stock.getHistoricalPrices().values());
        if (prices.size() < 2) {
            throw new RuntimeException("Not enough data for: " + ticker);
        }

        double[] returns = calculateDailyLogReturns(prices);

        double dailyMean = statsCalculator.mean(returns);
        double dailyVol = statsCalculator.standardDeviation(returns);

        double annualMean = dailyMean * TRADING_DAYS;
        double annualVol = dailyVol * Math.sqrt(TRADING_DAYS);

        double sharpe = 0.0;
        if (annualVol != 0) {
            sharpe = (annualMean - rfAnnual) / annualVol;
        }

        return new StockMetricsDTO(ticker, annualVol, sharpe);
    }

    private double[] calculateDailyLogReturns(List<Double> prices) {
        int n = prices.size();
        double[] r = new double[n - 1];
        for (int i = 1; i < n; i++) r[i - 1] = Math.log(prices.get(i) / prices.get(i - 1));
        return r;
    }

    private static class StockMetricsDTO {
        String ticker;
        double stdAnnual;
        double sharpeAnnual;

        public StockMetricsDTO(String ticker, double stdAnnual, double sharpeAnnual) {
            this.ticker = ticker;
            this.stdAnnual = stdAnnual;
            this.sharpeAnnual = sharpeAnnual;
        }
    }

    private String buildReport(StockMetricsDTO m1, StockMetricsDTO m2, double rfAnnual) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Compare ").append(m1.ticker).append(" vs ").append(m2.ticker).append(" ===\n\n");
        sb.append(String.format(Locale.US, "Risk-free: %.2f%%%n%n", 100.0 * rfAnnual));
        sb.append(String.format(Locale.US, "%-8s %12s %10s%n", "Ticker", "Vol(%)", "Sharpe"));
        sb.append(String.format(Locale.US, "%-8s %12.2f %10.3f%n", m1.ticker, m1.stdAnnual * 100, m1.sharpeAnnual));
        sb.append(String.format(Locale.US, "%-8s %12.2f %10.3f%n", m2.ticker, m2.stdAnnual * 100, m2.sharpeAnnual));

        String winner = (m1.sharpeAnnual > m2.sharpeAnnual) ? m1.ticker : (m2.sharpeAnnual > m1.sharpeAnnual ? m2.ticker : "Tie");
        sb.append("\nBetter Sharpe: ").append(winner);
        return sb.toString();
    }
}