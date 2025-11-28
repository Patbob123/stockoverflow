package view;

import interface_adapter.AbsController;
import interface_adapter.ViewModel;
import interface_adapter.change_view.ChangeViewController;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;


public abstract class PaddedView<V extends ViewModel<?>, C extends AbsController> extends JPanel
        implements ActionListener, PropertyChangeListener {
    @Getter
    private final V viewModel;
    @Getter
    private final String viewName;
    @Getter
    @Setter
    private C controller = null;
    @Getter
    @Setter
    private ChangeViewController changeViewController;

    public PaddedView(V viewModel) {
        this.viewModel = viewModel;
        this.viewName = viewModel.getViewName();
        setBorder(new EmptyBorder(15,15,15,15));
    }

    public PaddedView(int padding, V viewModel, String viewName) {
        this.viewModel = viewModel;
        this.viewName = viewName;
        setBorder(new EmptyBorder(padding, padding, padding, padding));
    }

    public PaddedView(int top, int left, int bottom, int right, V viewModel, String viewName) {
        super();
        this.viewModel = viewModel;
        this.viewName = viewName;
        setBorder(new EmptyBorder(top, left, bottom, right));
    }

    public PaddedView(LayoutManager layout, int padding, V viewModel, String viewName) {
        super(layout);
        this.viewModel = viewModel;
        this.viewName = viewName;
        setBorder(new EmptyBorder(padding, padding, padding, padding));
    }

    public PaddedView(LayoutManager layout, int top, int left, int bottom, int right, V viewModel, String viewName, AbsController controller) {
        super(layout);
        this.viewModel = viewModel;
        this.viewName = viewName;
        setBorder(new EmptyBorder(top, left, bottom, right));
    }

    public String getViewName() {
        return this.viewName;
    }
}