package use_case.refresh;

import entities.Portfolio;
import entities.Stock;
import entities.User;
import service.SessionManager;
import service.StockDataAPI;
import service.APIException;

import java.time.LocalDate;
import java.util.Map;

public class RefreshInteractor implements RefreshInputBoundary {

    private final RefreshOutputBoundary refreshPresenter;
    private final StockDataAPI stockDataAPI;

    public RefreshInteractor(RefreshOutputBoundary refreshPresenter, StockDataAPI stockDataAPI) {
        this.refreshPresenter = refreshPresenter;
        this.stockDataAPI = stockDataAPI;
    }

    @Override
    public void refreshStock(String ticker, Stock currentStock) {
        try {
            // Get the latest stock data
            Stock updatedStock = stockDataAPI.getLatestStockData(ticker);

            // Check if there is any new data
            if (currentStock.getLastUpdated() != null &&
                    updatedStock.getLastUpdated().isEqual(currentStock.getLastUpdated())) {
                refreshPresenter.prepareUpToDateView(ticker);
                return;
            }

            // Update stock data
            currentStock.updateQuote(
                    updatedStock.getLastUpdated(),
                    updatedStock.getOpen(),
                    updatedStock.getClose(),
                    updatedStock.getHigh(),
                    updatedStock.getLow()
            );

            // If the user is logged in, add it to the search history
            User currentUser = SessionManager.getInstance().getCurrentUser();
            if (currentUser != null) {
                currentUser.addToSearchHistory(ticker + " - " + LocalDate.now());
            }

            // success
            refreshPresenter.prepareStockSuccessView(currentStock);

        } catch (APIException e) {
            refreshPresenter.prepareFailView(e.getMessage());
        }
    }

    @Override
    public void refreshPortfolio(Portfolio portfolio) {
        try {
            // Get the codes of all the stocks in your investment portfolio
            Map<String, Stock> stocks = portfolio.getStocks();
            String[] tickers = stocks.keySet().toArray(new String[0]);

            if (tickers.length == 0) {
                refreshPresenter.prepareFailView("Portfolio is empty");
                return;
            }

            // Get the latest data on all stocks
            Map<String, Stock> updatedStocks = stockDataAPI.getMultipleStocksData(tickers);

            boolean hasNewData = false;

            // Update the data of each stock
            for (Map.Entry<String, Stock> entry : updatedStocks.entrySet()) {
                String ticker = entry.getKey();
                Stock updatedStock = entry.getValue();
                Stock existingStock = stocks.get(ticker);

                if (existingStock.getLastUpdated() == null ||
                        !updatedStock.getLastUpdated().isEqual(existingStock.getLastUpdated())) {

                    existingStock.updateQuote(
                            updatedStock.getLastUpdated(),
                            updatedStock.getOpen(),
                            updatedStock.getClose(),
                            updatedStock.getHigh(),
                            updatedStock.getLow()
                    );
                    hasNewData = true;
                }
            }

            // Check if there is any new data
            if (!hasNewData) {
                refreshPresenter.prepareUpToDateView(portfolio.getName());
                return;
            }

            // The last update time for updating the investment portfolio
            portfolio.updateLastUpdated();

            // If the user is logged in, add it to the search history
            User currentUser = SessionManager.getInstance().getCurrentUser();
            if (currentUser != null) {
                currentUser.addToSearchHistory("Portfolio: " + portfolio.getName() + " - " + LocalDate.now());
            }

            // success
            refreshPresenter.preparePortfolioSuccessView(portfolio);

        } catch (APIException e) {
            refreshPresenter.prepareFailView(e.getMessage());
        }
    }
}