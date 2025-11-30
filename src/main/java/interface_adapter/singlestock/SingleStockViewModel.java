package interface_adapter.singlestock;

import interface_adapter.ViewModel;
import view.SingleStockView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SingleStockViewModel extends ViewModel {

    // view name
    public static final String VIEW_NAME = "single stock";

    // Labels
    public static final String LABEL_TICKER = "Ticker:";
    public static final String LABEL_HISTORY = "History:";
    public static final String LABEL_FRED_API = "Fred API Key:";
    public static final String LABEL_RISK_FREE = "Risk-free:";

    // Buttons
    public static final String BUTTON_ANALYZE = "Analyze";
    public static final String BUTTON_BACK = "Back to Main";
    public static final String BUTTON_FRED = "Fred API";
    public static final String BUTTON_COMPARE = "Compare";
    public static final String BUTTON_SCENARIO = "Scenario and Stress Testing";
    public static final String BUTTON_MONTECARLO = "Monte Carlo";
    public static final String BUTTON_IMPORT = "Import/Export";
    public static final String BUTTON_HISTORY = "History";
    public static final String BUTTON_EXIT = "Exit";

    // Default field values and sizes
    public static final String DEFAULT_TICKER = "AAPL";
    public static final String DEFAULT_RISK_FREE_TXT = "0.02";
    public static final int TICKER_FIELD_COLUMNS = 10;
    public static final int RISK_FREE_FIELD_COLUMNS = 6;
    public static final int FRED_API_FIELD_COLUMNS = 20;

    // Colours
    public static final Color BG_COLOUR = new Color(248, 250, 252);
    public static final Color CARD_COLOUR = new Color(68, 72, 74);
    public static final Color PRIMARY_COLOUR = new Color(236, 122, 73);
    public static final Color PRIMARY_HOVER = new Color(209, 77, 37);
    public static final Color SECONDARY_COLOUR = new Color(45, 55, 72);
    public static final Color SECONDARY_HOVER = new Color(57, 69, 110);
    public static final Color SUCCESS_COLOUR = new Color(85, 193, 106);
    public static final Color SUCCESS_HOVER = new Color(29, 131, 80);
    public static final Color BORDER_COLOUR = new Color(38, 42, 44);
    public static final Color TEXT_PRIMARY = new Color(224, 244, 255);
    public static final Color TEXT_SECONDARY = new Color(150, 166, 189);

    // fonts
    public static final String FONT_NAME = "defaultFont";
    public static final Font BASE_FONT = UIManager.getFont(FONT_NAME) != null
            ? UIManager.getFont(FONT_NAME)
            : new Font("SansSerif", Font.PLAIN, 14);

    public static final Font TITLE_FONT = BASE_FONT.deriveFont(Font.BOLD, 28f);
    public static final Font SECTION_TITLE_FONT = BASE_FONT.deriveFont(Font.BOLD, 13f);
    public static final Font BUTTON_PRIMARY_FONT = BASE_FONT.deriveFont(Font.BOLD, 14f);

    private SingleStockState state = new SingleStockState();

    public SingleStockViewModel() {
        super(VIEW_NAME);
    }

    public void setState(SingleStockState state) {
        this.state = state;
    }

    public SingleStockState getState() {
        return state;
    }

    // --- Property Change Support (Crucial for View Updates) ---
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}