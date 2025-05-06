package money_problem.domain;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private final List<Position> positions = new ArrayList<>();
    private final CurrencyConverter currencyConverter;

    public Portfolio(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public void add(Position position) {
        positions.add(position);
    }

    public Position getTotal(Currency targetCurrency) throws MissingExchangeRatesException {
        double sum = 0;
        List<String> errorMessages = new ArrayList<>();
        for (Position position : positions) {
            try {
                var converted = currencyConverter.convert(new Position(position.amount(), position.currency()), targetCurrency);
                sum += converted.amount();
            } catch (MissingExchangeRateException e) {
                errorMessages.add(e.getMessage());
            }
        }
        if (!errorMessages.isEmpty()) {
            throw new MissingExchangeRatesException(errorMessages);
        }
        return new Position(sum, targetCurrency);
    }
}
