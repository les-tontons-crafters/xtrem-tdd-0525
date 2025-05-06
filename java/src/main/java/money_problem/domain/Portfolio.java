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

    // Expectation: public Result<Position, List<ExchangeRate>> getTotal(Currency targetCurrency)
    public ConversionResult getTotal(Currency targetCurrency) {
        try {
            double sum = 0;
            List<String> errorMessages = new ArrayList<>();
            for (Position position : positions) {
                try {
                    var converted = currencyConverter.convert(new Position(position.amount(), position.currency()), targetCurrency);
                    if(converted.isFailure()){
                        throw new MissingExchangeRateException(converted.failure().from(),converted.failure().to());
                    }
                    sum += converted.success().amount();
                } catch (MissingExchangeRateException e) {
                    errorMessages.add(e.getMessage());
                }
            }
            if (!errorMessages.isEmpty()) {
                throw new MissingExchangeRatesException(errorMessages);
            }

            return new ConversionResult(new Position(sum, targetCurrency));
        } catch (Exception e) {
            return new ConversionResult(e);
        }
    }
}
