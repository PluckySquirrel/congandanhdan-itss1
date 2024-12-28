package vn.edu.hust.soict.japango.exception;

import org.springframework.http.HttpStatus;

public class CustomExceptions {
    public static final ApiException USER_NOT_EXISTS_EXCEPTION = new ApiException(1002, "ユーザーは存在しません！", HttpStatus.NOT_FOUND);
    public static final ApiException INCORRECT_PASSWORD_EXCEPTION = new ApiException(1003, "パスワードが間違っています！");
    public static final ApiException USERNAME_USED_EXCEPTION = new ApiException(1004, "このユーザー名はすでに使用されています。");
    public static final ApiException EMAIL_USED_EXCEPTION = new ApiException(1004, "このメールアドレスはすでに使用されています。");
    public static final ApiException NEW_PASSWORD_SAME_AS_OLD_PASSWORD_EXCEPTION = new ApiException(1005, "新しいパスワードは旧パスワードと同じです！");
    public static final ApiException LOGIN_REQUIRED_EXCEPTION = new ApiException(1006, "この操作を行うにはログインが必要です。", HttpStatus.UNAUTHORIZED);
    public static final ApiException TOKEN_INVALID_EXCEPTION = new ApiException(1007, "申し訳ありませんが、トークンは期限切れまたは使用済みです。別のトークンをリクエストできます。");
    public static final ApiException HISTORY_ITEM_NOT_EXISTS_EXCEPTION = new ApiException(1008, "履歴アイテムは存在しません！", HttpStatus.NOT_FOUND);
    public static final ApiException SAVED_ITEM_NOT_EXISTS_EXCEPTION = new ApiException(1009, "保存されたアイテムは存在しません！", HttpStatus.NOT_FOUND);
}
