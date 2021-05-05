package uk.co.mruoc.cronparser.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CronResultFormatterTest {

    private final CronResultFormatter formatter = new CronResultFormatter();

    @Test
    void shouldFormatResult() {
        CronResult result = buildResult();

        String formatted = formatter.format(result);

        assertThat(formatted).isEqualTo("""
                minute        39 40
                hour          9 10
                day of month  20 21
                month         3 4
                day of week   1 2
                command       my-command""");
    }

    private static CronResult buildResult() {
        return CronResult.builder()
                .minutes(new int[]{40, 39})
                .hours(new int[]{10, 9})
                .daysOfMonth(new int[]{21, 20})
                .months(new int[]{4, 3})
                .daysOfWeek(new int[]{2, 1})
                .command("my-command")
                .build();
    }

}
