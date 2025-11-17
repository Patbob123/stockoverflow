package view;

import interface_adapter.mainmenu.MainMenuController;
import interface_adapter.mainmenu.MainMenuViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import view.portfolio.PortfolioAnalyzerFrame;
import javax.swing.SwingUtilities;

public class MainMenuView extends JPanel implements ActionListener, PropertyChangeListener {

    private final MainMenuViewModel mainMenuViewModel;
    private final MainMenuController mainMenuController;

    private final JButton stockButton = new JButton("Analyze Single Stock");
    private final JButton analyzePortfolioButton = new JButton("Analyze Portfolio");
    private final JButton createPortfolioButton = new JButton("Create/Import Portfolio");
    private final JButton historyStockButton = new JButton("History Single Stock");
    private final JButton exitButton = new JButton("Exit");


    public MainMenuView(MainMenuViewModel mainMenuViewModel) {
        //noteName.setAlignmentX(Component.CENTER_ALIGNMENT); ADD DATE HERE TOO
        this.mainMenuViewModel = mainMenuViewModel;
        this.mainMenuViewModel.addPropertyChangeListener(this);
        this.mainMenuController = null;


        final JPanel buttons = new JPanel();
        buttons.add(stockButton);
        buttons.add(analyzePortfolioButton);
        buttons.add(createPortfolioButton);
        buttons.add(historyStockButton);
        buttons.add(exitButton);

        stockButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(stockButton)) {
                        //MainMenuController.execute(noteInputField.getText());

                    }
                }
        );

        analyzePortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(analyzePortfolioButton)) {
                        //MainMenuController.execute(noteInputField.getText());
                        SwingUtilities.invokeLater(() -> new PortfolioAnalyzerFrame().setVisible(true));
                    }
                }
        );

        createPortfolioButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(createPortfolioButton)) {
                        //MainMenuController.execute(noteInputField.getText());

                    }
                }
        );

        historyStockButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(historyStockButton)) {
                        //MainMenuController.execute(noteInputField.getText());

                    }
                }
        );

        exitButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(exitButton)) {
                        mainMenuController.execute("exit"); //fix this later

                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //this.add(noteName);
        this.add(buttons);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Click " + e.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
