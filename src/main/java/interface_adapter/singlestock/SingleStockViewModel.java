package interface_adapter.singlestock;

import interface_adapter.ViewModel;
import lombok.Getter;
import lombok.Setter;
import use_case.singlestock.AnalyzeSingleStockOutputData;
import view.SingleStockView;

@Getter
public class SingleStockViewModel extends ViewModel<SingleStockState> {

    // view name if you use a ViewManager later

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

    public static final String HISTORY_FILE_NAME = ".stockoverflow-history.txt";

    // Instructions
    public static final String SHOW_ANALTSIS = "showAnalysis";
    public static final String SHOW_ERROR = "showError";

    private String currentTicker = DEFAULT_TICKER;
    private String currentRiskFree = DEFAULT_RISK_FREE_TXT;
    private String fredApiKey = "";

    public SingleStockViewModel() {
        super(SingleStockView.VIEW_NAME);
    }

    public void setCurrentTicker(String currentTicker) {
        this.currentTicker = currentTicker;
    }

    public void setCurrentRiskFree(String currentRiskFree) {
        this.currentRiskFree = currentRiskFree;
    }

    public void setFredApiKey(String fredApiKey) {
        this.fredApiKey = fredApiKey;
    }

    public void showAnalysis(AnalyzeSingleStockOutputData outputData){

        firePropertyChange(SHOW_ANALTSIS);
    };
    public void showError(String message){
        firePropertyChange(SHOW_ERROR);
    };
}
