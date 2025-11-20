package interface_adapter.create_portfolio;

import interface_adapter.ViewModel;
import java.util.List;

public class ImportExportViewModel extends ViewModel<ImportExportState> {

    public static final String TITLE_LABEL = "Import/Export Portfolio";
    public static final String IMPORT_TITLE_LABEL = "Import";
    public static final String EXPORT_TITLE_LABEL = "Export";

    public static final String CURRENT_SESSION_BUTTON_LABEL = "Export Current Session";
    public static final String EXPORT_PORTFOLIO_BUTTON_LABEL = "Export Portfolio";
    public static final String SELECT_SIMDATA_BUTTON_LABEL = "Export Sim Data";
    public static final String IMPORT_PORTFOLIO_BUTTON_LABEL = "Select Portfolio";
    public static final String BACK_BUTTON_LABEL = "<-";

    public static final int EXPORT_VERTICAL_STRUT = 10;
    public static final int PADDING = 15 ;
    public static final int INNER_PADDING = 5;
    public static final int HORIZONTAL_STRUT = 40;

    public static final int DROPDOWN_WIDTH = 200;
    public static final int DROPDOWN_HEIGHT = 25;
    public static final int BLOCK_HEIGHT = 360;

    public ImportExportViewModel() {
        super("ImportExportMenu");
        setState(new ImportExportState());
    }
}
