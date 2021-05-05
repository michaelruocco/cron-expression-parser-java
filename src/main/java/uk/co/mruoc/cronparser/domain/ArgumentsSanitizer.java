package uk.co.mruoc.cronparser.domain;

import java.util.Arrays;

public class ArgumentsSanitizer {

    private static final int REQUIRED_ARGUMENTS = 6;
    private static final String USAGE_MESSAGE = "usage: please provide a valid cron expression";

    public String[] sanitize(String[] args) {
        if (args.length < REQUIRED_ARGUMENTS) {
            throw new ParserException(toErrorMessage(args));
        }
        return toRequiredArgs(args);
    }

    private static String toErrorMessage(String[] args) {
        if (args.length == 0) {
            return USAGE_MESSAGE;
        }
        return toInvalidExpressionMessage(args);
    }

    private static String toInvalidExpressionMessage(String[] args) {
        var expression = String.join(" ", args);
        return String.format("%s, invalid cron expression provided %s", USAGE_MESSAGE, expression);
    }

    private static String[] toRequiredArgs(String[] args) {
        return Arrays.copyOfRange(args, args.length - REQUIRED_ARGUMENTS, args.length);
    }

}
