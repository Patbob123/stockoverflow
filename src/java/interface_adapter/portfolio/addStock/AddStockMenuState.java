package interface_adapter.portfolio.addStock;

import entities.Stock;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddStockMenuState {

    private Portfolio portfolio;

    private String stockNotFoundError;

    private Stock stock;

}
