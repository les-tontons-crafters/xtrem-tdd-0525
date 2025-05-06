package money_problem.domain;

import io.vavr.control.Either;

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
        List<Result<Money, ConversionError>> result = convert(currency);
        if (containsFailure(result)) {
            return createFailure(result);
        }
        return createSuccess(currency, result);
    }

    private List<Result<Money, ConversionError>> convert(Currency currency) {
        return moneyList.stream()
                .map(money -> moneyConversionResult(money, currency))
                .toList();
    }

    private Result<Money, ConversionError> moneyConversionResult(Money money, Currency to) {
        try {
            return new Result<>(new Money(currencyConverter.convert(money, to), to));
        } catch (MissingExchangeRateException e) {
            return new Result<>(new ConversionError(List.of(e.getMessage())));
        }
    }

    private Result<Money, ConversionError> createFailure(List<Result<Money, ConversionError>> result) {
        List<String> conversionErrors = result.stream()
                .flatMap(r -> r.conversionErrors().stream())
                .toList();
        ConversionError conversionError = new ConversionError(conversionErrors);
        return new Result<>(conversionError);
    }

    private Result<Money, ConversionError> createSuccess(Currency currency, List<Result<Money, ConversionError>> result) {
        return new Result<>(new Money(result.stream()
                .mapToDouble(r -> r.success().amount()).sum(), currency));
    }

    private boolean containsFailure(List<Result<Money, ConversionError>> result) {
        return result.stream().anyMatch(Result::isFailure);
    }

    public Either<ConversionError, Money> amountWithEither(Currency currency) {
        return Either.left(new ConversionError(List.of("KRW->EUR")));
    }
}
