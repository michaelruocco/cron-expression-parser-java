package uk.co.mruoc.cronparser.domain.notation;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.cronparser.domain.NotationOutOfBoundsException;
import uk.co.mruoc.cronparser.domain.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class RangeNotationParserTest {

    private final NotationParser parser = new RangeNotationParser();

    @Test
    void shouldOnlyApplyToRangeIntegerInput() {
        assertThat(parser.appliesTo("5-6")).isTrue();

        assertThat(parser.appliesTo("5-6.6")).isFalse();
        assertThat(parser.appliesTo("5.5-6")).isFalse();
        assertThat(parser.appliesTo("1")).isFalse();
        assertThat(parser.appliesTo("*")).isFalse();
        assertThat(parser.appliesTo("-1")).isFalse();
        assertThat(parser.appliesTo("*/2")).isFalse();
        assertThat(parser.appliesTo("3,4")).isFalse();
        assertThat(parser.appliesTo("text")).isFalse();
    }

    @Test
    void shouldReturnValuesWithinRange() {
        String input = "1-3";

        int[] values = parser.toValues(input, TimeUnit.HOURS);

        assertThat(values).containsExactly(1, 2, 3);
    }

    @Test
    void shouldReturnValuesWithinRangeIfStartIsGreaterThanEnd() {
        String input = "3-1";

        int[] values = parser.toValues(input, TimeUnit.HOURS);

        assertThat(values).containsExactly(1, 2, 3);
    }

    @Test
    void shouldReturnValueWhenRangeStartIsEqualToEnd() {
        String input = "3-3";

        int[] values = parser.toValues(input, TimeUnit.HOURS);

        assertThat(values).containsExactly(3);
    }

    @Test
    void shouldThrowExceptionIfInputIsOutsideBoundsOfTimeUnit() {
        String input = "20-25";

        Throwable error = catchThrowable(() -> parser.toValues(input, TimeUnit.HOURS));

        assertThat(error)
                .isInstanceOf(NotationOutOfBoundsException.class)
                .hasMessage("invalid hours value 25, outside bounds 0 and 23");
    }

    @Test
    void shouldThrowExceptionIfInputIsNotRangeInput() {
        String input = "2";

        Throwable error = catchThrowable(() -> parser.toValues(input, TimeUnit.HOURS));

        assertThat(error)
                .isInstanceOf(InvalidNotationException.class)
                .hasCauseInstanceOf(ArrayIndexOutOfBoundsException.class)
                .hasMessage(input);
    }

    @Test
    void shouldThrowExceptionIfInputIsInBoundsOfTimeUnitButIsNotInteger() {
        String input = "2-3.5";

        Throwable error = catchThrowable(() -> parser.toValues(input, TimeUnit.HOURS));

        assertThat(error)
                .isInstanceOf(InvalidNotationException.class)
                .hasCauseInstanceOf(NumberFormatException.class)
                .hasMessage(input);
    }

}
