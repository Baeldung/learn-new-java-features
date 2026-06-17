package com.baeldung.lnj;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NewJavaFeaturesUnitTest {

    @Test
    void whenUsingDateTimeFormatterBuilder_thenFormatsCorrectly() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 1, 20, 15, 30);

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
          .appendLocalized("yMMMMdjmm")
          .toFormatter(Locale.US);

        String formattedDate = dateTime.format(formatter);

        assertTrue(formattedDate.contains("January 20, 2025"), "formatted date should contain full date");
        assertTrue(formattedDate.contains("3:30") && formattedDate.contains("PM"), "formatted time should contain 3:30 PM");
    }
}
