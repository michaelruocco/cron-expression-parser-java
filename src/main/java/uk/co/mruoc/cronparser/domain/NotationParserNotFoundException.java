package uk.co.mruoc.cronparser.domain;

public class NotationParserNotFoundException extends ParserException {

    public NotationParserNotFoundException(String value) {
        super(String.format("notation parser not found for value %s", value));
    }

}
