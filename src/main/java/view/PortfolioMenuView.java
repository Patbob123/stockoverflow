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
    private final Map<String, Comparator> portfolioSort = new HashMap<>();

    private final JButton addButton = new JButton(PortfolioMenuViewModel.ADD_BUTTON_LABEL);
    private final JButton removeButton = new JButton(PortfolioMenuViewModel.REMOVE_BUTTON_LABEL);
    private final JButton simulationButton = new JButton(PortfolioMenuViewModel.SIMULATION_BUTTON_LABEL);
    private final JButton selectAllButton = new JButton(PortfolioMenuViewModel.SELECT_ALL_BUTTON_LABEL);
    private final JButton clearSelectionButton = new JButton(PortfolioMenuViewModel.CLEAR_SELECTION_BUTTON_LABEL);
    private final JButton savePortfolioButton =  new JButton(PortfolioMenuViewModel.SAVE_PORTFOLIO_BUTTON_LABEL);
    private final JButton exitButton = new JButton(PortfolioMenuViewModel.EXIT_BUTTON_LABEL);
    @Getter
    private final JPanel checkBoxPanel = new JPanel();
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

        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        JPanel stocksPanel = new JPanel();
        JPanel sortbyPanel = new JPanel();
        JLabel sortbyLabel = new JLabel("Sort by:");

        JComboBox<String> sortbyComboBox = new JComboBox<>(sortmethod);

        sortbyPanel.add(sortbyLabel);
        sortbyPanel.add(sortbyComboBox);

        stocksPanel.setLayout(new BoxLayout(stocksPanel, BoxLayout.Y_AXIS));
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
                                this.getViewModel().getState().getPortfolio().removeStock(checkBoxTranslator.get(checkBox));
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
                evt ->{
                    if(evt.getSource().equals(sortbyComboBox)){
                        String method = (String) sortbyComboBox.getSelectedItem();
                        Portfolio portfolio = this.getViewModel().getState().getPortfolio();
                        // portfolio.sortStockBy();
                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //this.add(noteName);
        this.add(scrollPane);
        this.add(buttons);
    }

    private void checkBoxConfigure(Boolean bool){
        for(JCheckBox checkBox : checkBoxTranslator.keySet()) {
            checkBox.setSelected(bool);
        }
    }

    public void refreshCheckBoxPanel(Portfolio portfolio){
        buttonMap.clear();
        checkBoxTranslator.clear();
        jPanelMap.clear();

        for (String ticker : portfolio.getVisualStocks()) {
            JPanel tickerPanel = new JPanel();
            JCheckBox checkBox = new JCheckBox();
            JButton button = new JButton(portfolio.getStock(ticker).getName());
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Portfolio portfolio = this.getViewModel().getState().getPortfolio();
        this.getController().getPortfolioMenuInputBoundary().executeUpdatePortfolio(portfolio);
        this.refreshCheckBoxPanel(portfolio);
    }
}
