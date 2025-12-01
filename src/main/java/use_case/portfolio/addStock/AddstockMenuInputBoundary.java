package use_case.portfolio.addStock;

import use_case.InputBoundary;

public interface AddstockMenuInputBoundary extends InputBoundary {

    void executeAddstock(String stockticker);

    void executeExit();
}
