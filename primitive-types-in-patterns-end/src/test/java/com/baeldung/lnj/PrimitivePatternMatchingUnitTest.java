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
        float actuallyInteger = 12.0f;
        float actuallyFloat = 12.5f;

        assertEquals("Whole number: 12", switch (actuallyInteger) {
            case int asInt -> "Whole number: " + asInt;
            case float asFloat -> "Other float: " + asFloat;
        });
        assertEquals("Other float: 12.5", switch (actuallyFloat) {
            case int asInt -> "Whole number: " + asInt;
            case float asFloat -> "Other float: " + asFloat;
        });
    }
}
