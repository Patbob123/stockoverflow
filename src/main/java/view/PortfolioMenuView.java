package view;

import entities.Portfolio.Portfolio;
import interface_adapter.portfolio.PortfolioMenuController;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class PortfolioMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "PortfolioMenu";

    private final PortfolioMenuViewModel portfolioMenuViewModel;
    private final PortfolioMenuController portfolioMenuController;

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

    public PortfolioMenuView(PortfolioMenuViewModel portfolioMenuViewModel) {
        //noteName.setAlignmentX(Component.CENTER_ALIGNMENT); ADD DATE HERE TOO
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

        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        Portfolio portfolio = null;


        addButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(addButton)) {
                        //MainMenuController.execute(noteInputField.getText());
                        this.portfolioMenuController.getPortfolioMenuInputBoundary().executeAddStock();
                    }
                }
        );

        removeButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(removeButton)) {
                        for (JCheckBox checkBox : checkBoxTranslator.keySet()) {
                            if (checkBox.getModel().isSelected()) {
                                portfolio.removeStock(checkBoxTranslator.get(checkBox));
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

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //this.add(noteName);
        this.add(checkBoxPanel);
        this.add(buttons);
    }

    private void checkBoxConfigure(Boolean bool){
        for(JCheckBox checkBox : checkBoxTranslator.keySet()) {
            checkBox.setSelected(bool);
        }
    }

    public void refreshCheckBoxPanel(){
        buttonMap.clear();
        checkBoxTranslator.clear();
        jPanelMap.clear();
        Portfolio portfolio = portfolioMenuViewModel.getState().getPortfolio();

        for (String ticker : portfolio.getStocks().keySet()) {
            JPanel tickerPanel = new JPanel();
            JCheckBox checkBox = new JCheckBox();
            JButton button = new JButton(portfolio.getStock(ticker).getName());
            button.addActionListener(
                    evt ->{
                        //TODO: transit to stock
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

    public String getViewName() {
        return viewName;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
