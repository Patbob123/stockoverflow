package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import entities.Portfolio.Portfolio;
import interface_adapter.ViewModel;
import interface_adapter.portfolio.PortfolioMenuController;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.portfolio.addStock.AddStockMenuState;
import lombok.Getter;
import lombok.Setter;

public class PortfolioMenuView extends PaddedView<PortfolioMenuViewModel, PortfolioMenuController> implements ActionListener, PropertyChangeListener {

    @Setter
    private ChangeViewController changeViewController;

    public static final String VIEW_NAME = "PortfolioMenu";

    private final String[] sortmethod = {"by name", "by price", "by amount of stock"};
    @Getter
    private final Map<String, Comparator<String>> portfolioSort = new HashMap<>();

    private final JButton addButton = new JButton(PortfolioMenuViewModel.ADD_BUTTON_LABEL);
    private final JButton removeButton = new JButton(PortfolioMenuViewModel.REMOVE_BUTTON_LABEL);
    private final JButton simulationButton = new JButton(PortfolioMenuViewModel.SIMULATION_BUTTON_LABEL);
    private final JButton selectAllButton = new JButton(PortfolioMenuViewModel.SELECT_ALL_BUTTON_LABEL);
    private final JButton clearSelectionButton = new JButton(PortfolioMenuViewModel.CLEAR_SELECTION_BUTTON_LABEL);
    private final JButton savePortfolioButton = new JButton(PortfolioMenuViewModel.SAVE_PORTFOLIO_BUTTON_LABEL);
    private final JButton exitButton = new JButton(PortfolioMenuViewModel.EXIT_BUTTON_LABEL);
    private final JButton changeNameButton = new JButton(PortfolioMenuViewModel.CHANGE_NAME_LABEL);
    @Getter
    private final JPanel checkBoxPanel = new JPanel();
    private final JLabel portfolioName = new JLabel("");

    private final JTextField nameField = new JTextField(15);
    private final Map<String, JButton> buttonMap = new HashMap<String, JButton>();
    private final Map<JCheckBox, String> checkBoxTranslator = new HashMap<JCheckBox, String>();
    private final Map<JCheckBox, JPanel> jPanelMap = new HashMap<JCheckBox, JPanel>();

