package interface_adapter.monte_carlo;

import interface_adapter.ViewModel;
import view.monte_carlo.MonteCarloView;

/**
 * The ViewModel (Presentation Model) that contains all data and state
 * required by the MonteCarloView to render the success screen.
 * This object is highly tailored for the UI and contains formatted strings
 * and data models ready for immediate display.
 */
public class MonteCarloViewModel extends ViewModel<MonteCarloState> {

    // 1. Data for the Chart (raw simulation results)
    private final double[][] simulationPaths;
    private final String chartTitle;

    // 2. Formatted Metrics (Strings ready for display)
    private final String initialPrice;
    private final String meanTerminalPrice;
    private final int pathsToShow; // View needs to know how many paths to render

    public MonteCarloViewModel(double[][] simulationPaths, String chartTitle,
                               String initialPrice, String meanTerminalPrice,
                               int pathsToShow) {
        super(MonteCarloView.VIEW_NAME);
        this.simulationPaths = simulationPaths;
        this.chartTitle = chartTitle;
        this.initialPrice = initialPrice;
        this.meanTerminalPrice = meanTerminalPrice;
        this.pathsToShow = pathsToShow;
    }

    // --- Getters ---

    public double[][] getSimulationPaths() { return simulationPaths; }
    public String getChartTitle() { return chartTitle; }
    public String getInitialPrice() { return initialPrice; }
    public String getMeanTerminalPrice() { return meanTerminalPrice; }
    public int getPathsToShow() { return pathsToShow; }
}