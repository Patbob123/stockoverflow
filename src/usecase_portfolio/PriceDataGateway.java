package usecase_portfolio;

import entities.PricePoint;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface PriceDataGateway {
    List<PricePoint> load(String ticker, LocalDate from, LocalDate to) throws IOException;
}
