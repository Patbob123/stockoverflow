package view;

import interface_adapter.portfolio.PortfolioMenuController;
import interface_adapter.portfolio.PortfolioMenuState;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import interface_adapter.change_view.ChangeViewController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimulationView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "SimulationView";

    private final PortfolioMenuViewModel portfolioMenuViewModel;
    
    private PortfolioMenuController portfolioMenuController;
    private ChangeViewController changeViewController;

    private final JButton historicalAnalysisButton = new JButton("Historical Analysis");
    private final JButton plottingButton = new JButton("Plotting");
    private final JButton backButton = new JButton("Back");
    private final JButton selectAllButton = new JButton("Select All");
    private final JButton clearSelectionButton = new JButton("Clear Selection");

    private final JPanel checkBoxPanel = new JPanel();
    private final Map<JCheckBox, String> checkBoxTranslator = new HashMap<>();

    public SimulationView(PortfolioMenuViewModel portfolioMenuViewModel) {
        this.portfolioMenuViewModel = portfolioMenuViewModel;
        this.portfolioMenuViewModel.addPropertyChangeListener(this);
        this.portfolioMenuController = null;

        final JPanel buttons = new JPanel();
        buttons.add(historicalAnalysisButton);
        buttons.add(plottingButton);
        buttons.add(selectAllButton);
        buttons.add(clearSelectionButton);
        buttons.add(backButton);

        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        // Plotting (Graph)
        plottingButton.addActionListener(evt -> {
            if (portfolioMenuController != null) {
                java.util.List<String> selectedTickers = new java.util.ArrayList<>();
                for (JCheckBox cb : checkBoxTranslator.keySet()) {
                    if (cb.isSelected()) {
                        selectedTickers.add(checkBoxTranslator.get(cb));
                    }
                }
                portfolioMenuController.executeGraph(selectedTickers);
            }
        });

        // Historical Analysis
        historicalAnalysisButton.addActionListener(evt -> {
            if (portfolioMenuController != null) {
                portfolioMenuController.executeAnalysis(30); // Analyze last 30 days
            }
        });

        // Select All
        selectAllButton.addActionListener(evt -> {
             checkBoxConfigure(true);
        });

        // Clear Selection
        clearSelectionButton.addActionListener(evt -> {
             checkBoxConfigure(false);
        });

        // Back Button
        backButton.addActionListener(evt -> {
            if (changeViewController != null) {
                changeViewController.changeView("MainMenu");
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(new JScrollPane(checkBoxPanel));
        this.add(buttons);
    }

    public void setPortfolioMenuController(PortfolioMenuController portfolioMenuController) {
        this.portfolioMenuController = portfolioMenuController;
    }

    public void setChangeViewController(ChangeViewController changeViewController) {
        this.changeViewController = changeViewController;
    }

    public String getViewName() {
        return viewName;
    }

    private void checkBoxConfigure(Boolean bool){
        for(JCheckBox checkBox : checkBoxTranslator.keySet()) {
            checkBox.setSelected(bool);
        }
    }

    public void refreshCheckBoxPanel(){
        checkBoxPanel.removeAll();
        checkBoxTranslator.clear();

        PortfolioMenuState state = portfolioMenuViewModel.getState();
        if (state == null || state.getPortfolio() == null) return;

        Portfolio portfolio = state.getPortfolio();

        if (portfolio.getStocks() != null) {
            for (String ticker : portfolio.getStocks().keySet()) {
                JPanel tickerPanel = new JPanel();
                JCheckBox checkBox = new JCheckBox();
                if (portfolio.getStock(ticker) != null) {
                    JLabel label = new JLabel(portfolio.getStock(ticker).getName());
                    checkBoxTranslator.put(checkBox, ticker);
                    tickerPanel.add(label);
                    tickerPanel.add(checkBox);
                    checkBoxPanel.add(tickerPanel);
                }
            }
        }
        checkBoxPanel.revalidate();
        checkBoxPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName()) || evt.getPropertyName() == null) {
            refreshCheckBoxPanel();
        }
        if ("graph".equals(evt.getPropertyName())) {
            PortfolioMenuState state = (PortfolioMenuState) evt.getNewValue();
            new StockGraphPanel(state.getStocksToGraph());
        }
        if ("analysis".equals(evt.getPropertyName())) {
            PortfolioMenuState state = (PortfolioMenuState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, state.getAnalysisResult(), "Analysis Result", JOptionPane.INFORMATION_MESSAGE);
        }
        if ("error".equals(evt.getPropertyName())) {
            PortfolioMenuState state = (PortfolioMenuState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
