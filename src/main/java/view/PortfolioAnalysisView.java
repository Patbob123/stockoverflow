package view;

import interface_adapter.portfolio_analysis.PortfolioAnalysisController;
import interface_adapter.portfolio_analysis.PortfolioAnalysisState;
import interface_adapter.portfolio_analysis.PortfolioAnalysisViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.singlestock.SingleStockViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PortfolioAnalysisView extends JPanel implements PropertyChangeListener {
    public final String viewName = "portfolio analysis";

    private final PortfolioAnalysisViewModel viewModel;
    private final PortfolioAnalysisController controller;
    private final ViewManagerModel viewManagerModel;

    private final JTextArea resultArea;
    private final JButton backButton;

    public PortfolioAnalysisView(PortfolioAnalysisViewModel viewModel,
                                 PortfolioAnalysisController controller,
                                 ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBackground(SingleStockViewModel.BG_COLOUR);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        headerPanel.setBorder(new EmptyBorder(30, 0, 20, 0));

        JLabel title = new JLabel(PortfolioAnalysisViewModel.TITLE_LABEL);
        title.setFont(SingleStockViewModel.TITLE_FONT.deriveFont(Font.BOLD, 28f));
        title.setForeground(SingleStockViewModel.PRIMARY_COLOUR);
        headerPanel.add(title);
        this.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(SingleStockViewModel.BG_COLOUR);
        centerPanel.setBorder(new EmptyBorder(10, 60, 40, 60));


        resultArea = new JTextArea();
        resultArea.setEditable(false);

        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));


        resultArea.setForeground(new Color(240, 240, 240)); // 亮白色文字
        resultArea.setBackground(new Color(35, 39, 42));    // 深邃黑灰背景，减少刺眼


        resultArea.setMargin(new Insets(30, 30, 30, 30));


        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        scrollPane.setBorder(BorderFactory.createLineBorder(SingleStockViewModel.BORDER_COLOUR, 2));
        scrollPane.getViewport().setBackground(new Color(35, 39, 42));

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 60, 30));
        bottomPanel.setBackground(SingleStockViewModel.BG_COLOUR);

        backButton = createStyledButton("Back to Stock List", SingleStockViewModel.SECONDARY_COLOUR);
        backButton.addActionListener(e -> {
            viewManagerModel.setActiveView("add stock");
            viewManagerModel.firePropertyChanged();
        });

        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton button = new JButton(text);
        button.setFont(SingleStockViewModel.BUTTON_PRIMARY_FONT.deriveFont(16f));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) { button.setBackground(bg.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent evt) { button.setBackground(bg); }
        });
        return button;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PortfolioAnalysisState state = (PortfolioAnalysisState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Analysis Error", JOptionPane.ERROR_MESSAGE);
            state.setError(null);
        } else if (state.getAnalysisResult() != null) {
            resultArea.setText(state.getAnalysisResult());
            resultArea.setCaretPosition(0);
        }
    }
}