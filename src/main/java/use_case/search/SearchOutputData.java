package use_case.search;

import java.util.List;

public class SearchOutputData {
    private final List<String> searchResults;
    private final boolean useCaseFailed;

    public SearchOutputData(List<String> searchResults, boolean useCaseFailed) {
        this.searchResults = searchResults;
        this.useCaseFailed = useCaseFailed;
    }

    public List<String> getSearchResults() { return searchResults; }
    public boolean isUseCaseFailed() { return useCaseFailed; }
}
