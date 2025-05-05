package money_problem.domain;

public class MissingExchangeRatesException extends Exception {
    public MissingExchangeRatesException(String missingExchangeRatesExceptionMessage) {
        super(missingExchangeRatesExceptionMessage);
    }
}
