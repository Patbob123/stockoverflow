package service;

import entities.Stock;
import java.time.LocalDate;
import java.util.Map;

public interface StockDataAPI {
    // Get the latest data of a single stock
    Stock getLatestStockData(String ticker) throws APIException;

    // Get the latest data on multiple stocks
    Map<String, Stock> getMultipleStocksData(String[] tickers) throws APIException;

    // Obtain historical data of stocks
    Map<LocalDate, Stock> getStockHistory(String ticker, String interval, LocalDate startDate, LocalDate endDate) throws APIException;
}