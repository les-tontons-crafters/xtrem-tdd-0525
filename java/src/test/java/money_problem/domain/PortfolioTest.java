package money_problem.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PortfolioTest {

	private static CurrencyConverter currencyConverter;
	private Portfolio portfolio;

	@BeforeAll
	static void setup() {
		currencyConverter = CurrencyConverter.withExchangeRate(USD, EUR, 1.0 / 1.2);
		currencyConverter.addExchangeRate(EUR, USD, 1.2);
		currencyConverter.addExchangeRate(USD, KRW, 1100);
	}

	@BeforeEach
	void BeforeEach() {
		portfolio = new Portfolio(currencyConverter);
	}

	@Test
	@DisplayName("1.2 USD = 1 EUR")
	void getTotalInEur() throws MissingExchangeRatesException {
		portfolio.add(new Position(1.2, Currency.USD));
		Assertions.assertThat(portfolio.getTotal(Currency.EUR)).isEqualTo(1);
	}

	@Test
	@DisplayName("10 EUR + 5 USD = 17 USD")
	void getTotalInUsd() throws MissingExchangeRatesException {
		portfolio.add(new Position(10, Currency.EUR));
		portfolio.add(new Position(5, Currency.USD));
		Assertions.assertThat(portfolio.getTotal(Currency.USD)).isEqualTo(17.0);
	}

	@Test
	@DisplayName("1100 KRW + 1 USD = 2200 KRW")
	void getTotalInKrw() throws MissingExchangeRatesException {
		portfolio.add(new Position(1100, KRW));
		portfolio.add(new Position(1, Currency.USD));
		Assertions.assertThat(portfolio.getTotal(KRW)).isEqualTo(2200.0);
	}

	@Test
	void getTotalShouldReturnMissingExchangeRatesExceptionWhenMultipleExchangeRatesMissing() {
		var portfolio = new Portfolio(CurrencyConverter.withExchangeRate(KRW, USD, 1));
		portfolio.add(new Position(1100, KRW));
		portfolio.add(new Position(1, Currency.USD));
		assertThatThrownBy(() -> portfolio.getTotal(EUR))
				.isInstanceOf(MissingExchangeRatesException.class)
				.hasMessage("KRW->EUR,USD->EUR");
	}
}
