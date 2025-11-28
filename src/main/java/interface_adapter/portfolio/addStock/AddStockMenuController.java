package interface_adapter.portfolio.addStock;

import interface_adapter.AbsController;
import lombok.Getter;
import use_case.portfolio.addStock.AddStockMenuInteractor;

public class AddStockMenuController extends AbsController {
    @Getter
    private final AddStockMenuInteractor addStockMenuInteractor;

    public AddStockMenuController(AddStockMenuInteractor addStockMenuInteractor) {
        this.addStockMenuInteractor = addStockMenuInteractor;
    }
    /**
     * Executes the Note related Use Cases.
     * @param command the note to be recorded
     */
}
