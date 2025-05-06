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
        Result<Money, ConversionError> moneyConversionErrorResult = amountNew(currency);
        if (moneyConversionErrorResult.isFailure()) {
            throw new MissingExchangeRatesException(moneyConversionErrorResult.conversionErrors());
        }
        return moneyConversionErrorResult.success();
    }

    public Result<Money, ConversionError> amountNew(final Currency currency) {
        try {
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
            return new Result<>(new Money(totalPortfolioValue, currency));
        } catch (final MissingExchangeRatesException e) {
            return new Result<>(new ConversionError(e.getMessage()));
        }
    }

}
