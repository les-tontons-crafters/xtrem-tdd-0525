package money_problem.domain;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PortfolioTest {

    @Test
    void whenCreatingEmptyPortfolioThenReturnZero() {
        final Portfolio testee = new Portfolio();

        final double actual = testee.amount(Currency.USD);

        assertThat(actual).isZero();
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioEmptyThenReturnExpectedAmount() {
        final Portfolio testee = new Portfolio();

        testee.add(1.0, Currency.USD);

        final double actual = testee.amount(Currency.USD);
        assertThat(actual).isEqualTo(1.0);
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() {
        final Portfolio testee = new Portfolio();
        testee.add(1.0, Currency.USD);

        final double actual = testee.amount(Currency.EUR);


        assertThat(actual).isCloseTo(0.83, Offset.offset(0.01));
    }

    @Test
    void addADifferentAmountInUsdWhenPortfolioIsEmptyThenReturnExpectedAmountInEur() {
        final Portfolio testee = new Portfolio();
        testee.add(2.0, Currency.USD);

        final double actual = testee.amount(Currency.EUR);


        assertThat(actual).isCloseTo(1.66, Offset.offset(0.01));
    }


//5 USD + 10 EUR = 17 USD
// 1 USD + 1100 KRW = 2200 KRW
}
