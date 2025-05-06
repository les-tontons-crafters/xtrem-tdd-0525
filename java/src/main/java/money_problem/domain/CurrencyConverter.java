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
    
    public ConversionResult convert(Position position, Currency currency) {
            if ( position.currency() == currency)
            {
                return new ConversionResult(position);
            }

            return findExchangeRate(position.currency(), currency)
                    .map(test -> new Position(position.amount() * test.rate(), currency))
                    .map(ConversionResult::new)
                    .orElse(new ConversionResult(new MissingExchangeRateException(position.currency(), currency)));
    }

    private Optional<ExchangeRate> findExchangeRate(Currency from, Currency to) {
        return exchangeRatesNew.stream().filter(exchangeRate -> exchangeRate.from().equals(from) && exchangeRate.to().equals(to)).findFirst();
    }

}