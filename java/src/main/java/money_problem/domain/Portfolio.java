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

    public double getTotal(Currency targetCurrency) throws MissingExchangeRatesException {
        double sum = 0;
        StringBuilder messages = new StringBuilder();
        for (Position position : positions) {
            double converted = 0;
            try {
                converted = currencyConverter.convert(position.amount(), position.currency(), targetCurrency);
            } catch (MissingExchangeRateException e) {
                messages.append(e.getMessage()).append(",");
            }
            sum += converted;
        }
        String messagesString = messages.toString();
        if (!messagesString.isEmpty()) {
            throw new MissingExchangeRatesException(messagesString.substring(0, messagesString.length() - 1));
        }

        return sum;
    }
}
