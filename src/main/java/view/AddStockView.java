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
import interface_adapter.singlestock.SingleStockViewModel;
import use_case.APIDataAccessInterface;
import use_case.UserDataAccessInterface;
import entities.User;
import entities.Portfolio;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private final PortfolioAnalysisController analysisController;
    private final MonteCarloController monteCarloController;
    private final MonteCarloViewModel monteCarloViewModel;
    private final ViewManagerModel viewManagerModel;
    private final UserDataAccessInterface userDataAccess;
    private final APIDataAccessInterface apiDataAccess;

    // UI Components
    private final JList<String> currentHoldingsList = new JList<>();
    private final DefaultListModel<String> holdingsModel = new DefaultListModel<>();

    private final JTextField searchInputField = new JTextField();
    private final JButton searchButton;
    private final JList<String> searchResultList = new JList<>();
    private final DefaultListModel<String> searchModel = new DefaultListModel<>();

    private final JButton addButton;
    private final JButton removeButton;
    private final JButton viewGraphButton;
    private final JButton analyzeButton;
    private final JButton monteCarloButton;
    private final JButton backButton;

    public AddStockView(AddStockViewModel viewModel,
                        AddStockController addStockController,
                        RemoveStockController removeStockController,
                        ShowGraphController showGraphController,
                        PortfolioAnalysisController analysisController,
                        MonteCarloController monteCarloController,
                        MonteCarloViewModel monteCarloViewModel,
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

        this.viewModel.addPropertyChangeListener(this);
        this.monteCarloViewModel.addPropertyChangeListener(this);


        this.setLayout(new BorderLayout());
        this.setBackground(SingleStockViewModel.BG_COLOUR);


        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 20));
        headerPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        JLabel titleLabel = new JLabel("Portfolio Management");
        titleLabel.setFont(SingleStockViewModel.TITLE_FONT);
        titleLabel.setForeground(SingleStockViewModel.SECONDARY_COLOUR);
        headerPanel.add(titleLabel);
        this.add(headerPanel, BorderLayout.NORTH);


        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        contentPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        contentPanel.setBorder(new EmptyBorder(0, 40, 20, 40));


        JPanel leftCard = createCardPanel();

        JLabel holdingsTitle = new JLabel("My Holdings");
        holdingsTitle.setFont(SingleStockViewModel.BASE_FONT.deriveFont(Font.BOLD, 18f));
        holdingsTitle.setForeground(SingleStockViewModel.SUCCESS_COLOUR); // 绿色标题
        holdingsTitle.setBorder(new EmptyBorder(0, 0, 15, 0));


        styleDarkList(currentHoldingsList, holdingsModel);
        JScrollPane holdingsScroll = new JScrollPane(currentHoldingsList);
        holdingsScroll.setBorder(BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR));

        removeButton = createStyledButton("Remove Selected", new Color(180, 60, 60)); // 红色按钮
        JPanel leftBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        leftBtnPanel.setBackground(SingleStockViewModel.CARD_COLOUR);
        leftBtnPanel.add(removeButton);

        leftCard.add(holdingsTitle, BorderLayout.NORTH);
        leftCard.add(holdingsScroll, BorderLayout.CENTER);
        leftCard.add(leftBtnPanel, BorderLayout.SOUTH);


        JPanel rightCard = createCardPanel();

        JLabel searchTitle = new JLabel("Add New Stock");
        searchTitle.setFont(SingleStockViewModel.BASE_FONT.deriveFont(Font.BOLD, 18f));
        searchTitle.setForeground(SingleStockViewModel.PRIMARY_COLOUR); // 橙色标题


        JPanel searchBox = new JPanel(new BorderLayout(10, 0));
        searchBox.setBackground(SingleStockViewModel.CARD_COLOUR);
        searchBox.setBorder(new EmptyBorder(0, 0, 15, 0));

        searchInputField.setFont(SingleStockViewModel.BASE_FONT.deriveFont(14f));
        searchInputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        searchButton = createStyledButton("Search", SingleStockViewModel.SECONDARY_COLOUR);
        searchButton.setPreferredSize(new Dimension(90, 35)); // 稍微调小一点搜索按钮

        searchBox.add(searchInputField, BorderLayout.CENTER);
        searchBox.add(searchButton, BorderLayout.EAST);

        JPanel rightHeader = new JPanel(new BorderLayout());
        rightHeader.setBackground(SingleStockViewModel.CARD_COLOUR);
        rightHeader.add(searchTitle, BorderLayout.NORTH);
        rightHeader.add(Box.createVerticalStrut(15), BorderLayout.CENTER); // 间距
        rightHeader.add(searchBox, BorderLayout.SOUTH);


        styleDarkList(searchResultList, searchModel);
        JScrollPane searchScroll = new JScrollPane(searchResultList);
        searchScroll.setBorder(BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR));

        addButton = createStyledButton("Add to Portfolio", SingleStockViewModel.PRIMARY_COLOUR);
        JPanel rightBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightBtnPanel.setBackground(SingleStockViewModel.CARD_COLOUR);
        rightBtnPanel.add(addButton);

        rightCard.add(rightHeader, BorderLayout.NORTH);
        rightCard.add(searchScroll, BorderLayout.CENTER);
        rightCard.add(rightBtnPanel, BorderLayout.SOUTH);


        contentPanel.add(leftCard);
        contentPanel.add(rightCard);
        this.add(contentPanel, BorderLayout.CENTER);


        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 20));
        bottomBar.setBackground(SingleStockViewModel.BG_COLOUR);
        bottomBar.setBorder(new EmptyBorder(0, 40, 20, 40));


        viewGraphButton = createStyledButton("View Graph", SingleStockViewModel.SECONDARY_COLOUR);
        analyzeButton = createStyledButton("Deep Analysis", SingleStockViewModel.SECONDARY_COLOUR);
        monteCarloButton = createStyledButton("Monte Carlo Sim", SingleStockViewModel.SECONDARY_COLOUR);
        backButton = createStyledButton("Back", Color.GRAY);

        bottomBar.add(viewGraphButton);
        bottomBar.add(analyzeButton);
        bottomBar.add(monteCarloButton);
        bottomBar.add(backButton);

        this.add(bottomBar, BorderLayout.SOUTH);

        setupListeners();
    }


    private JPanel createCardPanel() {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(SingleStockViewModel.CARD_COLOUR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));
        return card;
    }


    private void styleDarkList(JList<String> list, DefaultListModel<String> model) {
        list.setModel(model);
        // 比卡片背景稍亮一点的灰色，形成层次感
        list.setBackground(new Color(60, 64, 66));
        list.setForeground(Color.WHITE);
        list.setFont(SingleStockViewModel.BASE_FONT.deriveFont(14f));
        list.setSelectionBackground(SingleStockViewModel.PRIMARY_COLOUR);
        list.setSelectionForeground(Color.WHITE);
        list.setFixedCellHeight(30); // 增加行高，更易点击
    }


    private JButton createStyledButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(SingleStockViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(bg.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(bg); }
        });
        return button;
    }

    private void setupListeners() {
        // 1. Search
        searchButton.addActionListener(e -> {
            String query = searchInputField.getText().trim();
            if (!query.isEmpty()) {
                searchModel.clear();
                // 显示加载状态
                searchModel.addElement("Searching...");
                new SwingWorker<List<String>, Void>() {
                    @Override protected List<String> doInBackground() { return apiDataAccess.searchSymbols(query); }
                    @Override protected void done() {
                        try {
                            searchModel.clear(); // 清除 "Searching..."
                            List<String> results = get();
                            if (results == null || results.isEmpty()) searchModel.addElement("No results found.");
                            else for (String s : results) searchModel.addElement(s);
                        } catch (Exception ex) { ex.printStackTrace(); }
                    }
                }.execute();
            }
        });

        // 2. Add
        addButton.addActionListener(e -> {
            String selected = searchResultList.getSelectedValue();
            if (selected != null && !selected.equals("No results found.") && !selected.equals("Searching...")) {
                String ticker = selected.split(" - ")[0];
                AddStockState state = viewModel.getState();
                addStockController.execute(state.getUsername(), state.getPortfolioName(), Collections.singletonList(ticker));
            } else { JOptionPane.showMessageDialog(this, "Please select a valid stock."); }
        });

        // 3. Remove
        removeButton.addActionListener(e -> {
            String selected = currentHoldingsList.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Remove " + selected + "?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION && removeStockController != null) {
                    AddStockState state = viewModel.getState();
                    removeStockController.execute(state.getUsername(), state.getPortfolioName(), selected);
                }
            } else { JOptionPane.showMessageDialog(this, "Select a stock from holdings to remove."); }
        });

        // 4. View Graph
        viewGraphButton.addActionListener(e -> {
            String selected = getSelectedTicker();
            if (selected != null) showGraphController.execute(selected, "add stock");
            else JOptionPane.showMessageDialog(this, "Select a stock from either list.");
        });

        // 5. Analyze
        analyzeButton.addActionListener(e -> {
            AddStockState state = viewModel.getState();
            analysisController.execute(state.getUsername(), state.getPortfolioName());
        });

        // 6. Monte Carlo
        monteCarloButton.addActionListener(e -> {
            String selected = getSelectedTicker();
            if (selected != null) monteCarloController.execute(selected, 1000, 252);
            else JOptionPane.showMessageDialog(this, "Select a stock for simulation.");
        });

        // 7. Back
        backButton.addActionListener(e -> {
            viewManagerModel.setActiveView("create portfolio"); // 返回 Portfolio 列表
            viewManagerModel.firePropertyChanged();
        });
    }

    private String getSelectedTicker() {
        if (!currentHoldingsList.isSelectionEmpty()) return currentHoldingsList.getSelectedValue();
        else if (!searchResultList.isSelectionEmpty()) {
            String val = searchResultList.getSelectedValue();
            if (val != null && !val.equals("No results found.") && !val.equals("Searching..."))
                return val.split(" - ")[0];
        }
        return null;
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
                    for (String ticker : p.getStocks().keySet()) holdingsModel.addElement(ticker);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == viewModel) {
            AddStockState state = (AddStockState) evt.getNewValue();
            if (state.getMessage() != null) {
                JOptionPane.showMessageDialog(this, state.getMessage());
                state.setMessage(null);
            }
            refreshHoldings();
            searchModel.clear();
            searchInputField.setText("");
        } else if (evt.getSource() == monteCarloViewModel) {
            MonteCarloState mcState = (MonteCarloState) evt.getNewValue();
            if (mcState.getError() != null) {
                JOptionPane.showMessageDialog(this, mcState.getError());
            } else if (mcState.getSimulationPaths() != null) {
                MonteCarloChartView.showPaths(mcState.getSimulationPaths(), 50, "Monte Carlo: " + mcState.getTicker());
            }
        }
    }

    @Override public void actionPerformed(ActionEvent e) {}
}