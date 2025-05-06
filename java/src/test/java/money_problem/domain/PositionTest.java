package money_problem.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static money_problem.domain.Currency.*;
import static org.assertj.core.api.Assertions.assertThat;

class PositionTest {
    @Test
    @DisplayName("5 USD + 10 USD = 15 USD")
    void shouldAddInUsd() {
        assertThat(new Position(5, USD) .add(10)).isEqualTo(15);
    }

    @Test
    @DisplayName("10 EUR x 2 = 20 EUR")
    void shouldMultiplyInEuros() {
        assertThat(new Position(10, EUR).times(2)).isEqualTo(20);
    }

    @Test
    @DisplayName("4002 KRW / 4 = 1000.5 KRW")
    void shouldDivideInKoreanWons() {
        assertThat(new Position(4002, KRW).divide(4)).isEqualTo(1000.5);
    }
}