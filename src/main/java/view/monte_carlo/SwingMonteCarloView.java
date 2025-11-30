package view.monte_carlo;

import interface_adapter.monte_carlo.MonteCarloViewModel;
import view.monte_carlo.MonteCarloChartView; // Assuming MonteCarloChartView is in the view package or imported correctly

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;

/**
 * Concrete implementation of the MonteCarloView interface using Swing/JFreeChart.
 * This class now consumes the MonteCarloViewModel to render all components in a single call.
 */
public class SwingMonteCarloView implements MonteCarloView {

    // You can keep the JFrame if you plan to build a permanent application window.
    // For this example, we will focus on displaying results in a self-contained dialog/frame.

    public SwingMonteCarloView() {
        // Initialization can remain simple or be expanded to build the full application frame
    }

    /**
     * Implements the new success method from the updated MonteCarloView interface.
     */
    @Override
    public void showSuccessView(MonteCarloViewModel viewModel) {
        // 1. Create a frame/dialog to display both metrics and the chart
        JFrame resultsFrame = new JFrame(viewModel.getChartTitle());
        resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultsFrame.setLayout(new BorderLayout());

        // 2. Build the Metrics Panel using data from the ViewModel
        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new BoxLayout(metricsPanel, BoxLayout.Y_AXIS));

        metricsPanel.add(new JLabel("Ticker: " + viewModel.getChartTitle().replace("Monte Carlo Simulation Paths for ", "")));
        metricsPanel.add(new JLabel(viewModel.getInitialPrice()));
        metricsPanel.add(new JLabel(viewModel.getMeanTerminalPrice()));

        // 3. Render the Chart using the paths and title from the ViewModel
        // We use the MonteCarloChartView utility class you provided previously.
        // This method assumes the static/instance method signature in MonteCarloChartView is compatible.
        JFreeChart chart = MonteCarloChartView.buildPathsChart(
                viewModel.getSimulationPaths(),
                viewModel.getPathsToShow(),
                viewModel.getChartTitle()
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 500));

        // 4. Assemble and show the results
        resultsFrame.add(metricsPanel, BorderLayout.NORTH);
        resultsFrame.add(chartPanel, BorderLayout.CENTER);

        resultsFrame.pack();
        resultsFrame.setLocationRelativeTo(null);
        resultsFrame.setVisible(true);
    }

    /**
     * Implements the error method from the MonteCarloView interface.
     */
    @Override
    public void showErrorMessage(String message) {
        // Remains simple, as errors usually don't need complex formatting
        JOptionPane.showMessageDialog(null, message, "Analysis Error", JOptionPane.ERROR_MESSAGE);
    }

    // The previous displayMetrics and showPaths methods are removed/replaced
}