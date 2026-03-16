package com.baeldung.lnj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class NewJavaFeaturesUnitTest {

    @Test
    void whenUsingStartVirtualThread_thenThreadStartsImmediately() throws InterruptedException {
        AtomicBoolean ran = new AtomicBoolean(false);
        Runnable runnable = () -> ran.set(true);

        Thread virtualThread = Thread.startVirtualThread(runnable);
        virtualThread.join();

        assertTrue(ran.get(), "Virtual thread should execute the runnable");
        assertTrue(virtualThread.isVirtual(), "Thread should be virtual");
    }

    @Test
    void whenCreatingVirtualThreadWithBuilder_thenThreadIsVirtual() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Running in " + Thread.currentThread()
                .getName());
        };
        Thread virtualThread = Thread.ofVirtual()
            .name("MyVirtualThread")
            .unstarted(runnable);

        virtualThread.start();
        virtualThread.join();

        assertEquals("MyVirtualThread", virtualThread.getName(), "Virtual thread should have the configured name");
        assertTrue(virtualThread.isVirtual(), "Thread should be virtual");
    }

    @Test
    @Timeout(10)
    void whenUsingVirtualThreadExecutor_thenFastTaskIsNotBlockedByManySlowTasks() throws Exception {
    
        int taskCount = 100_000;
        AtomicInteger counter = new AtomicInteger(0);

        Runnable slowTask = () -> {
            counter.incrementAndGet();
            try {
                Thread.sleep(2000); // simulates slow blocking I/O
            } catch (InterruptedException ignored) {}
        };

        // try (ExecutorService executor = Executors.newFixedThreadPool(200)) { // platform threads: fast task is queued, test times out
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            // submit many slow tasks
            for (int i = 0; i < taskCount; i++) {
                executor.submit(slowTask);
            }

            // submit one fast task
            var fastTask = executor.submit(() -> "fast");

            assertEquals("fast", fastTask.get());
        }

        assertEquals(taskCount, counter.get(), "All slow tasks should complete");
    }

}