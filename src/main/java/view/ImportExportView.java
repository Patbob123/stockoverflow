package view;

import entities.Portfolio.Portfolio;
import entities.Simulation;
import interface_adapter.change_view.ChangeViewController;
import interface_adapter.create_portfolio.ImportExportController;
import interface_adapter.create_portfolio.ImportExportViewModel;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



public class ImportExportView extends PaddedView implements ActionListener, PropertyChangeListener {

    @Getter
    private final String viewName = "ImportExportMenu";
    private final ImportExportViewModel importExportViewModel;

    @Setter
    private ImportExportController importExportController;
    @Setter
    private ChangeViewController changeViewController;

    private final JButton currentSessionButton = new JButton(ImportExportViewModel.CURRENT_SESSION_BUTTON_LABEL);
    private final JButton exportPortfolioButton = new JButton(ImportExportViewModel.EXPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton selectSimDataButton = new JButton(ImportExportViewModel.SELECT_SIMDATA_BUTTON_LABEL);
    private final JButton importPortfolioButton = new JButton(ImportExportViewModel.IMPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton backButton = new JButton(ImportExportViewModel.BACK_BUTTON_LABEL);
    private final JLabel errorLabel = new JLabel();


    private final JComboBox<Portfolio> portfolioDropdown = new JComboBox<>();
    private final JComboBox<Simulation> simulationDropdown = new JComboBox<>();

    public ImportExportView(ImportExportViewModel importExportViewModel) {
        super(ImportExportViewModel.PADDING);
        // noteName.setAlignmentX(Component.CENTER_ALIGNMENT); ADD DATE HERE TOO
        this.importExportViewModel = importExportViewModel;
        this.importExportViewModel.addPropertyChangeListener(this);
        this.importExportController = null;
        this.changeViewController = null;

        // MESSY LAYOUT INCOMING
        setLayout(new BorderLayout());

        // TOP BAR PANEL
        final JPanel topPanel = new JPanel(new BorderLayout());

        backButton.setFocusable(false);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        topPanel.add(backButton, BorderLayout.WEST);

        final JLabel title = new JLabel(ImportExportViewModel.TITLE_LABEL, SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));   // TODO: Change to viewmodel later
        topPanel.add(title, BorderLayout.CENTER);

        JButton settingsButton = new JButton("O"); // idk maybe for fun
        settingsButton.setFocusable(false);
        settingsButton.setBorderPainted(false);
        settingsButton.setContentAreaFilled(false);
        topPanel.add(settingsButton, BorderLayout.EAST);

        topPanel.setPreferredSize(new Dimension(0, 60));
        topPanel.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.GRAY
        ));

        add(topPanel, BorderLayout.NORTH);



        // LEFT SIDE PANEL
        final JPanel exportPanel = new JPanel();
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
        exportPanel.setBorder(BorderFactory.createTitledBorder(ImportExportViewModel.EXPORT_TITLE_LABEL));


        //current session
        JPanel sessionBlock = new JPanel();
        sessionBlock.setLayout(new BoxLayout(sessionBlock, BoxLayout.Y_AXIS));
        sessionBlock.setBorder(BorderFactory.createTitledBorder("Current Session"));

        JPanel sessionRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sessionRow.add(currentSessionButton);

        sessionBlock.add(sessionRow);
        sessionBlock.setMaximumSize(new Dimension(
                Integer.MAX_VALUE,
                ImportExportViewModel.BLOCK_HEIGHT
        ));

        exportPanel.add(sessionBlock);

        // PORTFOLIO PANEL THING
        JPanel portfolioBlock = new JPanel();
        portfolioBlock.setLayout(new BoxLayout(portfolioBlock, BoxLayout.Y_AXIS));
        portfolioBlock.setBorder(BorderFactory.createTitledBorder("Portfolio"));

        portfolioDropdown.setPreferredSize(new Dimension(
                ImportExportViewModel.DROPDOWN_WIDTH,
                ImportExportViewModel.DROPDOWN_HEIGHT
        ));
        portfolioDropdown.setMaximumSize(new Dimension(
                Short.MAX_VALUE,
                ImportExportViewModel.DROPDOWN_HEIGHT
        ));

        JPanel portfolioDropdownRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        portfolioDropdownRow.add(portfolioDropdown);
        portfolioBlock.add(portfolioDropdownRow);

