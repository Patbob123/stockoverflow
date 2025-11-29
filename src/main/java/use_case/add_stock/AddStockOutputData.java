package use_case.add_stock;

import java.util.List;

public class AddStockOutputData {
    private final String portfolioName;
    private final List<String> addedTickers;
    private final boolean useCaseFailed;
    private final String message;

    public AddStockOutputData(String portfolioName, List<String> addedTickers, boolean useCaseFailed, String message) {
        this.portfolioName = portfolioName;
        this.addedTickers = addedTickers;
        this.useCaseFailed = useCaseFailed;
        this.message = message;
    }

    public String getPortfolioName() { return portfolioName; }
    public String getMessage() { return message; }
}