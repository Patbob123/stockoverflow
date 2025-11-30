package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.show_graph.ShowGraphState;
import interface_adapter.show_graph.ShowGraphViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ShowGraphView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "show graph";

    private final ShowGraphViewModel viewModel;
    private final ShowGraphController controller;
    private final ViewManagerModel viewManagerModel;

    // UI Components
    private final JTextField tickerInputField = new JTextField(20);
    private final StockGraphPanel graphPanel;
    private final JButton plotButton;
    private final JButton backButton;

    public ShowGraphView(ShowGraphViewModel viewModel, ShowGraphController controller, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        // --- Top Panel: Controls ---
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JLabel tickerLabel = new JLabel("Tickers (comma separated):");
        controlPanel.add(tickerLabel);
        controlPanel.add(tickerInputField);

        plotButton = new JButton(ShowGraphViewModel.PLOT_BUTTON_LABEL);
        controlPanel.add(plotButton);

        backButton = new JButton("Back");
        controlPanel.add(backButton);

        this.add(controlPanel, BorderLayout.NORTH);

        // --- Center: Graph ---
        graphPanel = new StockGraphPanel();
        graphPanel.setPreferredSize(new Dimension(800, 500));
        graphPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(graphPanel, BorderLayout.CENTER);

        // --- Listeners ---
        plotButton.addActionListener(e -> {
            if (e.getSource().equals(plotButton)) {
                String tickers = tickerInputField.getText();
                if (tickers != null && !tickers.isEmpty()) {
                    controller.execute(tickers, viewModel.getState().getPreviousViewName());
                } else {
                    JOptionPane.showMessageDialog(ShowGraphView.this, "Please enter a ticker symbol.");
                }
            }
        });

        backButton.addActionListener(e -> {
            if (e.getSource().equals(backButton)) {
                String targetView = viewModel.getState().getPreviousViewName();
                if (targetView == null || targetView.isEmpty()) {
                    targetView = "main menu";
                }
                viewManagerModel.setActiveView(targetView);
                viewManagerModel.firePropertyChanged();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ShowGraphState state = (ShowGraphState) evt.getNewValue();

        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage());
        }

        if (state.getStockData() != null && !state.getStockData().isEmpty()) {
            graphPanel.setDatasets(state.getStockData());
        }
    }
}