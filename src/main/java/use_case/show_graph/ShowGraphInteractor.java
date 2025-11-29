package use_case.show_graph;

import entities.Stock;
import use_case.APIDataAccessInterface;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ShowGraphInteractor implements ShowGraphInputBoundary {
    private final APIDataAccessInterface apiDataAccessObject;
    private final ShowGraphOutputBoundary showGraphPresenter;

    public ShowGraphInteractor(APIDataAccessInterface apiDataAccessObject, ShowGraphOutputBoundary showGraphPresenter) {
        this.apiDataAccessObject = apiDataAccessObject;
        this.showGraphPresenter = showGraphPresenter;
    }

    @Override
    public void execute(ShowGraphInputData showGraphInputData) {
        Map<String, Map<LocalDate, Double>> allStocksData = new HashMap<>();
        StringBuilder errorMessages = new StringBuilder();

        for (String ticker : showGraphInputData.getTickers()) {
            // Fetch stock data from API
            Stock stock = apiDataAccessObject.getStock(ticker);

            if (stock == null || stock.getHistoricalPrices().isEmpty()) {
                errorMessages.append("No data for: ").append(ticker).append("\n");
            } else {
                allStocksData.put(ticker, stock.getHistoricalPrices());
            }
        }

        if (allStocksData.isEmpty()) {
            // If no valid data was found for any stock
            showGraphPresenter.prepareFailView("Could not fetch data for selected stocks: " + errorMessages);
        } else {
            // Success: pass the map of data to the presenter
            ShowGraphOutputData outputData = new ShowGraphOutputData(allStocksData, false, null);
            showGraphPresenter.prepareSuccessView(outputData);
        }
    }
}