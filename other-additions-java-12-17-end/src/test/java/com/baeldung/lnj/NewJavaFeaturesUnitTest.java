package com.baeldung.lnj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.Task;

class NewJavaFeaturesUnitTest {

    @Test
    void whenUsingJava12StringIndent_thenBehaviorIsCorrect() {
        String indented = "Hello".indent(4);
        assertEquals("    Hello\n", indented);

        String multiLineStrIndented = """
            Hello
            Baeldung""".indent(4);
        assertEquals("    Hello\n    Baeldung\n", multiLineStrIndented);

        String negIntended = "    Hello".indent(-4);
        assertEquals("Hello\n", negIntended);
    }

    @Test
    void whenUsingJava12StringTransform_thenBehaviorIsCorrect() {
        String transformed = "Baeldung".transform(s -> s + " Task")
            .transform(String::toUpperCase);
        assertEquals("BAELDUNG TASK", transformed);

        int parsed = " 80 8  0".transform(s -> s.replace(" ", ""))
            .transform(Integer::parseInt);
        assertEquals(8080, parsed);
    }

    @Test
    void whenUsingFileMismatch_thenFindsFirstDifference(@TempDir Path tempDir) throws IOException {
        Path file1 = tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        Path file3 = tempDir.resolve("file3.txt");

        Files.writeString(file1, "Java 12");
        Files.writeString(file2, "Java 12");
        Files.writeString(file3, "Java 11");

        assertEquals(-1, Files.mismatch(file1, file2));
        assertEquals(6, Files.mismatch(file1, file3));
    }

    @Test
    void whenUsingCompactNumberFormat_thenFormatsCorrectly() {
        NumberFormat shortFormat = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        shortFormat.setMaximumFractionDigits(1);
        assertEquals("1K", shortFormat.format(1000));
        assertEquals("1.5M", shortFormat.format(1500000));

        NumberFormat longFormat = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.LONG);
        longFormat.setMaximumFractionDigits(0);
        assertEquals("1 million", longFormat.format(1000000));

        NumberFormat germanFormat = NumberFormat.getCompactNumberInstance(Locale.GERMAN, NumberFormat.Style.LONG);
        assertEquals("2 Millionen", germanFormat.format(2000000));
        assertEquals("1 Million", germanFormat.format(1000000));

    }

    @Test
    void whenNpeIsThrown_thenMessageIsHelpful() {
        Campaign campaign = null;
        Task task = new Task("t1", "Task 1", "Desc", null);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            campaign.getTasks().add(task);
        });

        assertEquals(
            "Cannot invoke \"com.baeldung.lnj.domain.model.Campaign.getTasks()\" because \"campaign\" is null",
            exception.getMessage()
        );
    }

    @Test
    void whenUsingRandomGenerator_thenGeneratesNumbers() {
        RandomGenerator random = RandomGenerator.getDefault();

        // Generate a stream of 5 random integers
        long count = random.ints(5).count();
        assertEquals(5, count);

        // Generate a single integer between 10 (inclusive) and 20 (exclusive)
        int value = random.nextInt(10, 20);
        assertTrue(value >= 10 && value < 20);
    }
}