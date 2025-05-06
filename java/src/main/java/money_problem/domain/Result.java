package money_problem.domain;

import java.util.Objects;

public class Result<S extends Money, F extends ConversionError> {

    private S money;
    private F conversionError;

    public Result(F conversionError) {
        this.conversionError = conversionError;
    }

    public Result(S money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result<?, ?> result = (Result<?, ?>) o;
        return Objects.equals(money, result.money) && Objects.equals(conversionError, result.conversionError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money, conversionError);
    }
}
