package view;

import entities.Portfolio.Portfolio;
import entities.Stock;
import interface_adapter.portfolio.PortfolioMenuState;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import interface_adapter.portfolio.addStock.AddStockMenuController;
import interface_adapter.portfolio.addStock.AddStockMenuViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddStockMenuView extends PaddedView<AddStockMenuViewModel, AddStockMenuController> implements ActionListener, PropertyChangeListener {

    public static final String VIEW_NAME = "Add Stock";

    private final JPanel tickerPanel = new JPanel();
    private final JPanel tickerStringPanel = new JPanel();
    private final JPanel tickerAmountPanel = new JPanel();
    private final JTextField tickerField = new JTextField(15);
    private final JTextField tickerAmountField = new JTextField(15);
    private final JLabel tickerString = new JLabel(AddStockMenuViewModel.TICKER_NAME_LABEL);
    private final JLabel tickerAmount = new JLabel(AddStockMenuViewModel.TICKER_AMOUNT_LABEL);
    private final JButton addStockButton = new JButton(AddStockMenuViewModel.ADDSTOCK_BUTTON_LABEL);
    private final JButton backToPortfolio = new JButton(AddStockMenuViewModel.BACK_TO_PORTFOLIO);

    public AddStockMenuView(AddStockMenuViewModel viewModel) {
        super(viewModel);
        this.getViewModel().addPropertyChangeListener(this);
        tickerPanel.setLayout(new BoxLayout(tickerPanel, BoxLayout.Y_AXIS));

        tickerStringPanel.add(tickerString);
        tickerStringPanel.add(tickerField);

        tickerAmountPanel.add(tickerAmount);
        tickerAmountPanel.add(tickerAmountField);

        tickerPanel.add(tickerStringPanel);
        tickerPanel.add(tickerAmountPanel);
        tickerPanel.add(addStockButton);
        tickerPanel.add(backToPortfolio);

        addStockButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(addStockButton)) {
                        final Portfolio portfolio = this.getViewModel().getState().getPortfolio();
                        if (portfolio.getStocks().containsKey(tickerField.getText())) {
                            final int amount = Integer.parseInt(tickerAmountField.getText());
                            if (amount != 0) {
                                portfolio.addStock(portfolio.getStock(tickerField.getText()), amount);
                            }
                            else {
                                portfolio.removeStock(tickerField.getText());
                            }
                        }
                        else {
                            final Stock stock = ((PortfolioMenuState) getChangeViewController()
                                    .getViewModel(PortfolioMenuView.VIEW_NAME)
                                    .getState()).getStockPriceDataAccess()
                                    .getDailySeries(tickerField.getText().toUpperCase(), 400);

                            portfolio.addStock(stock, Integer.parseInt(tickerAmountField.getText()));
                        }
                        tickerField.setText("");
                        tickerAmountField.setText("");
                    }
                }
        );
        backToPortfolio.addActionListener(
                evt -> {
                    if (evt.getSource().equals(backToPortfolio)) {
                        final PortfolioMenuViewModel portfolioViewModel =
                                (PortfolioMenuViewModel) this.getChangeViewController()
                                        .getViewModel(PortfolioMenuView.VIEW_NAME);
                        portfolioViewModel.firePropertyChange();
                        this.getChangeViewController().changeView(PortfolioMenuView.VIEW_NAME);
                    }
                }
        );

        tickerAmountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                final char keyChar = e.getKeyChar();
                if (!(Character.isDigit(keyChar) || keyChar == KeyEvent.VK_BACK_SPACE || keyChar == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });

        this.add(tickerPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
