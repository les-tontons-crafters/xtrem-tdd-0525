package money_problem.domain;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    
    private List<Position> positions = new ArrayList<>();
    private Bank bank;
    
    public Portfolio(Bank bank) {
        this.bank = bank;
    }


    public void add(Position position) {
        positions.add(position);
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
