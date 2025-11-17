package use_case.singlestock;

public class AnalyzeSingleStockInteractor implements AnalyzeSingleStockInputBoundary {

    private final RiskFreeRateDataAccessInterface riskFreeGateway;
    private final AnalyzeSingleStockOutputBoundary outputBoundary;

    public AnalyzeSingleStockInteractor(RiskFreeRateDataAccessInterface riskFreeGateway,
                                        AnalyzeSingleStockOutputBoundary outputBoundary) {
        this.riskFreeGateway = riskFreeGateway;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(AnalyzeSingleStockInputData inputData) {
        String ticker = inputData.getTicker();
        double rf = inputData.getRiskFreeAnnual();

        if (rf <= 0) {
            rf = riskFreeGateway.getCurrentRiskFreeRate();
        }

        String report = "Analysis for " + ticker + "\n"
                + String.format("Risk-free (annual): %.2f%%%n", rf * 100)
                + "(Real stats from API will go here later.already did the fred risk free for now)";

        AnalyzeSingleStockOutputData output =
                new AnalyzeSingleStockOutputData(ticker, rf, report);

        outputBoundary.present(output);
    }
}
