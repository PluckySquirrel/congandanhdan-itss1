package vn.edu.hust.soict.japango.common.utils;

public class StringUtils {
    public static String replaceArg(String string, String argKey, String argValue) {
        return string.replaceAll("\\{" + argKey + "}", argValue);
    }
}
