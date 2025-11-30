package entities;

/**
 * A utility entity for performing statistical calculations on financial data.
 * Used for User Story 6 (Analytics) and User Story 9 (Historical Returns).
 */
public class StatisticsCalculator {

    /**
     * Calculates the arithmetic mean (average) of an array of returns.
     */
    public double mean(double[] values) {
        if (values == null || values.length == 0) return 0.0;

        double sum = 0.0;
        for (double v : values) {
            sum += v;
        }
        return sum / values.length;
    }

    /**
     * Calculates the variance of an array of values.
     */
    public double variance(double[] values) {
        if (values == null || values.length == 0) return 0.0;

        double mean = mean(values);
        double temp = 0;
        for (double a : values) {
            temp += (a - mean) * (a - mean);
        }
        return temp / values.length;
    }

    /**
     * Calculates the standard deviation (volatility).
     */
    public double standardDeviation(double[] values) {
        return Math.sqrt(variance(values));
    }

    /**
     * Calculates the Sharpe Ratio.
     * Formula: (Mean Return - Risk Free Rate) / Standard Deviation
     */
    public double sharpeRatio(double[] returns, double riskFreeRate) {
        double meanReturn = mean(returns);
        double stdDev = standardDeviation(returns);

        if (stdDev == 0) return 0.0;
        return (meanReturn - riskFreeRate) / stdDev;
    }
}