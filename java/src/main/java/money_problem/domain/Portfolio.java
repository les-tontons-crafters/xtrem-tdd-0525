package money_problem.domain;

import java.util.EnumMap;
import java.util.Map;

public class Portfolio {

    private final Map<Currency, Double> currencyMap = new EnumMap<>(Currency.class);

    public void add(double amount, Currency currency) {
        double previousAmount = currencyMap.getOrDefault(currency, 0.0);
        double currentAmount = amount + previousAmount;
        currencyMap.put(currency, currentAmount);
    }

    public double amount(Currency to, CurrencyConverter currencyConverter) throws MissingExchangeRateException {
        var totalPortfolioValue = 0.0;
        for (Map.Entry<Currency, Double> entry : currencyMap.entrySet()) {
            totalPortfolioValue += currencyConverter.convert(entry.getValue(), entry.getKey(), to);
        }
        return totalPortfolioValue;
    }
}
