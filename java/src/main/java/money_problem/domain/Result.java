package money_problem.domain;

import java.util.Objects;

public class Result<Sucess, Error> {

    private final String foo;

    public Result(String foo) {
        this.foo = foo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Result<?, ?> result = (Result<?, ?>) o;
        return Objects.equals(foo, result.foo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(foo);
    }

}
