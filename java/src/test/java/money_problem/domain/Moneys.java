package money_problem.domain;

public class Moneys {
    public static Money dollars(double amount) {
        return new Money(amount, Currency.USD);
    }

    public static Money euros(double amount) {
        return new Money(amount, Currency.EUR);
    }

    public static Money southKoreanWons(double amount) {
        return new Money(amount, Currency.KRW);
    }
}
