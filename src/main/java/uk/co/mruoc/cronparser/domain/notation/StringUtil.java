package uk.co.mruoc.cronparser.domain.notation;

import java.util.regex.Pattern;

public class StringUtil {

    private static final Pattern INT_REGEX = Pattern.compile("\\d+");

    private StringUtil() {
        // utility class
    }

    public static boolean isInt(String value) {
        return INT_REGEX.matcher(value).matches();
    }

}
