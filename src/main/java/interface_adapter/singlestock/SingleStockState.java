package interface_adapter.singlestock;

import lombok.Getter;
import lombok.Setter;
import use_case.singlestock.AnalyzeSingleStockOutputData;
import use_case.singlestock.CompareTwoStocksOutputData;

@Getter
@Setter
public class SingleStockState {
    private AnalyzeSingleStockOutputData analyzeSingleStockOutputData;
    private String errorMessage;
    private CompareTwoStocksOutputData compareTwoStocksOutputData;
}
