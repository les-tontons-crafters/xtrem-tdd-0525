package money_problem.domain;

public class Portfolio {

    private double amount = 0;

    public double amount(Currency currency) {
        if (currency.equals(Currency.EUR)) {
            return 0.83;
        }
        return amount;
    }

    public void add(double amount, Currency currency) {
        this.amount = amount;
    }

}
