package interface_adapter.portfolio_analysis;

import interface_adapter.ViewModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PortfolioAnalysisViewModel extends ViewModel {
    public static final String TITLE_LABEL = "Portfolio Analytics (Last 100 Days)";

    private PortfolioAnalysisState state = new PortfolioAnalysisState();

    public PortfolioAnalysisViewModel() {
        super("portfolio analysis");
    }

    public void setState(PortfolioAnalysisState state) { this.state = state; }
    public PortfolioAnalysisState getState() { return state; }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void firePropertyChanged() { support.firePropertyChange("state", null, this.state); }
    public void addPropertyChangeListener(PropertyChangeListener listener) { support.addPropertyChangeListener(listener); }
}