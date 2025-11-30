package use_case.show_graph;

import java.time.LocalDate;
import java.util.List;

public class ShowGraphInputData {
    private final List<String> tickers;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String previousViewName;

    public ShowGraphInputData(List<String> tickers, LocalDate startDate, LocalDate endDate, String previousViewName) {
        this.tickers = tickers;
        this.startDate = startDate;
        this.endDate = endDate;
        this.previousViewName = previousViewName;
    }

    public List<String> getTickers() {
        return tickers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getPreviousViewName() {
        return previousViewName;
    }
}