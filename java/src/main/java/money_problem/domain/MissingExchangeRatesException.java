package money_problem.domain;

import java.util.List;

public class MissingExchangeRatesException extends Exception {
    public MissingExchangeRatesException(List<String> missingExchangeRatesExceptionMessages) {
        super(String.join(",", missingExchangeRatesExceptionMessages));
    }
}
