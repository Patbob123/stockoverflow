package view;

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
    private final ViewManagerModel viewManagerModel;

    private final JLabel resultLabel;
    private final JButton backButton;

    public PortfolioAnalysisView(PortfolioAnalysisViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());

        JLabel title = new JLabel(PortfolioAnalysisViewModel.TITLE_LABEL);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(title, BorderLayout.NORTH);

        resultLabel = new JLabel();
        resultLabel.setVerticalAlignment(SwingConstants.TOP);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.add(new JScrollPane(resultLabel), BorderLayout.CENTER);

        backButton = new JButton("Back to Portfolio");
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
            state.setError(null); // Clear error
        } else if (state.getAnalysisResult() != null) {
            resultLabel.setText(state.getAnalysisResult());
        }
    }
}