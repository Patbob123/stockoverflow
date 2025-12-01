package data_access;

import entities.Stock;
import use_case.APIDataAccessInterface;


import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class StockDataAccessObject implements APIDataAccessInterface {

    // --- API Keys ---
    private static final String ALPHA_VANTAGE_KEY = "RM73FNUNQXWQ14SC";
    private String fredApiKey = "YOUR_FRED_API_KEY";

    // --- URLs ---
    private static final String ALPHA_VANTAGE_URL = "https://www.alphavantage.co/query";
    private static final String STOOQ_BASE_URL = "https://stooq.com/q/d/l/";
    private static final String FRED_BASE_URL = "https://api.stlouisfed.org/fred/series/observations";

    // OkHttp Client
    private final OkHttpClient client = new OkHttpClient();

    public StockDataAccessObject() {

    }


    public StockDataAccessObject(String fredApiKey) {
        this.fredApiKey = fredApiKey;
    }

    // =========================================================
    // 1. Stooq
    // =========================================================
    @Override
    public Stock getStock(String ticker) {
        String stooqTicker = ticker.toUpperCase();
        if (!stooqTicker.contains(".")) {
            stooqTicker += ".US";
        }

        String urlString = String.format("%s?s=%s&i=d", STOOQ_BASE_URL, stooqTicker);

        try {
            List<String> csvLines = makeCSVCall(urlString);
            if (csvLines.size() <= 1) {
                return null;
            }

            Map<LocalDate, Double> historicalPrices = new TreeMap<>();
            Double latestOpen = null, latestHigh = null, latestLow = null, latestClose = null;
            LocalDate latestDate = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (int i = 1; i < csvLines.size(); i++) {
                String row = csvLines.get(i);
                String[] cols = row.split(",");
                if (cols.length < 5) continue;

                try {
                    String dateStr = cols[0];
                    double open = Double.parseDouble(cols[1]);
                    double high = Double.parseDouble(cols[2]);
                    double low = Double.parseDouble(cols[3]);
                    double close = Double.parseDouble(cols[4]);

                    LocalDate date = LocalDate.parse(dateStr, formatter);
                    historicalPrices.put(date, close);

                    if (latestDate == null || date.isAfter(latestDate)) {
                        latestDate = date;
                        latestOpen = open;
                        latestHigh = high;
                        latestLow = low;
                        latestClose = close;
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            if (historicalPrices.isEmpty() || latestDate == null) {
                return null;
            }

            Stock stock = new Stock(ticker.toUpperCase(), ticker.toUpperCase());
            stock.setHistoricalPrices(historicalPrices);
            stock.updateQuote(latestDate, latestOpen, latestClose, latestHigh, latestLow);

            return stock;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // =========================================================
    // 2. Alpha Vantage
    // =========================================================
    @Override
    public List<String> searchSymbols(String query) {
        String function = "SYMBOL_SEARCH";
        String urlString = String.format("%s?function=%s&keywords=%s&apikey=%s",
                ALPHA_VANTAGE_URL, function, query, ALPHA_VANTAGE_KEY);

        List<String> results = new ArrayList<>();
        try {
            System.out.println("DEBUG: Sending request to: " + ALPHA_VANTAGE_URL + "?function=" + function + "&keywords=" + query);
            String jsonResponse = makeJSONCall(urlString);

            System.out.println("DEBUG: API Response: " + jsonResponse);
            // -------------------------------------

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
            else if (jsonObject.has("Note")) {
                System.err.println("DEBUG: API Limit Hit! " + jsonObject.getString("Note"));

            }

            else if (jsonObject.has("Error Message")) {
                System.err.println("DEBUG: API Error! " + jsonObject.getString("Error Message"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    // =========================================================
    // 3. FRED API
    // =========================================================
    @Override
    public double getRiskFreeRate() {
        if (fredApiKey == null || fredApiKey.isEmpty()) {
            System.out.println("Warning: No FRED API Key provided. Using default 4.5%");
            return 0.045;
        }

        // Series ID: DGS3MO (3-Month Treasury Constant Maturity Rate)
        String seriesId = "DGS3MO";

        HttpUrl url = HttpUrl.parse(FRED_BASE_URL).newBuilder()
                .addQueryParameter("series_id", seriesId)
                .addQueryParameter("api_key", fredApiKey)
                .addQueryParameter("file_type", "json")
                .addQueryParameter("sort_order", "desc")
                .addQueryParameter("limit", "1")
                .build();

        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("FRED API call failed: " + response.code());
            }

            String body = response.body().string();
            JsonObject root = JsonParser.parseString(body).getAsJsonObject();
            JsonArray observations = root.getAsJsonArray("observations");

            if (observations == null || observations.size() == 0) {
                return 0.045; // Fallback
            }

            JsonObject latest = observations.get(0).getAsJsonObject();
            String valueStr = latest.get("value").getAsString();

            if (valueStr == null || valueStr.equals(".") || valueStr.isEmpty()) {
                return 0.045;
            }

            double percent = Double.parseDouble(valueStr);
            return percent / 100.0;

        } catch (Exception e) {
            System.err.println("Error calling FRED: " + e.getMessage());
            return 0.045;
        }
    }

    // --- Helper Methods ---

    private List<String> makeCSVCall(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        if (conn.getResponseCode() != 200) throw new IOException("HTTP error: " + conn.getResponseCode());

        List<String> lines = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) lines.add(inputLine);
        }
        return lines;
    }

    private String makeJSONCall(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);

        if (conn.getResponseCode() != 200) throw new IOException("HTTP error: " + conn.getResponseCode());

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) content.append(inputLine);
        }
        return content.toString();
    }
}