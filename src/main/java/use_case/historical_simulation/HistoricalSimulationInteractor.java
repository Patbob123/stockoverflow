package use_case.historical_simulation;

import entities.PriceBar;
import entities.Stock;
import use_case.InputBoundary;
import use_case.singlestock.StockPriceDataAccessInterface;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class HistoricalSimulationInteractor implements HistoricalSimulationInputBoundary, InputBoundary {
    private final HistoricalSimulationOutputBoundary outputBoundary;
    private final StockPriceDataAccessInterface stockDataAccess;

    public HistoricalSimulationInteractor(HistoricalSimulationOutputBoundary outputBoundary,
                                          StockPriceDataAccessInterface stockDataAccess) {
        this.outputBoundary = outputBoundary;
        this.stockDataAccess = stockDataAccess;
    }

    @Override
    public void execute(HistoricalSimulationInputData inputData) {
        Map<String, Double> stocks = inputData.getPortfolioStocks();
        if (stocks.isEmpty()) {
            outputBoundary.prepareFailView("Portfolio is empty.");
            return;
        }

        double totalInitialValue = 0.0;
        double totalCurrentValue = 0.0;
        LocalDate startDate = inputData.getStartDate();

        try {
            for (Map.Entry<String, Double> entry : stocks.entrySet()) {
                String ticker = entry.getKey();
                Double quantity = entry.getValue();


                Stock stock = stockDataAccess.getDailySeries(ticker, 2000);
                List<PriceBar> history = stock.getPriceHistory();

                if (history == null || history.isEmpty()) {
                    outputBoundary.prepareFailView("No data found for " + ticker);
                    return;
                }


                double startPrice = -1;
                double currentPrice = history.get(0).getClose();


                for (PriceBar bar : history) {
                    if (!bar.getDate().isAfter(startDate)) {
                        startPrice = bar.getClose();
                        break;
                    }
                }

                if (startPrice == -1) {
                    outputBoundary.prepareFailView("Data not available for " + ticker + " on " + startDate);
                    return;
                }

                totalInitialValue += startPrice * quantity;
                totalCurrentValue += currentPrice * quantity;
            }

            double totalReturn = (totalCurrentValue - totalInitialValue) / totalInitialValue;

            HistoricalSimulationOutputData output = new HistoricalSimulationOutputData(
                    totalInitialValue, totalCurrentValue, totalReturn, startDate.toString()
            );
            outputBoundary.prepareSuccessView(output);

        } catch (Exception e) {
            e.printStackTrace();
            outputBoundary.prepareFailView("Analysis failed: " + e.getMessage());
        }
    }
}