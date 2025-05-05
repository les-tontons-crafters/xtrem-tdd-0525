package money_problem.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PortfolioTest {
    
    @Test
    @DisplayName("1.2 USD equals 1 EUR")
    void shouldConvertEuroCorrectly(){
        var portfolio = new Portfolio();
        portfolio.add(1.2,  Currency.USD);

        double total = portfolio.getTotal(Currency.EUR);

        Assertions.assertThat(total).isEqualTo(1);
    }
    
    @Test
    @DisplayName("10 EUR + 5 USD equal 17 USD")
    public void shouldAddEurosAndUsd(){
        var portfolio = new Portfolio();
        portfolio.add(10, Currency.EUR);
        portfolio.add(5, Currency.USD);

        double total = portfolio.getTotal(Currency.USD);

        Assertions.assertThat(total).isEqualTo(17.0);
    }

    @Test
    @DisplayName("1100 KRW + 1 USD equal 2200 KRW")
    public void shouldAddKrwAndUsd(){
        var portfolio = new Portfolio();
        portfolio.add(1100, Currency.KRW);
        portfolio.add(1, Currency.USD);

        double total = portfolio.getTotal(Currency.KRW);

        Assertions.assertThat(total).isEqualTo(2200.0);
    }

}
