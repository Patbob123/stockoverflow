package view;

import interface_adapter.historical_simulation.HistoricalSimulationController;
import interface_adapter.historical_simulation.HistoricalSimulationState;
import interface_adapter.historical_simulation.HistoricalSimulationViewModel;
import interface_adapter.change_view.ChangeViewController; // 导入 ChangeViewController

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

public class HistoricalSimulationView extends PaddedView<HistoricalSimulationViewModel, HistoricalSimulationController> implements ActionListener, PropertyChangeListener {
    public static final String VIEW_NAME = "historical simulation";

    private ChangeViewController changeViewController;

    private final JTextField dateInputField = new JTextField(10);
    private final JLabel resultLabel = new JLabel("<html>Enter a date (YYYY-MM-DD)<br/>to see historical performance.</html>");
    private final JButton simulateButton = new JButton("Run Simulation");
    private final JButton backButton = new JButton("Back");

    public HistoricalSimulationView(HistoricalSimulationViewModel viewModel) {
        super(viewModel);
        this.getViewModel().addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Historical Portfolio Return");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Start Date (YYYY-MM-DD): "));
        inputPanel.add(dateInputField);
        inputPanel.add(simulateButton);

        JPanel resultPanel = new JPanel();
        resultPanel.add(resultLabel);

        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(inputPanel);
        this.add(resultPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(backButton);

        simulateButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(simulateButton)) {
                        HistoricalSimulationState state = getViewModel().getState();
                        Map<String, Double> stocks = state.getPortfolioStocks();
                        String date = dateInputField.getText();
                        if (getController() != null) {
                            getController().execute(stocks, date);
                        }
                    }
                }
        );

        backButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(backButton)) {
                        // 返回 PortfolioMenu
                        if (changeViewController != null) {
                            changeViewController.changeView(PortfolioMenuView.VIEW_NAME);
                        }
                    }
                }
        );
    }

    public void setChangeViewController(ChangeViewController changeViewController) {
        this.changeViewController = changeViewController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        HistoricalSimulationState state = (HistoricalSimulationState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
            state.setError(null);
        } else if (!state.getResultText().isEmpty()) {
            resultLabel.setText(state.getResultText());
        }
    }
}