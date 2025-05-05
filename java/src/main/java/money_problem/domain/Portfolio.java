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

    public double getTotal(Currency targetCurrency) throws MissingExchangeRateException {
        double sum = 0;
        for (Position position : positions) {
            double converted = currencyConverter.convert(position.amount(), position.currency(), targetCurrency);
            sum += converted;
        }
        return sum;
    }
}
