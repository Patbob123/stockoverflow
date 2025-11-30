package data_access;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.PriceBar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.singlestock.StockPriceDataAccessInterface;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AlphaVantageStockPriceDataAccess implements StockPriceDataAccessInterface {

    private static final String BASE_URL = "https://www.alphavantage.co/query";

    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;

    public AlphaVantageStockPriceDataAccess(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<PriceBar> getDailySeries(String ticker, int maxDays) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("Alpha Vantage API key is missing.");
        }

        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addQueryParameter("function", "TIME_SERIES_DAILY_ADJUSTED")
                .addQueryParameter("symbol", ticker)
                .addQueryParameter("outputsize", "full")
                .addQueryParameter("apikey", apiKey)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Alpha Vantage HTTP error: " +
                        response.code() + " " + response.message());
            }

            String body = response.body().string();


            System.out.println("Alpha Vantage raw JSON for " + ticker + ":");
            System.out.println(body.substring(0, Math.min(500, body.length())));

            JsonObject root = JsonParser.parseString(body).getAsJsonObject();

            if (root.has("Error Message")) {
                throw new RuntimeException("Alpha Vantage error: " +
                        root.get("Error Message").getAsString());
            }
            if (root.has("Note")) {
                throw new RuntimeException("Alpha Vantage note: " +
                        root.get("Note").getAsString());
            }

            JsonObject ts = root.getAsJsonObject("Time Series (Daily)");
            if (ts == null) {
                throw new RuntimeException(
                        "Alpha Vantage did not return 'Time Series (Daily)' for ticker '" +
                                ticker + "'. Top-level keys: " + root.keySet()//we need premium API key

                );
            }

            List<PriceBar> bars = new ArrayList<>();

            for (String dateStr : ts.keySet()) {
                JsonObject day = ts.getAsJsonObject(dateStr);
                if (day == null) continue;

                double open  = day.get("1. open").getAsDouble();
                double high  = day.get("2. high").getAsDouble();
                double low   = day.get("3. low").getAsDouble();
                double close = day.get("4. close").getAsDouble();
                long volume  = day.get("6. volume").getAsLong();

                LocalDate date = LocalDate.parse(dateStr);
                bars.add(new PriceBar(date, open, high, low, close, volume));
            }

            // newest first
            bars.sort(Comparator.comparing(PriceBar::getDate).reversed());

            if (bars.size() > maxDays) {
                return new ArrayList<>(bars.subList(0, maxDays));
            }
            return bars;

        } catch (IOException e) {
            throw new RuntimeException("Error calling Alpha Vantage", e);
        }
    }
}

