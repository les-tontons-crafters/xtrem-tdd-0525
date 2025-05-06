package money_problem.domain;

import org.assertj.core.data.Offset;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

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
    void add5UsdAnd5UsdThenReturn10Usd() {
        testee.add(dollars(5));
        testee.add(dollars(5));

        Result<Money, ConversionError> moneyConversionErrorResult = testee.amount(USD);

        assertThat(moneyConversionErrorResult.success().amount())
                .isEqualTo(10);
    }

    @Test
    void add5UsdAnd10EurThenReturn17Usd() {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        testee.add(dollars(5));
        testee.add(euros(10));

        Result<Money, ConversionError> moneyConversionErrorResult = testee.amount(USD);

        assertThat(moneyConversionErrorResult.success())
                .isEqualTo(new Money(17, USD));
    }

    @Test
    void whenCreatingEmptyPortfolioThenReturnZero() {
        Result<Money, ConversionError> moneyConversionErrorResult = testee.amount(USD);

        assertThat(moneyConversionErrorResult.success().amount())
                .isZero();
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioEmptyThenReturnExpectedAmount() {
        testee.add(dollars(1.0));

        Result<Money, ConversionError> moneyConversionErrorResult = testee.amount(USD);

        assertThat(moneyConversionErrorResult.success().amount())
                .isEqualTo(1.0);
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() {
        testee.add(dollars(1.0));

        Result<Money, ConversionError> moneyConversionErrorResult = testee.amount(EUR);

        assertThat(moneyConversionErrorResult.success().amount())
                .isCloseTo(0.83, Offset.offset(0.01));
    }

    @Test
    void addADifferentAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() {
        testee.add(dollars(2.0));

        Result<Money, ConversionError> moneyConversionErrorResult = testee.amount(EUR);

        assertThat(moneyConversionErrorResult.success().amount())
                .isCloseTo(1.66, Offset.offset(0.01));
    }

    @ParameterizedTest
    @CsvSource({
            "1.0, 0.0, 0.0, 1.0"
    })
    void addAmount(final double dollar, final double euro, final double southKoreanWon, final double expectedTotalAmount) {
        currencyConverter.addExchangeRate(EUR, USD, 1.2);
        currencyConverter.addExchangeRate(KRW, USD, 0.00073);

        testee.add(dollars(dollar));
        testee.add(euros(euro));
        testee.add(southKoreanWons(southKoreanWon));

        Result<Money, ConversionError> moneyConversionErrorResult = testee.amount(USD);

        assertThat(moneyConversionErrorResult.success().amount())
                .isCloseTo(expectedTotalAmount, Offset.offset(0.01));
    }

    @Test
    void evaluateAPortfolioShouldReturnMissingExchangeRatesExceptionWhenMultipleRatesMissing() {
        final var portfolio = new Portfolio(CurrencyConverter.withExchangeRate(KRW, USD, 1));
        portfolio.add(southKoreanWons(1100));
        portfolio.add(dollars(1));

        assertThatThrownBy(() -> {
            Result<Money, ConversionError> moneyConversionErrorResult = portfolio.amount(EUR);
            if (moneyConversionErrorResult.isFailure()) {
                throw new MissingExchangeRatesException(moneyConversionErrorResult.conversionErrors());
            }
        })
                .isInstanceOf(MissingExchangeRatesException.class)
                .hasMessage("KRW->EUR,USD->EUR");
    }

    @Test
    void conversionError() {
        final Portfolio testee = new Portfolio(currencyConverter);
        testee.add(new Money(10, USD));
        testee.add(new Money(10, EUR));

        final Result<Money, ConversionError> actual = testee.amount(KRW);

        final Result<Money, ConversionError> expected = new Result<>(new ConversionError(List.of("USD->KRW", "EUR->KRW")));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void successStory() {
        final Portfolio testee = new Portfolio(currencyConverter);
        testee.add(dollars(100));

        final Result<Money, ConversionError> actual = testee.amount(EUR);

        final Result<Money, ConversionError> expected = new Result<>(euros(83.0));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void failureStoryWithVavr() {
        testee.add(southKoreanWons(100));

        var result = testee.amountWithEither(EUR);

        VavrAssertions.assertThat(result)
                .containsOnLeft(new ConversionError(List.of("KRW->EUR")));
    }
}
