package view;

import interface_adapter.change_view.ChangeViewController;
import interface_adapter.create_portfolio.ImportExportController;
import interface_adapter.create_portfolio.ImportExportViewModel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ImportExportView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "ImportExportMenu";
    private final ImportExportViewModel importExportViewModel;

    private ImportExportController importExportController;
    private ChangeViewController changeViewController;

    private final JButton currentSessionButton = new JButton(ImportExportViewModel.CURRENT_SESSION_BUTTON_LABEL);
    private final JButton exportPortfolioButton = new JButton(ImportExportViewModel.EXPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton selectSimDataButton = new JButton(ImportExportViewModel.SELECT_SIMDATA_BUTTON_LABEL);
    private final JButton importPortfolioButton = new JButton(ImportExportViewModel.IMPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton backButton = new JButton(ImportExportViewModel.BACK_BUTTON_LABEL);

    public ImportExportView(ImportExportViewModel importExportViewModel) {
        // noteName.setAlignmentX(Component.CENTER_ALIGNMENT); ADD DATE HERE TOO
        this.importExportViewModel = importExportViewModel;
        this.importExportViewModel.addPropertyChangeListener(this);
        this.importExportController = null;
        this.changeViewController = null;

        // MESSY LAYOUT INCOMING
        setLayout(new BorderLayout());

        // TOP BAR PANEL
        final JPanel topPanel = new JPanel();
        final JLabel title = new JLabel(ImportExportViewModel.TITLE_LABEL);
        topPanel.add(backButton);
        topPanel.add(title);
        topPanel.setPreferredSize(new Dimension(0, 100));
        add(topPanel, BorderLayout.NORTH);

        // LOWER PANELS
        final JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());

        // LEFT SIDE PANEL
        final JPanel exportPanel = new JPanel();
        exportPanel.setLayout(new BoxLayout(exportPanel, BoxLayout.Y_AXIS));
        exportPanel.setBorder(BorderFactory.createTitledBorder(ImportExportViewModel.EXPORT_TITLE_LABEL));

        exportPanel.add(Box.createVerticalGlue());
        exportPanel.add(currentSessionButton);
        exportPanel.add(Box.createVerticalStrut(ImportExportViewModel.EXPORT_VERTICAL_STRUT));
        exportPanel.add(exportPortfolioButton);
        exportPanel.add(Box.createVerticalStrut(ImportExportViewModel.EXPORT_VERTICAL_STRUT));
        exportPanel.add(selectSimDataButton);
        exportPanel.add(Box.createVerticalGlue());

        lowerPanel.add(exportPanel, BorderLayout.WEST);

        // RIGHT SIDE PANEL
        final JPanel importPanel = new JPanel();
        importPanel.setLayout(new BoxLayout(importPanel, BoxLayout.Y_AXIS));
        importPanel.setBorder(BorderFactory.createTitledBorder(ImportExportViewModel.IMPORT_TITLE_LABEL));

        importPanel.add(Box.createVerticalGlue());
        importPanel.add(importPortfolioButton);
        importPanel.add(Box.createVerticalGlue());

        lowerPanel.add(importPanel, BorderLayout.EAST);

        final JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // horizontal center
        wrapper.add(lowerPanel);
        add(wrapper, BorderLayout.CENTER);

        importPortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(importPortfolioButton)) {
                        JFileChooser chooser = new JFileChooser();

                        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
                        chooser.setFileFilter(filter);

                        int result = chooser.showOpenDialog(this);

                        if (result == JFileChooser.APPROVE_OPTION) {
                            String path = chooser.getSelectedFile().getAbsolutePath();
                            importExportController.importPortfolio(path);
                        }
                    }
                }
        );

        exportPortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(exportPortfolioButton)) {
                        changeViewController.changeView("ImportExportMenu");
                    }
                }
        );
        backButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(backButton)) {
                        changeViewController.changeView("MainMenu");

                    }
                }
        );


    }

    public String getViewName() {
        return viewName;
    }

    public void setCreatePortfolioController(ImportExportController importExportController) {
        this.importExportController = importExportController;
    }

    public void setChangeViewController(ChangeViewController changeViewController) {
        this.changeViewController = changeViewController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
