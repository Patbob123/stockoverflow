package view;

import interface_adapter.ViewModel;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.portfolio.PortfolioMenuController;
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class AddStockMenuView extends PaddedView implements ActionListener, PropertyChangeListener {

    @Getter
    private final String viewName = "Add Stock";

    @Setter
    private ChangeViewController changeViewController;

    private final AddStockMenuViewModel addStockMenuViewModel;
    private final AddStockMenuController addStockMenuController;

    public AddStockMenuView(AddStockMenuViewModel addStockMenuViewModel) {
        this.addStockMenuViewModel = addStockMenuViewModel;
        this.addStockMenuViewModel.addPropertyChangeListener(this);
        this.addStockMenuController = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    @Override
    public ViewModel<?> getViewModel() {
        return null;
    }
}
