package vn.edu.hust.soict.japango.exception;

import org.springframework.http.HttpStatus;

public class CustomExceptions {
    public static final ApiException USER_NOT_EXISTS_EXCEPTION = new ApiException(1002, "User does not exist!", HttpStatus.NOT_FOUND);
    public static final ApiException INCORRECT_PASSWORD_EXCEPTION = new ApiException(1003, "Password is incorrect!");
    public static final ApiException USERNAME_USED_EXCEPTION = new ApiException(1004, "This username is already used.");
    public static final ApiException EMAIL_USED_EXCEPTION = new ApiException(1004, "This email is already used.");
    public static final ApiException NEW_PASSWORD_SAME_AS_OLD_PASSWORD_EXCEPTION = new ApiException(1005, "New password is the same as old password!");
    public static final ApiException LOGIN_REQUIRED_EXCEPTION = new ApiException(1006, "Please login to do this action.", HttpStatus.UNAUTHORIZED);
    public static final ApiException TOKEN_INVALID_EXCEPTION = new ApiException(1007, "Sorry, your token has expired or has been used. You can request another.");
    public static final ApiException HISTORY_ITEM_NOT_EXISTS_EXCEPTION = new ApiException(1008, "History item does not exist!", HttpStatus.NOT_FOUND);
    public static final ApiException SAVED_ITEM_NOT_EXISTS_EXCEPTION = new ApiException(1009, "Saved item does not exist!", HttpStatus.NOT_FOUND);
}
