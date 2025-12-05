package com.baeldung.lnj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class NewJavaFeaturesUnitTest {

    @Test
    void whenUsingTextBlock_thenJsonIsReadableAndCorrect() {
        String textBlockJson = """ 
            {
                "name": "Learn Java Task",
                "description": "Let's learn Java new features"
            }
            """;

        Stream<String> lines = textBlockJson.lines();
        Stream<String> expectedLines = Stream.of(
            // @formatter:off
            "{",
            "    \"name\": \"Learn Java Task\",",
            "    \"description\": \"Let's learn Java new features\"",
            "}"
        );
        // @formatter:on

        assertLinesMatch(expectedLines, lines);
    }

    @Test
    void whenTextBlockIsIndented_thenIncidentalWhitespaceIsRemoved() {
        String indentedBlock = """
            {
                "name": "Learn Java Task"
            }
            """;
        Stream<String> lines = indentedBlock.lines();
        Stream<String> expectedLines = Stream.of(
            // @formatter:off
            "{",
            "    \"name\": \"Learn Java Task\"",
            "}"
        );
        // @formatter:on

        assertLinesMatch(expectedLines, lines);
    }

    @Test
    void whenClosingQuotesAreShifted_thenIndentationIsAdded() {
        // @formatter:off
        String shiftedBlock = """
            {
                "name": "Learn Java Task"
            }
          """;
        // @formatter:on
        Stream<String> lines = shiftedBlock.lines();
        Stream<String> expectedLines = Stream.of(
            // @formatter:off
            "  {",
            "      \"name\": \"Learn Java Task\"",
            "  }"
        );
        // @formatter:on
        assertLinesMatch(expectedLines, lines);
    }

    @Test
    void whenCallIndentMethod_thenIndentationIsCorrect() {
        String indentedBlock = """
            {
                "name": "Learn Java Task"
            }
            """.indent(1);
        Stream<String> lines = indentedBlock.lines();
        Stream<String> expectedLines = Stream.of(
            // @formatter:off
            " {",
            "     \"name\": \"Learn Java Task\"",
            " }"
        );
        // @formatter:on
        assertLinesMatch(expectedLines, lines);
    }

    @Test
    void whenUsingSpaceAndLineTerminatorEscapes_thenCorrect() {
        String longSql = """
            SELECT * FROM Task \
            WHERE status = 'DONE' \
            ORDER BY due_date DESC\s\s
            """;
        Stream<String> lines = longSql.lines();
        Stream<String> expectedLines = Stream.of(
            // @formatter:off
            "SELECT * FROM Task WHERE status = 'DONE' ORDER BY due_date DESC  "
        );
        // @formatter:on
        assertLinesMatch(expectedLines, lines);
    }

    @Test
    void whenUsingStripIndent_thenCorrect() {
        String indentedString = "     {\n         \"name\": \"Hello Baeldung\"\n     }";
        String stripped = indentedString.stripIndent();

        Stream<String> linesAfterStrippedIndent = stripped.lines();
        Stream<String> expectedLines = Stream.of(
            // @formatter:off
            "{",
            "    \"name\": \"Hello Baeldung\"",
            "}"
        );
        // @formatter:on
        assertLinesMatch(expectedLines, linesAfterStrippedIndent);
    }

    @Test
    void whenUsingTranslateEscapes_thenCorrect() {
        String withEscapes = "Hello\\nWorld!\\tThis is a test.";

        //before translateEscapes: single line
        assertEquals(1, withEscapes.lines().count());

        String translated = withEscapes.translateEscapes();

        //after translateEscapes: multi-line
        assertEquals("Hello\nWorld!\tThis is a test.", translated);
        Stream<String> expectedLines = Stream.of(
            // @formatter:off
            "Hello",
            "World!	This is a test."
        );
        // @formatter:on
        assertLinesMatch(expectedLines, translated.lines());
    }

    @Test
    void whenUsingFormatted_thenCorrect() {
        String json = """
            {
                "name": "%s",
                "status": "%s"
            }
            """.formatted("Learn Java Task", "DONE");
        Stream<String> lines = json.lines();
        Stream<String> expectedLines = Stream.of(
            // @formatter:off
            "{",
            "    \"name\": \"Learn Java Task\",",
            "    \"status\": \"DONE\"",
            "}"
        );
        // @formatter:on
        assertLinesMatch(expectedLines, lines);
    }
}