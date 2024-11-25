package vn.edu.hust.soict.japango.exception;

public class CustomExceptions {
    public static final ApiException INPUT_IS_EMPTY_EXCEPTION = new ApiException(1001, "Input should not be null nor empty");
}
