package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel searchViewModel;

    public SearchPresenter(SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(SearchOutputData response) {
        SearchState state = searchViewModel.getState();
        state.setSearchResults(response.getSearchResults());
        state.setError(null);

        searchViewModel.setState(state);
        searchViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        SearchState state = searchViewModel.getState();
        state.setError(error);
        searchViewModel.firePropertyChanged();
    }
}