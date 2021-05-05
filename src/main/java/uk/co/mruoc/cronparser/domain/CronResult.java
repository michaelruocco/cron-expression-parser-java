package uk.co.mruoc.cronparser.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CronResult {

    private final int[] minutes;
    private final int[] hours;
    private final int[] daysOfMonth;
    private final int[] months;
    private final int[] daysOfWeek;
    private final String command;

}
