package money_problem.domain;

public record ConversionResult(Position success, MissingExchangeRateException failure) {
    public ConversionResult(Position position) {
        this(position, null);
    }

    public ConversionResult(MissingExchangeRateException exception) {
        this(null, exception);
    }
}