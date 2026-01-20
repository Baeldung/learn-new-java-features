package com.baeldung.lnj;

import static java.lang.classfile.ClassFile.ACC_PUBLIC;
import static java.lang.classfile.ClassFile.ACC_STATIC;
import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.ConstantDescs.CD_void;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Gatherers;

import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;

class NewJavaFeaturesUnitTest {

    private static final ScopedValue<String> CURRENT_USER = ScopedValue.newInstance();
    private static final ThreadLocal THREAD_USER = new ThreadLocal<>();

    @Test
    void whenUsingGatherer_thenCreatesFixedWindows() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7);
        List<List<Integer>> windows = numbers.stream()
            .gather(Gatherers.windowFixed(3))
            .toList();
        assertEquals(3, windows.size());
        assertEquals(List.of(1, 2, 3), windows.get(0));
        assertEquals(List.of(4, 5, 6), windows.get(1));
        assertEquals(List.of(7), windows.get(2));
    }

    @Test
    void whenUsingThreadLocal_thenManualCleanupIsRequired() {
        THREAD_USER.set("baeldung-admin");
        try {
            assertEquals("baeldung-admin", THREAD_USER.get());
        } finally {
            THREAD_USER.remove();
        }
    }

    @Test
    void whenUsingScopedValue_thenValueIsBoundToScope() {
        String user = "baeldung-user";
        ScopedValue.where(CURRENT_USER, user)
            .run(() -> {
                assertTrue(CURRENT_USER.isBound());
                assertEquals("baeldung-user", CURRENT_USER.get());
            });
        assertFalse(CURRENT_USER.isBound());
    }

    @Test
    void whenUsingKdfApi_thenDerivesKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        byte[] inputSecret = "my-secret-password".getBytes();
        SecretKey inputKey = new SecretKeySpec(inputSecret, "HKDF-SHA256");
        HKDFParameterSpec.ExtractThenExpand spec =
            HKDFParameterSpec.ofExtract()
                .addIKM(inputKey)
                .addSalt("my-salt".getBytes())
                .thenExpand("my-app-context".getBytes(), 32);

        KDF kdf = KDF.getInstance("HKDF-SHA256");
        SecretKey derivedKey = kdf.deriveKey("AES", spec);

        assertEquals("AES", derivedKey.getAlgorithm());
        assertEquals(32, derivedKey.getEncoded().length);
    }

    @Test
    void whenUsingClassFileApi_thenParsesMethods() {
        byte[] bytes = ClassFile.of()
            .build(ClassDesc.of("HelloWorld"), classBuilder -> {
                classBuilder.withMethodBody("main", MethodTypeDesc.of(CD_void, CD_String.arrayType()), ACC_PUBLIC | ACC_STATIC, codeBuilder -> {
                    codeBuilder.getstatic(ClassDesc.of("java.lang.System"), "out", ClassDesc.of("java.io.PrintStream"))
                        .ldc("Hello from Class-File API!")
                        .invokevirtual(ClassDesc.of("java.io.PrintStream"), "println", MethodTypeDesc.of(CD_void, CD_String))
                        .return_();
                });
            });
        ClassModel model = ClassFile.of()
            .parse(bytes);
        boolean hasMain = model.methods()
            .stream()
            .anyMatch(method -> method.methodName()
                .equalsString("main"));
        assertTrue(hasMain);
    }

}