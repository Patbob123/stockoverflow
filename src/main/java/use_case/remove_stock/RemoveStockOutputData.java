package use_case.remove_stock;

public class RemoveStockOutputData {
    private final String message;

    public RemoveStockOutputData(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
}