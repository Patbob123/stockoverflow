
package use_case.singlestock;

import entities.PriceBar;
import entities.Stock;

import java.util.List;
import java.util.Locale;

public class AnalyzeSingleStockInteractor implements AnalyzeSingleStockInputBoundary {

    private static final int TRADING_DAYS = 252; //as we have (365 - holidays or weekend

    private final StockPriceDataAccessInterface priceGateway;
    private final RiskFreeRateDataAccessInterface riskFreeGateway;
    private final AnalyzeSingleStockOutputBoundary outputBoundary;

    public AnalyzeSingleStockInteractor(StockPriceDataAccessInterface priceGateway,
                                        RiskFreeRateDataAccessInterface riskFreeGateway,
                                        AnalyzeSingleStockOutputBoundary outputBoundary) {
        this.priceGateway = priceGateway;
        this.riskFreeGateway = riskFreeGateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(AnalyzeSingleStockInputData inputData) {
        String ticker = inputData.getTicker();
        double rfAnnual = inputData.getRiskFreeAnnual();

        // if user enters <= 0, fall back to FRED
        if (rfAnnual <= 0) {
            rfAnnual = riskFreeGateway.getCurrentRiskFreeRate();
        }

        // get price history from Stooq (through the interface)
        Stock stock = priceGateway.getDailySeries(ticker, 400);
        List<PriceBar> series = stock.getPriceHistory();
        if (series.size() < 2) {
            throw new RuntimeException("Not enough data: " + series.size() + " days.");
        }

        PriceBar latest = series.get(0);
        PriceBar oldest = series.get(series.size() - 1);

        double[] returns = dailyLogReturns(series);

        Stats stats = computeStats(returns, rfAnnual);

        StringBuilder sb = new StringBuilder();
        sb.append("=== Single Stock Analysis (Stooq) ===\n");
        sb.append("Symbol           : ").append(ticker).append("\n");
        sb.append("Days of data     : ").append(series.size()).append("\n");
        sb.append("First date       : ").append(oldest.getDate()).append("\n");
        sb.append("Last date        : ").append(latest.getDate()).append("\n");
        sb.append(String.format(Locale.US, "Last close ($)    : %.2f%n", latest.getClose()));
        sb.append("\n-- Return statistics --\n");
        sb.append(String.format(Locale.US, "Daily mean (log)  : %.5f%n", stats.meanDaily));
        sb.append(String.format(Locale.US, "Daily vol (log)   : %.5f%n", stats.stdDaily));
        sb.append(String.format(Locale.US, "Ann. mean (log)   : %.4f%n", stats.meanAnnual));
        sb.append(String.format(Locale.US, "Ann. vol          : %.2f%%%n", 100.0 * stats.stdAnnual));
        sb.append(String.format(Locale.US,
                "Sharpe (annual)   : %.3f  [rf = %.2f%%%n",
                stats.sharpeAnnual, 100.0 * rfAnnual));

        String report = sb.toString();

        AnalyzeSingleStockOutputData output =
                new AnalyzeSingleStockOutputData(ticker, rfAnnual, report);
        outputBoundary.present(output);
    }

    private double[] dailyLogReturns(List<PriceBar> series) {//formula = ln (pt/pt-1)
        int n = series.size();
        double[] r = new double[n-1];

        for (int i = 0; i < n-1; i++) {
            double pt =  series.get(i).getClose();//we take this day
            double pt_1 = series.get(i+1).getClose();//previous day
            r[i] = Math.log(pt / pt_1);//insert to formula
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

        double sum = 0.0;// to find mean I found an average daily log return(basically 1/n*sum of all those returns in a period
        for (double r: returns) sum += r;
        double mean = sum / n;

        double sumSq = 0.0;// to find variance and standrd deviation we take 1/n * sum (returns - mean)
        for (double r: returns) {
            double d = r - mean;
            sumSq += d * d;
        }

        double var = sumSq / n;
        double std = Math.sqrt(var);
        Stats s = new Stats();
        s.meanDaily = mean;
        s.stdDaily = std;

        double trading = (double) TRADING_DAYS;//doing it annually
        s.meanAnnual = mean * trading;//aka mean*252
        s.stdAnnual = std * Math.sqrt(trading);//same (stand dev is squared so we take sqr root of 252)

        if (s.stdAnnual == 0.0) {//now we can compute Sharpe, mean - risk free / standdev
            s.sharpeAnnual = 0.0;
        } else {
            s.sharpeAnnual = (s.meanAnnual - rfAnnual) / s.stdAnnual;
        }
        return s;



    }
}