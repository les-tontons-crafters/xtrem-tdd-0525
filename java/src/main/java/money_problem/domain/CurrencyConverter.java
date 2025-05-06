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

    private static String keyFor(final Currency from, final Currency to) {
        return from + "->" + to;
    }

    public void addExchangeRate(final Currency from, final Currency to, final double rate) {
        exchangeRates.put(keyFor(from, to), rate);
    }

    public double convert(Money money, final Currency to) throws MissingExchangeRateException {
        if (!canConvert(money.currency(), to)) {
            throw new MissingExchangeRateException(money.currency(), to);
        }
        return convertSafely(money, to);
    }

    private double convertSafely(Money money, final Currency to) {
        return money.currency() == to
                ? money.amount()
                : money.amount() * exchangeRates.get(keyFor(money.currency(), to));
    }

    private boolean canConvert(final Currency from, final Currency to) {
        return from == to || exchangeRates.containsKey(keyFor(from, to));
    }
}