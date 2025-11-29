package entities.Portfolio;

import java.time.LocalTime;
import java.util.ArrayList;

public class PortfolioFactory {
    public Portfolio createPortfolio(String portfolioName) {
        ArrayList<String> emptyStockList = new ArrayList<>();
        LocalTime time = LocalTime.now();
        return new Portfolio(portfolioName, emptyStockList, time);
    }
}
