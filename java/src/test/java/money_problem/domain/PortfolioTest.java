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
        Portfolio testee = new Portfolio(currencyConverter);
        testee.add(new Money(5, USD));

        testee.add(new Money(5, USD));

        double actual = testee.amount(USD);
        assertThat(actual).isEqualTo(10);
    }

    @Test
    void add5UsdAnd10EurThenReturn17Usd() throws MissingExchangeRateException {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        Portfolio testee = new Portfolio(currencyConverter);

        testee.add(new Money(5, USD));
        testee.add(new Money(10, EUR));

        double actual = testee.amount(USD);
        assertThat(actual).isEqualTo(17);
    }

    @Test
    void whenCreatingEmptyPortfolioThenReturnZero() throws MissingExchangeRateException {
        Portfolio testee = new Portfolio(currencyConverter);

        double actual = testee.amount(USD);

        assertThat(actual).isZero();
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioEmptyThenReturnExpectedAmount() throws MissingExchangeRateException {
        Portfolio testee = new Portfolio(currencyConverter);

        testee.add(new Money(1.0, USD));

        double actual = testee.amount(USD);
        assertThat(actual).isEqualTo(1.0);
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRateException {
        Portfolio testee = new Portfolio(currencyConverter);
        testee.add(new Money(1.0, USD));

        double actual = testee.amount(EUR);

        assertThat(actual).isCloseTo(0.83, Offset.offset(0.01));
    }

    @Test
    void addADifferentAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRateException {
        Portfolio testee = new Portfolio(currencyConverter);

        testee.add(new Money(2.0, USD));

        double actual = testee.amount(EUR);

        assertThat(actual).isCloseTo(1.66, Offset.offset(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 0.0, 0.0, 1.0"
    })
    void addAmmount(double dollar, double euro, double southKoreanWon, double expectedTotalAmmount) throws MissingExchangeRateException {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        currencyConverter.addExchangeRate(KRW, USD, 0.00073);
        Portfolio testee = new Portfolio(currencyConverter);

        testee.add(new Money(dollar, USD));
        testee.add(new Money(euro, EUR));
        testee.add(new Money(southKoreanWon, KRW));

        double actual = testee.amount(USD);

        assertThat(actual).isCloseTo(expectedTotalAmmount, Offset.offset(0.01));
    }

    @Test
    void amountWhenPortfolioEmptyThenReturnZero() {
        Portfolio testee = new Portfolio(currencyConverter);

        Money actual = testee.sumCurrency(Currency.USD);
        assertThat(actual).isEqualTo(new Money(0.0, USD));
    }

}
