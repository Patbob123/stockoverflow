package view.monte_carlo;


import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * Concrete implementation of the MonteCarloView interface using Swing/JFreeChart.
 * This class interacts directly with the UI framework components.
 */
public class SwingMonteCarloView implements MonteCarloView {

    private final JFrame mainFrame; // Example of a main UI frame where data might be displayed

    public SwingMonteCarloView() {
        // Initialize your main UI components here if necessary
        this.mainFrame = new JFrame("Monte Carlo Simulation Results");
        this.mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.mainFrame.setPreferredSize(new Dimension(1000, 700)); // Larger frame for chart + metrics
    }

    @Override
    public void displayMetrics(String initialPrice, String expectedTerminalPrice) {
        JPanel metricsPanel = new JPanel();
        metricsPanel.setLayout(new BoxLayout(metricsPanel, BoxLayout.Y_AXIS));

        metricsPanel.add(new JLabel("Initial Price: " + initialPrice));
        metricsPanel.add(new JLabel("Expected Terminal Price: " + expectedTerminalPrice));

        // This is a minimal example. In a real app, you'd add this to a main layout.
        JOptionPane.showMessageDialog(null, metricsPanel, "Simulation Metrics", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showPaths(double[][] paths, int nToShow, String title) {
        // Use the static utility method from your existing class to display the chart
        MonteCarloChartView.showPaths(paths, nToShow, title);
    }

    @Override
    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Analysis Error", JOptionPane.ERROR_MESSAGE);
    }
}