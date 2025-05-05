package money_problem.domain;

public class Portfolio {

    private double amount = 0;

    public double amount(Currency currency) {
        return amount;
    }

    public void add(double amount, Currency currency) {
        this.amount = amount;
    }

}
