package interface_adapter.user.create;

import use_case.user.create.CreateAccountOutputBoundary;
import use_case.user.create.CreateAccountOutputData;
import view.CreateAccountView;

public class CreateAccountPresenter implements CreateAccountOutputBoundary {
    private final CreateAccountViewModel createAccountViewModel;
    private final LoginViewModel loginViewModel;
    private final CreateAccountView createAccountView;

    public CreateAccountPresenter(CreateAccountViewModel createAccountViewModel,
                                  LoginViewModel loginViewModel,
                                  CreateAccountView createAccountView) {
        this.createAccountViewModel = createAccountViewModel;
        this.loginViewModel = loginViewModel;
        this.createAccountView = createAccountView;
    }

    @Override
    public void prepareSuccessView(CreateAccountOutputData outputData) {
        createAccountViewModel.setMessage("Your account has been created successfully.Please log in");
        createAccountViewModel.setSuccess(true);
        createAccountViewModel.firePropertyChange();

        // Switch to the login view
        createAccountView.switchToLoginView();
    }

    @Override
    public void prepareFailureView(String errorMessage) {
        createAccountViewModel.setMessage(errorMessage);
        createAccountViewModel.setSuccess(false);
        createAccountViewModel.firePropertyChange();
    }
}
