package money_problem.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;

class PortfolioTest {

	private static Bank bank;

	@BeforeAll
	static void setup() {
		bank = Bank.withExchangeRate(USD, EUR, 1.0 / 1.2);
		bank.addExchangeRate(EUR, USD, 1.2);
		bank.addExchangeRate(USD, KRW, 1100);
	}

	@Test
	@DisplayName("1.2 USD equals 1 EUR")
	void shouldConvertEuroCorrectly() throws MissingExchangeRateException {
		var portfolio = new Portfolio(bank);
		portfolio.add(new Position(1.2, Currency.USD));

		double total = portfolio.getTotal(Currency.EUR);

		Assertions.assertThat(total).isEqualTo(1);
	}

	@Test
	@DisplayName("10 EUR + 5 USD equal 17 USD")
	void shouldAddEurosAndUsd() throws MissingExchangeRateException {
		var portfolio = new Portfolio(bank);
		portfolio.add(new Position(10, Currency.EUR));
		portfolio.add(new Position(5, Currency.USD));

		double total = portfolio.getTotal(Currency.USD);

		Assertions.assertThat(total).isEqualTo(17.0);
	}

	@Test
	@DisplayName("1100 KRW + 1 USD equal 2200 KRW")
	void shouldAddKrwAndUsd() throws MissingExchangeRateException {
		var portfolio = new Portfolio(bank);
		portfolio.add(new Position(1100, KRW));
		portfolio.add(new Position(1, Currency.USD));

		double total = portfolio.getTotal(KRW);

		Assertions.assertThat(total).isEqualTo(2200.0);
	}

}
