package use_case.portfolio_analysis;

import java.util.Map;

public class PortfolioAnalysisOutputData {
    private final double totalReturn;
    private final double volatility;
    private final double sharpeRatio;
    private final String bestStock;
    private final String worstStock;
    private final Map<String, Double> individualReturns;
    private final int daysAnalyzed; // New field
    private final boolean useCaseFailed;
    private final String errorMessage;

    public PortfolioAnalysisOutputData(double totalReturn, double volatility, double sharpeRatio,
                                       String bestStock, String worstStock,
                                       Map<String, Double> individualReturns,
                                       int daysAnalyzed,
                                       boolean useCaseFailed, String errorMessage) {
        this.totalReturn = totalReturn;
        this.volatility = volatility;
        this.sharpeRatio = sharpeRatio;
        this.bestStock = bestStock;
        this.worstStock = worstStock;
        this.individualReturns = individualReturns;
        this.daysAnalyzed = daysAnalyzed;
        this.useCaseFailed = useCaseFailed;
        this.errorMessage = errorMessage;
    }

    public double getTotalReturn() { return totalReturn; }
    public double getVolatility() { return volatility; }
    public double getSharpeRatio() { return sharpeRatio; }
    public String getBestStock() { return bestStock; }
    public String getWorstStock() { return worstStock; }
    public Map<String, Double> getIndividualReturns() { return individualReturns; }
    public int getDaysAnalyzed() { return daysAnalyzed; }
    public boolean isUseCaseFailed() { return useCaseFailed; }
    public String getErrorMessage() { return errorMessage; }
}