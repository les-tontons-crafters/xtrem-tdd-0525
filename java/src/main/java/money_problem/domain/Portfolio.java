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

    public Result<Position, List<MissingExchangeRate>> getTotal(Currency targetCurrency) {
        double sum = 0;
        List<MissingExchangeRate> missingExchangeRates = new ArrayList<>();
        for (Position position : positions) {
            var converted = currencyConverter.convert(new Position(position.amount(), position.currency()), targetCurrency);
            if (converted.isFailure()) {
                missingExchangeRates.add(converted.failure());
            } else {
                sum += converted.success().amount();
            }
        }
        if (!missingExchangeRates.isEmpty()) {
            return Result.fromFailure(missingExchangeRates);
        }
        return Result.fromSuccess(new Position(sum, targetCurrency));
    }
}
