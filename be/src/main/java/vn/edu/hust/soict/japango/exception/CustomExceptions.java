package vn.edu.hust.soict.japango.exception;

public class CustomExceptions {
    public static final ApiException USER_NOT_EXISTS_EXCEPTION = new ApiException(1002, "User does not exist!");
    public static final ApiException INCORRECT_PASSWORD_EXCEPTION = new ApiException(1003, "Password is incorrect!");
}
