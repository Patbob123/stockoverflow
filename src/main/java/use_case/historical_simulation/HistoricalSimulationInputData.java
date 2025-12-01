package use_case.historical_simulation;

import java.time.LocalDate;
import java.util.Map;

public class HistoricalSimulationInputData {
    private final Map<String, Double> portfolioStocks; // Ticker -> Quantity
    private final LocalDate startDate;

    public HistoricalSimulationInputData(Map<String, Double> portfolioStocks, LocalDate startDate) {
        this.portfolioStocks = portfolioStocks;
        this.startDate = startDate;
    }

    public Map<String, Double> getPortfolioStocks() { return portfolioStocks; }
    public LocalDate getStartDate() { return startDate; }
}