
package use_case.singlestock;

import entities.PriceBar;

import java.util.List;
import java.util.Locale;

public class AnalyzeSingleStockInteractor implements AnalyzeSingleStockInputBoundary {

    private final StockPriceDataAccessInterface priceGateway;
    private final RiskFreeRateDataAccessInterface riskFreeGateway;
    private final AnalyzeSingleStockOutputBoundary outputBoundary;

    public AnalyzeSingleStockInteractor(StockPriceDataAccessInterface priceGateway,
                                        RiskFreeRateDataAccessInterface riskFreeGateway,
                                        AnalyzeSingleStockOutputBoundary outputBoundary) {
        this.priceGateway = priceGateway;
        this.riskFreeGateway = riskFreeGateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(AnalyzeSingleStockInputData inputData) {
        String ticker = inputData.getTicker();
        double rf = inputData.getRiskFreeAnnual();

        // if user enters <= 0, fall back to FRED
        if (rf <= 0) {
            rf = riskFreeGateway.getCurrentRiskFreeRate();
        }

        // get price history from Stooq (through the interface)
        List<PriceBar> series = priceGateway.getDailySeries(ticker, 400);
        if (series.size() < 2) {
            throw new RuntimeException("Not enough data: " + series.size() + " days.");
        }

        PriceBar latest = series.get(0);
        PriceBar oldest = series.get(series.size() - 1);

        String report = String.format(Locale.US,
                "=== Single Stock Analysis (just Stooq for now) ===%n" +
                        "Symbol          : %s%n" + //aka APPL
                        "Days of data    : %d%n" +
                        "First date      : %s%n" +
                        "Last date       : %s%n" +
                        "Last close ($)  : %.2f%n" + //price
                        "Risk-free (ann.): %.2f%%%n%n" + //or form fred
                        "(Next: add full stats, Sharpe, VaR, scenario, etc.)",
                ticker,
                series.size(),
                oldest.getDate(),
                latest.getDate(),
                latest.getClose(),
                rf * 100.0
        );

        AnalyzeSingleStockOutputData output =
                new AnalyzeSingleStockOutputData(ticker, rf, report);

        outputBoundary.present(output);
    }
}