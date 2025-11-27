package view;

import entities.Portfolio.Portfolio;
import interface_adapter.portfolio.PortfolioMenuController;
import interface_adapter.portfolio.PortfolioMenuState;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PortfolioMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "PortfolioMenu";

    private final PortfolioMenuViewModel portfolioMenuViewModel;

    @Setter
    private PortfolioMenuController portfolioMenuController;

    private final JButton addButton = new JButton(PortfolioMenuViewModel.ADD_BUTTON_LABEL);
    private final JButton removeButton = new JButton(PortfolioMenuViewModel.REMOVE_BUTTON_LABEL);
    private final JButton simulationButton = new JButton(PortfolioMenuViewModel.SIMULATION_BUTTON_LABEL);
    private final JButton selectAllButton = new JButton(PortfolioMenuViewModel.SELECT_ALL_BUTTON_LABEL);
    private final JButton clearSelectionButton = new JButton(PortfolioMenuViewModel.CLEAR_SELECTION_BUTTON_LABEL);
    private final JButton savePortfolioButton =  new JButton(PortfolioMenuViewModel.SAVE_PORTFOLIO_BUTTON_LABEL);
    private final JButton exitButton = new JButton(PortfolioMenuViewModel.EXIT_BUTTON_LABEL);

    @Getter
    private final JPanel checkBoxPanel = new JPanel();
    private final Map<String, JButton> buttonMap = new HashMap<>();
    private final Map<JCheckBox, String> checkBoxTranslator = new HashMap<>();
    private final Map<JCheckBox, JPanel> jPanelMap = new HashMap<>();

    public PortfolioMenuView(PortfolioMenuViewModel portfolioMenuViewModel) {
        this.portfolioMenuViewModel = portfolioMenuViewModel;
        this.portfolioMenuViewModel.addPropertyChangeListener(this);
        this.portfolioMenuController = null;

        final JPanel buttons = new JPanel();
        buttons.add(addButton);
        buttons.add(removeButton);
        buttons.add(simulationButton);
        buttons.add(selectAllButton);
        buttons.add(clearSelectionButton);
        buttons.add(savePortfolioButton);
        buttons.add(exitButton);

        // User Story 9: Historical Analysis Button
        JButton analysisButton = new JButton("Historical Analysis");
        buttons.add(analysisButton);

        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        // Add Stock
        addButton.addActionListener(evt -> {
            if (evt.getSource().equals(addButton) && portfolioMenuController != null) {
                portfolioMenuController.getPortfolioMenuInputBoundary().executeAddStock();
            }
        });

        // Remove Stock
        removeButton.addActionListener(evt -> {
            if (evt.getSource().equals(removeButton) && portfolioMenuController != null) {
                ArrayList<String> stocksToRemove = new ArrayList<>();
                for (JCheckBox checkBox : checkBoxTranslator.keySet()) {
                    if (checkBox.isSelected()) {
                        stocksToRemove.add(checkBoxTranslator.get(checkBox));
                    }
                }
                if (!stocksToRemove.isEmpty()) {
                    portfolioMenuController.getPortfolioMenuInputBoundary().executeRemoveStock(stocksToRemove);
                }
            }
        });

        // User Story 5: Graph
        simulationButton.setText("Generate Graph");
        simulationButton.addActionListener(evt -> {
            if (evt.getSource().equals(simulationButton) && portfolioMenuController != null) {
                java.util.List<String> selectedTickers = new java.util.ArrayList<>();
                for (JCheckBox cb : checkBoxTranslator.keySet()) {
                    if (cb.isSelected()) {
                        selectedTickers.add(checkBoxTranslator.get(cb));
                    }
                }
                portfolioMenuController.executeGraph(selectedTickers);
            }
        });

        // Select All
        selectAllButton.addActionListener(evt -> {
            if (evt.getSource().equals(selectAllButton)) checkBoxConfigure(true);
        });

        // Clear Selection
        clearSelectionButton.addActionListener(evt -> {
            if (evt.getSource().equals(clearSelectionButton)) checkBoxConfigure(false);
        });

        // User Story 9: Analysis
        analysisButton.addActionListener(evt -> {
            if (portfolioMenuController != null) {
                portfolioMenuController.executeAnalysis(30); // Analyze last 30 days
            }
        });

        // Save
        savePortfolioButton.addActionListener(evt -> {
            if (evt.getSource().equals(savePortfolioButton) && portfolioMenuController != null) {
                portfolioMenuController.getPortfolioMenuInputBoundary().executeSavePortfolio();
            }
        });

        // Exit
        exitButton.addActionListener(evt -> {
            if (evt.getSource().equals(exitButton) && portfolioMenuController != null) {
                portfolioMenuController.getPortfolioMenuInputBoundary().executeExit();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(new JScrollPane(checkBoxPanel)); // Scrollable
        this.add(buttons);
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
        buttonMap.clear();
        checkBoxTranslator.clear();
        jPanelMap.clear();

        PortfolioMenuState state = portfolioMenuViewModel.getState();
        if (state == null || state.getPortfolio() == null) return;

        Portfolio portfolio = state.getPortfolio();

        if (portfolio.getStocks() != null) {
            for (String ticker : portfolio.getStocks().keySet()) {
                JPanel tickerPanel = new JPanel();
                JCheckBox checkBox = new JCheckBox();
                if (portfolio.getStock(ticker) != null) {
                    JButton button = new JButton(portfolio.getStock(ticker).getName());
                    buttonMap.put(ticker, button);
                    checkBoxTranslator.put(checkBox, ticker);
                    tickerPanel.add(button);
                    tickerPanel.add(checkBox);
                    checkBoxPanel.add(tickerPanel);
                    jPanelMap.put(checkBox, tickerPanel);
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
//
//public class PortfolioMenuView extends JFrame implements ActionListener {
//    private final JButton addStockButton = new JButton("ADD");
//    private final JButton removeStockButton = new JButton("REMOVE");
//    private final JButton simulateButton = new JButton("SIMULATE");
//    private final JButton compareButton = new JButton("COMPARE");
//    private final JButton saveButton = new JButton("SAVE");
//    private final JButton refreshButton = new JButton("Refresh");
//    private final JButton backButton = new JButton("Return");
//    private final JLabel messageLabel = new JLabel();
//    private final JTextArea portfolioDataArea = new JTextArea();
//
//    private String currentPortfolioName;
//    private RefreshDataController refreshDataController;
//
//    public PortfolioMenuView() {
//        super("Portfolio menu");
//        setupUI();
//        setupListeners();
//
//        setSize(800, 600);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//    }
//
//    private void setupUI() {
//        JPanel mainPanel = new JPanel(new BorderLayout());
//
//        //  Panel
//        JPanel buttonPanel = new JPanel(new GridLayout(1, 7, 5, 5));
//        buttonPanel.add(addStockButton);
//        buttonPanel.add(removeStockButton);
//        buttonPanel.add(simulateButton);
//        buttonPanel.add(compareButton);
//        buttonPanel.add(saveButton);
//        buttonPanel.add(refreshButton);
//        buttonPanel.add(backButton);
//
//        // Data area
//        portfolioDataArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(portfolioDataArea);
//
//        // message
//        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        messagePanel.add(messageLabel);
//
//        mainPanel.add(buttonPanel, BorderLayout.NORTH);
//        mainPanel.add(scrollPane, BorderLayout.CENTER);
//        mainPanel.add(messagePanel, BorderLayout.SOUTH);
//
//        add(mainPanel);
//    }
//
//    private void setupListeners() {
//        addStockButton.addActionListener(this);
//        removeStockButton.addActionListener(this);
//        simulateButton.addActionListener(this);
//        compareButton.addActionListener(this);
//        saveButton.addActionListener(this);
//        refreshButton.addActionListener(this);
//        backButton.addActionListener(this);
//    }
//
//    public void setCurrentPortfolio(Portfolio portfolio) {
//        this.currentPortfolioName = portfolio.getName();
//        updatePortfolioData();
//    }
//
//    public void setRefreshDataController(RefreshDataController controller) {
//        this.refreshDataController = controller;
//    }
//
//    public void showRefreshMessage(String message) {
//        messageLabel.setText(message);
//    }
//
//    public void updatePortfolioData() {
//
//
//        portfolioDataArea.setText(": " + currentPortfolioName + "\n");
//        portfolioDataArea.append("");
//        portfolioDataArea.append(": " + new java.util.Date() + "\n");
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getSource() == refreshButton) {
//            if (currentPortfolioName != null && refreshDataController != null) {
//                refreshDataController.execute(currentPortfolioName);
//            } else {
//                showRefreshMessage("");
//            }
//        }
//        //Event handling for other buttons
//    }
//}