package money_problem.domain;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static money_problem.domain.Currency.*;
import static money_problem.domain.Moneys.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PortfolioTest {
    private static CurrencyConverter currencyConverter = CurrencyConverter.withExchangeRate(USD, EUR, 0.83);
    private Portfolio testee;

    @BeforeAll
    static void setup() {
        currencyConverter = CurrencyConverter.withExchangeRate(USD, EUR, 0.83);
    }

    @BeforeEach
    void BeforeEach() {
        testee = new Portfolio(currencyConverter);
    }

    @Test
    void add5UsdAnd5UsdThenReturn10Usd() throws MissingExchangeRateException {
        testee.add(dollars(5));
        testee.add(dollars(5));

        assertThat(testee.amount(USD))
                .isEqualTo(10);
    }

    @Test
    void add5UsdAnd10EurThenReturn17Usd() throws MissingExchangeRateException {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        testee.add(dollars(5));
        testee.add(euros(10));

        assertThat(testee.amountWithMoney(USD))
                .isEqualTo(new Money(17, USD));
    }

    @Test
    void whenCreatingEmptyPortfolioThenReturnZero() throws MissingExchangeRateException {
        assertThat(testee.amount(USD))
                .isZero();
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioEmptyThenReturnExpectedAmount() throws MissingExchangeRateException {
        testee.add(dollars(1.0));

        assertThat(testee.amount(USD))
                .isEqualTo(1.0);
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRateException {
        testee.add(dollars(1.0));

        assertThat(testee.amount(EUR))
                .isCloseTo(0.83, Offset.offset(0.01));
    }

    @Test
    void addADifferentAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRateException {
        testee.add(dollars(2.0));

        assertThat(testee.amount(EUR))
                .isCloseTo(1.66, Offset.offset(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 0.0, 0.0, 1.0"
    })
    void addAmmount(double dollar, double euro, double southKoreanWon, double expectedTotalAmmount) throws MissingExchangeRateException {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        currencyConverter.addExchangeRate(KRW, USD, 0.00073);

        testee.add(dollars(dollar));
        testee.add(euros(euro));
        testee.add(southKoreanWons(southKoreanWon));

        assertThat(testee.amount(USD))
                .isCloseTo(expectedTotalAmmount, Offset.offset(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 0.0, 0.0, 1.0, USD",
            "1.0, 0.0, 0.0, 0.0, EUR",
            "1.0, 2.5, 0.0, 2.5, EUR"
    })
    void sumCurrency(double dollar, double euro, double southKoreanWon, double expectedTotalAmmount, Currency expectedCurrency) throws MissingExchangeRateException {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        currencyConverter.addExchangeRate(USD, EUR, 0.83);
        currencyConverter.addExchangeRate(KRW, USD, 0.00073);

        testee.add(dollars(dollar));
        testee.add(euros(euro));
        testee.add(southKoreanWons(southKoreanWon));

        assertThat(testee.sumCurrency(expectedCurrency)).
                isEqualTo(new Money(expectedTotalAmmount, expectedCurrency));
    }

    @Test
    void amountWhenPortfolioEmptyThenReturnZero() {
        assertThat(testee.sumCurrency(Currency.USD))
                .isEqualTo(dollars(0.0));
    }

    @Test
    void amountWhenPortfolioContains1UsdThenReturn1Usd() {
        testee.add(dollars(1.0));

        assertThat(testee.sumCurrency(USD))
                .isEqualTo(new Money(1.0, USD));
    }

    @Disabled
    @Test
        // TODO: Fix this test
    void evaluateAPortfolioShouldReturnMissingExchangeRatesExceptionWhenMultipleRatesMissing() {
        var portfolio = new Portfolio(CurrencyConverter.withExchangeRate(KRW, USD, 1));
        portfolio.add(southKoreanWons(1100));
        portfolio.add(dollars(1));

        assertThatThrownBy(() -> portfolio.amountWithMoney(EUR))
                .isInstanceOf(MissingExchangeRatesException.class)
                .hasMessage("KRW->EUR,USD->EUR");
    }
}
