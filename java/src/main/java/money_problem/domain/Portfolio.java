package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public class Portfolio {

    Map<Currency, Double> currencyMap = new HashMap<>();

    public void add(double amount, Currency currency) {
        Double previousAmount = currencyMap.getOrDefault(currency, 0.0);
        Double currentAmount = amount + previousAmount;
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
