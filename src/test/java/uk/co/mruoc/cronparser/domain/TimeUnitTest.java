package uk.co.mruoc.cronparser.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;
import static uk.co.mruoc.cronparser.domain.TimeUnit.DAYS_OF_MONTH;
import static uk.co.mruoc.cronparser.domain.TimeUnit.DAYS_OF_WEEK;
import static uk.co.mruoc.cronparser.domain.TimeUnit.HOURS;
import static uk.co.mruoc.cronparser.domain.TimeUnit.MINUTES;
import static uk.co.mruoc.cronparser.domain.TimeUnit.MONTHS;

public class TimeUnitTest {

    @Nested
    class MinutesTest {

        @Test
        void shouldReturnAllValues() {
            assertThat(MINUTES.allValues()).containsExactly(allValuesIncluding(0, 59));
        }

        @Test
        void shouldDoNothingIfValuesAreValid() {
            assertThatCode(() -> MINUTES.validate(0, 59)).doesNotThrowAnyException();
        }

        @Test
        void shouldThrowExceptionIfValueIsLessThanLowerBound() {
            assertThat(catchThrowable(() -> MINUTES.validate(60)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid minutes value 60, outside bounds 0 and 59");
        }

        @Test
        void shouldThrowExceptionIfValueIsGreaterThanUpperBound() {
            assertThat(catchThrowable(() -> MINUTES.validate(60)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid minutes value 60, outside bounds 0 and 59");
        }

        @Test
        void shouldReturnInputAsIntValues() {
            assertThat(MINUTES.toIntValues("1,2,3")).isEqualTo("1,2,3");
        }

    }

    @Nested
    class HoursTest {

        @Test
        void shouldReturnAllValues() {
            assertThat(HOURS.allValues()).containsExactly(allValuesIncluding(0, 23));
        }

        @Test
        void shouldDoNothingIfValuesAreValid() {
            assertThatCode(() -> HOURS.validate(0, 23)).doesNotThrowAnyException();
        }

        @Test
        void shouldThrowExceptionIfValueIsLessThanLowerBound() {
            assertThat(catchThrowable(() -> HOURS.validate(-1)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid hours value -1, outside bounds 0 and 23");
        }

        @Test
        void shouldThrowExceptionIfValueIsGreaterThanUpperBound() {
            assertThat(catchThrowable(() -> HOURS.validate(24)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid hours value 24, outside bounds 0 and 23");
        }

        @Test
        void shouldReturnInputAsIntValues() {
            assertThat(HOURS.toIntValues("1,2,3")).isEqualTo("1,2,3");
        }

    }

    @Nested
    class DaysOfMonthTest {

        @Test
        void shouldReturnAllValues() {
            assertThat(DAYS_OF_MONTH.allValues()).containsExactly(allValuesIncluding(1, 31));
        }

        @Test
        void shouldDoNothingIfValuesAreValid() {
            assertThatCode(() -> DAYS_OF_MONTH.validate(1, 31)).doesNotThrowAnyException();
        }

        @Test
        void shouldThrowExceptionIfValueIsLessThanLowerBound() {
            assertThat(catchThrowable(() -> DAYS_OF_MONTH.validate(0)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid days of month value 0, outside bounds 1 and 31");
        }

        @Test
        void shouldThrowExceptionIfValueIsGreaterThanUpperBound() {
            assertThat(catchThrowable(() -> DAYS_OF_MONTH.validate(32)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid days of month value 32, outside bounds 1 and 31");
        }

        @Test
        void shouldReturnInputAsIntValues() {
            assertThat(DAYS_OF_MONTH.toIntValues("1,2,3")).isEqualTo("1,2,3");
        }

    }

    @Nested
    class MonthsTest {

        @Test
        void shouldReturnAllValues() {
            assertThat(MONTHS.allValues()).containsExactly(allValuesIncluding(1, 12));
        }

        @Test
        void shouldDoNothingIfValuesAreValid() {
            assertThatCode(() -> MONTHS.validate(1, 12)).doesNotThrowAnyException();
        }

        @Test
        void shouldThrowExceptionIfValueLessThanLowerBound() {
            assertThat(catchThrowable(() -> MONTHS.validate(0)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid months value 0, outside bounds 1 and 12");
        }

        @Test
        void shouldThrowExceptionIfValueIsGreaterThanUpperBound() {
            assertThat(catchThrowable(() -> MONTHS.validate(13)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid months value 13, outside bounds 1 and 12");
        }

        @Test
        void shouldReturnTextualMonthsAsIntValues() {
            assertThat(MONTHS.toIntValues("JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC"))
                    .isEqualTo("1,2,3,4,5,6,7,8,9,10,11,12");
        }

        @Test
        void shouldReturnLowerCaseTextualMonthsAsIntValues() {
            assertThat(MONTHS.toIntValues("jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec"))
                    .isEqualTo("1,2,3,4,5,6,7,8,9,10,11,12");
        }

    }

    @Nested
    class DaysOfWeekTest {

        @Test
        void shouldReturnAllValues() {
            assertThat(DAYS_OF_WEEK.allValues()).containsExactly(allValuesIncluding(0, 6));
        }

        @Test
        void shouldDoNothingIfValuesAreValid() {
            assertThatCode(() -> DAYS_OF_WEEK.validate(0, 6)).doesNotThrowAnyException();
        }

        @Test
        void shouldThrowExceptionIfValueIsLessThanLowerBound() {
            assertThat(catchThrowable(() -> DAYS_OF_WEEK.validate(-1)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid days of week value -1, outside bounds 0 and 6");
        }

        @Test
        void shouldThrowExceptionIfValueIsGreaterThanUpperBound() {
            assertThat(catchThrowable(() -> DAYS_OF_WEEK.validate(7)))
                    .isInstanceOf(NotationOutOfBoundsException.class)
                    .hasMessage("invalid days of week value 7, outside bounds 0 and 6");
        }

        @Test
        void shouldReturnTextualDaysOfWeekAsIntValues() {
            assertThat(DAYS_OF_WEEK.toIntValues("MON,TUE,WED,THU,FRI,SAT,SUN"))
                    .isEqualTo("0,1,2,3,4,5,6");
        }

        @Test
        void shouldReturnLowerCaseTextualDaysOfWeekAsIntValues() {
            assertThat(DAYS_OF_WEEK.toIntValues("mon,tue,wed,thu,fri,sat,sun"))
                    .isEqualTo("0,1,2,3,4,5,6");
        }

    }

    private static Integer[] allValuesIncluding(int start, int end) {
        return IntStream.rangeClosed(start, end).boxed().toArray(Integer[]::new);
    }

}
