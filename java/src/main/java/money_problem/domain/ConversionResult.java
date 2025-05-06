package money_problem.domain;

public record ConversionResult(Position success, Exception failure) {
    public ConversionResult(Position position) {
        this(position, null);
    }

    public ConversionResult(Exception exception) {
        this(null, exception);
    }

    public boolean isFailure() {
        return this.failure != null;
    }
}