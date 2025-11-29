package use_case.show_graph;

import java.util.List;

public class ShowGraphInputData {
    private final List<String> tickers;

    public ShowGraphInputData(List<String> tickers) {
        this.tickers = tickers;
    }

    public List<String> getTickers() {
        return tickers;
    }
}