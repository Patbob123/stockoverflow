package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;

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

    private final Map<String, PaddedView> views = new HashMap<>();

    public ViewManager(JPanel mainPanel, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    public void addView(String name, PaddedView panel) {
        views.put(name, panel);
        mainPanel.add(panel, name);
    }

    private ViewModel<?> getViewModel(String viewName) {
        return views.get(viewName).getViewModel();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final String viewModelName = (String) evt.getNewValue();
            if(views.containsKey(viewModelName)){
                cardLayout.show(mainPanel, viewModelName);
            }else{
                System.out.println("view doesnt exist"); // TODO: probably make an exception here?
            }

        }
        else if (evt.getPropertyName().equals("getViewModel")) {
            final String viewModelName = (String) evt.getNewValue();
            if(views.containsKey(viewModelName)){
                this.viewManagerModel.setFormativeViewModel(getViewModel(viewModelName));
            }else{
                System.out.println("view doesnt exist"); // TODO: probably make an exception here?
            }
        }
    }
}