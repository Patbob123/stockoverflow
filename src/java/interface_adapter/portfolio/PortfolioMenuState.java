package interface_adapter.portfolio;

import entities.Stock;
import java.util.List;

public class PortfolioMenuState {

    private Portfolio portfolio = null;

    // User Story 5: Data to be graphed
    private List<Stock> stocksToGraph;

    // User Story 9: Analysis result message
    private String analysisResult;

    private String error;

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public List<Stock> getStocksToGraph() {
        return stocksToGraph;
    }

    public void setStocksToGraph(List<Stock> stocksToGraph) {
        this.stocksToGraph = stocksToGraph;
    }

    public String getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult) {
        this.analysisResult = analysisResult;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
