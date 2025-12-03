package interface_adapter.portfolio.addStock;

import interface_adapter.AbsController;
import lombok.Getter;
import lombok.Setter;
import use_case.portfolio.addStock.AddStockMenuInteractor;
import use_case.portfolio.addStock.AddStockOutputData;
import use_case.portfolio.addStock.AddstockInputData;

@Getter
@Setter
public class AddStockMenuController extends AbsController {
    private final AddStockMenuInteractor addStockMenuInteractor;
    private AddstockInputData addstockInputData;
    private AddStockOutputData addStockOutputData;

    public AddStockMenuController(AddStockMenuInteractor addStockMenuInteractor, AddstockInputData addstockInputData, AddStockOutputData addStockOutputData) {
        this.addStockMenuInteractor = addStockMenuInteractor;
    }

    public AddStockMenuController(AddStockMenuInteractor addStockMenuInteractor) {
        this.addStockMenuInteractor = addStockMenuInteractor;
        this.addStockOutputData = null;
        this.addstockInputData = null;
    }
    /**
     * Executes the Note related Use Cases.
     * @param command the note to be recorded
     */
}
