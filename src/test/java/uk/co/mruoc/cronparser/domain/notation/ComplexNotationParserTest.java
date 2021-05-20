package uk.co.mruoc.cronparser.domain.notation;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.cronparser.domain.NotationOutOfBoundsException;
import uk.co.mruoc.cronparser.domain.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class ComplexNotationParserTest {

    private final NotationParser parser = new ComplexNotationParser();

    @Test
    void shouldOnlyApplyToValidIntegerInputs() {
        assertThat(parser.appliesTo("1,4-8,*/15")).isTrue();
        assertThat(parser.appliesTo("3,45/15")).isTrue();
        assertThat(parser.appliesTo("1")).isTrue();
        assertThat(parser.appliesTo("*")).isTrue();
        assertThat(parser.appliesTo("*/2")).isTrue();
        assertThat(parser.appliesTo("3-4")).isTrue();

        assertThat(parser.appliesTo("-1")).isFalse();
        assertThat(parser.appliesTo("text")).isFalse();
    }

    @Test
    void shouldReturnCorrectValuesForComplexNotation() {
        String input = "1,4-8,*/15";

        int[] values = parser.toValues(input, TimeUnit.MINUTES);

        assertThat(values).containsExactly(0, 1, 4, 5, 6, 7, 8, 15, 30, 45);
    }

    @Test
    void shouldReturnCorrectValuesForComplexIntervalStartNotationWithComma() {
        String input = "3,45/15";

        int[] values = parser.toValues(input, TimeUnit.MINUTES);

        assertThat(values).containsExactly(3, 45);
    }

    @Test
    void shouldReturnCorrectValuesForComplexIntervalStartNotationWithCommaAndRange() {
        String input = "3,40-45/5";

        int[] values = parser.toValues(input, TimeUnit.MINUTES);

        assertThat(values).containsExactly(3, 40, 45);
    }

    @Test
    void shouldThrowExceptionIfInputIsOutsideBoundsOfTimeUnit() {
        String input = "60";

        Throwable error = catchThrowable(() -> parser.toValues(input, TimeUnit.MINUTES));

        assertThat(error)
                .isInstanceOf(NotationOutOfBoundsException.class)
                .hasMessage("invalid minutes value 60, outside bounds 0 and 59");
    }

}
