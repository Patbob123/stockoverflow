package interface_adapter.add_portfolio;

import interface_adapter.ViewModel;
import view.AddPortfolioView;

import javax.swing.*;
import java.awt.*;

public class AddPortfolioViewModel extends ViewModel<AddPortfolioState> {

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

    // Sizing
    public static final int TOP_BAR_HEIGHT = 60;
    public static final int CARD_WIDTH = 400;
    public static final int CARD_HEIGHT = 200;
    public static final int BUTTON_SPACING = 20;

    // Font sizes
    public static final int NORMAL_FONT_SIZE = 14;
    public static final int SUBTITLE_FONT_SIZE = 16;
    public static final int BUTTON_FONT_SIZE = 14;

    // Padding
    public static final int PADDING = 15;
    public static final int PADDING_LARGE = 20;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_SMALL = 12;

    // Button dimensions
    public static final int BUTTON_WIDTH = 300;
    public static final int BUTTON_HEIGHT = 50;
    public static final int BACK_BUTTON_WIDTH = 100;
    public static final int BACK_BUTTON_HEIGHT = 36;

    // Labels
    public static final String TITLE_LABEL = "Add Portfolio";
    public static final String SUBTITLE_LABEL = "Please select method to create portfolio";
    public static final String CREATE_BUTTON_LABEL = "Analyze Portfolio";
    public static final String IMPORT_BUTTON_LABEL = "Import Portfolio";
    public static final String BACK_BUTTON_LABEL = "← Main";

    // Fonts
    public static final String FONT_NAME = "defaultFont";
    public static final String FONT_FAMILY = UIManager.getFont(FONT_NAME) != null
            ? UIManager.getFont(FONT_NAME).getFamily()
            : "SansSerif";
    public static final Font BASE_FONT = UIManager.getFont(FONT_NAME) != null
            ? UIManager.getFont(FONT_NAME)
            : new Font("SansSerif", Font.PLAIN, 14);

    public static final Font TITLE_FONT = BASE_FONT.deriveFont(Font.BOLD, 28f);
    public static final Font SUBTITLE_FONT = BASE_FONT.deriveFont(Font.PLAIN, 16f);
    public static final Font BUTTON_FONT = BASE_FONT.deriveFont(Font.BOLD, 14f);
    public static final Font BUTTON_SECONDARY_FONT = BASE_FONT.deriveFont(Font.PLAIN, (float) NORMAL_FONT_SIZE);

    public AddPortfolioViewModel() {
        super(AddPortfolioView.VIEW_NAME);
        setState(new AddPortfolioState());
    }
}