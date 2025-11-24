package view;

import interface_adapter.simulation.SimulationController;
import interface_adapter.simulation.SimulationState;
import interface_adapter.simulation.SimulationViewModel;
import interface_adapter.change_view.ChangeViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SimulationView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "simulation";

    private final SimulationViewModel simulationViewModel;
    private SimulationController simulationController;
    private ChangeViewController changeViewController;

    private final JTextField tickerInputField = new JTextField(10);
    private final JTextField numPathsField = new JTextField("100", 5);
    private final JTextField timeHorizonField = new JTextField("1.0", 5);

    private final JButton simulateButton = new JButton(SimulationViewModel.SIMULATE_BUTTON_LABEL);
    private final JButton backButton = new JButton(SimulationViewModel.BACK_BUTTON_LABEL);

    private final JPanel chartPanel = new JPanel();

    public SimulationView(SimulationViewModel simulationViewModel) {
        this.simulationViewModel = simulationViewModel;
        this.simulationViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        JLabel title = new JLabel(SimulationViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel(SimulationViewModel.TICKER_LABEL));
        inputPanel.add(tickerInputField);
        inputPanel.add(new JLabel("Paths:"));
        inputPanel.add(numPathsField);
        inputPanel.add(new JLabel("Horizon (Years):"));
        inputPanel.add(timeHorizonField);
        inputPanel.add(simulateButton);
        inputPanel.add(backButton);

        // Chart Display Area
        chartPanel.setLayout(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        chartPanel.add(new JLabel("Chart will appear here", SwingConstants.CENTER), BorderLayout.CENTER);

        this.add(title, BorderLayout.NORTH);
        this.add(inputPanel, BorderLayout.SOUTH);
        this.add(chartPanel, BorderLayout.CENTER);

        simulateButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (evt.getSource().equals(simulateButton)) {
                        String ticker = tickerInputField.getText();
                        int paths;
                        double horizon;
                        try {
                            paths = Integer.parseInt(numPathsField.getText());
                            horizon = Double.parseDouble(timeHorizonField.getText());

                            if (simulationController != null) {
                                simulationController.execute(ticker, paths, horizon);
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(SimulationView.this, "Invalid number format for paths or horizon.");
                        }
                    }
                }
            }
        );

        backButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (evt.getSource().equals(backButton)) {
                        if (changeViewController != null) {
                            changeViewController.changeView("MainMenu");
                        }
                    }
                }
            }
        );
    }

    public void setSimulationController(SimulationController controller) {
        this.simulationController = controller;
    }

    public void setChangeViewController(ChangeViewController controller) {
        this.changeViewController = controller;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle other actions if needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SimulationState state = (SimulationState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        } else if (state.getSimulationPaths() != null) {
            // Update Chart
            updateChart(state.getSimulationPaths());
        }
    }

    private void updateChart(double[][] paths) {
        // Use MonteCarloChartView logic to create JFreeChart
        // Since MonteCarloChartView methods are static and open a frame or return JFreeChart
        // I need to adapt it slightly to return a chart I can embed, OR just call its method to create one.
        // MonteCarloChartView.buildPathsChart is private...
        // But MonteCarloChartView.showPaths opens a new window.

        // I should probably modify MonteCarloChartView to be more reusable or duplicate the chart creation logic here.
        // Let's modify MonteCarloChartView to make buildPathsChart public or add a method that returns the panel.

        // For now, I will assume I can reflect or I should modify MonteCarloChartView.
        // I will modify MonteCarloChartView in the next step or right now.
        // It's better to modify it to be reusable.

        // Since I cannot modify it in this tool call easily without context switch,
        // I will just implement a simple chart builder here using the same code,
        // as copying 20 lines is safe.

        org.jfree.chart.JFreeChart chart = createChart(paths);
        org.jfree.chart.ChartPanel panel = new org.jfree.chart.ChartPanel(chart);
        chartPanel.removeAll();
        chartPanel.add(panel, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }

    private org.jfree.chart.JFreeChart createChart(double[][] paths) {
        org.jfree.data.xy.XYSeriesCollection dataset = new org.jfree.data.xy.XYSeriesCollection();
        int show = Math.min(100, paths.length); // limit lines for performance

        for (int i = 0; i < show; i++) {
            org.jfree.data.xy.XYSeries s = new org.jfree.data.xy.XYSeries("Path " + (i + 1));
            double[] path = paths[i];
            for (int t = 0; t < path.length; t++) s.add(t, path[t]);
            dataset.addSeries(s);
        }

        org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createXYLineChart(
                "Monte Carlo Simulation: " + tickerInputField.getText(),
                "Time Step (days)",
                "Price",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                false, true, false
        );
        return chart;
    }
}
