package view;

import interface_adapter.change_view.ChangeViewController;
import interface_adapter.create_portfolio.CreatePortfolioController;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CreatePortfolioView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "CreatePortfolioMenu";
    private final CreatePortfolioViewModel createPortfolioViewModel;

    private CreatePortfolioController createPortfolioController;
    private ChangeViewController changeViewController;

    private final JButton currentSessionButton = new JButton(CreatePortfolioViewModel.CURRENT_SESSION_BUTTON_LABEL);
    private final JButton exportPortfolioButton = new JButton(CreatePortfolioViewModel.EXPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton selectSimDataButton = new JButton(CreatePortfolioViewModel.SELECT_SIMDATA_BUTTON_LABEL);
    private final JButton importPortfolioButton = new JButton(CreatePortfolioViewModel.IMPORT_PORTFOLIO_BUTTON_LABEL);
    private final JButton backButton = new JButton(CreatePortfolioViewModel.BACK_BUTTON_LABEL);

    public CreatePortfolioView(CreatePortfolioViewModel createPortfolioViewModel) {
        // noteName.setAlignmentX(Component.CENTER_ALIGNMENT); ADD DATE HERE TOO
        this.createPortfolioViewModel = createPortfolioViewModel;
        this.createPortfolioViewModel.addPropertyChangeListener(this);
        this.createPortfolioController = null;
        this.changeViewController = null;

        // MESSY LAYOUT INCOMING
        setLayout(new BorderLayout());

        // TOP BAR PANEL
        final JPanel topPanel = new JPanel();
        final JLabel title = new JLabel(CreatePortfolioViewModel.TITLE_LABEL);
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
        exportPanel.setBorder(BorderFactory.createTitledBorder(CreatePortfolioViewModel.EXPORT_TITLE_LABEL));

        exportPanel.add(Box.createVerticalGlue());
        exportPanel.add(currentSessionButton);
        exportPanel.add(Box.createVerticalStrut(CreatePortfolioViewModel.EXPORT_VERTICAL_STRUT));
        exportPanel.add(exportPortfolioButton);
        exportPanel.add(Box.createVerticalStrut(CreatePortfolioViewModel.EXPORT_VERTICAL_STRUT));
        exportPanel.add(selectSimDataButton);
        exportPanel.add(Box.createVerticalGlue());

        lowerPanel.add(exportPanel, BorderLayout.WEST);

        // RIGHT SIDE PANEL
        final JPanel importPanel = new JPanel();
        importPanel.setLayout(new BoxLayout(importPanel, BoxLayout.Y_AXIS));
        importPanel.setBorder(BorderFactory.createTitledBorder(CreatePortfolioViewModel.IMPORT_TITLE_LABEL));

        importPanel.add(Box.createVerticalGlue());
        importPanel.add(importPortfolioButton);
        importPanel.add(Box.createVerticalGlue());

        lowerPanel.add(importPanel, BorderLayout.EAST);

        final JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // horizontal center
        wrapper.add(lowerPanel);
        add(wrapper, BorderLayout.CENTER);

        exportPortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(exportPortfolioButton)) {
                        changeViewController.changeView("CreatePortfolioMenu");
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

    public void setCreatePortfolioController(CreatePortfolioController createPortfolioController) {
        this.createPortfolioController = createPortfolioController;
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
