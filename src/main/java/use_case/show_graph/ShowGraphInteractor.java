package use_case.show_graph;

import entities.PriceBar;
import use_case.singlestock.StockPriceDataAccessInterface;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ShowGraphInteractor implements ShowGraphInputBoundary {

    final StockPriceDataAccessInterface apiDataAccessObject;
    final ShowGraphOutputBoundary showGraphPresenter;

    public ShowGraphInteractor(StockPriceDataAccessInterface apiDataAccessObject,
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
            try {

                List<PriceBar> priceBars = apiDataAccessObject.getDailySeries(ticker, 100).getPriceHistory();

                if (priceBars != null && !priceBars.isEmpty()) {
                    Map<LocalDate, Double> historicalPrices = new TreeMap<>();

                    for (PriceBar bar : priceBars) {
                        historicalPrices.put(bar.getDate(), bar.getClose());
                    }

                    data.put(ticker, historicalPrices);
                }
            } catch (Exception e) {
                System.err.println("Failed to fetch data for " + ticker + ": " + e.getMessage());
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