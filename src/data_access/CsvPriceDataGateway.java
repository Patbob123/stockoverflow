package data_access;

import entities.PricePoint;
import usecase_portfolio.PriceDataGateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CsvPriceDataGateway implements PriceDataGateway {
    private final Path dataDir;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CsvPriceDataGateway(String dataDirectory) {
        this.dataDir = Paths.get(dataDirectory);
    }

    @Override
    public List<PricePoint> load(String ticker, LocalDate from, LocalDate to) throws IOException {
        Objects.requireNonNull(ticker, "ticker");
        Path csv = dataDir.resolve(ticker.toUpperCase() + ".csv");
        if (!Files.exists(csv)) {
            throw new IOException("CSV not exist: " + csv.toAbsolutePath());
        }
        List<PricePoint> result = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(csv, StandardCharsets.UTF_8)) {
            String header = br.readLine();
            if (header == null) throw new IOException("empty CSV: " + csv);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",");
                LocalDate date = LocalDate.parse(parts[0].trim(), fmt);
                double close = Double.parseDouble(parts[4].trim());
                if ((from == null || !date.isBefore(from)) && (to == null || !date.isAfter(to))) {
                    result.add(new PricePoint(date, close));
                }
            }
        }
        result.sort(Comparator.comparing(PricePoint::getDate));
        return result;
    }
}
