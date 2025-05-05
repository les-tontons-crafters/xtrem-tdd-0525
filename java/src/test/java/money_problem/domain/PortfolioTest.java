package money_problem.domain;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static money_problem.domain.Currency.*;
import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.USD;
import static org.assertj.core.api.Assertions.assertThat;

class PortfolioTest {

    private static final CurrencyConverter currencyConverter = CurrencyConverter.withExchangeRate(USD, EUR, 0.83);

    @Test
    void add5UsdAnd5UsdThenReturn10Usd() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio();
        testee.add(5, USD);

        testee.add(5, USD);

        final double actual = testee.amount(USD, currencyConverter);
        assertThat(actual).isEqualTo(10);
    }

    @Test
    void add5UsdAnd10EurThenReturn17Usd() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio();
        currencyConverter.addExchangeRate(EUR, USD, 1.2);

        testee.add(5, USD);
        testee.add(10, EUR);

        final double actual = testee.amount(USD, currencyConverter);
        assertThat(actual).isEqualTo(17);
    }

    @Test
    void whenCreatingEmptyPortfolioThenReturnZero() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio();

        final double actual = testee.amount(USD, currencyConverter);

        assertThat(actual).isZero();
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioEmptyThenReturnExpectedAmount() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio();

        testee.add(1.0, USD);

        final double actual = testee.amount(USD, currencyConverter);
        assertThat(actual).isEqualTo(1.0);
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio();
        testee.add(1.0, USD);

        final double actual = testee.amount(EUR, currencyConverter);

        assertThat(actual).isCloseTo(0.83, Offset.offset(0.01));
    }

    @Test
    void addADifferentAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio();

        testee.add(2.0, USD);

        final double actual = testee.amount(EUR, currencyConverter);

        assertThat(actual).isCloseTo(1.66, Offset.offset(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 0.0, 0.0, 1.0"
    })
    void addAmmount(double dollar, double euro, double southKoreanWon, double expectedTotalAmmount) throws MissingExchangeRateException {
        final Portfolio testee = new Portfolio();
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        currencyConverter.addExchangeRate(KRW, USD, 0.00073);

        testee.add(dollar, USD);
        testee.add(euro, EUR);
        testee.add(southKoreanWon, KRW);

        final double actual = testee.amount(USD, currencyConverter);

        assertThat(actual).isCloseTo(expectedTotalAmmount, Offset.offset(0.01));
    }

    //Replace amount through currencyMap
    //1 USD + 1100 KRW = 2200 KRW
}
