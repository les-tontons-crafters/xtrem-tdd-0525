package money_problem.domain;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Portfolio {

    private final Map<Currency, Double> currencyMap = new EnumMap<>(Currency.class);

    private final List<Money> moneyList = new ArrayList<>();

    private final CurrencyConverter currencyConverter;

    public Portfolio(final CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public void add(final Money money) {
        final double previousAmount = currencyMap.getOrDefault(money.currency(), 0.0);
        final double currentAmount = money.amount() + previousAmount;
        currencyMap.put(money.currency(), currentAmount);

        moneyList.add(money);
    }

    public double amount(final Currency to) throws MissingExchangeRateException {
        var totalPortfolioValue = 0.0;
        for (final Map.Entry<Currency, Double> entry : currencyMap.entrySet()) {
            totalPortfolioValue += currencyConverter.convert(entry.getValue(), entry.getKey(), to);
        }
        return totalPortfolioValue;
    }

    public Money amountWithMoney(final Currency currency) throws MissingExchangeRateException {
        // TODO : implement with a basic for loop for now, this method should replace method 'amount()'
        final var doubleStream = moneyList.stream()
                .map(m -> {
                    try {
                        // TODO : use Money as well in the convert method
                        return currencyConverter.convert(m.amount(), m.currency(), currency);
                    } catch (final MissingExchangeRateException e) {
                        throw new RuntimeException(e);
                    }
                });


        return new Money(amount(currency), currency);
    }
}
