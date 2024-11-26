package vn.edu.hust.soict.japango.exception;

public class CustomExceptions {
    public static final ApiException USER_NOT_EXISTS_EXCEPTION = new ApiException(1002, "User does not exist!");
    public static final ApiException INCORRECT_PASSWORD_EXCEPTION = new ApiException(1003, "Password is incorrect!");
    public static final ApiException USERNAME_USED_EXCEPTION = new ApiException(1004, "This username is already used.");
    public static final ApiException EMAIL_USED_EXCEPTION = new ApiException(1004, "This email is already used.");
}
