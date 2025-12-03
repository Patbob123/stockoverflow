package use_case.portfolio;

import entities.Portfolio.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PortfolioOutputData {
    private Portfolio portfolio;
}
