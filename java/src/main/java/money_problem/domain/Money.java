package money_problem.domain;

public record Money(double amount, Currency currency) {
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