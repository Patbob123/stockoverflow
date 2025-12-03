package interface_adapter.import_export;

import java.util.List;

import entities.Portfolio.Portfolio;
import entities.Simulation;
import lombok.Getter;
import lombok.Setter;

/**
 * State for Import/Export view.
 */
public class ImportExportState {

    private List<Portfolio> portfolioList;
    private List<Simulation> simDataList;

    @Getter
    private Portfolio selectedPortfolio;
    @Setter
    private Simulation selectedSimData;

    /**
     * Gets the list of portfolios.
     *
     * @return list of portfolios
     */
    public List<Portfolio> getPortfolios() {
        return portfolioList;
    }

    /**
     * Gets the list of simulations.
     *
     *  @return list of simulations
     */
    public List<Simulation> getSimData() {
        return simDataList;
    }

    /**
     * Sets the list of portfolios.
     *
     * @param newPortfolioList the new list we want to set to
     */
    public void setPortfolios(List<Portfolio> newPortfolioList) {
        this.portfolioList = newPortfolioList;
    }

    /**
     * Sets the list of simulations.
     *
     * @param newSimDataList the new list we want to set to
     */
    public void setSimData(List<Simulation> newSimDataList) {
        this.simDataList = newSimDataList;
    }
}
