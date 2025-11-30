package app;

import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import interface_adapter.show_graph.ShowGraphController; // View 需要用到这个来画图
import interface_adapter.ViewManagerModel;
import use_case.APIDataAccessInterface;
import use_case.search.SearchInputBoundary;
import interface_adapter.search.SearchInteractor;
import use_case.search.SearchOutputBoundary;
import view.SearchView;

public class SearchUseCaseFactory {
    private SearchUseCaseFactory() {}

    public static SearchView create(
            ViewManagerModel viewManagerModel,
            SearchViewModel searchViewModel,
            ShowGraphController showGraphController,
            APIDataAccessInterface apiDataAccessObject) {

        SearchController searchController = createSearchUseCase(searchViewModel, apiDataAccessObject);

        return new SearchView(searchViewModel, searchController, showGraphController, viewManagerModel);
    }

    private static SearchController createSearchUseCase(
            SearchViewModel searchViewModel,
            APIDataAccessInterface apiDataAccessObject) {

        SearchOutputBoundary searchOutputBoundary = new SearchPresenter(searchViewModel);
        SearchInputBoundary searchInteractor = new SearchInteractor(apiDataAccessObject, searchOutputBoundary);

        return new SearchController(searchInteractor);
    }
}