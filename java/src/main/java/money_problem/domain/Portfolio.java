package money_problem.domain;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Portfolio {

    private final Map<Currency, Double> currencyMap = new EnumMap<>(Currency.class);

    private final List<Money> moneyList = new ArrayList<>();

    private final CurrencyConverter currencyConverter;

    public Portfolio(CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public void add(Money money) {
        double previousAmount = currencyMap.getOrDefault(money.currency(), 0.0);
        double currentAmount = money.amount() + previousAmount;
        currencyMap.put(money.currency(), currentAmount);

        moneyList.add(money);
    }

    public double amount(Currency to) throws MissingExchangeRateException {
        var totalPortfolioValue = 0.0;
        for (Map.Entry<Currency, Double> entry : currencyMap.entrySet()) {
            totalPortfolioValue += currencyConverter.convert(entry.getValue(), entry.getKey(), to);
        }
        return totalPortfolioValue;
    }

    public Money sumCurrency(Currency currency) {
        double totalAmount = 0.0;
        for (Money money : moneyList) {
            if (money.currency() == currency) {
                totalAmount += money.amount();
            }
        }
        return new Money(totalAmount, currency);
    }

}
