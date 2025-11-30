package interface_adapter.portfolio_analysis;

import interface_adapter.ViewManagerModel;
import use_case.portfolio_analysis.PortfolioAnalysisOutputBoundary;
import use_case.portfolio_analysis.PortfolioAnalysisOutputData;

import java.util.Map;

public class PortfolioAnalysisPresenter implements PortfolioAnalysisOutputBoundary {
    private final PortfolioAnalysisViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public PortfolioAnalysisPresenter(ViewManagerModel viewManagerModel, PortfolioAnalysisViewModel viewModel) {
        this.viewManagerModel = viewManagerModel;
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(PortfolioAnalysisOutputData data) {
        StringBuilder sb = new StringBuilder();

        // Header
        sb.append("=== PORTFOLIO ANALYSIS REPORT ===\n");
        sb.append("Data Points: ").append(data.getDaysAnalyzed()).append(" trading days\n");
        sb.append("---------------------------------\n\n");

        // Key Metrics
        sb.append(String.format("Total Return:        %10.2f%%\n", data.getTotalReturn() * 100));
        sb.append(String.format("Annualized Volatility: %8.2f%%\n", data.getVolatility() * 100));
        sb.append(String.format("Sharpe Ratio:        %10.4f\n", data.getSharpeRatio()));
        sb.append("\n");

        // Highlights
        sb.append("---------------------------------\n");
        sb.append(String.format("Best Performer:  %s\n", data.getBestStock()));
        sb.append(String.format("Worst Performer: %s\n", data.getWorstStock()));
        sb.append("---------------------------------\n\n");

        // Individual Details
        sb.append("Individual Stock Returns:\n");
        for (Map.Entry<String, Double> entry : data.getIndividualReturns().entrySet()) {
            sb.append(String.format("  %-6s : %6.2f%%\n", entry.getKey(), entry.getValue() * 100));
        }

        // Update State
        PortfolioAnalysisState state = viewModel.getState();
        state.setAnalysisResult(sb.toString());
        state.setError(null);

        viewModel.setState(state);
        viewModel.firePropertyChanged();

        // Ensure view is active (optional if already on the view)
        viewManagerModel.setActiveView(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        PortfolioAnalysisState state = viewModel.getState();
        state.setError(error);
        viewModel.firePropertyChanged();
    }
}