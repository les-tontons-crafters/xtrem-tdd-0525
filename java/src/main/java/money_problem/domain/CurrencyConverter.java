package money_problem.domain;

import java.util.*;

public final class CurrencyConverter {
    private final List<ExchangeRate> exchangeRatesNew = new ArrayList<>();

    public static CurrencyConverter withExchangeRate(ExchangeRate exchangeRate) {
        var bank = new CurrencyConverter();
        bank.addExchangeRate(new ExchangeRate(exchangeRate.from(), exchangeRate.to(), exchangeRate.rate()));

        return bank;
    }

    public void addExchangeRate(ExchangeRate exchangeRate) {
        Optional<ExchangeRate> exchangeRate1 = findExchangeRate(exchangeRate.from(), exchangeRate.to());
        exchangeRate1.ifPresent(exchangeRatesNew::remove);
        exchangeRatesNew.add(exchangeRate);
    }

    private static ExchangeRate createFailureWithMissingExchangeRate(Position position, Currency currency) {
        return new ExchangeRate(position.currency(), currency, 0);
    }

    private static Position applyExchangeRateToPosition(Position position, Currency currency, ExchangeRate test) {
        return new Position(position.amount() * test.rate(), currency);
    }

    private Optional<ExchangeRate> findExchangeRate(Currency from, Currency to) {
        return exchangeRatesNew.stream().filter(exchangeRate -> exchangeRate.from().equals(from) && exchangeRate.to().equals(to)).findFirst();
    }

    public Result<Position, ExchangeRate> convert(Position position, Currency targetCurrency) {
        if (position.isTargetCurrency(targetCurrency)) {
            return Result.fromSuccess(position);
        }
        return findExchangeRate(position.currency(), targetCurrency)
                .map(exchangeRate -> applyExchangeRateToPosition(position, targetCurrency, exchangeRate))
                .map(Result::<Position, ExchangeRate>fromSuccess)
                .orElse(Result.fromFailure(createFailureWithMissingExchangeRate(position, targetCurrency)));
    }
}