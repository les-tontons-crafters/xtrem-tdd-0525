package money_problem.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PortfolioTest {

    @Test
    void whenCreatingEmptyPortfolioThenReturnZero() {
        Portfolio testee = new Portfolio();

        double actual = testee.amount(Currency.USD);

        assertThat(actual).isZero();
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioEmptyThenReturnExpectedAmount() {
        Portfolio testee = new Portfolio();

        testee.add(1.0, Currency.USD);

        double actual = testee.amount(Currency.USD);
        assertThat(actual).isEqualTo(1.0);
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() {
        Portfolio testee = new Portfolio();
        testee.add(1.0, Currency.USD);

        double actual = testee.amount(Currency.EUR);


        assertThat(actual).isEqualTo(0.82);
    }
//5 USD + 10 EUR = 17 USD
// 1 USD + 1100 KRW = 2200 KRW
}
