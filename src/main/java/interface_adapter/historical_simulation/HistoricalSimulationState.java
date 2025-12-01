package interface_adapter.historical_simulation;

import java.util.HashMap;
import java.util.Map;

public class HistoricalSimulationState {
    private String resultText = "";
    private String error = null;
    private Map<String, Double> portfolioStocks = new HashMap<>();

    public HistoricalSimulationState(HistoricalSimulationState copy) {
        resultText = copy.resultText;
        error = copy.error;
        portfolioStocks = new HashMap<>(copy.portfolioStocks);
    }
    public HistoricalSimulationState() {}

    public String getResultText() { return resultText; }
    public void setResultText(String resultText) { this.resultText = resultText; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public Map<String, Double> getPortfolioStocks() { return portfolioStocks; }
    public void setPortfolioStocks(Map<String, Double> portfolioStocks) { this.portfolioStocks = portfolioStocks; }
}