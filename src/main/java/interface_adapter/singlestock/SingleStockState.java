package interface_adapter.singlestock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleStockState {
    private String currentTicker = "";
    private String currentRiskFree = "";
    private String report;
    private String errorMessage;
}
