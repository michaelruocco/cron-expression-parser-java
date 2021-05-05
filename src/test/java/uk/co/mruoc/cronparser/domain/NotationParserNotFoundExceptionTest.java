package uk.co.mruoc.cronparser.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotationParserNotFoundExceptionTest {

    @Test
    void shouldReturnMessage() {
        String input = "my-input";

        Throwable error = new NotationParserNotFoundException(input);

        assertThat(error.getMessage()).isEqualTo("notation parser not found for value my-input");
    }

}
