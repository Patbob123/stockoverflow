package view;

import entities.Portfolio.Portfolio;
import interface_adapter.portfolio.PortfolioMenuController;
import interface_adapter.portfolio.PortfolioMenuState;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "PortfolioMenu";

    private final PortfolioMenuViewModel portfolioMenuViewModel;
    private PortfolioMenuController portfolioMenuController; // Not final, set via setter

    private final JButton addButton = new JButton(PortfolioMenuViewModel.ADD_BUTTON_LABEL);
    private final JButton removeButton = new JButton(PortfolioMenuViewModel.REMOVE_BUTTON_LABEL);
    private final JButton simulationButton = new JButton(PortfolioMenuViewModel.SIMULATION_BUTTON_LABEL);
    private final JButton selectAllButton = new JButton(PortfolioMenuViewModel.SELECT_ALL_BUTTON_LABEL);
    private final JButton clearSelectionButton = new JButton(PortfolioMenuViewModel.CLEAR_SELECTION_BUTTON_LABEL);
    private final JButton savePortfolioButton =  new JButton(PortfolioMenuViewModel.SAVE_PORTFOLIO_BUTTON_LABEL);
    private final JButton exitButton = new JButton(PortfolioMenuViewModel.EXIT_BUTTON_LABEL);

    // New Analyze Button
    private final JButton analyzeButton = new JButton(PortfolioMenuViewModel.ANALYZE_BUTTON_LABEL);

    @Getter
    private final JPanel checkBoxPanel = new JPanel();
    private final Map<String, JButton> buttonMap = new HashMap<String, JButton>();
    private final Map<JCheckBox, String> checkBoxTranslator = new HashMap<JCheckBox, String>();
    private final Map<JCheckBox, JPanel> jPanelMap = new HashMap<JCheckBox, JPanel>();

    // Flag to check if market data has been loaded
    private boolean marketDataLoaded = false;

    public PortfolioMenuView(PortfolioMenuViewModel portfolioMenuViewModel) {
        this.portfolioMenuViewModel = portfolioMenuViewModel;
        this.portfolioMenuViewModel.addPropertyChangeListener(this);
        // Controller set later

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS)); // Vertical alignment for buttons

        // Arrange buttons
        buttons.add(addButton);
        buttons.add(removeButton);
        buttons.add(simulationButton);
        buttons.add(selectAllButton);
        buttons.add(clearSelectionButton);
        buttons.add(savePortfolioButton);
        buttons.add(analyzeButton); // Add analyze button
        buttons.add(exitButton);

        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        // existing listeners...
        addButton.addActionListener(evt -> {
            if (evt.getSource().equals(addButton) && portfolioMenuController != null) {
                portfolioMenuController.getPortfolioMenuInputBoundary().executeAddStock();
            }
        });

        // Simplified remove for now, assumes portfolio is managed in state
        removeButton.addActionListener(evt -> {
            // Logic to remove
        });

        simulationButton.addActionListener(evt -> {
             // Logic
        });

        selectAllButton.addActionListener(evt -> checkBoxConfigure(true));

        clearSelectionButton.addActionListener(evt -> checkBoxConfigure(false));

        savePortfolioButton.addActionListener(evt -> {
            // Logic
        });

        analyzeButton.addActionListener(evt -> {
            if (portfolioMenuController != null) {
                List<String> selectedTickers = new ArrayList<>();
                for (Map.Entry<JCheckBox, String> entry : checkBoxTranslator.entrySet()) {
                    if (entry.getKey().isSelected()) {
                        selectedTickers.add(entry.getValue());
                    }
                }
                portfolioMenuController.executeAnalyze(selectedTickers);
            }
        });

        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(checkBoxPanel), BorderLayout.CENTER);
        this.add(buttons, BorderLayout.EAST);
    }

    public void setPortfolioMenuController(PortfolioMenuController controller) {
        this.portfolioMenuController = controller;
    }

    private void checkBoxConfigure(Boolean bool){
        for(JCheckBox checkBox : checkBoxTranslator.keySet()) {
            checkBox.setSelected(bool);
        }
    }

    // Method to trigger data loading when view becomes visible or initialized
    public void loadMarketData() {
        if (!marketDataLoaded && portfolioMenuController != null) {
            portfolioMenuController.executeLoadMarketData();
            marketDataLoaded = true;
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        // This is called when the panel is added to the parent
        // Use this to trigger load if controller is ready
        if (portfolioMenuController != null) {
            loadMarketData();
        }
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PortfolioMenuState state = (PortfolioMenuState) evt.getNewValue();

        // Handle Available Stocks Update
        if (state.getAvailableStocks() != null && !state.getAvailableStocks().isEmpty()) {
            refreshCheckBoxPanelWithTickers(state.getAvailableStocks());
            // Clear list in state to avoid re-rendering loop if not needed, or just track diff?
            // simpler: just refresh.
        }

        // Handle Analysis Results
        if (state.getAnalysisMessage() != null) {
            showAnalysisResults(state);
            state.setAnalysisMessage(null); // Reset to avoid showing again on unrelated updates
        }
    }

    private void refreshCheckBoxPanelWithTickers(List<String> tickers) {
        // Only refresh if different? For now, clear and rebuild.
        checkBoxPanel.removeAll();
        buttonMap.clear();
        checkBoxTranslator.clear();
        jPanelMap.clear();

        for (String ticker : tickers) {
            JPanel tickerPanel = new JPanel();
            tickerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JCheckBox checkBox = new JCheckBox();
            JLabel label = new JLabel(ticker);

            checkBoxTranslator.put(checkBox, ticker);

            tickerPanel.add(checkBox);
            tickerPanel.add(label);

            checkBoxPanel.add(tickerPanel);
            jPanelMap.put(checkBox, tickerPanel);
        }
        checkBoxPanel.revalidate();
        checkBoxPanel.repaint();
    }

    private void showAnalysisResults(PortfolioMenuState state) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Analysis Results", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(800, 600);

        // Stats Panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        if (state.getAnalysisStats() != null) {
            for (Map.Entry<String, Double> entry : state.getAnalysisStats().entrySet()) {
                statsPanel.add(new JLabel(entry.getKey() + ": " + String.format("%.4f", entry.getValue())));
            }
        }

        // Chart Panel
        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new BorderLayout());
        if (state.getAnalysisPlotData() != null) {
            org.jfree.chart.JFreeChart chart = createChart(state.getAnalysisPlotData());
            org.jfree.chart.ChartPanel panel = new org.jfree.chart.ChartPanel(chart);
            chartPanel.add(panel, BorderLayout.CENTER);
        }

        dialog.add(new JScrollPane(statsPanel), BorderLayout.WEST);
        dialog.add(chartPanel, BorderLayout.CENTER);

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private org.jfree.chart.JFreeChart createChart(double[][] paths) {
        org.jfree.data.xy.XYSeriesCollection dataset = new org.jfree.data.xy.XYSeriesCollection();

        // Need to associate paths with tickers.
        // The View doesn't know the order of paths vs tickers easily unless passed.
        // For now, label them "Stock 1", "Stock 2"... or we should have passed a Map<String, double[]>
        // I'll stick to simple labeling for this iteration.

        for (int i = 0; i < paths.length; i++) {
            org.jfree.data.xy.XYSeries s = new org.jfree.data.xy.XYSeries("Stock " + (i + 1));
            double[] path = paths[i];
            for (int t = 0; t < path.length; t++) s.add(t, path[t]);
            dataset.addSeries(s);
        }

        return org.jfree.chart.ChartFactory.createXYLineChart(
                "Normalized Price History",
                "Time Step (Days)",
                "Price",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                true, true, false
        );
    }
}
