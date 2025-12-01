package interface_adapter.login;

import interface_adapter.ViewModel;
import interface_adapter.import_export.ImportExportState;
import view.ImportExportView;
import view.LoginView;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel extends ViewModel<LoginState> {


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

    // font size
    public static final int TITLE_FONT_SIZE = 24;
    public static final int SECTION_FONT_SIZE = 13;
    public static final int NORMAL_FONT_SIZE = 14;
    public static final int HINT_FONT_SIZE = 12;
    public static final int BORDER_FONT_SIZE = 16;

    // some fonts
    public static final String FONT_NAME = "defaultFont";
    public static final String FONT_FAMILY = UIManager.getFont(FONT_NAME) != null
            ? UIManager.getFont(FONT_NAME).getFamily()
            : "SansSerif";
    public static final Font BASE_FONT = UIManager.getFont(FONT_NAME) != null
            ? UIManager.getFont(FONT_NAME)
            : new Font("SansSerif", Font.PLAIN, 14);
    public static final String BACK_BUTTON_LABEL = "<- Back";
    public static final Font TITLE_FONT = BASE_FONT.deriveFont(Font.BOLD, 28f);
    public static final Font HEADER_FONT = BASE_FONT.deriveFont(Font.BOLD, 20f);
    public static final Font ICON_FONT = BASE_FONT.deriveFont(Font.PLAIN, 24f);
    public static final Font SECTION_TITLE_FONT = BASE_FONT.deriveFont(Font.BOLD, (float) SECTION_FONT_SIZE);
    public static final Font BUTTON_PRIMARY_FONT = BASE_FONT.deriveFont(Font.BOLD, (float) NORMAL_FONT_SIZE);
    public static final Font BUTTON_SECONDARY_FONT = BASE_FONT.deriveFont(Font.PLAIN, (float) NORMAL_FONT_SIZE);
    public static final Font HINT_FONT = BASE_FONT.deriveFont(Font.PLAIN, 13f);
    public static final Font ERROR_FONT = BASE_FONT.deriveFont(Font.PLAIN, 24f);
    public static final Font DROPDOWN_FONT = BASE_FONT.deriveFont(Font.PLAIN, 13f);

    public final String TITLE_LABEL = "Log In View";
    public final String USERNAME_LABEL = "Enter username";
    public final String PASSWORD_LABEL = "Enter password";

    public final String LOGIN_BUTTON_LABEL = "Log in";
    public final String CANCEL_BUTTON_LABEL = "Cancel";

    private LoginState state = new LoginState();

    public LoginViewModel() {
        super(LoginView.VIEW_NAME);
        setState(new LoginState());
    }

    public void setState(LoginState state) {
        this.state = state;
    }

    public LoginState getState() {
        return state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}