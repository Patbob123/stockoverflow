package view;

import interface_adapter.change_view.ChangeViewController;
import interface_adapter.portfolio.PortfolioMenuViewModel;
import interface_adapter.portfolio.addStock.AddStockMenuController;
import interface_adapter.portfolio.addStock.AddStockMenuViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddStockMenuView extends PaddedView<AddStockMenuViewModel, AddStockMenuController> {

    public static final String VIEW_NAME = "Add Stock";

    private final JPanel tickerPanel = new JPanel();
    private final JButton addStockButton = new JButton(AddStockMenuViewModel.ADDSTOCK_BUTTON_LABEL);
    private final JButton backToPortfolio = new JButton(AddStockMenuViewModel.BACK_TO_PORTFOLIO);

    public AddStockMenuView(AddStockMenuViewModel viewModel) {
        super(viewModel);
        this.getViewModel().addPropertyChangeListener(this);
        tickerPanel.setLayout(new BoxLayout(tickerPanel, BoxLayout.Y_AXIS));
        tickerPanel.add(addStockButton);
        tickerPanel.add(backToPortfolio);

        addStockButton.addActionListener(
                evt -> {
                    if(evt.getSource().equals(addStockButton)) {
                        //
                    }
                }
        );
        backToPortfolio.addActionListener(
                evt -> {
                    if(evt.getSource().equals(backToPortfolio)) {
                        final PortfolioMenuViewModel portfolioViewModel =
                                (PortfolioMenuViewModel) this.getChangeViewController()
                                        .getViewModel(PortfolioMenuView.VIEW_NAME);
                        portfolioViewModel.firePropertyChange();
                        this.getChangeViewController().changeView(PortfolioMenuView.VIEW_NAME);
                    }
                }
        );

        this.add(tickerPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
