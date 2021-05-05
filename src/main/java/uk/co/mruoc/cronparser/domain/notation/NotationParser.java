package uk.co.mruoc.cronparser.domain.notation;

import uk.co.mruoc.cronparser.domain.TimeUnit;

public interface NotationParser {

    boolean appliesTo(String value);

    int[] toValues(String input, TimeUnit unit);

}
