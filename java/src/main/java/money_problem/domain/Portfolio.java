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

    public double getTotal(Currency currency) throws MissingExchangeRateException {
        double sum = 0;
        for (Position position : positions) {
            double converted = bank.convert(position.amount(), position.currency(), currency);
            sum += converted;
        }
        return sum;
    }
}
