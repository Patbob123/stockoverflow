package interface_adapter.mainmenu;

import interface_adapter.ViewManagerModel;
import interface_adapter.Login.LoginViewModel;
import interface_adapter.Login.LoginState;
import interface_adapter.create_portfolio.CreatePortfolioState;
import interface_adapter.create_portfolio.CreatePortfolioViewModel;
import interface_adapter.add_stock.AddStockViewModel;
import interface_adapter.add_stock.AddStockState;
import interface_adapter.singlestock.SingleStockViewModel;
import interface_adapter.singlestock.SingleStockState;
import use_case.mainmenu.MainMenuOutputBoundary;

public class MainMenuPresenter implements MainMenuOutputBoundary {

    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoginViewModel loginViewModel;
    private final CreatePortfolioViewModel createPortfolioViewModel;
    private final AddStockViewModel addStockViewModel;
    private final SingleStockViewModel singleStockViewModel;

    public MainMenuPresenter(ViewManagerModel viewManagerModel,
                             MainMenuViewModel mainMenuViewModel,
                             LoginViewModel loginViewModel,
                             CreatePortfolioViewModel createPortfolioViewModel,
                             AddStockViewModel addStockViewModel,
                             SingleStockViewModel singleStockViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.loginViewModel = loginViewModel;
        this.createPortfolioViewModel = createPortfolioViewModel;
        this.addStockViewModel = addStockViewModel;
        this.singleStockViewModel = singleStockViewModel;
    }

    @Override
    public void prepareLoginView() {
        MainMenuState mainState = mainMenuViewModel.getState();
        mainState.setUsername("");
        mainMenuViewModel.setState(mainState);
        mainMenuViewModel.firePropertyChanged();

        LoginState loginState = loginViewModel.getState();
        loginState.setPassword("");
        loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void preparePortfolioView() {
        String currentUser = mainMenuViewModel.getState().getUsername();
        CreatePortfolioState portfolioState = createPortfolioViewModel.getState();
        portfolioState.setUsername(currentUser);
        portfolioState.setError(null);

        createPortfolioViewModel.setState(portfolioState);
        createPortfolioViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(createPortfolioViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareSingleStockView() {
        SingleStockState state = singleStockViewModel.getState();
        state.setReport("");
        state.setErrorMessage(null);
        singleStockViewModel.setState(state);
        singleStockViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(SingleStockViewModel.VIEW_NAME);
        viewManagerModel.firePropertyChanged();
    }

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