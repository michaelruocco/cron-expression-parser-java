package uk.co.mruoc.cronparser.domain.notation;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.cronparser.domain.NotationOutOfBoundsException;
import uk.co.mruoc.cronparser.domain.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class SimpleNotationParserTest {

    private final NotationParser parser = new SimpleNotationParser();

    @Test
    void shouldOnlyApplyToIntegerInputs() {
        assertThat(parser.appliesTo("1")).isTrue();
        assertThat(parser.appliesTo("3,4")).isTrue();

        assertThat(parser.appliesTo("1.5")).isFalse();
        assertThat(parser.appliesTo("*")).isFalse();
        assertThat(parser.appliesTo("-1")).isFalse();
        assertThat(parser.appliesTo("*/2")).isFalse();
        assertThat(parser.appliesTo("5-6")).isFalse();
        assertThat(parser.appliesTo("text")).isFalse();
    }

    @Test
    void shouldReturnIntegerForValidIntegerInput() {
        String input = "1";

        int[] values = parser.toValues(input, TimeUnit.HOURS);

        assertThat(values).containsExactly(1);
    }

    @Test
    void shouldReturnIntegersForMultipleValidIntegerInputs() {
        String input = "1,3";

        int[] values = parser.toValues(input, TimeUnit.HOURS);

        assertThat(values).containsExactly(1, 3);
    }

    @Test
    void shouldThrowExceptionIfInputIsOutsideBoundsOfTimeUnit() {
        String input = "24";

        Throwable error = catchThrowable(() -> parser.toValues(input, TimeUnit.HOURS));

        assertThat(error)
                .isInstanceOf(NotationOutOfBoundsException.class)
                .hasMessage("invalid hours value 24, outside bounds 0 and 23");
    }

    @Test
    void shouldThrowExceptionIfInputIsNotInteger() {
        String input = "2.5";

        Throwable error = catchThrowable(() -> parser.toValues(input, TimeUnit.HOURS));

        assertThat(error)
                .isInstanceOf(InvalidNotationException.class)
                .hasCauseInstanceOf(NumberFormatException.class)
                .hasMessage(input);
    }

}
