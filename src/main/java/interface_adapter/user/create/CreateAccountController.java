package interface_adapter.user.create;

import use_case.user.create.CreateAccountInputBoundary;
import use_case.user.create.CreateAccountInputData;

public class CreateAccountController {
    private final CreateAccountInputBoundary createAccountUseCaseInteractor;

    public CreateAccountController(CreateAccountInputBoundary createAccountUseCaseInteractor) {
        this.createAccountUseCaseInteractor = createAccountUseCaseInteractor;
    }

    public void execute(String username, String password) {
        CreateAccountInputData inputData = new CreateAccountInputData(username, password);
        createAccountUseCaseInteractor.execute(inputData);
    }
}
