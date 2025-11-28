package view;

import interface_adapter.change_view.ChangeViewController;
import interface_adapter.portfolio.addStock.AddStockMenuController;
import interface_adapter.portfolio.addStock.AddStockMenuViewModel;
import lombok.Getter;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class AddStockMenuView extends PaddedView<AddStockMenuViewModel, AddStockMenuController> implements ActionListener, PropertyChangeListener {
    public static final String VIEW_NAME = "Add stock";
    @Setter
    private ChangeViewController changeViewController;

    public AddStockMenuView(AddStockMenuViewModel viewModel) {
        super(viewModel);
        this.getViewModel().addPropertyChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
