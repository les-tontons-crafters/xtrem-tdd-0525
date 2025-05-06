package money_problem.domain;

import java.util.Objects;

public class Result<S extends Money, F extends ConversionError> {

    private F conversionError;

    public Result(F conversionError) {
        this.conversionError = conversionError;
    }

    public Result(S conversionError) {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Result<?, ?> result = (Result<?, ?>) o;
        return Objects.equals(conversionError, result.conversionError);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(conversionError);
    }

}
