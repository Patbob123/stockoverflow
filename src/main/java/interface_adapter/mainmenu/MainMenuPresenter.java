package interface_adapter.mainmenu;

import interface_adapter.ViewManagerModel;
import interface_adapter.Login.LoginViewModel;
import interface_adapter.Login.LoginState;
import interface_adapter.create_portfolio.CreatePortfolioState;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.search.SearchViewModel;
import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.add_stock.AddStockState;
import use_case.mainmenu.MainMenuOutputBoundary;

public class MainMenuPresenter implements MainMenuOutputBoundary {

    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final CreatePortfolioViewModel createPortfolioViewModel;
    private final SearchViewModel searchViewModel;
    // Added to support navigation to AddStockView
    private final AddStockViewModel addStockViewModel;

    public MainMenuPresenter(ViewManagerModel viewManagerModel,
                             MainMenuViewModel mainMenuViewModel,
                             LoginViewModel loginViewModel,
                             CreatePortfolioViewModel createPortfolioViewModel,
                             SearchViewModel searchViewModel,
                             AddStockViewModel addStockViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.loginViewModel = loginViewModel;
        this.createPortfolioViewModel = createPortfolioViewModel;
        this.searchViewModel = searchViewModel;
        this.addStockViewModel = addStockViewModel;
    }

    @Override
    public void prepareLoginView() {
        // Clear Main Menu State
        MainMenuState mainState = mainMenuViewModel.getState();
        mainState.setUsername("");
        mainMenuViewModel.setState(mainState);
        mainMenuViewModel.firePropertyChanged();

        // Clear Login State (security)
        LoginState loginState = loginViewModel.getState();
        loginState.setPassword("");
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();

        // Switch to Login View
        viewManagerModel.setActiveView(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void preparePortfolioView() {
        // Pass the current username to the CreatePortfolio State
        String currentUser = mainMenuViewModel.getState().getUsername();

        CreatePortfolioState portfolioState = createPortfolioViewModel.getState();
        portfolioState.setUsername(currentUser);
        // Reset error state if any
        portfolioState.setError(null);

        createPortfolioViewModel.setState(portfolioState);
        createPortfolioViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(createPortfolioViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSearchView() {
        viewManagerModel.setActiveView(searchViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepares the Add Stock View.
     * This needs to be defined in MainMenuOutputBoundary interface.
     */
    @Override
    public void prepareAddStockView(String portfolioName, String username) {
        AddStockState state = addStockViewModel.getState();
        state.setPortfolioName(portfolioName);
        state.setUsername(username);

        state.setMessage(null);

        addStockViewModel.setState(state);
        addStockViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(addStockViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}