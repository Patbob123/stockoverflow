package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import entities.Portfolio.Portfolio;
import entities.Portfolio.PortfolioList;
import interface_adapter.import_export.ImportExportController;
import interface_adapter.import_export.ImportExportViewModel;

/**
 * The ImportExportView represents the graphical interface for importing and exporting
 * portfolios, current session data, and simulation data.
 */
public class ImportExportView extends PaddedView<ImportExportViewModel, ImportExportController>
        implements ActionListener, PropertyChangeListener {

    /**
     * The name identifier for this view.
     */
    public static final String VIEW_NAME = "ImportExportMenu";

    private final JButton currentSessionButton = createPrimaryButton(
            ImportExportViewModel.CURRENT_SESSION_BUTTON_LABEL);
    private final JButton exportPortfolioButton = createSecondaryButton(
            ImportExportViewModel.EXPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton selectSimDataButton = createSecondaryButton(
            ImportExportViewModel.SELECT_SIMDATA_BUTTON_LABEL);
    private final JButton importPortfolioButton = createSuccessButton(
            ImportExportViewModel.IMPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton backButton = createTextButton(
            ImportExportViewModel.BACK_BUTTON_LABEL);
    private final JLabel errorLabel = new JLabel();

    private final JComboBox<Portfolio> portfolioDropdown = new JComboBox<>();
    private final JComboBox<String> simulationDropdown = new JComboBox<>(new String[]{"Bulk"});

    /**
     * Constructs an ImportExportView with the specified view model.
     *
     * @param importExportViewModel the view model for this view
     */
    public ImportExportView(ImportExportViewModel importExportViewModel) {
        super(importExportViewModel);
        this.getViewModel().addPropertyChangeListener(this);
        createView();
    }

    private void createView() {
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createErrorPanel(), BorderLayout.SOUTH);
        setupListeners();
    }

    private JPanel createTopPanel() {
        final JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(ImportExportViewModel.CARD_COLOUR);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(ImportExportViewModel.ZERO,
                        ImportExportViewModel.ZERO,
                        ImportExportViewModel.ONE,
                        ImportExportViewModel.ZERO,
                        ImportExportViewModel.BORDER_COLOUR),
                new EmptyBorder(ImportExportViewModel.TWENTY,
                        ImportExportViewModel.THIRTY,
                        ImportExportViewModel.TWENTY,
                        ImportExportViewModel.THIRTY)
        ));

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(createTitleLabel(), BorderLayout.CENTER);
        topPanel.add(createSpacer(), BorderLayout.EAST);

        return topPanel;
    }

    private JLabel createTitleLabel() {
        final JLabel title = new JLabel(ImportExportViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(ImportExportViewModel.TITLE_FONT);
        title.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        return title;
    }

    private JPanel createSpacer() {
        final JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(ImportExportViewModel.HUNDRED,
                ImportExportViewModel.ZERO));
        return spacer;
    }

    private JPanel createMainPanel() {
        final JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(ImportExportViewModel.THIRTY,
                ImportExportViewModel.THIRTY,
                ImportExportViewModel.THIRTY,
                ImportExportViewModel.THIRTY));

        final GridBagConstraints gbc = createGridBagConstraints();

        gbc.gridx = ImportExportViewModel.ZERO;
        mainPanel.add(createExportPanel(), gbc);

        gbc.gridx = ImportExportViewModel.ONE;
        mainPanel.add(createImportPanel(), gbc);

        return mainPanel;
    }

    private GridBagConstraints createGridBagConstraints() {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = ImportExportViewModel.WEIGHT_ONE;
        gbc.weightx = ImportExportViewModel.WEIGHT_ONE;
        gbc.insets = new Insets(ImportExportViewModel.ZERO,
                ImportExportViewModel.TEN,
                ImportExportViewModel.ZERO,
                ImportExportViewModel.TEN);
        return gbc;
    }

    private JPanel createExportPanel() {
        final JPanel exportPanel = createModernCard();
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));

        exportPanel.add(createExportHeader());
        exportPanel.add(Box.createVerticalStrut(ImportExportViewModel.TWENTY));
        exportPanel.add(createCurrentSessionSection());
        exportPanel.add(Box.createVerticalStrut(ImportExportViewModel.FIFTEEN));
        exportPanel.add(createPortfolioSection());
        exportPanel.add(Box.createVerticalStrut(ImportExportViewModel.FIFTEEN));
        exportPanel.add(createSimulationSection());
        exportPanel.add(Box.createVerticalGlue());

        return exportPanel;
    }

    private JPanel createCurrentSessionSection() {
        final JPanel sessionSection = createSection(
                ImportExportViewModel.CURRENT_SESSION_TITLE);
        currentSessionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        sessionSection.add(currentSessionButton);
        return sessionSection;
    }

    private JPanel createPortfolioSection() {
        final JPanel portfolioSection = createSection(ImportExportViewModel.PORTFOLIO_TITLE);
        styleDropdown(portfolioDropdown);
        portfolioDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        portfolioSection.add(portfolioDropdown);
        portfolioSection.add(Box.createVerticalStrut(ImportExportViewModel.TEN));
        exportPortfolioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        portfolioSection.add(exportPortfolioButton);
        return portfolioSection;
    }

    private JPanel createSimulationSection() {
        final JPanel simulationSection = createSection(
                ImportExportViewModel.SIMULATION_TITLE);
        styleDropdown(simulationDropdown);
        simulationDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        simulationSection.add(simulationDropdown);
        simulationSection.add(Box.createVerticalStrut(ImportExportViewModel.TEN));
        selectSimDataButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        simulationSection.add(selectSimDataButton);
        return simulationSection;
    }

    private JPanel createImportPanel() {
        final JPanel importPanel = createModernCard();
        importPanel.setLayout(new BoxLayout(importPanel, BoxLayout.Y_AXIS));

        importPanel.add(createImportHeader());
        importPanel.add(Box.createVerticalGlue());
        importPanel.add(createImportCenter());
        importPanel.add(Box.createVerticalGlue());

        return importPanel;
    }

    private JPanel createImportHeader() {
        final JPanel importHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        importHeader.setOpaque(false);
        final JLabel importIcon = new JLabel("<>");
        importIcon.setFont(ImportExportViewModel.ICON_FONT);
        importIcon.setForeground(ImportExportViewModel.SUCCESS_COLOUR);
        final JLabel importTitle = new JLabel(ImportExportViewModel.IMPORT_TITLE_LABEL);
        importTitle.setFont(ImportExportViewModel.HEADER_FONT);
        importTitle.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        importHeader.add(importIcon);
        importHeader.add(importTitle);
        return importHeader;
    }

    private JPanel createImportCenter() {
        final JPanel importCenter = new JPanel();
        importCenter.setLayout(new BoxLayout(importCenter, BoxLayout.Y_AXIS));
        importCenter.setOpaque(false);

        importPortfolioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        importPortfolioButton.setPreferredSize(new Dimension(
                ImportExportViewModel.IMPORT_BUTTON_WIDTH,
                ImportExportViewModel.IMPORT_BUTTON_HEIGHT));
        importPortfolioButton.setMaximumSize(new Dimension(
                ImportExportViewModel.IMPORT_BUTTON_WIDTH,
                ImportExportViewModel.IMPORT_BUTTON_HEIGHT));
        importCenter.add(importPortfolioButton);
        importCenter.add(Box.createVerticalStrut(ImportExportViewModel.FIFTEEN));
        importCenter.add(createHintLabel());

        return importCenter;
    }

    private JLabel createHintLabel() {
        final JLabel hint = new JLabel(ImportExportViewModel.IMPORT_HINT);
        hint.setFont(ImportExportViewModel.HINT_FONT);
        hint.setForeground(ImportExportViewModel.TEXT_SECONDARY);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        return hint;
    }

    private JPanel createErrorPanel() {
        final JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        errorPanel.setBorder(new EmptyBorder(ImportExportViewModel.ZERO,
                ImportExportViewModel.ZERO,
                ImportExportViewModel.TWENTY,
                ImportExportViewModel.ZERO));
        errorLabel.setFont(ImportExportViewModel.ERROR_FONT);
        errorLabel.setForeground(ImportExportViewModel.PRIMARY_HOVER);
        errorPanel.add(errorLabel);
        return errorPanel;
    }

    private static JPanel createExportHeader() {
        final JPanel exportHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exportHeader.setOpaque(false);
        final JLabel exportIcon = new JLabel("<>");
        exportIcon.setFont(ImportExportViewModel.ICON_FONT);
        exportIcon.setForeground(ImportExportViewModel.PRIMARY_COLOUR);
        final JLabel exportTitle = new JLabel(ImportExportViewModel.EXPORT_TITLE_LABEL);
        exportTitle.setFont(ImportExportViewModel.HEADER_FONT);
        exportTitle.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        exportHeader.add(exportIcon);
        exportHeader.add(exportTitle);
        return exportHeader;
    }

    private JPanel createModernCard() {
        final JPanel card = new JPanel();
        card.setBackground(ImportExportViewModel.CARD_COLOUR);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ImportExportViewModel.BORDER_COLOUR,
                        ImportExportViewModel.ONE, true),
                new EmptyBorder(ImportExportViewModel.CARD_PADDING,
                        ImportExportViewModel.CARD_PADDING,
                        ImportExportViewModel.CARD_PADDING,
                        ImportExportViewModel.CARD_PADDING)
        ));
        return card;
    }

    private JPanel createSection(String title) {
        final JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ImportExportViewModel.BORDER_COLOUR,
                        ImportExportViewModel.ONE, true),
                new EmptyBorder(ImportExportViewModel.PADDING,
                        ImportExportViewModel.PADDING,
                        ImportExportViewModel.PADDING,
                        ImportExportViewModel.PADDING)
        ));

        section.add(createSectionTitle(title));
        section.add(Box.createVerticalStrut(ImportExportViewModel.EXPORT_VERTICAL_STRUT));

        return section;
    }

    private JLabel createSectionTitle(String title) {
        final JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(ImportExportViewModel.SECTION_TITLE_FONT);
        sectionTitle.setForeground(ImportExportViewModel.TEXT_SECONDARY);
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        return sectionTitle;
    }

    private JButton createPrimaryButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(ImportExportViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(ImportExportViewModel.PRIMARY_COLOUR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(ImportExportViewModel.BUTTON_WIDTH,
                ImportExportViewModel.PRIMARY_BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(Short.MAX_VALUE,
                ImportExportViewModel.PRIMARY_BUTTON_HEIGHT));

        addPrimaryButtonHoverEffect(button);
        return button;
    }

    private void addPrimaryButtonHoverEffect(JButton button) {
        final Color originalColor = button.getBackground();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ImportExportViewModel.PRIMARY_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    private JButton createSecondaryButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(ImportExportViewModel.BUTTON_SECONDARY_FONT);
        button.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(ImportExportViewModel.BORDER_COLOUR,
                ImportExportViewModel.ONE, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(ImportExportViewModel.BUTTON_WIDTH,
                ImportExportViewModel.SECONDARY_BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(Short.MAX_VALUE,
                ImportExportViewModel.SECONDARY_BUTTON_HEIGHT));

        addSecondaryButtonHoverEffect(button);
        return button;
    }

    private void addSecondaryButtonHoverEffect(JButton button) {
        final Color originalColor = button.getBackground();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ImportExportViewModel.SECONDARY_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    private JButton createSuccessButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(ImportExportViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(ImportExportViewModel.SUCCESS_COLOUR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addSuccessButtonHoverEffect(button);
        return button;
    }

    private void addSuccessButtonHoverEffect(JButton button) {
        final Color originalColor = button.getBackground();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ImportExportViewModel.SUCCESS_HOVER);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
    }

    private JButton createTextButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(ImportExportViewModel.BUTTON_SECONDARY_FONT);
        button.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        addTextButtonHoverEffect(button);
        return button;
    }

    private void addTextButtonHoverEffect(JButton button) {
        final Color originalColor = button.getForeground();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(ImportExportViewModel.TEXT_SECONDARY);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(originalColor);
            }
        });
    }

    private void styleDropdown(JComboBox<?> dropdown) {
        dropdown.setFont(ImportExportViewModel.DROPDOWN_FONT);
        dropdown.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        dropdown.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ImportExportViewModel.BORDER_COLOUR,
                        ImportExportViewModel.ONE, true),
                new EmptyBorder(ImportExportViewModel.DROPDOWN_PADDING_VERTICAL,
                        ImportExportViewModel.DROPDOWN_PADDING_HORIZONTAL,
                        ImportExportViewModel.DROPDOWN_PADDING_VERTICAL,
                        ImportExportViewModel.DROPDOWN_PADDING_HORIZONTAL)
        ));
        dropdown.setPreferredSize(new Dimension(ImportExportViewModel.BUTTON_WIDTH,
                ImportExportViewModel.DROPDOWN_HEIGHT));
        dropdown.setMaximumSize(new Dimension(Short.MAX_VALUE,
                ImportExportViewModel.DROPDOWN_HEIGHT));
    }

    private void setupListeners() {
        importPortfolioButton.addActionListener(evt -> handleImportPortfolio());
        currentSessionButton.addActionListener(event -> handleExportCurrentSession());
        exportPortfolioButton.addActionListener(event -> handleExportPortfolio());
        selectSimDataButton.addActionListener(event -> handleExportSimData());
        backButton.addActionListener(evt -> this.getChangeViewController().backView());
    }

    private void handleImportPortfolio() {
        final String path = getCsvFilePath();
        if (path != null) {
            this.getController().importPortfolio(path);
        }
    }

    private void handleExportCurrentSession() {
        final String path = getDirectoryPath("ticker_history.csv");
        if (path == null) {
            errorLabel.setText(ImportExportViewModel.ERROR_INVALID_PATH);
        }
        else {
            this.getController().exportCurrentSession(path);
        }

    }

    private void handleExportPortfolio() {
        final Portfolio selected = (Portfolio) portfolioDropdown.getSelectedItem();
        if (selected == null) {
            errorLabel.setText(ImportExportViewModel.ERROR_NO_PORTFOLIO);
        }
        else {
            final String path = getDirectoryPath("portfolios.csv");
            if (path == null) {
                errorLabel.setText(ImportExportViewModel.ERROR_INVALID_PATH);
            }
            else {
                final PortfolioList portfolioList = new PortfolioList();
                portfolioList.addPortfolio(selected);
                this.getController().exportPortfolio(portfolioList, path);
            }

        }

    }

    private void handleExportSimData() {
        final String path = getDirectoryPath("simulations.csv");
        if (path == null) {
            errorLabel.setText(ImportExportViewModel.ERROR_INVALID_PATH);
        }
        else {
            this.getController().exportSimData(path);
        }

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println("Click " + event.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Property change handling can be implemented here if needed
    }

    private String getCsvFilePath() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(ImportExportViewModel.CSV_DIALOG_TITLE);
        final FileNameExtensionFilter filter = new FileNameExtensionFilter(
                ImportExportViewModel.CSV_FILTER_DESC, ImportExportViewModel.CSV_EXTENSION);
        chooser.setFileFilter(filter);
        final int result = chooser.showOpenDialog(this);

        String abs = "";
        if (result == JFileChooser.APPROVE_OPTION) {
            abs = chooser.getSelectedFile().getAbsolutePath();
        }
        else {
            abs = null;
        }
        return abs;
    }

    private String getDirectoryPath(String defaultFileName) {
        final JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setSelectedFile(new java.io.File(defaultFileName));
        chooser.setDialogTitle(ImportExportViewModel.FOLDER_DIALOG_TITLE);
        final int result = chooser.showSaveDialog(this);

        String abs = "";
        if (result == JFileChooser.APPROVE_OPTION) {
            abs = chooser.getSelectedFile().getAbsolutePath();
        }
        else {
            abs = null;
        }
        return abs;
    }
}
