package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.USD;

public class Portfolio {

    Map<Currency, Double> currencyMap = new HashMap<>();

    private double amount = 0;

    public void add(final double amount, final Currency currency) {
        if (currency.equals(EUR)) {
            final double amountTemp = amount * 1.2;
            final Double previousAmount = currencyMap.get(currency);
            final Double currentAmount = amountTemp + previousAmount;
            currencyMap.put(currency, currentAmount);
            this.amount += amountTemp;
            return;
        }
        this.amount += amount;
    }

    public double amount(final Currency to, final CurrencyConverter currencyConverter) throws MissingExchangeRateException {
        return currencyConverter.convert(amount, USD, to);
    }

}
