package uk.co.mruoc.cronparser.domain;

public interface Writer {

    void writeOutput(String value);

    void writeError(String value);

}
