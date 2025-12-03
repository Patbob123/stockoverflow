package interface_adapter.import_export;

import java.util.List;
import entities.Portfolio.Portfolio;
import entities.Simulation;
import lombok.Getter;
import lombok.Setter;

/**
 * State for Import/Export view
 */
public class ImportExportState {

    private List<Portfolio> portfolioList;
    private List<Simulation> simDataList;

    @Getter
    private Portfolio selectedPortfolio;
    @Setter
    private Simulation selectedSimData;

    /**
     * Gets the list of portfolios
     */
    public List<Portfolio> getPortfolios() {
        return portfolioList;
    }

    /**
     * Gets the list of simulations
     */
    public List<Simulation> getSimData() {
        return simDataList;
    }

    /**
     * Sets the list of portfolios
     */
    public void setPortfolios(List<Portfolio> newPortfolioList) {
        this.portfolioList = newPortfolioList;
    }

    /**
     * Sets the list of simulations
     */
    public void setSimData(List<Simulation> newSimDataList) {
        this.simDataList = newSimDataList;
    }
}
