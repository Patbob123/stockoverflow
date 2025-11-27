package entities;

import java.util.List;
import java.lang.Math;

public class StatisticsCalculator {

    private static final int TRADING_DAYS_PER_YEAR = 252;

    // --- Core Statistical Methods (MISSING METHOD ADDED) ---

    /**
     * Calculates the mean (average) of an array of doubles.
     */
    public double mean(double[] returns) { // <--- THIS IS THE MISSING METHOD
        double sum = 0.0;
        if (returns.length == 0) {
            return 0.0;
        }
        for (double r : returns) {
            sum += r;
        }
        return sum / returns.length;
    }

    /**
     * Calculates the standard deviation (volatility) of an array of doubles.
     */
    public double standardDeviation(double[] returns, double mean) {
        if (returns.length <= 1) {
            return 0.0;
        }
        double sumOfSquaredDifferences = 0.0;
        for (double r : returns) {
            sumOfSquaredDifferences += Math.pow(r - mean, 2);
        }
        // Use N-1 for sample standard deviation
        return Math.sqrt(sumOfSquaredDifferences / (returns.length - 1));
    }

    // --- Financial Metric Methods ---

    /**
     * Calculates the daily logarithmic returns from a list of PriceBar objects.
     */
    public double[] calculateDailyLogReturns(List<PriceBar> priceHistory) {
        if (priceHistory.size() < 2) {
            return new double[0];
        }

        double[] returns = new double[priceHistory.size() - 1];

        for (int i = 1; i < priceHistory.size(); i++) {
            double priceToday = priceHistory.get(i - 1).getClose();
            double priceYesterday = priceHistory.get(i).getClose();
            returns[i - 1] = Math.log(priceToday / priceYesterday);
        }
        return returns;
    }

    // --- Main Calculation Method ---

    /**
     * Calculates annual return and volatility and returns the resulting StockMetrics entity.
     */
    public StockMetrics calculateMetrics(List<PriceBar> priceHistory) {

        double[] dailyLogReturns = calculateDailyLogReturns(priceHistory);

        if (dailyLogReturns.length == 0) {
            return new StockMetrics(0.0, 0.0);
        }

        // 1. Calculate Daily Mean Return
        double muDaily = mean(dailyLogReturns); // <--- NOW CALLING THE DEFINED METHOD

        // 2. Calculate Daily Volatility (Standard Deviation)
        double sigmaDaily = standardDeviation(dailyLogReturns, muDaily);

        // 3. Annualize Metrics
        double muAnnual = muDaily * TRADING_DAYS_PER_YEAR;
        double sigmaAnnual = sigmaDaily * Math.sqrt(TRADING_DAYS_PER_YEAR);

        // 4. Return the populated StockMetrics entity
        return new StockMetrics(muAnnual, sigmaAnnual);
    }
}