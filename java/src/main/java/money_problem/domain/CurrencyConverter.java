package money_problem.domain;

import java.util.HashMap;
import java.util.Map;

public final class CurrencyConverter {
    private final Map<String, Double> exchangeRates;

    private CurrencyConverter(final Map<String, Double> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public static CurrencyConverter withExchangeRate(final Currency from, final Currency to, final double rate) {
        final var bank = new CurrencyConverter(new HashMap<>());
        bank.addExchangeRate(from, to, rate);

        return bank;
    }

    public void addExchangeRate(final Currency from, final Currency to, final double rate) {
        exchangeRates.put(keyFor(from, to), rate);
    }

    private static String keyFor(final Currency from, final Currency to) {
        return from + "->" + to;
    }

    public double convert(final double amount, final Currency from, final Currency to) throws MissingExchangeRateException {
        if (!canConvert(from, to)) {
            throw new MissingExchangeRateException(from, to);
        }
        return convertSafely(amount, from, to);
    }

    private double convertSafely(final double amount, final Currency from, final Currency to) {
        return from == to
                ? amount
                : amount * exchangeRates.get(keyFor(from, to));
    }

    private boolean canConvert(final Currency from, final Currency to) {
        return from == to || exchangeRates.containsKey(keyFor(from, to));
    }
}