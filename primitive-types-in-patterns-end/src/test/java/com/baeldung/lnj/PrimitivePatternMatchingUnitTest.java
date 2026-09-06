package com.baeldung.lnj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PrimitivePatternMatchingUnitTest {

    @Test
    void givenExactValue_whenMatchingByteInstanceof_thenBindsNarrowedValue() {
        int value = 42;

        assertTrue(value instanceof byte narrowed && narrowed == 42);
    }

    @Test
    void givenLossyValue_whenMatchingByteInstanceof_thenDoesNotMatch() {
        int value = 2_345_323;

        assertFalse(value instanceof byte narrowed);
    }

    @Test
    void givenBooleanValues_whenSwitching_thenCoversBothCases() {
        boolean enabled = true;
        boolean disabled = false;

        assertEquals("Enabled", switch (enabled) {
            case true -> "Enabled";
            case false -> "Disabled";
        });
        assertEquals("Disabled", switch (disabled) {
            case true -> "Enabled";
            case false -> "Disabled";
        });
    }

    @Test
    void givenFloatValues_whenSwitching_thenSelectsExactOrTotalPattern() {
        float wholeValue = 12.0f;
        float remainingValue = 12.5f;

        assertEquals("Whole number: 12", switch (wholeValue) {
            case int whole -> "Whole number: " + whole;
            case float remaining -> "Other float: " + remaining;
        });
        assertEquals("Other float: 12.5", switch (remainingValue) {
            case int whole -> "Whole number: " + whole;
            case float remaining -> "Other float: " + remaining;
        });
    }
}
