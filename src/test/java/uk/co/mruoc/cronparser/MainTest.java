package uk.co.mruoc.cronparser;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.org.webcompere.systemstubs.SystemStubs.tapSystemErrAndOut;

class MainTest {

    @Test
    void shouldParseValidCronExpression() throws Exception {
        String[] args = {"*/15", "0", "1,15", "*", "0-4", "/usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).isEqualTo("""
                minute        0 15 30 45
                hour          0
                day of month  1 15
                month         1 2 3 4 5 6 7 8 9 10 11 12
                day of week   0 1 2 3 4
                command       /usr/bin/find
                """);
    }

    @Test
    void shouldParseValidComplexMinutesCronExpression() throws Exception {
        String[] args = {"1,4-8,*/15", "0", "1,15", "*", "1-5", "/usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("minute        0 1 4 5 6 7 8 15 30 45");
    }

    @Test
    void shouldParseValidTextualDaysOfWeekCronExpression() throws Exception {
        String[] args = {"1,4-8,*/15", "0", "1,15", "*", "Mon-Fri", "/usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("day of week   0 1 2 3 4");
    }

    @Test
    void shouldParseValidTextualMonthsCronExpression() throws Exception {
        String[] args = {"1,4-8,*/15", "0", "1,15", "jan,jun,dec", "1-5", "/usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).contains("month         1 6 12");
    }

    @Test
    void shouldPrintUsageIfNoExpressionPassed() throws Exception {
        String[] args = new String[0];

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).isEqualTo("usage: please provide a valid cron expression\n");
    }

    @Test
    void shouldPrintErrorIfLessThanSixArgumentsProvided() throws Exception {
        String[] args = {"59", "0", "1,15", "*", "1-5"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).isEqualTo("usage: please provide a valid cron expression, " +
                "invalid cron expression provided 59 0 1,15 * 1-5\n");
    }

    @Test
    void shouldPrintErrorIfValueIsOutOfBounds() throws Exception {
        String[] args = {"60", "0", "1,15", "*", "1-5", "/usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).isEqualTo("invalid minutes value 60, outside bounds 0 and 59\n");
    }

    @Test
    void shouldPrintErrorIfNotationParserNotFoundForSimpleDecimalValue() throws Exception {
        String[] args = {"3.5", "0", "1,15", "*", "1-5", "/usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).isEqualTo("notation parser not found for value 3.5\n");
    }

    @Test
    void shouldPrintErrorIfNotationParserNotFoundForIntervalWithDecimalValue() throws Exception {
        String[] args = {"3", "0", "1,15", "*", "*/2.5", "/usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).isEqualTo("notation parser not found for value */2.5\n");
    }

    @Test
    void shouldPrintErrorIfNotationParserNotFoundForRangeWithDecimalValue() throws Exception {
        String[] args = {"3", "0", "1,15", "*", "1-5.5", "/usr/bin/find"};

        String output = tapSystemErrAndOut(() -> Main.main(args));

        assertThat(output).isEqualTo("notation parser not found for value 1-5.5\n");
    }

}
