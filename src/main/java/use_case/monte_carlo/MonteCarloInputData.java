package use_case.monte_carlo;

public class MonteCarloInputData {
    private final String ticker;
    private final double horizonYears;
    private final int nSteps;
    private final int nPaths;

    public MonteCarloInputData(String ticker, double horizonYears, int nSteps, int nPaths) {
        this.ticker = ticker;
        this.horizonYears = horizonYears;
        this.nSteps = nSteps;
        this.nPaths = nPaths;
    }

    public String getTicker() { return ticker; }
    public double getHorizonYears() { return horizonYears; }
    public int getNSteps() { return nSteps; }
    public int getNPaths() { return nPaths; }
}