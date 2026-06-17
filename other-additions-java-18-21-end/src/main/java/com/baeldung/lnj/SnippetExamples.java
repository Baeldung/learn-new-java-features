package com.baeldung.lnj;

/**
 * Demonstrates the {@code @snippet} tag introduced in Java 18 (JEP 413).
 *
 * <p>Internal snippet — code embedded directly in the Javadoc comment:
 *
 * {@snippet :
 *   int result = Math.max(3, 7); // result is 7
 * }
 *
 * <p>External snippet — code loaded from a separate source file by region:
 *
 * {@snippet file="ApiExample.java" region="usage"}
 */
public class SnippetExamples {
}
