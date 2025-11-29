package view;

import interface_adapter.show_graph.ShowGraphState;
import interface_adapter.show_graph.ShowGraphViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ShowGraphView extends JPanel implements PropertyChangeListener {
    public final String viewName = "show graph";
    private final ShowGraphViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final GraphPanel graphPanel;

    public ShowGraphView(ShowGraphViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        JLabel title = new JLabel(ShowGraphViewModel.TITLE_LABEL);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        this.add(title, BorderLayout.NORTH);

        graphPanel = new GraphPanel();
        this.add(graphPanel, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton backButton = new JButton("Back");
        buttons.add(backButton);
        this.add(buttons, BorderLayout.SOUTH);

        // Dynamic Back Logic
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String target = viewModel.getState().getPreviousViewName();
                if (target == null) target = "search stock";

                viewManagerModel.setActiveView(target);
                viewManagerModel.firePropertyChanged();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ShowGraphState state = (ShowGraphState) evt.getNewValue();
        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        } else {
            graphPanel.updateData(state.getStockData());
        }
    }
}