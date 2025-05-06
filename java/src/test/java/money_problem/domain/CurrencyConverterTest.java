package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;

class CurrencyConverterTest {
    private final CurrencyConverter currencyConverter = CurrencyConverter.withExchangeRate(new ExchangeRate(EUR, USD, 1.2));

    @Test
    @DisplayName("10 EUR -> USD = 12 USD")
    void shouldConvertEuroToUsd()  {
        assertThat(currencyConverter.convertNew(new Position(10, EUR), USD))
                .isEqualTo(new ConversionResult(new Position(12, USD)));
    }

    @Test
    @DisplayName("10 EUR -> EUR = 10 EUR")
    void shouldConvertInSameCurrency()  {
        assertThat(currencyConverter.convertNew(new Position(10, EUR), EUR))
                .isEqualTo(new ConversionResult(new Position(10, Currency.EUR)));
    }

    @Test
    @DisplayName("Throws a MissingExchangeRateException in case of missing exchange rates")
    void shouldReturnALeftOnMissingExchangeRate() {
        assertThat(currencyConverter.convertNew(new Position(10, EUR), KRW).failure().getMessage())
                .isEqualTo(new ConversionResult(new MissingExchangeRateException(EUR, KRW)).failure().getMessage());
    }

    @Test
    @DisplayName("Conversion with different exchange rates EUR to USD")
    void shouldConvertWithDifferentExchangeRates()  {
        assertThat(currencyConverter.convertNew(new Position(10, EUR), USD))
                .isEqualTo(new ConversionResult(new Position(12, USD)));

        currencyConverter.addExchangeRate(new ExchangeRate(EUR, USD, 1.3));

        assertThat(currencyConverter.convertNew(new Position(10, EUR), USD))
                .isEqualTo(new ConversionResult(new Position(13, USD)));
    }
}