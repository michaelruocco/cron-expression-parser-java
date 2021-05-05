package uk.co.mruoc.cronparser.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Getter
public enum TimeUnit {

    MINUTES(0, 59),
    HOURS(0, 23),
    DAYS_OF_MONTH(1, 31),
    MONTHS(1, 12),
    DAYS_OF_WEEK(0, 6);

    private final int lowerBound;
    private final int upperBound;

    public int[] allValues() {
        return IntStream.rangeClosed(lowerBound, upperBound).toArray();
    }

    public void validate(int... values) {
        Arrays.stream(values).forEach(this::validate);
    }

    public String toIntValues(String rawValues) {
        return switch(this) {
            case DAYS_OF_WEEK -> toDaysOfWeek(rawValues);
            case MONTHS -> toMonths(rawValues);
            default -> rawValues;
        };
    }

    public void validate(int value) {
        if (isOutOfBounds(value)) {
            throw new NotationOutOfBoundsException(value, this);
        }
    }

    public String formattedName() {
        return name().toLowerCase().replace("_", " ");
    }

    private boolean isOutOfBounds(int value) {
        return value < lowerBound || value > upperBound;
    }

    private static String toDaysOfWeek(String rawValues) {
        return rawValues.toUpperCase()
                .replace("MON", "0")
                .replace("TUE", "1")
                .replace("WED", "2")
                .replace("THU", "3")
                .replace("FRI", "4")
                .replace("SAT", "5")
                .replace("SUN", "6");
    }

    private static String toMonths(String rawValues) {
        return rawValues.toUpperCase()
                .replace("JAN", "1")
                .replace("FEB", "2")
                .replace("MAR", "3")
                .replace("APR", "4")
                .replace("MAY", "5")
                .replace("JUN", "6")
                .replace("JUL", "7")
                .replace("AUG", "8")
                .replace("SEP", "9")
                .replace("OCT", "10")
                .replace("NOV", "11")
                .replace("DEC", "12");
    }

}
