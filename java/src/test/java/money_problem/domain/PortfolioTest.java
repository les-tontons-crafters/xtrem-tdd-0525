package money_problem.domain;

import io.vavr.control.Either;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;

class PortfolioTest {

    private static CurrencyConverter currencyConverter;
    private Portfolio portfolio;

    @BeforeAll
    static void setup() {
        currencyConverter = CurrencyConverter.withExchangeRate(new ExchangeRate(USD, EUR, 1.0 / 1.2));
        currencyConverter.addExchangeRate(new ExchangeRate(EUR, USD, 1.2));
        currencyConverter.addExchangeRate(new ExchangeRate(USD, KRW, 1100));
    }

    @BeforeEach
    void BeforeEach() {
        portfolio = new Portfolio(currencyConverter);
    }

    @Test
    @DisplayName("1.2 USD = 1 EUR")
    void getTotalInEur() {
        portfolio.add(new Position(1.2, Currency.USD));
        assertThat(portfolio.getTotal(EUR)).isEqualTo(Result.fromSuccess(new Position(1, EUR)));


        Either<String, Integer> hundred = Either.<String, Integer>right(100).flatMap(this::getHalf).flatMap(this::getHalf);
        VavrAssertions.assertThat(hundred).containsOnRight(25);

        Either<String, Integer> fifty = Either.<String, Integer>right(50).flatMap(this::getHalf).flatMap(this::getHalf);
        VavrAssertions.assertThat(fifty).containsOnLeft("Cannot split in half");

        Either<String, Integer> two_hundreds = Either.<String, Integer>right(50)
                .flatMap(this::getHalf)
                .flatMap(this::getHalf)
                .flatMap(this::getHalf)
                .flatMap(this::getHalf)
                .flatMap(this::getHalf)
                .flatMap(this::getHalf)
                .flatMap(this::getHalf);
        VavrAssertions.assertThat(two_hundreds).containsOnLeft("Cannot split in half");
    }

    private Either<String, Integer> getHalf(Integer value) {
        if (value % 2 != 0) {
            return Either.left("Cannot split in half");
        }

        return Either.right(value / 2);
    }

    @Test
    @DisplayName("10 EUR + 5 USD = 17 USD")
    void getTotalInUsd() {
        portfolio.add(new Position(10, Currency.EUR));
        portfolio.add(new Position(5, Currency.USD));
        assertThat(portfolio.getTotal(USD)).isEqualTo(Result.fromSuccess(new Position(17.0, USD)));
    }

    @Test
    @DisplayName("1100 KRW + 1 USD = 2200 KRW")
    void getTotalInKrw() {
        portfolio.add(new Position(1100, KRW));
        portfolio.add(new Position(1, Currency.USD));
        assertThat(portfolio.getTotal(KRW)).isEqualTo(Result.fromSuccess(new Position(2200.0, KRW)));
    }

    @Test
    void getTotalShouldReturnMissingExchangeRatesExceptionWhenMultipleRatesMissing() {
        var portfolio = new Portfolio(CurrencyConverter.withExchangeRate(new ExchangeRate(KRW, USD, 1)));
        portfolio.add(new Position(1100, KRW));
        portfolio.add(new Position(1, Currency.USD));
        assertThat(portfolio.getTotal(EUR).failure()).contains(new MissingExchangeRate(KRW, EUR ), new MissingExchangeRate(USD, EUR))
				.hasSize(2);
    }

    @Test
    void getTotalShouldReturnMissingExchangeRatesExceptionWhenSingleRateMissing() {
        var portfolio = new Portfolio(CurrencyConverter.withExchangeRate(new ExchangeRate(KRW, USD, 1)));
        portfolio.add(new Position(1100, KRW));
        assertThat(portfolio.getTotal(EUR).failure()).contains(new MissingExchangeRate(KRW, EUR)).hasSize(1);
    }
}
