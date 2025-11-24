package interface_adapter.portfolio;

import entities.Portfolio.Portfolio;
import entities.Stock;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class PortfolioMenuState {

    private Portfolio portfolio = null;

    // User Story 5: Data to be graphed
    private List<Stock> stocksToGraph;

    // User Story 9: Analysis result message
    private String analysisResult;

    private String error;
}