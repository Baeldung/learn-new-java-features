package com.baeldung.lnj.simd;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;

class VectorApiUnitTest {

    @Test
    void whenReadingThePreferredSpecies_thenItReportsThisMachinesLaneCount() {
        VectorSpecies<Integer> species = IntVector.SPECIES_PREFERRED;

        System.out.println("species: " + species);
        System.out.println("lanes: " + species.length());

        assertTrue(species.length() > 0);
    }

    @Test
    void whenAddingWithTheVectorLoopOnly_thenTheTrailingElementsAreUntouched() {
        int[] a = new int[99];
        int[] b = new int[99];
        Arrays.fill(a, 1);
        Arrays.fill(b, 2);
        int[] c = new int[99];

        VectorAddition.addVectorLoopOnly(a, b, c);

        int bound = VectorAddition.SPECIES.loopBound(99);
        System.out.println("full vectors covered " + bound + " of 99 elements");
        System.out.println("c[" + bound + ".." + (99 - 1) + "] = " + Arrays.toString(Arrays.copyOfRange(c, bound, 99)));

        assertTrue(bound > 0);
        assertTrue(bound < 99);
        for (int i = bound; i < 99; i++) {
            assertEquals(0, c[i]);
        }
    }

    @Test
    void whenAddingWithTheVectorizedMethod_thenItMatchesTheScalarLoop() {
        int[] a = IntStream.range(0, 99).toArray();
        int[] b = IntStream.range(0, 99).map(i -> i * 2).toArray();
        int[] expected = new int[99];
        int[] actual = new int[99];

        VectorAddition.addScalar(a, b, expected);
        VectorAddition.addVectorized(a, b, actual);

        int bound = VectorAddition.SPECIES.loopBound(99);
        assertTrue(bound > 0);
        assertTrue(bound < 99);
        assertArrayEquals(expected, actual);
    }
}
