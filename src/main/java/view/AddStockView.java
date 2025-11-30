package view;

import interface_adapter.add_stock.AddStockController;
import interface_adapter.add_stock.AddStockState;
import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.portfolio_analysis.PortfolioAnalysisController;
import interface_adapter.remove_stock.RemoveStockController;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.monte_carlo.MonteCarloController;
import interface_adapter.monte_carlo.MonteCarloState;
import interface_adapter.monte_carlo.MonteCarloViewModel;
import interface_adapter.ViewManagerModel;
import use_case.APIDataAccessInterface;
import use_case.UserDataAccessInterface;
import entities.User;
import entities.Portfolio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

/**
 * View for managing a specific portfolio.
 * Allows adding/removing stocks, viewing graphs, analyzing the portfolio, and running simulations.
 */
public class AddStockView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "add stock";

    private final AddStockViewModel viewModel;
    private final AddStockController addStockController;
    private final RemoveStockController removeStockController;
    private final ShowGraphController showGraphController;
    private final PortfolioAnalysisController analysisController;

    // Monte Carlo dependencies
    private final MonteCarloController monteCarloController;
    private final MonteCarloViewModel monteCarloViewModel;

    private final ViewManagerModel viewManagerModel;
    private final UserDataAccessInterface userDataAccess;
    private final APIDataAccessInterface apiDataAccess;

    // UI Components
    private final JList<String> currentHoldingsList = new JList<>();
    private final DefaultListModel<String> holdingsModel = new DefaultListModel<>();

    private final JTextField searchInputField = new JTextField(15);
    private final JButton searchButton = new JButton("Search");
    private final JList<String> searchResultList = new JList<>();
    private final DefaultListModel<String> searchModel = new DefaultListModel<>();

    private final JButton addButton = new JButton("Add Selected");
    private final JButton removeButton = new JButton("Remove Selected");

    private final JButton viewGraphButton = new JButton("View Graph");
    private final JButton analyzeButton = new JButton("Analyze Portfolio");
    private final JButton monteCarloButton = new JButton("Monte Carlo Sim");
    private final JButton backButton = new JButton("Back to Portfolios");

    public AddStockView(AddStockViewModel viewModel,
                        AddStockController addStockController,
                        RemoveStockController removeStockController,
                        ShowGraphController showGraphController,
                        PortfolioAnalysisController analysisController,
                        MonteCarloController monteCarloController, // New dependency
                        MonteCarloViewModel monteCarloViewModel,   // New dependency
                        ViewManagerModel viewManagerModel,
                        UserDataAccessInterface userDataAccess,
                        APIDataAccessInterface apiDataAccess) {

        this.viewModel = viewModel;
        this.addStockController = addStockController;
        this.removeStockController = removeStockController;
        this.showGraphController = showGraphController;
        this.analysisController = analysisController;
        this.monteCarloController = monteCarloController;
        this.monteCarloViewModel = monteCarloViewModel;
        this.viewManagerModel = viewManagerModel;
        this.userDataAccess = userDataAccess;
        this.apiDataAccess = apiDataAccess;

        // Register listeners for both ViewModels
        this.viewModel.addPropertyChangeListener(this);
        this.monteCarloViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout(10, 10));

        // --- Left Panel: Holdings ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("My Holdings"));
        currentHoldingsList.setModel(holdingsModel);
        leftPanel.add(new JScrollPane(currentHoldingsList), BorderLayout.CENTER);

        JPanel leftBottom = new JPanel();
        leftBottom.add(removeButton);
        leftPanel.add(leftBottom, BorderLayout.SOUTH);

        // --- Right Panel: Search ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Search & Add"));

        JPanel searchBox = new JPanel();
        searchBox.add(new JLabel("Ticker:"));
        searchBox.add(searchInputField);
        searchBox.add(searchButton);
        rightPanel.add(searchBox, BorderLayout.NORTH);

        searchResultList.setModel(searchModel);
        rightPanel.add(new JScrollPane(searchResultList), BorderLayout.CENTER);

        JPanel rightButtons = new JPanel();
        rightButtons.add(addButton);
        rightPanel.add(rightButtons, BorderLayout.SOUTH);

        // --- Bottom Panel: Analytics & Navigation ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(viewGraphButton);
        bottomPanel.add(analyzeButton);
        bottomPanel.add(monteCarloButton); // Added Monte Carlo button
        bottomPanel.add(backButton);

        // Split Pane configuration
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        this.add(splitPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---

        // 1. Fuzzy Search (using SwingWorker to keep UI responsive)
        searchButton.addActionListener(e -> {
            String query = searchInputField.getText().trim();
            if (!query.isEmpty()) {
                searchModel.clear();
                new SwingWorker<List<String>, Void>() {
                    @Override
                    protected List<String> doInBackground() {
                        return apiDataAccess.searchSymbols(query);
                    }
                    @Override
                    protected void done() {
                        try {
                            List<String> results = get();
                            if (results == null || results.isEmpty()) searchModel.addElement("No results found.");
                            else for (String s : results) searchModel.addElement(s);
                        } catch (Exception ex) { ex.printStackTrace(); }
                    }
                }.execute();
            }
        });

        // 2. Add Stock
        addButton.addActionListener(e -> {
            String selected = searchResultList.getSelectedValue();
            if (selected != null && !selected.equals("No results found.")) {
                String ticker = selected.split(" - ")[0]; // Parse "AAPL - Apple Inc."
                AddStockState state = viewModel.getState();
                addStockController.execute(state.getUsername(), state.getPortfolioName(), Collections.singletonList(ticker));
            } else {
                JOptionPane.showMessageDialog(this, "Please select a stock from search results.");
            }
        });

        // 3. Remove Stock
        removeButton.addActionListener(e -> {
            String selected = currentHoldingsList.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Remove " + selected + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION && removeStockController != null) {
                    AddStockState state = viewModel.getState();
                    removeStockController.execute(state.getUsername(), state.getPortfolioName(), selected);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a stock from holdings to remove.");
            }
        });

        // 4. View Graph
        viewGraphButton.addActionListener(e -> {
            String selected = getSelectedTicker();

            if (selected != null) {
                // Execute graph logic with context "add stock" so the Back button knows where to return
                showGraphController.execute(selected, "add stock");
            } else {
                JOptionPane.showMessageDialog(this, "Select a stock to view graph.");
            }
        });

        // 5. Analyze Portfolio
        analyzeButton.addActionListener(e -> {
            AddStockState state = viewModel.getState();
            String username = state.getUsername();
            String pName = state.getPortfolioName();
            if (username != null && pName != null) {
                analysisController.execute(username, pName);
            }
        });

        // 6. Monte Carlo Simulation
        monteCarloButton.addActionListener(e -> {
            String selected = getSelectedTicker();

            if (selected != null) {
                // Run simulation: Ticker, 1000 simulations, 252 days (1 year)
                // You could add input dialogs here to let the user choose parameters
                monteCarloController.execute(selected, 1000, 252);
            } else {
                JOptionPane.showMessageDialog(this, "Select a stock for simulation.");
            }
        });

        // 7. Back to Portfolio Menu
        backButton.addActionListener(e -> {
            viewManagerModel.setActiveView("create portfolio"); // Matches PortfolioMenuView viewName
            viewManagerModel.firePropertyChanged();
        });
    }

    /**
     * Helper to get the selected ticker from either list (Holdings or Search).
     */
    private String getSelectedTicker() {
        String selected = null;
        if (!currentHoldingsList.isSelectionEmpty()) {
            selected = currentHoldingsList.getSelectedValue();
        } else if (!searchResultList.isSelectionEmpty()) {
            String val = searchResultList.getSelectedValue();
            if (val != null && !val.equals("No results found.")) selected = val.split(" - ")[0];
        }
        return selected;
    }

    /**
     * Refreshes the holdings list from the User Data Access Object.
     */
    private void refreshHoldings() {
        holdingsModel.clear();
        AddStockState state = viewModel.getState();
        String username = state.getUsername();
        String pName = state.getPortfolioName();

        if (username != null && !username.isEmpty()) {
            User user = userDataAccess.get(username);
            if (user != null) {
                Portfolio p = user.getPortfolioList().getPortfolio(pName);
                if (p != null && p.getStocks() != null) {
                    for (String ticker : p.getStocks().keySet()) {
                        holdingsModel.addElement(ticker);
                    }
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Handle updates from AddStockViewModel
        if (evt.getSource() == viewModel) {
            AddStockState state = (AddStockState) evt.getNewValue();
            if (state.getMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getMessage());
                state.setMessage(null);
            }
            refreshHoldings();
            searchModel.clear();
            searchInputField.setText("");
        }
        // Handle updates from MonteCarloViewModel
        else if (evt.getSource() == monteCarloViewModel) {
            MonteCarloState mcState = (MonteCarloState) evt.getNewValue();

            if (mcState.getError() != null) {
                JOptionPane.showMessageDialog(this, mcState.getError());
            } else if (mcState.getSimulationPaths() != null) {
                // Show the chart in a popup window using the static utility class
                MonteCarloChartView.showPaths(
                        mcState.getSimulationPaths(),
                        50, // Show first 50 paths to avoid clutter
                        "Monte Carlo Simulation: " + mcState.getTicker()
                );
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Required by ActionListener interface
    }
}