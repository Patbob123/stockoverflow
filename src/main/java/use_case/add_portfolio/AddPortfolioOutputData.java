package use_case.add_portfolio;

public class AddPortfolioOutputData {
    private final String targetViewName;

    public AddPortfolioOutputData(String targetViewName) {
        this.targetViewName = targetViewName;
    }

    public String getTargetViewName() {
        return targetViewName;
    }
}