    public PortfolioMenuView(PortfolioMenuViewModel viewModel) {
        super(viewModel);
        //noteName.setAlignmentX(Component.CENTER_ALIGNMENT); ADD DATE HERE TO

        this.getViewModel().addPropertyChangeListener(this);

        final JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.add(addButton);
        buttons.add(removeButton);
        buttons.add(simulationButton);
        buttons.add(selectAllButton);
        buttons.add(clearSelectionButton);
        buttons.add(savePortfolioButton);
        buttons.add(exitButton);

        final JPanel changeNameBox = new JPanel();

        changeNameBox.add(nameField);
        changeNameBox.add(changeNameButton);

        buttons.add(changeNameBox);

        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        final JPanel portfolioStockBox = new JPanel();
        portfolioStockBox.add(portfolioName);
        final JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        final JPanel stocksPanel = new JPanel();
        final JPanel sortbyPanel = new JPanel();
        final JLabel sortbyLabel = new JLabel("Sort by:");

        final JComboBox<String> sortbyComboBox = new JComboBox<>(sortmethod);

        stocksPanel.setLayout(new BoxLayout(stocksPanel, BoxLayout.Y_AXIS));
        sortbyPanel.add(sortbyLabel);
        sortbyPanel.add(sortbyComboBox);
        stocksPanel.add(scrollPane);
        stocksPanel.add(sortbyPanel);

        addButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(addButton)) {
                        //MainMenuController.execute(noteInputField.getText());
                        final String addStockViewName = AddStockMenuView.VIEW_NAME;
                        final ViewModel<AddStockMenuState> addStockMenuViewModel =
                                (ViewModel<AddStockMenuState>) changeViewController.getViewModel(
                                        addStockViewName
                                        );
                        this.getController().getPortfolioMenuInputBoundary().executeAddStock(addStockMenuViewModel);

                        changeViewController.changeView(addStockViewName);
                    }
                }
        );

        removeButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(removeButton)) {
                        for (JCheckBox checkBox : checkBoxTranslator.keySet()) {
                            if (checkBox.getModel().isSelected()) {
                                this.getViewModel().getState().getPortfolio()
                                        .removeStock(checkBoxTranslator.get(checkBox));
                                buttonMap.remove(checkBoxTranslator.get(checkBox));
                                checkBoxTranslator.remove(checkBox);
                                checkBoxPanel.remove(jPanelMap.get(checkBox));
                                jPanelMap.remove(checkBox);
                            }
                        }
                    }
                }
        );

        simulationButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(simulationButton)) {
                        //MainMenuController.execute(noteInputField.getText());

                    }
                }
        );

        selectAllButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(selectAllButton)) {
                        //MainMenuController.execute(noteInputField.getText());
                        checkBoxConfigure(true);
                    }
                }
        );

        clearSelectionButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(clearSelectionButton)) {
                        checkBoxConfigure(false);

                    }
                }
        );

        savePortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(savePortfolioButton)) {

                    }
                }
        );

        sortbyComboBox.addActionListener(
                evt -> {
                    if(evt.getSource().equals(sortbyComboBox)){
                        String method = (String) sortbyComboBox.getSelectedItem();
                        Portfolio portfolio = this.getViewModel().getState().getPortfolio();
                        // portfolio.sortStockBy();
                    }
                }
        );

        changeNameButton.addActionListener(
                evt -> {
                    if(evt.getSource().equals(changeNameButton)) {
                        final String name = nameField.getText();
                        final Portfolio portfolio = this.getViewModel().getState().getPortfolio();
                        if (name.isEmpty()) {
                            System.out.println(portfolio.getName());
                        }
                        else {
                            portfolio.setName(name);
                            this.refresh();
                            System.out.println(portfolio.getName());
                        }
                        // portfolio.sortStockBy();
                    }
                }
        );

        exitButton.addActionListener(
                evt -> {
                    if(evt.getSource().equals(exitButton)) {
                        changeViewController.changeView(MainMenuView.VIEW_NAME);
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //this.add(noteName);
        portfolioStockBox.add(stocksPanel);
        this.add(portfolioStockBox);
        this.add(buttons);
    }

    private void checkBoxConfigure(Boolean bool) {
        for(JCheckBox checkBox : checkBoxTranslator.keySet()) {
            checkBox.setSelected(bool);
        }
    }

    public void refreshCheckBoxPanel(Portfolio portfolio) {
        buttonMap.clear();
        checkBoxTranslator.clear();
        jPanelMap.clear();
        nameField.setText("");
        portfolioName.setText(portfolio.getName());
        for (String ticker : portfolio.getVisualStocks()) {
            final JPanel tickerPanel = new JPanel();
            final JCheckBox checkBox = new JCheckBox();
            final JButton button = new JButton(portfolio.getStock(ticker).getTicker());
            button.addActionListener(
                    evt -> {
                        if (evt.getSource().equals(button)) {
                            //TODO: redirect to stock
                            changeViewController.changeView("");
                        }
                    }
            );
            buttonMap.put(ticker, button);
            checkBoxTranslator.put(checkBox, ticker);
            tickerPanel.add(button);
            tickerPanel.add(checkBox);
            checkBoxPanel.add(tickerPanel);
            jPanelMap.put(checkBox, tickerPanel);
        }
    }

    public void refresh() {
        final Portfolio portfolio = this.getViewModel().getState().getPortfolio();
        this.getController().getPortfolioMenuInputBoundary().executeUpdatePortfolio(portfolio);
        this.refreshCheckBoxPanel(portfolio);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        refresh();
    }
}
