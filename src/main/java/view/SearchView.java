package view;

import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import interface_adapter.show_graph.ShowGraphController;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "search stock";

    private final SearchViewModel searchViewModel;
    private final SearchController searchController;
    private final ShowGraphController showGraphController;
    private final ViewManagerModel viewManagerModel;


    private final JTextField searchInputField = new JTextField(15);
    private final JButton searchButton = new JButton("Search Symbols");


    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> resultList = new JList<>(listModel);

    private final JButton plotButton = new JButton("Plot Selected Graph");
    private final JButton backButton = new JButton("Back");

    public SearchView(SearchViewModel searchViewModel,
                      SearchController searchController,
                      ShowGraphController showGraphController,
                      ViewManagerModel viewManagerModel) {

        this.searchViewModel = searchViewModel;
        this.searchController = searchController;
        this.showGraphController = showGraphController;
        this.viewManagerModel = viewManagerModel;

        this.searchViewModel.addPropertyChangeListener(this);
        this.setLayout(new BorderLayout(10, 10));

        // --- Top: Title ---
        JLabel title = new JLabel(SearchViewModel.TITLE_LABEL);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(title, BorderLayout.NORTH);

        // --- Center: Search Box & List ---
        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Keyword:"));
        inputPanel.add(searchInputField);
        inputPanel.add(searchButton);

        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(resultList), BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);

        // --- Bottom: Buttons ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(plotButton);
        bottomPanel.add(backButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        // --- Action Listeners ---

        // 1. Search Logic
        searchButton.addActionListener(e -> {
            String query = searchInputField.getText();
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    searchController.execute(query);
                    return null;
                }
            }.execute();
        });

        // 2. Plot Logic
        plotButton.addActionListener(e -> {
            String selected = resultList.getSelectedValue();
            if (selected != null) {
                String ticker = selected.split(" - ")[0];
                showGraphController.execute(ticker);
            } else {
                String directText = searchInputField.getText();
                if (!directText.isEmpty()) {
                    showGraphController.execute(directText);
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a stock to plot.");
                }
            }
        });

        // 3. Back Logic
        backButton.addActionListener(e -> {
            viewManagerModel.setActiveView("main menu");
            viewManagerModel.firePropertyChanged();
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SearchState state = (SearchState) evt.getNewValue();

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
            state.setError(null);
        }

        if (state.getSearchResults() != null) {
            listModel.clear();
            for (String result : state.getSearchResults()) {
                listModel.addElement(result);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
}