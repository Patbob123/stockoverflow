package interface_adapter.import_export;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import entities.PortfolioList;
import entities.Simulation;
import interface_adapter.ViewModel;

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
    public static final String CURRENT_SESSION_TITLE = "⌚ Current Session";
    public static final String PORTFOLIO_TITLE = "📂 Portfolio";
    public static final String SIMULATION_TITLE = "📈 Simulation";
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
    public static final String FONT_FAMILY = UIManager.getFont(FONT_NAME) != null
            ? UIManager.getFont(FONT_NAME).getFamily()
            : "SansSerif";
    public static final Font BASE_FONT = UIManager.getFont(FONT_NAME) != null
            ? UIManager.getFont(FONT_NAME)
            : new Font("SansSerif", Font.PLAIN, 14);

    public static final Font TITLE_FONT = BASE_FONT.deriveFont(Font.BOLD, 28f);
    public static final Font HEADER_FONT = BASE_FONT.deriveFont(Font.BOLD, 20f);
    public static final Font ICON_FONT = BASE_FONT.deriveFont(Font.PLAIN, 24f);
    public static final Font SECTION_TITLE_FONT = BASE_FONT.deriveFont(Font.BOLD, (float) SECTION_FONT_SIZE);
    public static final Font BUTTON_PRIMARY_FONT = BASE_FONT.deriveFont(Font.BOLD, (float) NORMAL_FONT_SIZE);
    public static final Font BUTTON_SECONDARY_FONT = BASE_FONT.deriveFont(Font.PLAIN, (float) NORMAL_FONT_SIZE);
    public static final Font HINT_FONT = BASE_FONT.deriveFont(Font.PLAIN, 13f);
    public static final Font ERROR_FONT = BASE_FONT.deriveFont(Font.PLAIN, 24f);
    public static final Font DROPDOWN_FONT = BASE_FONT.deriveFont(Font.PLAIN, 13f);

    private PortfolioList portfolioList = new PortfolioList();
    private List<Simulation> simulations = new ArrayList<>();

    public ImportExportViewModel() {
        super("ImportExportMenu");
        setState(new ImportExportState());
    }

    public void setPortfolios(PortfolioList portfolioList){
        this.portfolioList = portfolioList;
    }

    public void setSimulation(List<Simulation> simulations) {
        this.simulations = simulations;
    }
}