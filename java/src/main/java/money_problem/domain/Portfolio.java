package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

import static money_problem.domain.Currency.USD;

public class Portfolio {

    private final double amount = 0;

    Map<Currency, Double> currencyMap = new HashMap<>();

    public void add(double amount, Currency currency) {
        Double previousAmount = currencyMap.getOrDefault(currency, 0.0);
        Double currentAmount = amount + previousAmount;
        currencyMap.put(currency, currentAmount);
    }

    public double amount(Currency to, CurrencyConverter currencyConverter) throws MissingExchangeRateException {
        return currencyConverter.convert(amount, USD, to);
    }

}
