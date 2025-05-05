package money_problem.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PortfolioTest {

    @Test
    void emptyPortfolio() {
        final Portfolio testee = new Portfolio();
        final double actual = testee.get(Currency.USD);
        assertThat(actual).isZero();
    }
}
