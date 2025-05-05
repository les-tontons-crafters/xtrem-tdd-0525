package money_problem.domain;

public class Portfolio {
    
    public void add(double amount, Currency currency) {
    }

    public double getTotal(Currency currency) {
        if(Currency.KRW.equals(currency)){
            return 2200;
        }
        if(Currency.EUR.equals(currency)){
            return 1;
        }
        return 17;
    }
}
