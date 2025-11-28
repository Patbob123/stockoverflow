package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import entities.Portfolio.Portfolio;
import entities.PortfolioList;
import entities.Simulation;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.import_export.ImportExportController;
import interface_adapter.import_export.ImportExportViewModel;
import lombok.Getter;
import lombok.Setter;

public class ImportExportView extends PaddedView<ImportExportViewModel, ImportExportController> implements ActionListener, PropertyChangeListener {
    public static final String VIEW_NAME = "ImportExportMenu";
    @Setter
    private ImportExportController controller;
    @Setter
    private ChangeViewController changeViewController;

    private final JButton currentSessionButton = createPrimaryButton(ImportExportViewModel.CURRENT_SESSION_BUTTON_LABEL);
    private final JButton exportPortfolioButton = createSecondaryButton(ImportExportViewModel.EXPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton selectSimDataButton = createSecondaryButton(ImportExportViewModel.SELECT_SIMDATA_BUTTON_LABEL);
    private final JButton importPortfolioButton = createSuccessButton(ImportExportViewModel.IMPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton backButton = createTextButton(ImportExportViewModel.BACK_BUTTON_LABEL);
    private final JLabel errorLabel = new JLabel();

    private final JComboBox<Portfolio> portfolioDropdown = new JComboBox<>();
    private final JComboBox<Simulation> simulationDropdown = new JComboBox<>();

    public ImportExportView(ImportExportViewModel importExportViewModel) {
        super(importExportViewModel);
        this.getViewModel().addPropertyChangeListener(this);
        this.controller = null;
        this.changeViewController = null;
        createView();
    }

    private void createView() {

        setLayout(new BorderLayout());

        final JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(ImportExportViewModel.CARD_COLOUR);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, ImportExportViewModel.BORDER_COLOUR),
                new EmptyBorder(20, 30, 20, 30)
        ));

        topPanel.add(backButton, BorderLayout.WEST);

