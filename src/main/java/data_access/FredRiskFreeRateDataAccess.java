package data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import use_case.singlestock.RiskFreeRateDataAccessInterface;

import java.io.IOException;

public class FredRiskFreeRateDataAccess implements RiskFreeRateDataAccessInterface {
    private static final String BASE_URL =
            "https://api.stlouisfed.org/fred/series/observations";

    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;
    private final String seriesId;

    public FredRiskFreeRateDataAccess(String apiKey) {
        this(apiKey, "DGS3MO");
    }

    public FredRiskFreeRateDataAccess(String apiKey, String seriesId) {
        this.apiKey = apiKey;
        this.seriesId = seriesId;
    }

    @Override
    public double getCurrentRiskFreeRate() {
        HttpUrl url = HttpUrl.parse(BASE_URL).newBuilder()
                .addQueryParameter("series_id", seriesId)
                .addQueryParameter("api_key", apiKey)
                .addQueryParameter("file_type", "json")
                .addQueryParameter("sort_order", "desc")
                .addQueryParameter("limit", "1")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("FRED API call failed: "
                        + response.code() + " " + response.message());
            }

            String body = response.body().string();
            System.out.println(body);
            JsonObject root = JsonParser.parseString(body).getAsJsonObject();

            JsonArray observations = root.getAsJsonArray("observations");
            if (observations == null || observations.size() == 0) {
                throw new RuntimeException("No observations returned for series " + seriesId);
            }

            JsonObject latest = observations.get(0).getAsJsonObject();
            String valueStr = latest.get("value").getAsString();

            if (valueStr == null || valueStr.equals(".") || valueStr.isEmpty()) {
                throw new RuntimeException(
                        "Latest FRED observation has missing value for " + seriesId
                );
            }

            double percent = Double.parseDouble(valueStr); //translate to percentage
            return percent / 100.0;
        } catch (IOException e) {
            throw new RuntimeException("Error calling FRED API", e);
        }
    }
}