        JPanel portfolioExportRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        portfolioExportRow.add(new JLabel("📁"));
        portfolioExportRow.add(exportPortfolioButton);
        portfolioBlock.add(portfolioExportRow);

        portfolioBlock.setMaximumSize(new Dimension(
                Integer.MAX_VALUE,
                ImportExportViewModel.BLOCK_HEIGHT
        ));

        exportPanel.add(portfolioBlock);

        // I DONT WANT TO DO THIS ANYMORE
        JPanel simulationBlock = new JPanel();
        simulationBlock.setLayout(new BoxLayout(simulationBlock, BoxLayout.Y_AXIS));
        simulationBlock.setBorder(BorderFactory.createTitledBorder("Simulation"));

        simulationDropdown.setPreferredSize(new Dimension(
                ImportExportViewModel.DROPDOWN_WIDTH,
                ImportExportViewModel.DROPDOWN_HEIGHT
        ));
        simulationDropdown.setMaximumSize(new Dimension(
                Short.MAX_VALUE,
                ImportExportViewModel.DROPDOWN_HEIGHT
        ));

        JPanel simulationDropdownRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        simulationDropdownRow.add(simulationDropdown);
        simulationBlock.add(simulationDropdownRow);

        JPanel simulationExportRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        simulationExportRow.add(new JLabel("▶"));
        simulationExportRow.add(selectSimDataButton);
        simulationBlock.add(simulationExportRow);

        simulationBlock.setMaximumSize(new Dimension(
                Integer.MAX_VALUE,
                ImportExportViewModel.BLOCK_HEIGHT
        ));

        exportPanel.add(simulationBlock);




        // RIGHT SIDE PANEL
        final JPanel importPanel = new JPanel();
        importPanel.setLayout(new BoxLayout(importPanel, BoxLayout.Y_AXIS));
        importPanel.setBorder(BorderFactory.createTitledBorder(ImportExportViewModel.IMPORT_TITLE_LABEL));

        importPanel.add(Box.createVerticalGlue());
        importPanel.add(importPortfolioButton);
        importPanel.add(Box.createVerticalGlue());


        //configure the frkijg middle panel

        int panelWidth = 360;
        exportPanel.setPreferredSize(new Dimension(panelWidth, exportPanel.getPreferredSize().height));
        importPanel.setPreferredSize(new Dimension(panelWidth, importPanel.getPreferredSize().height));

        exportPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        importPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        final JPanel lowerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        lowerPanel.add(exportPanel, gbc);

        gbc.gridx = 1;
        lowerPanel.add(importPanel, gbc);

        add(lowerPanel, BorderLayout.CENTER);

        final JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        errorPanel.add(errorLabel);
        add(errorPanel, BorderLayout.SOUTH);

        importPortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(importPortfolioButton)) {
                            String path = getCsvFilePath();
                            importExportController.importPortfolio(path);

                    }
                }
        );

        currentSessionButton.addActionListener(e -> {
            String path = getDirectoryPath();
            if (path == null) {
                errorLabel.setText("Invalid file path");
                return;
            }
            importExportController.exportCurrentSession(path);
        });

        exportPortfolioButton.addActionListener(e -> {
            Portfolio selected = (Portfolio) portfolioDropdown.getSelectedItem();
            String path = getDirectoryPath();
            if (path == null) {
                errorLabel.setText("Invalid file path");
                return;
            }
            if (selected == null) {
                errorLabel.setText("Choose a portfolio first");
                return;
            }
            importExportController.exportPortfolio(selected, path);

        });

        selectSimDataButton.addActionListener(e -> {
            Simulation selected = (Simulation) simulationDropdown.getSelectedItem();
            String path = getDirectoryPath();
            if (path == null) {
                errorLabel.setText("Invalid file path");
                return;
            }
            if (selected == null) {
                errorLabel.setText("Choose a simulation first");
                return;
            }

            importExportController.exportSimData(selected, path);

        });

        backButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(backButton)) {
                        changeViewController.changeView("MainMenu");

                    }
                }
        );


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    private String getCsvFilePath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select CSV file to import");

        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
        chooser.setFileFilter(filter);

        int result = chooser.showSaveDialog(this);

        return result == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile().getAbsolutePath(): null;
    }

    private String getDirectoryPath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select folder to export");

        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = chooser.showSaveDialog(this);

        return result == JFileChooser.APPROVE_OPTION ? chooser.getSelectedFile().getAbsolutePath(): null;
    }
}
