package use_case.portfolio_analysis;

import entities.Portfolio;
import entities.Stock;
import entities.User;
import use_case.APIDataAccessInterface;
import use_case.UserDataAccessInterface;

import java.time.LocalDate;
import java.util.*;

public class PortfolioAnalysisInteractor implements PortfolioAnalysisInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final APIDataAccessInterface apiDataAccess;
    private final PortfolioAnalysisOutputBoundary outputBoundary;

    public PortfolioAnalysisInteractor(UserDataAccessInterface userDataAccess,
                                       APIDataAccessInterface apiDataAccess,
                                       PortfolioAnalysisOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccess;
        this.apiDataAccess = apiDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(PortfolioAnalysisInputData inputData) {
        User user = userDataAccess.get(inputData.getUsername());
        if (user == null) {
            outputBoundary.prepareFailView("User not found.");
            return;
        }

        Portfolio portfolio = user.getPortfolioList().getPortfolio(inputData.getPortfolioName());
        if (portfolio == null || portfolio.getStocks().isEmpty()) {
            outputBoundary.prepareFailView("Portfolio is empty or not found.");
            return;
        }

        Map<String, Stock> stocks = portfolio.getStocks();
        Map<String, Map<LocalDate, Double>> allHistories = new HashMap<>();

        // 1. Fetch Data
        for (String ticker : stocks.keySet()) {
            Stock stock = apiDataAccess.getStock(ticker); // Ensure we have historical data
            if (stock != null && stock.getHistoricalPrices() != null && !stock.getHistoricalPrices().isEmpty()) {
                allHistories.put(ticker, stock.getHistoricalPrices());
            }
        }

        if (allHistories.isEmpty()) {
            outputBoundary.prepareFailView("Could not fetch historical data for analysis.");
            return;
        }

        Map<String, Double> individualReturns = new HashMap<>();
        List<Double> portfolioDailyReturns = new ArrayList<>();


        double maxReturn = -Double.MAX_VALUE;
        double minReturn = Double.MAX_VALUE;
        String bestStock = "N/A";
        String worstStock = "N/A";

        // Aggregate Portfolio Value per day
        Map<LocalDate, Double> portfolioValueCurve = new TreeMap<>();

        for (Map.Entry<String, Map<LocalDate, Double>> entry : allHistories.entrySet()) {
            String ticker = entry.getKey();
            Map<LocalDate, Double> history = entry.getValue();

            // Individual Total Return
            List<LocalDate> dates = new ArrayList<>(history.keySet());
            Collections.sort(dates);
            if (dates.size() > 1) {
                double startPrice = history.get(dates.get(0));
                double endPrice = history.get(dates.get(dates.size() - 1));
                double ret = (endPrice - startPrice) / startPrice;
                individualReturns.put(ticker, ret);

                if (ret > maxReturn) { maxReturn = ret; bestStock = ticker; }
                if (ret < minReturn) { minReturn = ret; worstStock = ticker; }
            }

            // Add to portfolio curve (Assuming 1 share each)
            for (LocalDate date : history.keySet()) {
                portfolioValueCurve.put(date, portfolioValueCurve.getOrDefault(date, 0.0) + history.get(date));
            }
        }

        // 3. Calculate Portfolio Metrics
        List<LocalDate> sortedDates = new ArrayList<>(portfolioValueCurve.keySet());
        Collections.sort(sortedDates);

        if (sortedDates.size() < 2) {
            outputBoundary.prepareFailView("Not enough historical data points.");
            return;
        }

        double startValue = portfolioValueCurve.get(sortedDates.get(0));
        double endValue = portfolioValueCurve.get(sortedDates.get(sortedDates.size() - 1));
        double totalPortfolioReturn = (endValue - startValue) / startValue;

        // Calculate Volatility (Std Dev of Daily Returns)
        List<Double> dailyReturns = new ArrayList<>();
        for (int i = 1; i < sortedDates.size(); i++) {
            double prev = portfolioValueCurve.get(sortedDates.get(i-1));
            double curr = portfolioValueCurve.get(sortedDates.get(i));
            if (prev != 0) {
                dailyReturns.add((curr - prev) / prev);
            }
        }

        double meanDailyReturn = calculateMean(dailyReturns);
        double variance = 0.0;
        for (double r : dailyReturns) {
            variance += Math.pow(r - meanDailyReturn, 2);
        }
        double stdDev = Math.sqrt(variance / dailyReturns.size());
        double annualizedVolatility = stdDev * Math.sqrt(252); // Annualized

        // Calculate Sharpe Ratio (Assuming Risk Free Rate = 2% annual)
        double riskFreeRateDaily = 0.02 / 252;
        double sharpeRatio = (meanDailyReturn - riskFreeRateDaily) / stdDev;
        // Annualize Sharpe (approx)
        double annualizedSharpe = sharpeRatio * Math.sqrt(252);

        PortfolioAnalysisOutputData outputData = new PortfolioAnalysisOutputData(
                totalPortfolioReturn,
                annualizedVolatility,
                annualizedSharpe,
                bestStock,
                worstStock,
                individualReturns,
                false,
                null
        );
        outputBoundary.prepareSuccessView(outputData);
    }

    private double calculateMean(List<Double> data) {
        if (data == null || data.isEmpty()) return 0.0;
        double sum = 0.0;
        for(Double d : data) sum += d;
        return sum / data.size();
    }
}