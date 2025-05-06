package money_problem.domain;

public class MoneyCalculator {
    public static double add(Position position, double addedAmount) {
        return position.amount() + addedAmount;
    }

    public static double times(Position position, int times) {
        return position.amount() * times;
    }

    public static double divide(Position position, int divisor) {
        return position.amount() / divisor;
    }
}