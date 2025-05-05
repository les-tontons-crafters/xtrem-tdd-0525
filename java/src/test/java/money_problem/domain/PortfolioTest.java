package money_problem.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static money_problem.domain.Currency.EUR;
import static money_problem.domain.Currency.USD;

public class PortfolioTest {
    
    @Test
    @DisplayName("1.2 USD equals 1 EUR")
    void shouldConvertEuroCorrectly(){
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);
        var portfolio = new Portfolio(bank);
        portfolio.add(new Position(1.2, Currency.USD));

        double total = portfolio.getTotal(Currency.EUR);

        Assertions.assertThat(total).isEqualTo(1);
    }
    
    @Test
    @DisplayName("10 EUR + 5 USD equal 17 USD")
    public void shouldAddEurosAndUsd(){
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);
        var portfolio = new Portfolio(bank);
        portfolio.add(new Position(10, Currency.EUR));
        portfolio.add(new Position(5, Currency.USD));

        double total = portfolio.getTotal(Currency.USD);

        Assertions.assertThat(total).isEqualTo(17.0);
    }

    @Test
    @DisplayName("1100 KRW + 1 USD equal 2200 KRW")
    public void shouldAddKrwAndUsd(){
        Bank bank = Bank.withExchangeRate(EUR, USD, 1.2);
        var portfolio = new Portfolio(bank);
        portfolio.add(new Position(1100, Currency.KRW));
        portfolio.add(new Position(1, Currency.USD));

        double total = portfolio.getTotal(Currency.KRW);

        Assertions.assertThat(total).isEqualTo(2200.0);
    }

}
