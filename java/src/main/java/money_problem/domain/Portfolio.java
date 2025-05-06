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

    public Money amount(Currency currency) throws MissingExchangeRatesException {
        var totalPortfolioValue = 0.0;
        List<String> missingExchangeRates = new ArrayList<>();
        for (Money money : moneyList) {
            try {
                totalPortfolioValue += currencyConverter.convert(new Money(money.amount(), money.currency()), currency);

            } catch (MissingExchangeRateException e) {
                missingExchangeRates.add(e.getMessage());

            }
        }
        if (!missingExchangeRates.isEmpty()) {
            throw new MissingExchangeRatesException(missingExchangeRates);
        }
        return new Money(totalPortfolioValue, currency);
    }

    public Result<Money, ConversionError> amountNew(Currency currency) {
	    try {
		    return new Result<>(amount(currency));
	    } catch (MissingExchangeRatesException e) {
            return new Result<>(new ConversionError("wayne"));
	    }
    }

}
