package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public final class CurrencyConverter {
    private final Map<String, Double> exchangeRates;

    private CurrencyConverter(Map<String, Double> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public static CurrencyConverter withExchangeRate(ExchangeRate exchangeRate) {
        var bank = new CurrencyConverter(new HashMap<>());
        bank.addExchangeRate(exchangeRate.from(), exchangeRate.to(), exchangeRate.rate());

        return bank;
    }

    public void addExchangeRate(Currency from, Currency to, double rate) {
        exchangeRates.put(keyFor(from, to), rate);
    }

    private static String keyFor(Currency from, Currency to) {
        return from + "->" + to;
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
                : position.amount() * exchangeRates.get(keyFor(position.currency(), to));
    }

    private boolean canConvert(Currency from, Currency to) {
        return from == to || exchangeRates.containsKey(keyFor(from, to));
    }
}