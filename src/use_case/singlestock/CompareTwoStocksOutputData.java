package use_case.singlestock;

public class CompareTwoStocksOutputData {

    private final String ticker1;
    private final String ticker2;
    private final double riskFreeAnnual;
    private final String report;

    public CompareTwoStocksOutputData(String ticker1,
                                      String ticker2,
                                      double riskFreeAnnual,
                                      String report) {
        this.ticker1 = ticker1;
        this.ticker2 = ticker2;
        this.riskFreeAnnual = riskFreeAnnual;
        this.report = report;
    }

    public String getTicker1() {
        return ticker1;
    }

    public String getTicker2() {
        return ticker2;
    }

    public double getRiskFreeAnnual() {
        return riskFreeAnnual;
    }

    public String getReport() {
        return report;
    }
}
