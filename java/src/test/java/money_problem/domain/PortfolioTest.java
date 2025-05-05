package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PortfolioTest {

    @Test
    @DisplayName("Checks empty Portfolio")
    void emptyPortfolio() {
        Portfolio testee = new Portfolio();
        double actual = testee.get(Currency.USD);
        assertThat(actual).isZero();
    }

    @Test
    @DisplayName("Adds some money to Portfolio and reads it")
    void addUSD() {
        Portfolio testee = new Portfolio();
        testee.add(1.0, Currency.USD);

        double actual = testee.get(Currency.USD);
        assertThat(actual).isEqualTo(1.0);
    }

}
