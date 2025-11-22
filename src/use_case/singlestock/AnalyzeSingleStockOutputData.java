package use_case.singlestock;

public class AnalyzeSingleStockOutputData {

    private final String ticker;
    private final double riskFreeAnnual;
    private final String report;

    public AnalyzeSingleStockOutputData(String ticker,
                                        double riskFreeAnnual,
                                        String report) {
        this.ticker = ticker;
        this.riskFreeAnnual = riskFreeAnnual;
        this.report = report;
    }

    public String getTicker() {
        return ticker;
    }

    public double getRiskFreeAnnual() {
        return riskFreeAnnual;
    }

    public String getReport() {
        return report;
    }
}
