package data_access;

import entities.Stock;
import use_case.APIDataAccessInterface;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Data Access Object for retrieving stock data.
 * <p>
 * Strategy:
 * 1. Historical Data (getStock): Uses Stooq (CSV format) for unlimited, free historical data.
 * 2. Symbol Search (searchSymbols): Uses Alpha Vantage (JSON) because Stooq lacks a search API.
 */
public class StockDataAccessObject implements APIDataAccessInterface {

    // Alpha Vantage Key for SEARCH functionality only
    private static final String ALPHA_VANTAGE_KEY = "RS6QLHWDVT98HTGB";
    private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";

    // Stooq Base URL for historical data (CSV)
    // Format: https://stooq.com/q/d/l/?s=AAPL.US&i=d
    private static final String STOOQ_BASE_URL = "https://stooq.com/q/d/l/";

    @Override
    public Stock getStock(String ticker) {
        // Stooq usually requires a suffix for US stocks (e.g., "AAPL.US").
        // We append .US if not present, assuming the user is searching for US stocks.
        String stooqTicker = ticker.toUpperCase();
        if (!stooqTicker.contains(".")) {
            stooqTicker += ".US";
        }

        String urlString = String.format("%s?s=%s&i=d", STOOQ_BASE_URL, stooqTicker);

        try {
            List<String> csvLines = makeCSVCall(urlString);

            // If the list is empty or only contains the header
            if (csvLines.size() <= 1) {
                System.out.println("No data found for ticker: " + ticker);
                return null;
            }

            // Stooq CSV Header: Date,Open,High,Low,Close,Volume
            // We skip the first line (header)
            Map<LocalDate, Double> historicalPrices = new TreeMap<>();

            // Temporary variables for the latest quote
            Double latestOpen = null, latestHigh = null, latestLow = null, latestClose = null;
            LocalDate latestDate = null;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (int i = 1; i < csvLines.size(); i++) {
                String row = csvLines.get(i);
                String[] cols = row.split(",");

                // Ensure row has enough columns
                if (cols.length < 5) continue;

                try {
                    String dateStr = cols[0];
                    double open = Double.parseDouble(cols[1]);
                    double high = Double.parseDouble(cols[2]);
                    double low = Double.parseDouble(cols[3]);
                    double close = Double.parseDouble(cols[4]);

                    LocalDate date = LocalDate.parse(dateStr, formatter);
                    historicalPrices.put(date, close);

                    // Since Stooq usually returns newest first or unsorted, we check dates
                    // However, we are parsing all lines. We need the MOST RECENT one for the stock object fields.
                    if (latestDate == null || date.isAfter(latestDate)) {
                        latestDate = date;
                        latestOpen = open;
                        latestHigh = high;
                        latestLow = low;
                        latestClose = close;
                    }

                } catch (NumberFormatException e) {
                    // Skip malformed lines
                    continue;
                }
            }

            if (historicalPrices.isEmpty() || latestDate == null) {
                return null;
            }

            // Create Stock Entity
            Stock stock = new Stock(ticker.toUpperCase(), ticker.toUpperCase()); // Name is same as ticker for Stooq
            stock.setHistoricalPrices(historicalPrices);
            stock.updateQuote(latestDate, latestOpen, latestClose, latestHigh, latestLow);

            return stock;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> searchSymbols(String query) {
        // KEEPING ALPHA VANTAGE FOR SEARCH
        // Stooq does not have a robust "Search by keyword" API returning JSON.
        String function = "SYMBOL_SEARCH";
        String urlString = String.format("%s?function=%s&keywords=%s&apikey=%s",
                ALPHA_VANTAGE_URL, function, query, ALPHA_VANTAGE_KEY);

        List<String> results = new ArrayList<>();
        try {
            String jsonResponse = makeJSONCall(urlString);
            JSONObject jsonObject = new JSONObject(jsonResponse);

            if (jsonObject.has("bestMatches")) {
                JSONArray matches = jsonObject.getJSONArray("bestMatches");
                for (int i = 0; i < matches.length(); i++) {
                    JSONObject match = matches.getJSONObject(i);
                    String symbol = match.getString("1. symbol");
                    String name = match.getString("2. name");
                    results.add(symbol + " - " + name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // --- Helper Methods ---

    /**
     * Makes an HTTP GET request and returns lines of CSV data.
     */
    private List<String> makeCSVCall(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000); // 5 seconds timeout
        conn.setReadTimeout(5000);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("HTTP error code: " + responseCode);
        }

        List<String> lines = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                lines.add(inputLine);
            }
        }
        return lines;
    }

    /**
     * Makes an HTTP GET request and returns raw JSON string.
     */
    private String makeJSONCall(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("HTTP error code: " + responseCode);
        }

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        return content.toString();
    }
}