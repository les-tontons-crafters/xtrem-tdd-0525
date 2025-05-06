package money_problem.domain;

import java.util.*;

public final class CurrencyConverter {
    private final Map<String, Double> exchangeRates;
    private final List<ExchangeRate> exchangeRatesNew = new ArrayList<>();

    private CurrencyConverter(Map<String, Double> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public static CurrencyConverter withExchangeRate(ExchangeRate exchangeRate) {
        var bank = new CurrencyConverter(new HashMap<>());
        bank.addExchangeRate(new ExchangeRate(exchangeRate.from(), exchangeRate.to(), exchangeRate.rate()));

        return bank;
    }

    public void addExchangeRate(ExchangeRate exchangeRate) {
        exchangeRates.put(keyFor(exchangeRate.from(), exchangeRate.to()), exchangeRate.rate());
        Optional<ExchangeRate> exchangeRate1 = dummyName(exchangeRate.from(), exchangeRate.to());
        exchangeRate1.ifPresent(exchangeRatesNew::remove);
        exchangeRatesNew.add(exchangeRate);
    }

    private static String keyFor(Currency from, Currency to) {
        return from + "->" + to;
    }

    public double convert(Position position, Currency to) throws MissingExchangeRateException {
        if (!canConvertNew(position.currency(), to)) {
            throw new MissingExchangeRateException(position.currency(), to);
        }
        return convertSafelyNew(position, to);
    }

    private double convertSafelyNew(Position position, Currency to) {
        return position.currency() == to
                ? position.amount()
                : position.amount() * retrieveRateForTargetCurrency(position.currency(), to);
    }

    private double retrieveRateForTargetCurrency(Currency from, Currency to) {
        return exchangeRatesNew.stream().filter(exchangeRate -> exchangeRate.from().equals(from) && exchangeRate.to().equals(to)).findFirst().orElseThrow().rate();
    }

    private Optional<ExchangeRate> dummyName(Currency from, Currency to) {
        return exchangeRatesNew.stream().filter(exchangeRate -> exchangeRate.from().equals(from) && exchangeRate.to().equals(to)).findFirst();
    }

    private boolean canConvert(Currency from, Currency to) {
        return from == to || exchangeRates.containsKey(keyFor(from, to));
    }

    private boolean canConvertNew(Currency from, Currency to) {
        return from == to || exchangeRatesNew.stream().anyMatch(exchangeRate -> exchangeRate.from().equals(from) && exchangeRate.to().equals(to));
    }
}