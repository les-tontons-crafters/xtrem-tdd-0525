package money_problem.domain;

public record Position(double amount, Currency currency) {
    public double add(double addedAmount) {
        return amount() + addedAmount;
    }

    public double times(int times) {
        return amount() * times;
    }

    public double divide(int divisor) {
        return amount() / divisor;
    }

    public boolean isTargetCurrency(Currency targetCurrency) {
        return currency.equals(targetCurrency);
    }
}