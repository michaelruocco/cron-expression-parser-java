package uk.co.mruoc.cronparser.domain;


public class NotationOutOfBoundsException extends ParserException {

    public NotationOutOfBoundsException(int value, TimeUnit unit) {
        this(Integer.toString(value), unit);
    }

    public NotationOutOfBoundsException(String value, TimeUnit unit) {
        super(String.format("invalid %s value %s, outside bounds %d and %d",
                unit.formattedName(),
                value,
                unit.getLowerBound(),
                unit.getUpperBound()));
    }

}
