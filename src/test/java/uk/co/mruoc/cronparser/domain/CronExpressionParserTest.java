package uk.co.mruoc.cronparser.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CronExpressionParserTest {

    private static final String[] EXPRESSION = {"*/15", "0", "1,15", "*", "1-5", "/usr/bin/find"};

    private final CronExpressionParser parser = new CronExpressionParser();

    @Test
    void shouldParseMinutesOfCronExpression() {
        CronResult result = parser.parse(EXPRESSION);

        assertThat(result.getMinutes()).containsExactly(0, 15, 30, 45);
    }

    @Test
    void shouldParseHoursOfCronExpression() {
        CronResult result = parser.parse(EXPRESSION);

        assertThat(result.getHours()).containsExactly(0);
    }

    @Test
    void shouldParseDaysOfMonthOfCronExpression() {
        CronResult result = parser.parse(EXPRESSION);

        assertThat(result.getDaysOfMonth()).containsExactly(1, 15);
    }

    @Test
    void shouldParseMonthsOfCronExpression() {
        CronResult result = parser.parse(EXPRESSION);

        assertThat(result.getMonths()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    }

    @Test
    void shouldParseDayOfWeekOfCronExpression() {
        CronResult result = parser.parse(EXPRESSION);

        assertThat(result.getDaysOfWeek()).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void shouldParseCommandOfCronExpression() {
        CronResult result = parser.parse(EXPRESSION);

        assertThat(result.getCommand()).isEqualTo("/usr/bin/find");
    }

}
