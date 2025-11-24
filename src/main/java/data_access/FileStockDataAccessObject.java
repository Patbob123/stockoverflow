package data_access;

import use_case.simulation.StockDataAccessInterface;

import java.time.LocalDate;
import java.util.*;

public class FileStockDataAccessObject implements StockDataAccessInterface {

    @Override
    public Map<LocalDate, Double> getHistoricalPrices(String ticker) {
        // Mock data generator for testing purposes
        Map<LocalDate, Double> prices = new TreeMap<>();

        double startPrice;
        double volatility;
        double drift;

        // Customize slightly based on ticker to make it feel real
        switch (ticker.toUpperCase()) {
            case "AAPL":
                startPrice = 150.0;
                volatility = 0.02; // daily vol
                drift = 0.0005;
                break;
            case "TSLA":
                startPrice = 200.0;
                volatility = 0.035;
                drift = 0.001;
                break;
            case "SPY":
                startPrice = 400.0;
                volatility = 0.01;
                drift = 0.0003;
                break;
            case "GOOGL":
                startPrice = 140.0;
                volatility = 0.015;
                drift = 0.0004;
                break;
            case "AMZN":
                startPrice = 130.0;
                volatility = 0.02;
                drift = 0.0005;
                break;
            case "MSFT":
                startPrice = 350.0;
                volatility = 0.012;
                drift = 0.0006;
                break;
            case "NVDA":
                startPrice = 450.0;
                volatility = 0.04;
                drift = 0.002;
                break;
            default:
                startPrice = 100.0;
                volatility = 0.02;
                drift = 0.0005;
        }

        LocalDate date = LocalDate.now().minusYears(1);
        double currentPrice = startPrice;
        Random random = new Random();

        // Generate 1 year of daily data
        for (int i = 0; i < 252; i++) {
            prices.put(date, currentPrice);

            // Geometric Brownian Motion step for realistic path
            double shock = random.nextGaussian();
            currentPrice = currentPrice * Math.exp(drift - 0.5 * volatility * volatility + volatility * shock);

            date = date.plusDays(1);
            if (date.getDayOfWeek().getValue() >= 6) { // Skip weekends
                date = date.plusDays(2);
            }
        }

        return prices;
    }

    @Override
    public List<String> getAllAvailableTickers() {
        return Arrays.asList("AAPL", "TSLA", "SPY", "GOOGL", "AMZN", "MSFT", "NVDA");
    }
}
