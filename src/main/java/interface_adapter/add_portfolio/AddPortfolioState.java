package interface_adapter.add_portfolio;

public class AddPortfolioState {
    private String targetView = "";
    private String errorMessage = "";

    public String getTargetView() {
        return targetView;
    }

    public void setTargetView(String targetView) {
        this.targetView = targetView;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}