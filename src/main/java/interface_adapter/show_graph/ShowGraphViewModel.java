package interface_adapter.show_graph;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class ShowGraphViewModel extends ViewModel<ShowGraphState> {

    public static final String TITLE_LABEL = "Market Graph";
    public static final String PLOT_BUTTON_LABEL = "Plot Graph";
    public static final String BACK_BUTTON_LABEL = "Back";

    public ShowGraphViewModel() {
        super("show graph");
        this.setState(new ShowGraphState());
    }
}