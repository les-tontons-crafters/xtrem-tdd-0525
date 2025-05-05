package money_problem.domain;

import java.util.EnumMap;
import java.util.Map;

public class Portfolio {

    private final Map<Currency, Double> currencyMap = new EnumMap<>(Currency.class);
    private final CurrencyConverter currencyConverter;

    public Portfolio(final CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public void add(final Money money) {
        final double previousAmount = currencyMap.getOrDefault(money.currency(), 0.0);
        final double currentAmount = money.amount() + previousAmount;
        currencyMap.put(money.currency(), currentAmount);
    }

    public double amount(final Currency to) throws MissingExchangeRateException {
        var totalPortfolioValue = 0.0;
        for (final Map.Entry<Currency, Double> entry : currencyMap.entrySet()) {
            totalPortfolioValue += currencyConverter.convert(entry.getValue(), entry.getKey(), to);
        }
        return totalPortfolioValue;
    }
}
