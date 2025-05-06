package money_problem.domain;

import java.util.Objects;

public class Result<S extends Money, F extends ConversionError> {

    private S money;
    private F conversionError;

    public Result(final F conversionError) {
        this.conversionError = conversionError;
    }

    public Result(final S money) {
        this.money = money;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final Result<?, ?> result = (Result<?, ?>) o;
        return Objects.equals(money, result.money) && Objects.equals(conversionError, result.conversionError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, conversionError);
    }

    @Override
    public String toString() {
        return "Result{" +
                "money=" + money +
                ", conversionError=" + conversionError +
                '}';
    }
}
