package com.baeldung.lnj.catalog;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.baeldung.lnj.domain.model.Task;

class TaskCatalogLoader {

    private static final List<String> TASK_CODES = List.of("DESIGN", "COPY", "LAUNCH");

    private static final List<String> TASK_NAMES = List.of("Design the banner", "Write the ad copy", "Launch the campaign");

    static final int STANDARD_TASK_COUNT = TASK_CODES.size();

    private static final long SOURCE_DELAY_MILLIS = 200;

    private static final AtomicInteger CATALOG_LOADS = new AtomicInteger();

    private static final AtomicInteger TASK_LOADS = new AtomicInteger();

    static TaskCatalog load() {
        CATALOG_LOADS.incrementAndGet();
        readFromSlowSource();
        return new TaskCatalog(IntStream.range(0, STANDARD_TASK_COUNT).mapToObj(TaskCatalogLoader::buildTask).toList());
    }

    static Task loadTask(int index) {
        TASK_LOADS.incrementAndGet();
        readFromSlowSource();
        return buildTask(index);
    }

    static int catalogLoadCount() {
        return CATALOG_LOADS.get();
    }

    static int taskLoadCount() {
        return TASK_LOADS.get();
    }

    private static Task buildTask(int index) {
        return new Task(TASK_CODES.get(index), TASK_NAMES.get(index), "Campaign task", LocalDate.of(2026, 6, 30));
    }

    private static void readFromSlowSource() {
        try {
            Thread.sleep(SOURCE_DELAY_MILLIS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