        final JLabel title = new JLabel(ImportExportViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(ImportExportViewModel.TITLE_FONT);
        title.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        topPanel.add(title, BorderLayout.CENTER);

        final JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        spacer.setPreferredSize(new Dimension(100, 0));
        topPanel.add(spacer, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // main panel
        final JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 10, 0, 10);

        // EXPORT PANEL
        final JPanel exportPanel = createModernCard();
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));

        // the text + the icons, also no more settings who needs that
        final JPanel exportHeader = getJpanel();
        exportPanel.add(exportHeader);
        exportPanel.add(Box.createVerticalStrut(20));

        //  Cur session
        final JPanel sessionSection = createSection(ImportExportViewModel.CURRENT_SESSION_TITLE);
        currentSessionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        sessionSection.add(currentSessionButton);
        exportPanel.add(sessionSection);
        exportPanel.add(Box.createVerticalStrut(15));

        // portfolio
        final JPanel portfolioSection = createSection(ImportExportViewModel.PORTFOLIO_TITLE);
        styleDropdown(portfolioDropdown);
        portfolioDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        portfolioSection.add(portfolioDropdown);
        portfolioSection.add(Box.createVerticalStrut(10));
        exportPortfolioButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        portfolioSection.add(exportPortfolioButton);
        exportPanel.add(portfolioSection);
        exportPanel.add(Box.createVerticalStrut(15));

        // simulation
        final JPanel simulationSection = createSection(ImportExportViewModel.SIMULATION_TITLE);
        styleDropdown(simulationDropdown);
        simulationDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
        simulationSection.add(simulationDropdown);
        simulationSection.add(Box.createVerticalStrut(10));
        selectSimDataButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        simulationSection.add(selectSimDataButton);
        exportPanel.add(simulationSection);

        exportPanel.add(Box.createVerticalGlue());

        gbc.gridx = 0;
        mainPanel.add(exportPanel, gbc);

        // IMPORT PANEL
        JPanel importPanel = createModernCard();
        importPanel.setLayout(new BoxLayout(importPanel, BoxLayout.Y_AXIS));

        // copy paste from above
        JPanel importHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        importHeader.setOpaque(false);
        JLabel importIcon = new JLabel("⬆");
        importIcon.setFont(ImportExportViewModel.ICON_FONT);
        importIcon.setForeground(ImportExportViewModel.SUCCESS_COLOUR);
        final JLabel importTitle = new JLabel(ImportExportViewModel.IMPORT_TITLE_LABEL);
        importTitle.setFont(ImportExportViewModel.HEADER_FONT);
        importTitle.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        importHeader.add(importIcon);
        importHeader.add(importTitle);
        importPanel.add(importHeader);

        importPanel.add(Box.createVerticalGlue());

        // remember to add computer icon later
        final JPanel importCenter = new JPanel();
        importCenter.setLayout(new BoxLayout(importCenter, BoxLayout.Y_AXIS));
        importCenter.setOpaque(false);

        importPortfolioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        importPortfolioButton.setPreferredSize(new Dimension(280, 50));
        importPortfolioButton.setMaximumSize(new Dimension(280, 50));
        importCenter.add(importPortfolioButton);

        importCenter.add(Box.createVerticalStrut(15));

        final JLabel hint = new JLabel(ImportExportViewModel.IMPORT_HINT);
        hint.setFont(ImportExportViewModel.HINT_FONT);
        hint.setForeground(ImportExportViewModel.TEXT_SECONDARY);
        hint.setAlignmentX(Component.CENTER_ALIGNMENT);
        importCenter.add(hint);

        importPanel.add(importCenter);
        importPanel.add(Box.createVerticalGlue());

        gbc.gridx = 1;
        mainPanel.add(importPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // beeg error text
        final JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        errorPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        errorLabel.setFont(ImportExportViewModel.ERROR_FONT);
        errorLabel.setForeground(new Color(220, 38, 38));
        errorPanel.add(errorLabel);
        add(errorPanel, BorderLayout.SOUTH);

        setupListeners();
    }

    private static JPanel getJpanel() {
        final JPanel exportHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        exportHeader.setOpaque(false);
        final JLabel exportIcon = new JLabel("⬇");
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
                new LineBorder(ImportExportViewModel.BORDER_COLOUR, 1, true),
                new EmptyBorder(25, 25, 25, 25)
        ));
        return card;
    }

    private JPanel createSection(String title) {
        final JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ImportExportViewModel.BORDER_COLOUR, 1, true),
                new EmptyBorder(ImportExportViewModel.PADDING, ImportExportViewModel.PADDING,
                        ImportExportViewModel.PADDING, ImportExportViewModel.PADDING)
        ));

        final JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(ImportExportViewModel.SECTION_TITLE_FONT);
        sectionTitle.setForeground(ImportExportViewModel.TEXT_SECONDARY);
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        section.add(sectionTitle);
        section.add(Box.createVerticalStrut(ImportExportViewModel.EXPORT_VERTICAL_STRUT));

        return section;
    }

    private JButton createPrimaryButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(ImportExportViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(ImportExportViewModel.PRIMARY_COLOUR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(250, 45));
        button.setMaximumSize(new Dimension(Short.MAX_VALUE, 45));

        final Color originalColor = button.getBackground();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ImportExportViewModel.PRIMARY_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        return button;
    }

    private JButton createSecondaryButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(ImportExportViewModel.BUTTON_SECONDARY_FONT);
        button.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(ImportExportViewModel.BORDER_COLOUR, 1, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(250, 38));
        button.setMaximumSize(new Dimension(Short.MAX_VALUE, 38));

        final Color originalColor = button.getBackground();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ImportExportViewModel.SECONDARY_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        return button;
    }

    private JButton createSuccessButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(ImportExportViewModel.BUTTON_PRIMARY_FONT);
        button.setBackground(ImportExportViewModel.SUCCESS_COLOUR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final Color originalColor = button.getBackground();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ImportExportViewModel.SUCCESS_HOVER);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        return button;
    }

    private JButton createTextButton(String text) {
        final JButton button = new JButton(text);
        button.setFont(ImportExportViewModel.BUTTON_SECONDARY_FONT);
        button.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final Color originalColor = button.getForeground();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(ImportExportViewModel.TEXT_SECONDARY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(originalColor);
            }
        });
        return button;
    }

    private void styleDropdown(JComboBox<?> dropdown) {
        dropdown.setFont(ImportExportViewModel.DROPDOWN_FONT);
        dropdown.setForeground(ImportExportViewModel.TEXT_PRIMARY);
        dropdown.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(ImportExportViewModel.BORDER_COLOUR, 1, true),
                new EmptyBorder(8, 12, 8, 12)
        ));
        dropdown.setPreferredSize(new Dimension(250, 40));
        dropdown.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));
    }

    private void setupListeners() {
        importPortfolioButton.addActionListener(evt -> {
            final String path = getCsvFilePath();
            if (path != null) {
                controller.importPortfolio(path);
            }
        });

        currentSessionButton.addActionListener(e -> {
            final String path = getDirectoryPath();
            if (path == null) {
                errorLabel.setText(ImportExportViewModel.ERROR_INVALID_PATH);
                return;
            }
            controller.exportCurrentSession(path);
        });

        exportPortfolioButton.addActionListener(e -> {
            final Portfolio selected = (Portfolio) portfolioDropdown.getSelectedItem();
            PortfolioList portfolioList = new PortfolioList();
            portfolioList.addPortfolio(selected);
            final String path = getDirectoryPath();
            if (path == null) {
                errorLabel.setText(ImportExportViewModel.ERROR_INVALID_PATH);
                return;
            }
            if (selected == null) {
                errorLabel.setText(ImportExportViewModel.ERROR_NO_PORTFOLIO);
                return;
            }
            controller.exportPortfolio(portfolioList, path);
        });

        selectSimDataButton.addActionListener(e -> {
            final Simulation selected = (Simulation) simulationDropdown.getSelectedItem();
            final String path = getDirectoryPath();
            if (path == null) {
                errorLabel.setText(ImportExportViewModel.ERROR_INVALID_PATH);
                return;
            }
            if (selected == null) {
                errorLabel.setText(ImportExportViewModel.ERROR_NO_SIMULATION);
                return;
            }
            controller.exportSimData(selected, path);
        });

        backButton.addActionListener(evt -> {
            changeViewController.backView();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    private String getCsvFilePath() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(ImportExportViewModel.CSV_DIALOG_TITLE);
        final FileNameExtensionFilter filter = new FileNameExtensionFilter(ImportExportViewModel.CSV_FILTER_DESC, ImportExportViewModel.CSV_EXTENSION);
        chooser.setFileFilter(filter);
        final int result = chooser.showOpenDialog(this);
        return result == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile().getAbsolutePath() : null;
    }

    private String getDirectoryPath() {
        final JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(ImportExportViewModel.FOLDER_DIALOG_TITLE);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        final int result = chooser.showSaveDialog(this);
        return result == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile().getAbsolutePath() : null;
    }
}