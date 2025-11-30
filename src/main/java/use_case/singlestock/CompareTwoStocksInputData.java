package use_case.singlestock;

public class CompareTwoStocksInputData {

    private final String ticker1;
    private final String ticker2;
    private final double riskFreeAnnual;
    // i set two tivkers to compare
    public CompareTwoStocksInputData(String ticker1, String ticker2, double riskFreeAnnual) {
        this.ticker1 = ticker1;
        this.ticker2 = ticker2;
        this.riskFreeAnnual = riskFreeAnnual;
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
}