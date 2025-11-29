package view;

import interface_adapter.add_stock.AddStockController;
import interface_adapter.add_stock.AddStockState;
import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.portfolio_analysis.PortfolioAnalysisController; // 新增导入
import interface_adapter.remove_stock.RemoveStockController;
import interface_adapter.show_graph.ShowGraphController;
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

public class AddStockView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "add stock";

    private final AddStockViewModel viewModel;
    private final AddStockController addStockController;
    private final RemoveStockController removeStockController;
    private final ShowGraphController showGraphController;
    private final PortfolioAnalysisController analysisController; // 新增：分析控制器
    private final ViewManagerModel viewManagerModel;

    private final UserDataAccessInterface userDataAccess;
    private final APIDataAccessInterface apiDataAccess;

    // Components
    private final JList<String> currentHoldingsList = new JList<>();
    private final DefaultListModel<String> holdingsModel = new DefaultListModel<>();

    private final JTextField searchInputField = new JTextField(15);
    private final JButton searchButton = new JButton("Search");
    private final JList<String> searchResultList = new JList<>();
    private final DefaultListModel<String> searchModel = new DefaultListModel<>();

    private final JButton addButton = new JButton("Add Selected");
    private final JButton removeButton = new JButton("Remove Selected");

    private final JButton viewGraphButton = new JButton("View Graph");
    private final JButton analyzeButton = new JButton("Analyze Portfolio"); // 新增按钮
    private final JButton backButton = new JButton("Back to Portfolios");

    public AddStockView(AddStockViewModel viewModel,
                        AddStockController addStockController,
                        RemoveStockController removeStockController,
                        ShowGraphController showGraphController,
                        PortfolioAnalysisController analysisController, // 新增参数
                        ViewManagerModel viewManagerModel,
                        UserDataAccessInterface userDataAccess,
                        APIDataAccessInterface apiDataAccess) {

        this.viewModel = viewModel;
        this.addStockController = addStockController;
        this.removeStockController = removeStockController;
        this.showGraphController = showGraphController;
        this.analysisController = analysisController;
        this.viewManagerModel = viewManagerModel;
        this.userDataAccess = userDataAccess;
        this.apiDataAccess = apiDataAccess;

        this.viewModel.addPropertyChangeListener(this);
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

        // --- Bottom Panel ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(viewGraphButton);
        bottomPanel.add(analyzeButton); // 添加分析按钮
        bottomPanel.add(backButton);

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        this.add(splitPane, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // --- Listeners ---

        // 1. Fuzzy Search
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
                            if (results.isEmpty()) searchModel.addElement("No results found.");
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
                String ticker = selected.split(" - ")[0];
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
                if (confirm == JOptionPane.YES_OPTION) {
                    AddStockState state = viewModel.getState();
                    removeStockController.execute(state.getUsername(), state.getPortfolioName(), selected);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a stock from holdings to remove.");
            }
        });

        // 4. View Graph
        viewGraphButton.addActionListener(e -> {
            String selected = null;
            if (!currentHoldingsList.isSelectionEmpty()) {
                selected = currentHoldingsList.getSelectedValue();
            } else if (!searchResultList.isSelectionEmpty()) {
                String val = searchResultList.getSelectedValue();
                if (val != null && !val.equals("No results found.")) selected = val.split(" - ")[0];
            }

            if (selected != null) {
                showGraphController.execute(Collections.singletonList(selected), "add stock");
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

        // 6. Back
        backButton.addActionListener(e -> {
            viewManagerModel.setActiveView("create portfolio");
            viewManagerModel.firePropertyChanged();
        });
    }

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
        AddStockState state = (AddStockState) evt.getNewValue();
        if (state.getMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getMessage());
            state.setMessage(null);
        }
        refreshHoldings();
        searchModel.clear();
        searchInputField.setText("");
        ((JComponent)this.getComponent(0)).setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}