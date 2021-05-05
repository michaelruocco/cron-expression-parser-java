package uk.co.mruoc.cronparser.domain;

import lombok.Builder;

@Builder
public class App {

    @Builder.Default
    private final ArgumentsSanitizer sanitizer = new ArgumentsSanitizer();

    @Builder.Default
    private final CronExpressionParser parser = new CronExpressionParser();

    @Builder.Default
    private final CronResultFormatter formatter = new CronResultFormatter();

    @Builder.Default
    private final Writer writer = new SystemWriter();

    public void run(String[] args) {
        try {
            String[] sanitizedArgs = sanitizer.sanitize(args);
            CronResult result = parser.parse(sanitizedArgs);
            print(result);
        } catch (ParserException e) {
            printErrorMessage(e);
        }
    }

    private void print(CronResult result) {
        writer.writeOutput(formatter.format(result));
    }

    private void printErrorMessage(Throwable e) {
        writer.writeError(e.getMessage());
    }

}
