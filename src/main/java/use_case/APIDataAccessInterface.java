package use_case;

import entities.Stock;
import java.util.List;

public interface APIDataAccessInterface {
    Stock getStock(String ticker);

    List<String> searchSymbols(String query);
}