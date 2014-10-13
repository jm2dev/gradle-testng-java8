package com.opt1mates.citations;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ResponseBuilderTest {
    @Test
    public void ris_application_for_ris_format() {
        String actual = getContentType("RIS");
        String expected = "application/x-research-info-systems";

        assertThat("Valid RIS content type", actual, is(expected));
    }

    @Test
    public void bibtex_application_for_bibtex() {
        String actual = getContentType("BIBTEX");
        String expected = "text/x-bibtex";

        assertThat("Valid BiBTeX content type", actual, is(expected));
    }

    @Test
    public void plain_text_for_anything_else() {
        String actual = getContentType("ascii");
        String expected = "application-octet-stream";

        assertThat("Plain text for the rest of the world", actual, is(expected));
    }

    @Test
    public void default_to_plain_text() {
        String actual = getContentType("WYSWYG");
        String expected = "application-octet-stream";

        assertThat("Default to ASCII", actual, is(expected));
    }

    private String getContentType(String format) {
        final Citation RIS = new Citation("RIS", "application/x-research-info-systems");
        final Citation BIBTEX = new Citation("BIBTEX", "text/x-bibtex");
        final Citation ASCII = new Citation("ASCII", "application-octet-stream");
        Stream<Citation> citations = Stream.of(RIS, BIBTEX, ASCII);

        Optional<Citation> optionalCitation = citations.filter(citation -> citation.getFormat().equalsIgnoreCase(format)).findFirst();
        return optionalCitation.orElse(ASCII).getContentType();
    }

    private String getOldContentType(String format) {
        Map<String, String> contentTypes = new HashMap<>();
        contentTypes.put("RIS", "application/x-research-info-systems");
        contentTypes.put("BIBTEX", "text/x-bibtex");

        String result;

        if (contentTypes.containsKey(format))
            result = contentTypes.get(format);
        else
            result = "application-octet-stream";

        return result;
    }

    private class Citation {
        private final String format;
        private final String contentType;

        private Citation(String format, String contentType) {
            this.format = format;
            this.contentType = contentType;
        }

        public String getFormat() {
            return format;
        }

        public String getContentType() {
            return contentType;
        }
    }
}
