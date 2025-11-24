package interface_adapter.portfolio;

import entities.Portfolio.Portfolio;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PortfolioMenuState {

    private Portfolio portfolio = null;
    private List<String> availableStocks = null;

    // Analysis Results
    private String analysisMessage = null;
    private Map<String, Double> analysisStats = null;
    private double[][] analysisPlotData = null;

    // Manual Getters and Setters to ensure compilation without Lombok config
    public Portfolio getPortfolio() { return portfolio; }
    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }

    public List<String> getAvailableStocks() { return availableStocks; }
    public void setAvailableStocks(List<String> availableStocks) { this.availableStocks = availableStocks; }

    public String getAnalysisMessage() { return analysisMessage; }
    public void setAnalysisMessage(String analysisMessage) { this.analysisMessage = analysisMessage; }

    public Map<String, Double> getAnalysisStats() { return analysisStats; }
    public void setAnalysisStats(Map<String, Double> analysisStats) { this.analysisStats = analysisStats; }

    public double[][] getAnalysisPlotData() { return analysisPlotData; }
    public void setAnalysisPlotData(double[][] analysisPlotData) { this.analysisPlotData = analysisPlotData; }
}
