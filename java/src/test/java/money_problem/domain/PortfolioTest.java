package money_problem.domain;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static money_problem.domain.Currency.*;
import static money_problem.domain.Moneys.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PortfolioTest {

    private static CurrencyConverter currencyConverter;

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
    void add5UsdAnd5UsdThenReturn10Usd() throws MissingExchangeRatesException {
        testee.add(dollars(5));
        testee.add(dollars(5));

        assertThat(testee.amount(USD).amount())
                .isEqualTo(10);
    }

    @Test
    void add5UsdAnd10EurThenReturn17Usd() throws MissingExchangeRatesException {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        testee.add(dollars(5));
        testee.add(euros(10));

        assertThat(testee.amount(USD))
                .isEqualTo(new Money(17, USD));
    }

    @Test
    void whenCreatingEmptyPortfolioThenReturnZero() throws MissingExchangeRatesException {
        assertThat(testee.amount(USD).amount())
                .isZero();
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioEmptyThenReturnExpectedAmount() throws MissingExchangeRatesException {
        testee.add(dollars(1.0));

        assertThat(testee.amount(USD).amount())
                .isEqualTo(1.0);
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRatesException {
        testee.add(dollars(1.0));

        assertThat(testee.amount(EUR).amount())
                .isCloseTo(0.83, Offset.offset(0.01));
    }

    @Test
    void addADifferentAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() throws MissingExchangeRatesException {
        testee.add(dollars(2.0));

        assertThat(testee.amount(EUR).amount())
                .isCloseTo(1.66, Offset.offset(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 0.0, 0.0, 1.0"
    })
    void addAmount(double dollar, double euro, double southKoreanWon, double expectedTotalAmount) throws MissingExchangeRatesException {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        currencyConverter.addExchangeRate(KRW, USD, 0.00073);

        testee.add(dollars(dollar));
        testee.add(euros(euro));
        testee.add(southKoreanWons(southKoreanWon));

        assertThat(testee.amount(USD).amount())
                .isCloseTo(expectedTotalAmount, Offset.offset(0.01));
    }

    @Test
    void evaluateAPortfolioShouldReturnMissingExchangeRatesExceptionWhenMultipleRatesMissing() {
        var portfolio = new Portfolio(CurrencyConverter.withExchangeRate(KRW, USD, 1));
        portfolio.add(southKoreanWons(1100));
        portfolio.add(dollars(1));

        assertThatThrownBy(() -> portfolio.amount(EUR))
                .isInstanceOf(MissingExchangeRatesException.class)
                .hasMessage("KRW->EUR,USD->EUR");
    }

    @Test
    void foo() {
        Portfolio testee = new Portfolio(currencyConverter);
        assertThat(testee.amountNew(KRW)).isEqualTo(new Result<Money, ConversionError>("wayne"));
    }

}
