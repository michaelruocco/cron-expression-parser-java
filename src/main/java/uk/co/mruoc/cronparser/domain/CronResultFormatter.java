package uk.co.mruoc.cronparser.domain;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;

public class CronResultFormatter {

    public String format(CronResult result) {
        return toLines(result).collect(Collectors.joining(lineSeparator()));
    }

    public static Stream<String> toLines(CronResult result) {
        return Stream.of(
                formatMinutes(result),
                formatHours(result),
                formatDaysOfMonth(result),
                formatMonths(result),
                formatDaysOfWeek(result),
                formatCommand(result)
        );
    }

    private static String formatMinutes(CronResult result) {
        return formatValues("minute", result::getMinutes);
    }

    private static String formatHours(CronResult result) {
        return formatValues("hour", result::getHours);
    }

    private static String formatDaysOfMonth(CronResult result) {
        return formatValues("day of month", result::getDaysOfMonth);
    }

    private static String formatMonths(CronResult result) {
        return formatValues("month", result::getMonths);
    }

    private static String formatDaysOfWeek(CronResult result) {
        return formatValues("day of week", result::getDaysOfWeek);
    }

    private static String formatCommand(CronResult result) {
        return formatValues("command", result.getCommand());
    }

    private static String formatValues(String name, Supplier<int[]> values) {
        return formatValues(name, toString(values.get()));
    }

    private static String formatValues(String name, String values) {
        return String.format("%-14s%s", name, values);
    }

    private static String toString(int[] values) {
        return Arrays.stream(values)
                .sorted()
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(" "));
    }
}
