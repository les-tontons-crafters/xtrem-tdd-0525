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

    public Result<Money, ConversionError> amount(Currency currency) {
        List<Result<Money, ConversionError>> result = moneyList.stream()
                .map(money -> moneyConversionResult(money, currency))
                .toList();
        if (result.stream().anyMatch(Result::isFailure)) {
            return new Result<>(new ConversionError(result.stream().flatMap(r -> r.conversionErrors().stream()).toList()));
        }
        return new Result<>(new Money(result.stream()
                .mapToDouble(r -> r.success().amount()).sum(), currency));
    }

    private Result<Money, ConversionError> moneyConversionResult(Money money, Currency to) {
        try {
            return new Result<>(new Money(currencyConverter.convert(money, to), to));
        } catch (MissingExchangeRateException e) {
            return new Result<>(new ConversionError(List.of(e.getMessage())));
        }
    }

}
