package use_case.singlestock;

public class AnalyzeSingleStockInputData {

    private final String ticker;
    private final double riskFreeAnnual;

    public AnalyzeSingleStockInputData(String ticker, double riskFreeAnnual) {
        this.ticker = ticker;
        this.riskFreeAnnual = riskFreeAnnual;
    }

    public String getTicker() {
        return ticker;
    }

    public double getRiskFreeAnnual() {
        return riskFreeAnnual;
    }
}