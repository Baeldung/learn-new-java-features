package com.baeldung.lnj.simd;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.VectorSpecies;

class VectorAddition {

    static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;

    static void addScalar(int[] a, int[] b, int[] c) {
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }
    }

    static void addVectorLoopOnly(int[] a, int[] b, int[] c) {
        int upperBound = SPECIES.loopBound(a.length);
        for (int i = 0; i < upperBound; i += SPECIES.length()) {
            IntVector va = IntVector.fromArray(SPECIES, a, i);
            IntVector vb = IntVector.fromArray(SPECIES, b, i);
            va.add(vb).intoArray(c, i);
        }
    }

    static void addVectorized(int[] a, int[] b, int[] c) {
        int i = 0;
        int upperBound = SPECIES.loopBound(a.length);
        for (; i < upperBound; i += SPECIES.length()) {
            IntVector va = IntVector.fromArray(SPECIES, a, i);
            IntVector vb = IntVector.fromArray(SPECIES, b, i);
            va.add(vb).intoArray(c, i);
        }
        for (; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }
    }
}
