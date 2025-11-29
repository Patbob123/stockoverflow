package interface_adapter.mainmenu;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_stock.AddStockState;
import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.create_portfolio.CreatePortfolioState;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.search.SearchViewModel;

public class MainMenuController {

    private final ViewManagerModel viewManagerModel;
    private final SearchViewModel searchViewModel;
    private final CreatePortfolioViewModel createPortfolioViewModel;
    private final AddStockViewModel addStockViewModel;

    public MainMenuController(ViewManagerModel viewManagerModel,
                              SearchViewModel searchViewModel,
                              CreatePortfolioViewModel createPortfolioViewModel,
                              AddStockViewModel addStockViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
        this.createPortfolioViewModel = createPortfolioViewModel;
        this.addStockViewModel = addStockViewModel;
    }

    public void goToSearch() {
        viewManagerModel.setActiveView(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void goToPortfolio(String username) {
        CreatePortfolioState state = createPortfolioViewModel.getState();
        state.setUsername(username);
        createPortfolioViewModel.setState(state);
        createPortfolioViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(createPortfolioViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }


    public void goToAddStock(String portfolioName, String username) {
        AddStockState state = addStockViewModel.getState();
        state.setPortfolioName(portfolioName);
        state.setUsername(username);
        state.getSearchResults().clear();

        addStockViewModel.setState(state);
        addStockViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(addStockViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    public void executeLogout() {
        System.out.println("Logging out...");
        viewManagerModel.setActiveView("log in");
        viewManagerModel.firePropertyChanged();
    }
}