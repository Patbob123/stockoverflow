package data_access;

import entities.Stock;
import use_case.APIDataAccessInterface;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StockDataAccessObject implements APIDataAccessInterface {

    // TODO: Replace with your actual Alpha Vantage API Key
    private static final String API_KEY = "RS6QLHWDVT98HTGB";
    private static final String BASE_URL = "https://www.alphavantage.co/query";

    @Override
    public Stock getStock(String ticker) {
        String function = "TIME_SERIES_DAILY";
        String urlString = String.format("%s?function=%s&symbol=%s&apikey=%s",
                BASE_URL, function, ticker, API_KEY);

        try {
            String jsonResponse = makeAPICall(urlString);
            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (jsonObject.has("Error Message") || !jsonObject.has("Time Series (Daily)")) {
                return null;
            }

            JSONObject timeSeries = jsonObject.getJSONObject("Time Series (Daily)");
            Stock stock = new Stock(ticker, ticker);
            Map<LocalDate, Double> historicalPrices = new TreeMap<>();

            Iterator<String> keys = timeSeries.keys();
            while (keys.hasNext()) {
                String dateStr = keys.next();
                JSONObject dailyData = timeSeries.getJSONObject(dateStr);
                double closePrice = dailyData.getDouble("4. close");
                LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                historicalPrices.put(date, closePrice);
            }

            stock.setHistoricalPrices(historicalPrices);

            if (!historicalPrices.isEmpty()) {
                LocalDate latestDate = ((TreeMap<LocalDate, Double>) historicalPrices).lastKey();
                Double latestPrice = historicalPrices.get(latestDate);
                stock.updateQuote(latestDate, 0.0, latestPrice, 0.0, 0.0);
            }

            return stock;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> searchSymbols(String query) {
        String function = "SYMBOL_SEARCH";
        String urlString = String.format("%s?function=%s&keywords=%s&apikey=%s",
                BASE_URL, function, query, API_KEY);
        List<String> results = new ArrayList<>();
        try {
            String jsonResponse = makeAPICall(urlString);
            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (jsonObject.has("bestMatches")) {
                JSONArray matches = jsonObject.getJSONArray("bestMatches");
                for (int i = 0; i < matches.length(); i++) {
                    JSONObject match = matches.getJSONObject(i);
                    String symbol = match.getString("1. symbol");
                    String name = match.getString("2. name");
                    results.add(symbol + " - " + name); // Format: AAPL - Apple Inc.
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private String makeAPICall(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("HTTP error code: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}