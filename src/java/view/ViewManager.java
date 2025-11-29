package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The View Manager for the program. It listens for property change events
 * in the ViewManagerModel and updates which View should be visible.
 */
public class ViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final ViewManagerModel viewManagerModel;

    private final Map<String, JPanel> views = new HashMap<>();

    public ViewManager(JPanel mainPanel, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    public void addView(String name, JPanel panel) {
        views.put(name, panel);
        mainPanel.add(panel, name);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final String viewModelName = (String) evt.getNewValue();
            System.out.println("A"+viewModelName);
            cardLayout.show(mainPanel, viewModelName);
        }
    }
}