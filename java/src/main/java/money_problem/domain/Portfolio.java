package money_problem.domain;

import static money_problem.domain.Currency.USD;

public class Portfolio {

    private double amount = 0;

    public void add(double amount, Currency currency) {
        this.amount += amount;
    }

    public double amount(Currency to, CurrencyConverter currencyConverter) throws MissingExchangeRateException {
        return currencyConverter.convert(amount, USD, to);
    }

}
