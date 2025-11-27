package entities;

/**
 * A dedicated entity to hold aggregate financial metrics
 * derived from a stock's historical data, such as annual return and volatility.
 */
public class StockMetrics {
    private final double annualReturn;
    private final double annualVolatility;

    public StockMetrics(double annualReturn, double annualVolatility) {
        this.annualReturn = annualReturn;
        this.annualVolatility = annualVolatility;
    }

    public double getAnnualReturn() {
        return annualReturn;
    }

    public double getAnnualVolatility() {
        return annualVolatility;
    }
}