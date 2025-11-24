package entities;

public class StatisticsCalculator {
    public double mean(double[] returns) {
        if (returns.length == 0) return 0.0;
        double sum = 0.0;
        for (double val : returns) {
            sum += val;
        }
        return sum / returns.length;
    }

    public double calculateVolatility(double[] returns) {
        if (returns.length < 2) return 0.0;
        double mean = mean(returns);
        double sumSquaredDiffs = 0.0;
        for (double val : returns) {
            sumSquaredDiffs += Math.pow(val - mean, 2);
        }
        // Sample standard deviation
        return Math.sqrt(sumSquaredDiffs / (returns.length - 1));
    }
}
