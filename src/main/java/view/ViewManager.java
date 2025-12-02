package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import lombok.Getter;

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

    /**
     * Map of view names to PaddedView objects
     * Allows lookup of views
     */
    @Getter
    private final Map<String, PaddedView<?, ?>> views = new HashMap<>();

    /**
     * Constructs a ViewManager with the specified components.
     *
     * @param mainPanel the JPanel containing all views using CardLayout
     * @param cardLayout the CardLayout manager for switching between views
     * @param viewManagerModel same model in the sample code that tracks the views
     */
    public ViewManager(JPanel mainPanel, CardLayout cardLayout, ViewManagerModel viewManagerModel) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    /**
     * Adds a view to the manager and the main panel
     *
     * @param name the name of the view
     * @param panel the PaddedView to add
     */
    public void addView(String name, PaddedView<?, ?> panel) {
        views.put(name, panel);
        mainPanel.add(panel, name);
    }

    /**
     * Gets view model of a view
     *
     * @param viewName the name of the view
     * @return the ViewModel for the specified view, or null if view doesn't exist
     */
    private ViewModel<?> getViewModel(String viewName) {
        final PaddedView<?, ?> view = views.get(viewName);
        return view != null ? view.getViewModel() : null;
    }

    /**
     * Handles property change events from the ViewManagerModel
     * @param evt the PropertyChangeEvent containing the event details
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final String propertyName = evt.getPropertyName();

        if (propertyName.equals(ViewModel.getDEFAULT_PROPERTY_NAME())) {
            final String viewModelName = (String) evt.getNewValue();

            if (views.containsKey(viewModelName)) {
                cardLayout.show(mainPanel, viewModelName);
            } else {
                System.err.println("ERROR: Attempted to switch to non-existent view: " + viewModelName);
            }
        }
        else if (propertyName.equals(ViewManagerModel.GET_VIEW_MODEL_NAME)) {
            final String viewModelName = (String) evt.getNewValue();

            if (views.containsKey(viewModelName)) {
                final ViewModel<?> viewModel = getViewModel(viewModelName);
                this.viewManagerModel.setFormativeViewModel(viewModel);
            } else {
                System.err.println("ERROR: No view: " + viewModelName);;
            }
        }
    }
}