package money_problem.domain;

public record Result<S, F>(S success, F failure) {
    public static <S, F> Result<S, F> fromSuccess(S successValue) {
        return new Result<>(successValue, null);
    }

    public static <S, F> Result<S, F> fromFailure(F failureValue) {
        return new Result<>(null, failureValue);
    }

    public boolean isFailure() {
        return this.failure != null;
    }
}
