package uk.co.mruoc.cronparser.domain.notation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import uk.co.mruoc.cronparser.domain.TimeUnit;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

import static uk.co.mruoc.cronparser.domain.notation.StringUtil.isInt;

@RequiredArgsConstructor
public class IntervalNotationParser implements NotationParser {

    private static final String WILDCARD = "*";

    private final SimpleNotationParser simpleParser;
    private final RangeNotationParser rangeParser;

    public IntervalNotationParser() {
        this(new SimpleNotationParser(), new RangeNotationParser());
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
            int[] starts = toStarts(parts[0], unit);
            unit.validate(starts);
            var interval = Integer.parseInt(parts[1]);
            return calculateIntervals(starts, unit, interval);
        } catch (ArrayIndexOutOfBoundsException | InvalidNotationException e) {
            throw new InvalidNotationException(input, e);
        }
    }

    private static String[] split(String value) {
        return StringUtils.split(value, "/");
    }

    private int[] toStarts(String value, TimeUnit unit) {
        if (WILDCARD.equals(value)) {
            return new int[]{unit.getLowerBound()};
        }
        if (rangeParser.appliesTo(value)) {
            return rangeParser.toValues(value, unit);
        }
        return simpleParser.toValues(value, unit);
    }

    private static int[] calculateIntervals(int[] starts, TimeUnit unit, int interval) {
        return Arrays.stream(starts)
                .flatMap(start -> calculateIntervals(start, unit, interval))
                .distinct()
                .sorted()
                .toArray();
    }

    private static IntStream calculateIntervals(int start, TimeUnit unit, int interval) {
        return IntStream.iterate(start, lessThanOrEqualToUpperBound(unit), incrementBy(interval));
    }

    private static IntPredicate lessThanOrEqualToUpperBound(TimeUnit unit) {
        return i -> i <= unit.getUpperBound();
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
