package data_access;

import entities.PriceBar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import main.java.use_case.singlestock.StockPriceDataAccessInterface;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StooqStockDataAccess implements StockPriceDataAccessInterface {

    private static final String BASE_URL = "https://stooq.com/q/d/l/";

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public List<PriceBar> getDailySeries(String ticker, int maxDays) {
        // Stooq uses suffixes, e.g. AAPL.US; you can adjust this if needed
        String stooqSymbol = ticker.trim().toLowerCase();
        if (!stooqSymbol.contains(".")) {//now if you write appl-> appl.us as a format,but handles appl.to
            stooqSymbol += ".us";
        }

        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addQueryParameter("s", stooqSymbol)
                .addQueryParameter("i", "d")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Stooq call failed: "
                        + response.code() + " " + response.message());
            }

            String body = response.body().string();
            String[] lines = body.split("\\R");

            List<PriceBar> bars = new ArrayList<>();

            // first line is header: date,open,high,low,close,volume
            for (int i = 1; i < lines.length; i++) {
                String line = lines[i].trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 6) continue;

                String dateStr = parts[0]; //date
                String openStr = parts[1]; //open price
                String highStr = parts[2];
                String lowStr  = parts[3]; //..
                String closeStr= parts[4];
                String volStr  = parts[5];

                // Sometimes Stooq puts "null"
                if (openStr.equals("null") || highStr.equals("null")
                        || lowStr.equals("null") || closeStr.equals("null")) {
                    continue;
                }

                LocalDate date = LocalDate.parse(dateStr);
                double open  = Double.parseDouble(openStr);
                double high  = Double.parseDouble(highStr);
                double low   = Double.parseDouble(lowStr);
                double close = Double.parseDouble(closeStr);
                long volume  = volStr.equals("null") ? 0L : Long.parseLong(volStr);

                bars.add(new PriceBar(date, open, high, low, close, volume));
            }


            bars.sort(Comparator.comparing(PriceBar::getDate).reversed());

            if (bars.size() > maxDays) {
                return new ArrayList<>(bars.subList(0, maxDays));
            }
            return bars;

        } catch (IOException e) {
            throw new RuntimeException("Error calling Stooq", e);
        }
    }
}
