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
        var total = 0.0;
        for (Map.Entry<Currency, Double> entry : currencyMap.entrySet()) {
            Currency currency = entry.getKey();
            Double amount = entry.getValue();
            if (currency == to) {
                total += amount;
            } else {
                total += currencyConverter.convert(amount, entry.getKey(), to);
            }
        }
        return total;
    }

}
