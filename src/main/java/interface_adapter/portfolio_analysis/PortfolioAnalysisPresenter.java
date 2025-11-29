package interface_adapter.portfolio_analysis;

import interface_adapter.ViewManagerModel;
import use_case.portfolio_analysis.PortfolioAnalysisOutputBoundary;
import use_case.portfolio_analysis.PortfolioAnalysisOutputData;

import java.util.Map;

public class PortfolioAnalysisPresenter implements PortfolioAnalysisOutputBoundary {
    private final PortfolioAnalysisViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public PortfolioAnalysisPresenter(PortfolioAnalysisViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(PortfolioAnalysisOutputData data) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><h3>Analysis Results (Approx. 100 Days)</h3>");
        sb.append("<table border='1' cellspacing='0' cellpadding='5'>");

        sb.append(String.format("<tr><td><b>Total Portfolio Return</b></td><td>%.2f%%</td></tr>", data.getTotalReturn() * 100));
        sb.append(String.format("<tr><td><b>Annualized Volatility (Risk)</b></td><td>%.2f%%</td></tr>", data.getVolatility() * 100));
        sb.append(String.format("<tr><td><b>Sharpe Ratio</b></td><td>%.4f</td></tr>", data.getSharpeRatio()));
        sb.append(String.format("<tr><td><b>Best Performer</b></td><td>%s</td></tr>", data.getBestStock()));
        sb.append(String.format("<tr><td><b>Worst Performer</b></td><td>%s</td></tr>", data.getWorstStock()));
        sb.append("</table>");

        sb.append("<h4>Individual Stock Returns:</h4><ul>");
        for (Map.Entry<String, Double> entry : data.getIndividualReturns().entrySet()) {
            String color = entry.getValue() >= 0 ? "green" : "red";
            sb.append(String.format("<li>%s: <span style='color:%s'>%.2f%%</span></li>",
                    entry.getKey(), color, entry.getValue() * 100));
        }
        sb.append("</ul></body></html>");

        PortfolioAnalysisState state = viewModel.getState();
        state.setAnalysisResult(sb.toString());
        state.setError(null);

        viewModel.setState(state);
        viewModel.firePropertyChanged();

        viewManagerModel.setActiveView(viewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        // Handle error (maybe show popup in current view)
        // For now, simpler to just set error state
        PortfolioAnalysisState state = viewModel.getState();
        state.setError(error);
        viewModel.firePropertyChanged();
    }
}