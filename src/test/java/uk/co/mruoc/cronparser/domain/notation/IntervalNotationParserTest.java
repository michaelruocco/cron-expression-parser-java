package uk.co.mruoc.cronparser.domain.notation;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.cronparser.domain.NotationOutOfBoundsException;
import uk.co.mruoc.cronparser.domain.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class IntervalNotationParserTest {

    private final NotationParser parser = new IntervalNotationParser();

    @Test
    void shouldOnlyApplyToIntervalWithIntegerRangeOrMultipleStartValues() {
        assertThat(parser.appliesTo("2/2")).isTrue();
        assertThat(parser.appliesTo("*/6")).isTrue();
        assertThat(parser.appliesTo("2-4/6")).isTrue();
        assertThat(parser.appliesTo("4/6")).isTrue();

        assertThat(parser.appliesTo("2-4.4/6")).isFalse();
        assertThat(parser.appliesTo("2.2,4/6")).isFalse();
        assertThat(parser.appliesTo("*/6.5")).isFalse();
        assertThat(parser.appliesTo("2.5/6")).isFalse();
        assertThat(parser.appliesTo("1")).isFalse();
        assertThat(parser.appliesTo("*")).isFalse();
        assertThat(parser.appliesTo("-1")).isFalse();
        assertThat(parser.appliesTo("1-2")).isFalse();
        assertThat(parser.appliesTo("3,4")).isFalse();
        assertThat(parser.appliesTo("text")).isFalse();
    }

    @Test
    void shouldReturnIntervalValues() {
        String input = "2/3";

        int[] values = parser.toValues(input, TimeUnit.HOURS);

        assertThat(values).containsExactly(2, 5, 8, 11, 14, 17, 20, 23);
    }

    @Test
    void shouldReturnIntervalValuesWithRangeOfStartValues() {
        String input = "2-4/6";

        int[] values = parser.toValues(input, TimeUnit.HOURS);

        assertThat(values).containsExactly(2);
    }

    @Test
    void shouldReturnIntervalValuesWithSingleStartValues() {
        String input = "4/6";

        int[] values = parser.toValues(input, TimeUnit.HOURS);

        assertThat(values).containsExactly(4, 10, 16, 22);
    }

    @Test
    void shouldReturnIntervalValuesWithWildcardStartValue() {
        String input = "*/6";

        int[] values = parser.toValues(input, TimeUnit.HOURS);

        assertThat(values).containsExactly(0, 6, 12, 18);
    }

    @Test
    void shouldThrowExceptionIfStartIsOutsideBoundsOfTimeUnit() {
        String input = "25/2";

        Throwable error = catchThrowable(() -> parser.toValues(input, TimeUnit.HOURS));

        assertThat(error)
                .isInstanceOf(NotationOutOfBoundsException.class)
                .hasMessage("invalid hours value 25, outside bounds 0 and 23");
    }

    @Test
    void shouldThrowExceptionIfInputIsNotIntervalInput() {
        String input = "2";

        Throwable error = catchThrowable(() -> parser.toValues(input, TimeUnit.HOURS));

        assertThat(error)
                .isInstanceOf(InvalidNotationException.class)
                .hasCauseInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessage(input);
    }

    @Test
    void shouldThrowExceptionIfInputIsInBoundsOfTimeUnitButIsNotInteger() {
        String input = "3.5/2";

        Throwable error = catchThrowable(() -> parser.toValues(input, TimeUnit.HOURS));

        assertThat(error)
                .isInstanceOf(InvalidNotationException.class)
                .hasMessage(input);
    }

}
