package view;

import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;

public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "search stock";

    private final SearchViewModel searchViewModel;
    private final ShowGraphController showGraphController;
    private final ViewManagerModel viewManagerModel;

    private final JTextField tickerInputField = new JTextField(15);
    private final JButton searchButton;
    private final JButton backButton;

    public SearchView(SearchViewModel searchViewModel, ShowGraphController controller, ViewManagerModel viewManagerModel) {
        this.searchViewModel = searchViewModel;
        this.showGraphController = controller;
        this.viewManagerModel = viewManagerModel;

        this.searchViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel(SearchViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel tickerInfo = new LabelTextPanel(
                new JLabel(SearchViewModel.SEARCH_LABEL), tickerInputField);

        JPanel buttons = new JPanel();
        searchButton = new JButton(SearchViewModel.SEARCH_BUTTON_LABEL);
        backButton = new JButton(SearchViewModel.BACK_BUTTON_LABEL);
        buttons.add(searchButton);
        buttons.add(backButton);


        searchButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(searchButton)) {
                            SearchState currentState = searchViewModel.getState();

                            showGraphController.execute(Collections.singletonList(currentState.getTicker()), "search stock");
                        }
                    }
                }
        );

        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(backButton)) {
                            viewManagerModel.setActiveView("main menu");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        tickerInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SearchState currentState = searchViewModel.getState();
                        String text = tickerInputField.getText() + e.getKeyChar();
                        currentState.setTicker(text);
                        searchViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {}

                    @Override
                    public void keyReleased(KeyEvent e) {}
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(tickerInfo);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {}
}