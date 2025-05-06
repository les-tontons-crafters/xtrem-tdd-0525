package money_problem.domain;

public class MoneyCalculator {
    public static double add(Money money, double addedAmount) {
        return money.amount() + addedAmount;
    }

    public static double times(Money money, int times) {
        return money.amount() * times;
    }

    public static double divide(Money money, int divisor) {
        return money.amount() / divisor;
    }
}