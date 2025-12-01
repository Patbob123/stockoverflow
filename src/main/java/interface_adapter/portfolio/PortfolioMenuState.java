package interface_adapter.portfolio;

import entities.Portfolio.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import use_case.singlestock.StockPriceDataAccessInterface;

@Getter
@Setter
public class PortfolioMenuState {

    private Portfolio portfolio = null;

    private StockPriceDataAccessInterface stockPriceDataAccess;

    public PortfolioMenuState() {

    }

    public PortfolioMenuState(StockPriceDataAccessInterface stockPriceDataAccess) {
        this.stockPriceDataAccess = stockPriceDataAccess;
    }
}
