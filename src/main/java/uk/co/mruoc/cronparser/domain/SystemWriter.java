package uk.co.mruoc.cronparser.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SystemWriter implements Writer {

    @Override
    public void writeOutput(String value) {
        System.out.println(value);
    }

    @Override
    public void writeError(String value) {
        System.err.println(value);
    }

}
