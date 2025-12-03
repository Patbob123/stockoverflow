package interface_adapter.import_export;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.UIManager;

import entities.Portfolio.PortfolioList;
import entities.Simulation;
import interface_adapter.ViewModel;
import view.ImportExportView;

/**
 * ViewModel for Import/Export view.
 */
public class ImportExportViewModel extends ViewModel<ImportExportState> {

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

    // Font sizes
    public static final int TITLE_FONT_SIZE = 24;
    public static final int SECTION_FONT_SIZE = 13;
    public static final int NORMAL_FONT_SIZE = 14;
    public static final int HINT_FONT_SIZE = 12;
    public static final int BORDER_FONT_SIZE = 16;

    // Padding
    public static final int PADDING = 15;
    public static final int PADDING_LARGE = 20;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_SMALL = 12;
    public static final int PADDING_TINY = 8;
    public static final int INNER_PADDING = 5;
    public static final int EXPORT_VERTICAL_STRUT = 10;
    public static final int HORIZONTAL_STRUT = 40;

    // Button dimensions
    public static final int BUTTON_WIDTH = 180;
    public static final int BUTTON_HEIGHT = 36;
    public static final int SECONDARY_BUTTON_WIDTH = 160;
    public static final int SECONDARY_BUTTON_HEIGHT = 32;
    public static final int DROPDOWN_WIDTH = 200;
    public static final int DROPDOWN_HEIGHT = 25;

    // Numeric constants
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TEN = 10;
    public static final int FIFTEEN = 15;
    public static final int TWENTY = 20;
    public static final int THIRTY = 30;
    public static final int HUNDRED = 100;
    public static final double WEIGHT_ONE = 1.0;

    // Button dimensions
    public static final int PRIMARY_BUTTON_HEIGHT = 45;
    public static final int IMPORT_BUTTON_WIDTH = 280;
    public static final int IMPORT_BUTTON_HEIGHT = 50;

    // Dropdown dimensions
    public static final int DROPDOWN_PADDING_VERTICAL = 8;
    public static final int DROPDOWN_PADDING_HORIZONTAL = 12;

    // Card padding
    public static final int CARD_PADDING = 25;

    // Labels
    public static final String TITLE_LABEL = "Import & Export";
    public static final String IMPORT_TITLE_LABEL = "Import";
    public static final String EXPORT_TITLE_LABEL = "Export";
    public static final String CURRENT_SESSION_BUTTON_LABEL = "Export Current Session";
    public static final String EXPORT_PORTFOLIO_BUTTON_LABEL = "Export Portfolio";
    public static final String SELECT_SIMDATA_BUTTON_LABEL = "Export Sim Data";
    public static final String IMPORT_PORTFOLIO_BUTTON_LABEL = "Select Portfolio";
    public static final String BACK_BUTTON_LABEL = "<- Back";

    // Titles I SWEAR THIS IS NOT AI GENERATED I DONT WANT TO USE SVGS
    public static final String CURRENT_SESSION_TITLE = "Current Session";
    public static final String PORTFOLIO_TITLE = "Portfolio";
    public static final String SIMULATION_TITLE = "Simulation";
    public static final String IMPORT_HINT = "Select a CSV file to import";

    // Error messages
    public static final String ERROR_INVALID_PATH = "Invalid file path";
    public static final String ERROR_NO_PORTFOLIO = "Choose a portfolio first";
    public static final String ERROR_NO_SIMULATION = "Choose a simulation first";

    // Dialog titles
    public static final String CSV_DIALOG_TITLE = "Select CSV file to import";
    public static final String FOLDER_DIALOG_TITLE = "Select folder to export";
    public static final String CSV_FILTER_DESC = "CSV Files";
    public static final String CSV_EXTENSION = "csv";

    public static final String FONT_NAME = "defaultFont";
    public static final String FONT_FAMILY;
    public static final Font BASE_FONT;

    public static final Font TITLE_FONT;
    public static final Font HEADER_FONT;
    public static final Font ICON_FONT;
    public static final Font SECTION_TITLE_FONT;
    public static final Font BUTTON_PRIMARY_FONT;
    public static final Font BUTTON_SECONDARY_FONT;
    public static final Font HINT_FONT;
    public static final Font ERROR_FONT;
    public static final Font DROPDOWN_FONT;

    static {
        final Font defaultFont = UIManager.getFont(FONT_NAME);
        final int fourteen = 14;
        final float titleFontSize = 28f;
        final float headerFontSize = 20f;
        final float iconFontSize = 24f;
        final float sectionTitleFontSize = (float) SECTION_FONT_SIZE;
        final float buttonPrimaryFontSize = (float) NORMAL_FONT_SIZE;
        final float buttonSecondaryFontSize = (float) NORMAL_FONT_SIZE;
        final float hintFontSize = 13f;
        final float errorFontSize = 24f;
        final float dropdownFontSize = 13f;
        if (defaultFont != null) {
            FONT_FAMILY = defaultFont.getFamily();
            BASE_FONT = defaultFont;
        }
        else {
            FONT_FAMILY = "SansSerif";
            BASE_FONT = new Font("SansSerif", Font.PLAIN, fourteen);
        }

        TITLE_FONT = BASE_FONT.deriveFont(Font.BOLD, titleFontSize);
        HEADER_FONT = BASE_FONT.deriveFont(Font.BOLD, headerFontSize);
        ICON_FONT = BASE_FONT.deriveFont(Font.PLAIN, iconFontSize);
        SECTION_TITLE_FONT = BASE_FONT.deriveFont(Font.BOLD, sectionTitleFontSize);
        BUTTON_PRIMARY_FONT = BASE_FONT.deriveFont(Font.BOLD, buttonPrimaryFontSize);
        BUTTON_SECONDARY_FONT = BASE_FONT.deriveFont(Font.PLAIN, buttonSecondaryFontSize);
        HINT_FONT = BASE_FONT.deriveFont(Font.PLAIN, hintFontSize);
        ERROR_FONT = BASE_FONT.deriveFont(Font.PLAIN, errorFontSize);
        DROPDOWN_FONT = BASE_FONT.deriveFont(Font.PLAIN, dropdownFontSize);
    }

    /**
     * Constructor for an ImportExportViewModel and set init state.
     */
    public ImportExportViewModel() {
        super(ImportExportView.VIEW_NAME);
        setState(new ImportExportState());
    }

    /**
     * Sets the list of portfolios available for import/export.
     *
     * @param portfolioList List of portfolios
     */
    public void setPortfolios(PortfolioList portfolioList) {
        getState().setPortfolios(portfolioList.getPortfolios());
    }

    /**
     * Sets the list of simulations available for import/export.
     *
     * @param simulations simulations.
     */
    public void setSimulation(List<Simulation> simulations) {
        getState().setSimData(simulations);
    }
}
