package interface_adapter.historical_simulation;

import interface_adapter.AbsController;
import use_case.historical_simulation.HistoricalSimulationInputBoundary;
import use_case.historical_simulation.HistoricalSimulationInputData;

import java.time.LocalDate;
import java.util.Map;

public class HistoricalSimulationController extends AbsController {
    private final HistoricalSimulationInputBoundary interactor;

    public HistoricalSimulationController(HistoricalSimulationInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(Map<String, Double> portfolioStocks, String dateStr) {
        try {
            LocalDate date = LocalDate.parse(dateStr); // Expects YYYY-MM-DD
            HistoricalSimulationInputData data = new HistoricalSimulationInputData(portfolioStocks, date);
            interactor.execute(data);
        } catch (Exception e) {
            // In a real app, handle date parsing error in the view or here
            System.err.println("Invalid date format: " + dateStr);
        }
    }
}