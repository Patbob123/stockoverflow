package use_case.show_graph;

import entities.Stock;
import use_case.APIDataAccessInterface;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ShowGraphInteractor implements ShowGraphInputBoundary {
    final APIDataAccessInterface apiDataAccessObject;
    final ShowGraphOutputBoundary showGraphPresenter;

    public ShowGraphInteractor(APIDataAccessInterface apiDataAccessObject,
                               ShowGraphOutputBoundary showGraphPresenter) {
        this.apiDataAccessObject = apiDataAccessObject;
        this.showGraphPresenter = showGraphPresenter;
    }

    @Override
    public void execute(ShowGraphInputData inputData) {
        if (inputData.getTickers() == null || inputData.getTickers().isEmpty()) {
            showGraphPresenter.prepareFailView("No tickers provided.");
            return;
        }

        Map<String, Map<LocalDate, Double>> data = new HashMap<>();

        for (String ticker : inputData.getTickers()) {
            Stock stock = apiDataAccessObject.getStock(ticker);
            if (stock != null && stock.getHistoricalPrices() != null) {
                data.put(stock.getTicker(), stock.getHistoricalPrices());
            }
        }

        if (data.isEmpty()) {
            showGraphPresenter.prepareFailView("No valid data found for the provided tickers.");
        } else {
            ShowGraphOutputData output = new ShowGraphOutputData(
                    data,
                    null,
                    inputData.getPreviousViewName()
            );
            showGraphPresenter.prepareSuccessView(output);
        }
    }
}