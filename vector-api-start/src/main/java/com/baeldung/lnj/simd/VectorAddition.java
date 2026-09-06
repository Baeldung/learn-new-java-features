package com.baeldung.lnj.simd;

class VectorAddition {

    static void addScalar(int[] a, int[] b, int[] c) {
        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] + b[i];
        }
    }
}
