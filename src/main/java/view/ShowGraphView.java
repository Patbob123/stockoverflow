package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.show_graph.ShowGraphState;
import interface_adapter.show_graph.ShowGraphViewModel;
import interface_adapter.singlestock.SingleStockViewModel;
import lombok.Setter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ShowGraphView extends PaddedView<ShowGraphViewModel, ShowGraphController> implements ActionListener, PropertyChangeListener {

    public static final String VIEW_NAME = "show graph";

    @Setter
    private ShowGraphController controller;
    @Setter
    private ViewManagerModel viewManagerModel;

    // UI Components
    private final JTextField tickerInputField = new JTextField(20);
    private final StockGraphPanel graphPanel;
    private final JButton plotButton;
    private final JButton backButton;

    public ShowGraphView(ShowGraphViewModel viewModel) {
        super(viewModel);

        viewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(SingleStockViewModel.BG_COLOUR);

        // --- Top Container ---
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.setBackground(SingleStockViewModel.BG_COLOUR);

        JLabel titleLabel = new JLabel("Stock Price Visualization");
        titleLabel.setFont(SingleStockViewModel.TITLE_FONT.deriveFont(24f));
        titleLabel.setForeground(SingleStockViewModel.PRIMARY_COLOUR);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(SingleStockViewModel.BG_COLOUR);
        titlePanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        titlePanel.add(titleLabel);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        controlPanel.setBackground(SingleStockViewModel.CARD_COLOUR);
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR, 1),
                new EmptyBorder(5, 20, 5, 20)
        ));
        controlPanel.setMaximumSize(new Dimension(800, 70));

        JLabel inputLabel = new JLabel("Tickers (comma separated):");
        inputLabel.setFont(SingleStockViewModel.BASE_FONT.deriveFont(Font.BOLD));
        inputLabel.setForeground(SingleStockViewModel.TEXT_SECONDARY);

        tickerInputField.setFont(SingleStockViewModel.BASE_FONT);
        tickerInputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        plotButton = createStyledButton("Plot Graph", SingleStockViewModel.PRIMARY_COLOUR);

        controlPanel.add(inputLabel);
        controlPanel.add(tickerInputField);
        controlPanel.add(plotButton);

        topContainer.add(titlePanel);
        topContainer.add(controlPanel);
        topContainer.add(Box.createVerticalStrut(20));

        this.add(topContainer, BorderLayout.NORTH);

        // --- Graph Container ---
        JPanel graphContainer = new JPanel(new BorderLayout());
        graphContainer.setBackground(SingleStockViewModel.BG_COLOUR);
        graphContainer.setBorder(new EmptyBorder(0, 40, 0, 40));

        graphPanel = new StockGraphPanel();
        graphPanel.setBackground(Color.WHITE);
        graphPanel.setBorder(BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR, 2));

        graphContainer.add(graphPanel, BorderLayout.CENTER);
        this.add(graphContainer, BorderLayout.CENTER);

        // --- Bottom Container ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 40, 20));
        bottomPanel.setBackground(SingleStockViewModel.BG_COLOUR);

        backButton = createStyledButton("Back", SingleStockViewModel.SECONDARY_COLOUR);
        bottomPanel.add(backButton);

        this.add(bottomPanel, BorderLayout.SOUTH);

        // --- Listeners ---
        plotButton.addActionListener(e -> {
            String tickers = tickerInputField.getText();
            if (tickers != null && !tickers.isEmpty()) {
                if (controller != null) {
                    String prevView = getViewModel().getState().getPreviousViewName();
                    controller.execute(tickers, prevView);
                } else {
                    System.out.println("ShowGraphController is not set");
                }
            } else {
                JOptionPane.showMessageDialog(ShowGraphView.this, "Please enter a ticker symbol.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            String targetView = getViewModel().getState().getPreviousViewName();
            if (targetView == null || targetView.isEmpty()) {
                targetView = "main menu";
            }
            viewManagerModel.setActiveView(targetView);
            viewManagerModel.firePropertyChange();
        });
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(SingleStockViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(bg.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(bg); }
        });
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ShowGraphState state = (ShowGraphState) evt.getNewValue();

        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            state.setErrorMessage(null);
        }

        if (state.getStockData() != null && !state.getStockData().isEmpty()) {
            graphPanel.setDatasets(state.getStockData());
            graphPanel.repaint();
        }
    }
}