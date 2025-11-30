package use_case.singlestock;

import entities.StatisticsCalculator;
import entities.Stock;
import use_case.APIDataAccessInterface;

import java.time.LocalDate;
import java.util.*;

public class AnalyzeSingleStockInteractor implements AnalyzeSingleStockInputBoundary {

    private static final int TRADING_DAYS = 252;
    private final APIDataAccessInterface apiDataAccess;
    private final AnalyzeSingleStockOutputBoundary outputBoundary;
    private final StatisticsCalculator statsCalculator;

    public AnalyzeSingleStockInteractor(APIDataAccessInterface apiDataAccess,
                                        AnalyzeSingleStockOutputBoundary outputBoundary) {
        this.apiDataAccess = apiDataAccess;
        this.outputBoundary = outputBoundary;
        this.statsCalculator = new StatisticsCalculator();
    }

    @Override
    public void execute(AnalyzeSingleStockInputData inputData) {
        String ticker = inputData.getTicker();
        double rfAnnual = inputData.getRiskFreeAnnual();

        if (rfAnnual <= 0) {
            rfAnnual = apiDataAccess.getRiskFreeRate();
        }

        Stock stock = apiDataAccess.getStock(ticker);
        if (stock == null || stock.getHistoricalPrices().isEmpty()) {
            throw new RuntimeException("No data found for ticker: " + ticker);
        }

        Map<LocalDate, Double> history = stock.getHistoricalPrices();
        List<Double> prices = new ArrayList<>(history.values());

        if (prices.size() < 2) {
            throw new RuntimeException("Not enough data to calculate returns: " + prices.size() + " days.");
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

        double lastClose = prices.get(prices.size() - 1);
        List<LocalDate> dates = new ArrayList<>(history.keySet());
        LocalDate firstDate = dates.get(0);
        LocalDate lastDate = dates.get(dates.size() - 1);

        StringBuilder sb = new StringBuilder();
        sb.append("=== Single Stock Analysis ===\n");
        sb.append("Symbol           : ").append(stock.getTicker()).append("\n");
        sb.append("Name             : ").append(stock.getName() == null ? "N/A" : stock.getName()).append("\n");
        sb.append("Days of data     : ").append(prices.size()).append("\n");
        sb.append("Data Range       : ").append(firstDate).append(" to ").append(lastDate).append("\n");
        sb.append(String.format(Locale.US, "Last close ($)    : %.2f%n", lastClose));
        sb.append("\n-- Return statistics --\n");
        sb.append(String.format(Locale.US, "Daily mean (log)  : %.5f%n", dailyMean));
        sb.append(String.format(Locale.US, "Daily vol (log)   : %.5f%n", dailyVol));
        sb.append(String.format(Locale.US, "Ann. mean (log)   : %.4f%n", annualMean));
        sb.append(String.format(Locale.US, "Ann. vol          : %.2f%%%n", 100.0 * annualVol));
        sb.append(String.format(Locale.US, "Sharpe (annual)   : %.3f  [rf = %.2f%%%n",
                sharpe, 100.0 * rfAnnual));

        AnalyzeSingleStockOutputData output =
                new AnalyzeSingleStockOutputData(ticker, rfAnnual, sb.toString());
        outputBoundary.present(output);
    }


    private double[] calculateDailyLogReturns(List<Double> prices) {
        int n = prices.size();
        double[] r = new double[n - 1];
        for (int i = 1; i < n; i++) {
            double pt = prices.get(i);
            double pt_1 = prices.get(i - 1);
            r[i - 1] = Math.log(pt / pt_1);
        }
        return r;
    }
}