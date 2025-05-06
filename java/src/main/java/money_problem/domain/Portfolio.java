package money_problem.domain;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    private final List<Money> moneyList = new ArrayList<>();

    private final CurrencyConverter currencyConverter;

    public Portfolio(final CurrencyConverter currencyConverter) {
        this.currencyConverter = currencyConverter;
    }

    public void add(final Money money) {
        moneyList.add(money);
    }

    public Money amount(final Currency currency) throws MissingExchangeRatesException {
        var totalPortfolioValue = 0.0;
        final List<String> missingExchangeRates = new ArrayList<>();
        for (final Money money : moneyList) {
            try {
                totalPortfolioValue += currencyConverter.convert(new Money(money.amount(), money.currency()), currency);

            } catch (final MissingExchangeRateException e) {
                missingExchangeRates.add(e.getMessage());

            }
        }
        if (!missingExchangeRates.isEmpty()) {
            throw new MissingExchangeRatesException(missingExchangeRates);
        }
        return new Money(totalPortfolioValue, currency);
    }

    public Result<Money, ConversionError> amountNew(final Currency currency) {
	    try {
		    return new Result<>(amount(currency));
        } catch (final MissingExchangeRatesException e) {
            return new Result<>(new ConversionError(e.getMessage()));
	    }
    }

}
