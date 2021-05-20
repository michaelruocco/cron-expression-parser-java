package uk.co.mruoc.cronparser.domain.notation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import uk.co.mruoc.cronparser.domain.TimeUnit;

import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static uk.co.mruoc.cronparser.domain.notation.StringUtil.isInt;

@RequiredArgsConstructor
public class IntervalNotationParser implements NotationParser {

    private static final String WILDCARD = "*";

    private final RangeNotationParser rangeParser;
    private final SimpleNotationParser simpleParser;

    public IntervalNotationParser() {
        this(new RangeNotationParser(), new SimpleNotationParser());
    }

    @Override
    public boolean appliesTo(String value) {
        String[] parts = split(value);
        if (parts.length == 2) {
            return isIntWildcardRangeOrIntegers(parts[0]) && isInt(parts[1]);
        }
        return false;
    }

    @Override
    public int[] toValues(String input, TimeUnit unit) {
        try {
            String[] parts = split(input);
            String first = parts[0];
            int start = toStart(first, unit);
            int end = toEnd(first, unit);
            unit.validate(start);
            unit.validate(end);
            var interval = Integer.parseInt(parts[1]);
            return calculateIntervalsArray(start, end, interval);
        } catch (ArrayIndexOutOfBoundsException | InvalidNotationException e) {
            throw new InvalidNotationException(input, e);
        }
    }

    private static String[] split(String value) {
        return StringUtils.split(value, "/");
    }

    private int toStart(String value, TimeUnit unit) {
        if (WILDCARD.equals(value)) {
            return unit.getLowerBound();
        }
        if (rangeParser.appliesTo(value)) {
            return rangeParser.toFirstValue(value, unit);
        }
        return simpleParser.toFirstValue(value, unit);
    }

    private int toEnd(String value, TimeUnit unit) {
        if (rangeParser.appliesTo(value)) {
            int [] values = rangeParser.toValues(value, unit);
            return values[values.length-1];
        }
        return unit.getUpperBound();
    }

    private static int[] calculateIntervalsArray(int start, int end, int interval) {
        return calculateIntervals(start, end, interval).distinct().sorted().toArray();
    }

    private static IntStream calculateIntervals(int start, int end, int interval) {
        return IntStream.iterate(start, lessThan(end), incrementBy(interval));
    }

    private static IntPredicate lessThan(int max) {
        return i -> i <= max;
    }

    private static IntUnaryOperator incrementBy(int interval) {
        return i -> i + interval;
    }

    private boolean isIntWildcardRangeOrIntegers(String value) {
        return WILDCARD.equals(value) ||
                rangeParser.appliesTo(value) ||
                simpleParser.appliesTo(value);
    }

}
