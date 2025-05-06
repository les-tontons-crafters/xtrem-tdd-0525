package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CurrencyConverterTest {
    private final CurrencyConverter currencyConverter = CurrencyConverter.withExchangeRate(new ExchangeRate(EUR, USD, 1.2));

    @Test
    @DisplayName("10 EUR -> USD = 12 USD")
    void shouldConvertEuroToUsd() throws MissingExchangeRateException {
        assertThat(currencyConverter.convert(new Position(10, EUR), USD))
                .isEqualTo(new Position(12,USD));
        assertThat(currencyConverter.convertNew(new Position(10, EUR), USD))
                .isEqualTo(new ConversionResult(new Position(12, USD)));
    }

    @Test
    @DisplayName("10 EUR -> EUR = 10 EUR")
    void shouldConvertInSameCurrency() throws MissingExchangeRateException {
        assertThat(currencyConverter.convert(new Position(10, EUR), EUR))
                .isEqualTo(new Position(10, Currency.EUR));
    }

    @Test
    @DisplayName("Throws a MissingExchangeRateException in case of missing exchange rates")
    void shouldReturnALeftOnMissingExchangeRate() {
        assertThatThrownBy(() -> currencyConverter.convert(new Position(10, EUR), KRW))
                .isInstanceOf(MissingExchangeRateException.class)
                .hasMessage("EUR->KRW");
    }

    @Test
    @DisplayName("Conversion with different exchange rates EUR to USD")
    void shouldConvertWithDifferentExchangeRates() throws MissingExchangeRateException {
        assertThat(currencyConverter.convert(new Position(10, EUR), USD))
                .isEqualTo(new Position(12, USD));

        currencyConverter.addExchangeRate(new ExchangeRate(EUR, USD, 1.3));

        assertThat(currencyConverter.convert(new Position(10, EUR), USD))
                .isEqualTo(new Position(13, USD));
    }
}