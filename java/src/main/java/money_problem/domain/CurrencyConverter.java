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

    public double convert(Position position, Currency to) throws MissingExchangeRateException {
        if (!canConvert(position.currency(), to)) {
            throw new MissingExchangeRateException(position.currency(), to);
        }
        return convertSafely(position, to);
    }

    private double convertSafely(Position position, Currency to) {
        return position.currency() == to
                ? position.amount()
                : position.amount() * findExchangeRate(position.currency(), to).orElseThrow().rate();
    }

    private Optional<ExchangeRate> findExchangeRate(Currency from, Currency to) {
        return exchangeRatesNew.stream().filter(exchangeRate -> exchangeRate.from().equals(from) && exchangeRate.to().equals(to)).findFirst();
    }

    private boolean canConvert(Currency from, Currency to) {
        return from == to || exchangeRatesNew.stream().anyMatch(exchangeRate -> exchangeRate.from().equals(from) && exchangeRate.to().equals(to));
    }
}