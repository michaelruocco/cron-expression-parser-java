package uk.co.mruoc.cronparser.domain.notation;

import uk.co.mruoc.cronparser.domain.TimeUnit;

public interface NotationParser {

    boolean appliesTo(String value);

    int[] toValues(String input, TimeUnit unit);

    default int toFirstValue(String input, TimeUnit unit) {
        return toValues(input, unit)[0];
    }

}
