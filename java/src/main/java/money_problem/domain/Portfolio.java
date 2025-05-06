package money_problem.domain;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private final List<Money> moneyList = new ArrayList<>();

    private final CurrencyConverter currencyConverter;

    public Portfolio(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public void add(Money money) {
        moneyList.add(money);
    }

    public Money amount(Currency currency) throws MissingExchangeRateException {
        var totalPortfolioValue = 0.0;
        for (Money money : moneyList) {
            totalPortfolioValue += currencyConverter.convert(new Money(money.amount(), money.currency()), currency);
        }
        return new Money(totalPortfolioValue, currency);
    }

}
