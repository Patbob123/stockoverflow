package use_case.portfolio.addStock;

import entities.Portfolio.Portfolio;
import entities.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddstockInputData {
    private Portfolio stock;
}
