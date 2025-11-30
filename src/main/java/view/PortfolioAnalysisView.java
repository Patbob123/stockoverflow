package view;

import interface_adapter.portfolio_analysis.PortfolioAnalysisController;
import interface_adapter.portfolio_analysis.PortfolioAnalysisState;
import interface_adapter.portfolio_analysis.PortfolioAnalysisViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
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

        JLabel title = new JLabel(PortfolioAnalysisViewModel.TITLE_LABEL);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(title, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        backButton = new JButton("Back to Stock List");
        backButton.addActionListener(e -> {
            viewManagerModel.setActiveView("add stock");
            viewManagerModel.firePropertyChanged();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        PortfolioAnalysisState state = (PortfolioAnalysisState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
            state.setError(null);
        } else if (state.getAnalysisResult() != null) {
            resultArea.setText(state.getAnalysisResult());
        }
    }
}