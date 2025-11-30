package use_case.portfolio_analysis;

import entities.Portfolio;
import entities.StatisticsCalculator;
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
    private final StatisticsCalculator statsCalculator;

    public PortfolioAnalysisInteractor(UserDataAccessInterface userDataAccess,
                                       APIDataAccessInterface apiDataAccess,
                                       PortfolioAnalysisOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccess;
        this.apiDataAccess = apiDataAccess;
        this.outputBoundary = outputBoundary;
        this.statsCalculator = new StatisticsCalculator();
    }

    @Override
    public void execute(PortfolioAnalysisInputData inputData) {
        // 1. Validate User and Portfolio
        User user = userDataAccess.get(inputData.getUsername());
        if (user == null) {
            outputBoundary.prepareFailView("User not found: " + inputData.getUsername());
            return;
        }

        Portfolio portfolio = user.getPortfolioList().getPortfolio(inputData.getPortfolioName());
        if (portfolio == null || portfolio.getStocks().isEmpty()) {
            outputBoundary.prepareFailView("Portfolio is empty or does not exist.");
            return;
        }

        // 2. Fetch Historical Data
        Map<String, Stock> stocks = portfolio.getStocks();
        Map<String, Map<LocalDate, Double>> allHistories = new HashMap<>();

        for (String ticker : stocks.keySet()) {
            Stock stock = apiDataAccess.getStock(ticker);
            if (stock != null && stock.getHistoricalPrices() != null && !stock.getHistoricalPrices().isEmpty()) {
                allHistories.put(ticker, stock.getHistoricalPrices());
            }
        }

        if (allHistories.isEmpty()) {
            outputBoundary.prepareFailView("Could not fetch sufficient historical data.");
            return;
        }

        // 3. Analyze Data
        Map<String, Double> individualReturns = new HashMap<>();
        Map<LocalDate, Double> portfolioValueCurve = new TreeMap<>(); // Dates sorted automatically

        double maxReturn = -Double.MAX_VALUE;
        double minReturn = Double.MAX_VALUE;
        String bestStock = "N/A";
        String worstStock = "N/A";

        // 3a. Individual Stock Performance & Portfolio Curve Construction
        for (Map.Entry<String, Map<LocalDate, Double>> entry : allHistories.entrySet()) {
            String ticker = entry.getKey();
            Map<LocalDate, Double> history = entry.getValue();

            // Calculate Individual Return (Simple: End - Start / Start)
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

            // Aggregate to Portfolio Value (Assuming Equal Weight / 1 share each for simplicity in MVP)
            for (LocalDate date : history.keySet()) {
                portfolioValueCurve.put(date, portfolioValueCurve.getOrDefault(date, 0.0) + history.get(date));
            }
        }

        // 4. Calculate Portfolio Metrics
        List<LocalDate> sortedDates = new ArrayList<>(portfolioValueCurve.keySet());

        if (sortedDates.size() < 2) {
            outputBoundary.prepareFailView("Not enough overlapping historical data points across stocks.");
            return;
        }

        double startValue = portfolioValueCurve.get(sortedDates.get(0));
        double endValue = portfolioValueCurve.get(sortedDates.get(sortedDates.size() - 1));
        double totalPortfolioReturn = (endValue - startValue) / startValue;

        // Calculate Daily Returns for Volatility/Sharpe
        // Using StatisticsCalculator entity if possible, or helper methods
        double[] dailyReturns = new double[sortedDates.size() - 1];
        for (int i = 1; i < sortedDates.size(); i++) {
            double prev = portfolioValueCurve.get(sortedDates.get(i-1));
            double curr = portfolioValueCurve.get(sortedDates.get(i));
            if (prev != 0) {
                dailyReturns[i-1] = (curr - prev) / prev;
            }
        }

        // Use Entity for Math
        double meanDailyReturn = statsCalculator.mean(dailyReturns);
        double stdDevDaily = statsCalculator.standardDeviation(dailyReturns);

        // Annualize
        double annualizedVolatility = stdDevDaily * Math.sqrt(252);

        // Sharpe Ratio (Risk Free Rate approx 2% annual)
        double riskFreeRateDaily = 0.02 / 252.0;
        double sharpeRatio = 0.0;
        if (stdDevDaily > 0) {
            sharpeRatio = (meanDailyReturn - riskFreeRateDaily) / stdDevDaily;
            // Annualize Sharpe
            sharpeRatio = sharpeRatio * Math.sqrt(252);
        }

        // 5. Prepare Output
        PortfolioAnalysisOutputData outputData = new PortfolioAnalysisOutputData(
                totalPortfolioReturn,
                annualizedVolatility,
                sharpeRatio,
                bestStock,
                worstStock,
                individualReturns,
                sortedDates.size(), // Number of days analyzed
                false,
                null
        );

        outputBoundary.prepareSuccessView(outputData);
    }
}