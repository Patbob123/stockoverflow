package service;

import entities.Stock;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AlphaVantageAPI implements StockDataAPI {
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with the actual API key


    private static final String BASE_URL = "https://www.alphavantage.co/query";
    private final HttpClient client;

    public AlphaVantageAPI() {
        this.client = HttpClient.newHttpClient();
    }

    @Override
    public Stock getLatestStockData(String ticker) throws APIException {
        try {
            String function = "GLOBAL_QUOTE";
            String url = String.format("%s?function=%s&symbol=%s&apikey=%s",
                    BASE_URL, function, ticker, API_KEY);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new APIException("Failed to fetch data: HTTP status code " + response.statusCode());
            }

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            // 检查API错误
            if (json.has("Error Message")) {
                throw new APIException(json.get("Error Message").getAsString());
            }

            if (json.has("Note")) {
                System.out.println("API Note: " + json.get("Note").getAsString());
            }

            JsonObject quote = json.getAsJsonObject("Global Quote");

            if (quote == null || quote.entrySet().isEmpty()) {
                throw new APIException("No data found for ticker: " + ticker);
            }

            Stock stock = new Stock(ticker, "");

            double open = parseDouble(quote.get("02. open").getAsString());
            double high = parseDouble(quote.get("03. high").getAsString());
            double low = parseDouble(quote.get("04. low").getAsString());
            double close = parseDouble(quote.get("05. price").getAsString());

            stock.updateQuote(LocalDate.now(), open, close, high, low);

            return stock;
        } catch (IOException | InterruptedException e) {
            throw new APIException("Error fetching stock data: " + e.getMessage());
        }
    }

    @Override
    public Map<String, Stock> getMultipleStocksData(String[] tickers) throws APIException {
        Map<String, Stock> result = new HashMap<>();
        for (String ticker : tickers) {
            try {
                Stock stock = getLatestStockData(ticker);
                result.put(ticker, stock);
            } catch (APIException e) {
                System.err.println("Error fetching data for " + ticker + ": " + e.getMessage());
            }
        }
        return result;
    }

    @Override
    public Map<LocalDate, Stock> getStockHistory(String ticker, String interval, LocalDate startDate, LocalDate endDate) throws APIException {
        try {
            String function = "TIME_SERIES_DAILY";
            String url = String.format("%s?function=%s&symbol=%s&outputsize=full&apikey=%s",
                    BASE_URL, function, ticker, API_KEY);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new APIException("Failed to fetch history: HTTP status code " + response.statusCode());
            }

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            // 检查API错误
            if (json.has("Error Message")) {
                throw new APIException(json.get("Error Message").getAsString());
            }

            String timeSeriesKey = "Time Series (Daily)";
            if (!json.has(timeSeriesKey)) {
                throw new APIException("No historical data found for ticker: " + ticker);
            }

            JsonObject timeSeries = json.getAsJsonObject(timeSeriesKey);
            Map<LocalDate, Stock> history = new HashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (Map.Entry<String, com.google.gson.JsonElement> entry : timeSeries.entrySet()) {
                LocalDate date = LocalDate.parse(entry.getKey(), formatter);

                // Check if the date is within the range
                if ((startDate != null && date.isBefore(startDate)) ||
                        (endDate != null && date.isAfter(endDate))) {
                    continue;
                }

                JsonObject data = entry.getValue().getAsJsonObject();
                Stock stock = new Stock(ticker, "");

                double open = parseDouble(data.get("1. open").getAsString());
                double high = parseDouble(data.get("2. high").getAsString());
                double low = parseDouble(data.get("3. low").getAsString());
                double close = parseDouble(data.get("4. close").getAsString());

                stock.updateQuote(date, open, close, high, low);
                history.put(date, stock);
            }

            return history;
        } catch (IOException | InterruptedException e) {
            throw new APIException("Error fetching stock history: " + e.getMessage());
        }
    }

    private double parseDouble(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        return Double.parseDouble(value);
    }
}