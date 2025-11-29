package interface_adapter.portfolio.addStock;

import lombok.Getter;
import use_case.portfolio.addStock.AddStockMenuInteractor;

public class AddStockMenuController {
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
