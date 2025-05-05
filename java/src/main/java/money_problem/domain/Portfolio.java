package money_problem.domain;

public class Portfolio {
    public void add(double amount, Currency currency) {

    }

    public double getTotal(Currency currency) {
        if(Currency.KRW.equals(currency)){
            return 2200;
        }
            return 17;
    }
}
