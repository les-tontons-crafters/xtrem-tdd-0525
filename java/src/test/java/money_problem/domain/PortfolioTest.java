package money_problem.domain;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;

class PortfolioTest {

    private static final CurrencyConverter currencyConverter = CurrencyConverter.withExchangeRate(USD, EUR, 0.83);

    @Test
    void add5UsdAnd5UsdThenReturn10Usd() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio(currencyConverter);
        testee.add(new Money(5, USD));

        testee.add(new Money(5, USD));

        final double actual = testee.amount(USD);
        assertThat(actual).isEqualTo(10);
    }

    @Test
    void add5UsdAnd10EurThenReturn17Usd() throws MissingExchangeRateException {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        final Portfolio testee = new Portfolio(currencyConverter);

        testee.add(new Money(5, USD));
        testee.add(new Money(10, EUR));

        final double actual = testee.amount(USD);
        assertThat(actual).isEqualTo(17);
    }

    @Test
    void whenCreatingEmptyPortfolioThenReturnZero() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio(currencyConverter);

        final double actual = testee.amount(USD);

        assertThat(actual).isZero();
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioEmptyThenReturnExpectedAmount() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio(currencyConverter);

        testee.add(new Money(1.0, USD));

        final double actual = testee.amount(USD);
        assertThat(actual).isEqualTo(1.0);
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio(currencyConverter);
        testee.add(new Money(1.0, USD));

        final double actual = testee.amount(EUR);

        assertThat(actual).isCloseTo(0.83, Offset.offset(0.01));
    }

    @Test
    void addADifferentAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio(currencyConverter);

        testee.add(new Money(2.0, USD));

        final double actual = testee.amount(EUR);

        assertThat(actual).isCloseTo(1.66, Offset.offset(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 0.0, 0.0, 1.0"
    })
    void addAmmount(final double dollar, final double euro, final double southKoreanWon, final double expectedTotalAmmount) throws MissingExchangeRateException {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        currencyConverter.addExchangeRate(KRW, USD, 0.00073);
        final Portfolio testee = new Portfolio(currencyConverter);

        testee.add(new Money(dollar, USD));
        testee.add(new Money(euro, EUR));
        testee.add(new Money(southKoreanWon, KRW));

        final double actual = testee.amount(USD);

        assertThat(actual).isCloseTo(expectedTotalAmmount, Offset.offset(0.01));
    }

}
