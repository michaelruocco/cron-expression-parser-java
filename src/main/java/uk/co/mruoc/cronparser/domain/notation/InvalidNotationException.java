package uk.co.mruoc.cronparser.domain.notation;

import uk.co.mruoc.cronparser.domain.ParserException;

public class InvalidNotationException extends ParserException {

    public InvalidNotationException(String value, Throwable cause) {
        super(value, cause);
    }

}
