package view;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioFactory;
import interface_adapter.ViewModel;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuViewModel;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainMenuView extends PaddedView<MainMenuViewModel, MainMenuController> implements ActionListener, PropertyChangeListener {

    @Setter
    private ChangeViewController changeViewController;

    private final JButton stockButton = new JButton(MainMenuViewModel.STOCK_BUTTON_LABEL);
    private final JButton analyzePortfolioButton = new JButton(MainMenuViewModel.PORTFOLIO_BUTTON_LABEL);
    private final JButton createPortfolioButton = new JButton(MainMenuViewModel.CREATE_PORTFOLIO_BUTTON_LABEL);
    private final JButton historyStockButton = new JButton(MainMenuViewModel.HISTORY_BUTTON_LABEL);
    private final JButton exitButton = new JButton(MainMenuViewModel.EXIT_BUTTON_LABEL);
    private final PortfolioFactory portfolioFactory = new PortfolioFactory();

    public static final String VIEW_NAME = "MainMenu";

    public MainMenuView(MainMenuViewModel viewModel) {
        super(viewModel);
        //noteName.setAlignmentX(Component.CENTER_ALIGNMENT); ADD DATE HERE TOO
        this.getViewModel().addPropertyChangeListener(this);
        this.changeViewController = null;

        final JPanel buttons = new JPanel();
        buttons.add(stockButton);
        buttons.add(analyzePortfolioButton);
        buttons.add(createPortfolioButton);
        buttons.add(historyStockButton);
        buttons.add(exitButton);

        stockButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(stockButton)) {
                        changeViewController.changeView("SingleStockMenu");

                    }
                }
        );

        analyzePortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(analyzePortfolioButton)) {
                        final Portfolio portfolio = portfolioFactory.createPortfolio("Untitled");
                        final PortfolioMenuViewModel portfolioViewModel =
                                (PortfolioMenuViewModel) changeViewController
                                        .getViewModel(PortfolioMenuView.VIEW_NAME);
                        portfolioViewModel.getState().setPortfolio(portfolio);
                        portfolioViewModel.firePropertyChange();
                        changeViewController.changeView("PortfolioMenu");
                    }
                }
        );

        createPortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(createPortfolioButton)) {
                        changeViewController.changeView("AddPortfolioMenu");

                    }
                }
        );

        historyStockButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(historyStockButton)) {
                        //MainMenuController.execute(noteInputField.getText());

                    }
                }
        );

        exitButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(exitButton)) {
                        this.getController().execute("exit"); //fix this later

                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this.add(noteName);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
