package uk.co.mruoc.cronparser.domain;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.cronparser.domain.notation.ComplexNotationParser;
import uk.co.mruoc.cronparser.domain.notation.NotationParser;

@RequiredArgsConstructor
public class CronExpressionParser {

    private final NotationParser notationParser;

    public CronExpressionParser() {
        this(new ComplexNotationParser());
    }

    public CronResult parse(String[] values) {
        return CronResult.builder()
                .minutes(parse(values, TimeUnit.MINUTES))
                .hours(parse(values, TimeUnit.HOURS))
                .daysOfMonth(parse(values, TimeUnit.DAYS_OF_MONTH))
                .months(parse(values, TimeUnit.MONTHS))
                .daysOfWeek(parse(values, TimeUnit.DAYS_OF_WEEK))
                .command(values[5])
                .build();
    }

    private int[] parse(String[] values, TimeUnit timeUnit) {
        String rawValues = values[timeUnit.ordinal()];
        String intValues = timeUnit.toIntValues(rawValues);
        if (notationParser.appliesTo(intValues)) {
            return notationParser.toValues(intValues, timeUnit);
        }
        throw new NotationParserNotFoundException(rawValues);
    }

}
