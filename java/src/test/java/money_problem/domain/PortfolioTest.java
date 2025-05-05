package money_problem.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PortfolioTest {

    @Test
    void whenCreatingEmptyPortfolioThenReturnZero() {
        Portfolio testee = new Portfolio();

        double actual = testee.get(Currency.USD);

        assertThat(actual).isZero();
    }

    @Test
    void addParticularAmountInUsdWhenPortfolioEmptyThenReturnExpectedAmount() {
        Portfolio testee = new Portfolio();

        testee.add(1.0, Currency.USD);

        double actual = testee.get(Currency.USD);
        assertThat(actual).isEqualTo(1.0);
    }

}
