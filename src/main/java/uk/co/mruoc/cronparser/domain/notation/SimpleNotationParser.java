package uk.co.mruoc.cronparser.domain.notation;

import org.apache.commons.lang3.StringUtils;
import uk.co.mruoc.cronparser.domain.TimeUnit;

import java.util.Arrays;
import java.util.stream.Stream;

public class SimpleNotationParser implements NotationParser {

    @Override
    public boolean appliesTo(String input) {
        return split(input).allMatch(StringUtil::isInt);
    }

    @Override
    public int[] toValues(String input, TimeUnit unit) {
        int[] values = toIntegers(input);
        unit.validate(values);
        return values;
    }

    private static int[] toIntegers(String input) {
        return split(input).mapToInt(SimpleNotationParser::toInteger).toArray();
    }

    private static int toInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidNotationException(input, e);
        }
    }

    private static Stream<String> split(String input) {
        return Arrays.stream(StringUtils.split(input, ","));
    }

}
