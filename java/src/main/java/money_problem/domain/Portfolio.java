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

    public Result<Money, ConversionError> amount(final Currency currency) {
        final List<Result<Money, ConversionError>> result = moneyList.stream().map(money -> moneyConversionErrorResult(currency, money)).toList();

        if (result.stream().anyMatch(Result::isFailure)) {
            return new Result<>(new ConversionError(result.stream().flatMap(r -> r.conversionErrors().stream()).toList()));
        }
        return new Result<>(new Money(result.stream()
                .mapToDouble(r -> r.success().amount()).sum(), currency));
    }

    private Result<Money, ConversionError> moneyConversionErrorResult(Currency currency, Money money) {
        Result<Money, ConversionError> tempResult;
        try {
            tempResult = new Result<>(new Money(currencyConverter.convert(new Money(money.amount(), money.currency()), currency), currency));

        } catch (final MissingExchangeRateException e) {
            tempResult = new Result<>(new ConversionError(List.of(e.getMessage())));
        }
        return tempResult;
    }

}
