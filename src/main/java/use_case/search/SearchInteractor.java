package use_case.search;

import use_case.APIDataAccessInterface;
import java.util.List;

public class SearchInteractor implements SearchInputBoundary {
    final APIDataAccessInterface apiDataAccessObject;
    final SearchOutputBoundary searchPresenter;

    public SearchInteractor(APIDataAccessInterface apiDataAccessInterface,
                            SearchOutputBoundary searchOutputBoundary) {
        this.apiDataAccessObject = apiDataAccessInterface;
        this.searchPresenter = searchOutputBoundary;
    }

    @Override
    public void execute(SearchInputData searchInputData) {
        String query = searchInputData.getQuery();
        if (query == null || query.trim().isEmpty()) {
            searchPresenter.prepareFailView("Please enter a search term.");
            return;
        }

        try {
            // 使用 DAO 的 searchSymbols 方法 (连接 AlphaVantage)
            List<String> results = apiDataAccessObject.searchSymbols(query);

            if (results.isEmpty()) {
                searchPresenter.prepareFailView("No stocks found for: " + query);
            } else {
                searchPresenter.prepareSuccessView(new SearchOutputData(results, false));
            }
        } catch (Exception e) {
            searchPresenter.prepareFailView("Network error during search.");
        }
    }
}