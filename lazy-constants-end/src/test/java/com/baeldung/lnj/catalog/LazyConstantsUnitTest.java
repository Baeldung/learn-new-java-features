package com.baeldung.lnj.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import com.baeldung.lnj.domain.model.Task;

class LazyConstantsUnitTest {

    @Test
    void whenPlaybookIsRead_thenCatalogIsBuiltOnceOnFirstAccess() {
        CampaignPlaybook.activeUntil();
        assertEquals(0, TaskCatalogLoader.catalogLoadCount());

        CampaignPlaybook.standardTasks();
        assertEquals(1, TaskCatalogLoader.catalogLoadCount());

        CampaignPlaybook.standardTasks();
        assertEquals(1, TaskCatalogLoader.catalogLoadCount());
    }

    @Test
    void whenOneStandardTaskIsRead_thenOnlyThatTaskIsBuilt() {
        assertEquals(0, TaskCatalogLoader.taskLoadCount());

        Task task = CampaignPlaybook.standardTask(0);

        assertEquals("Design the banner", task.getName());
        assertEquals(1, TaskCatalogLoader.taskLoadCount());
    }

    @Test
    void whenComputingFunctionReturnsNull_thenGetThrows() {
        LazyConstant<TaskCatalog> catalog = LazyConstant.of(() -> null);

        assertThrows(NullPointerException.class, catalog::get);
    }

    @Test
    void whenComputingFunctionThrows_thenLaterGetRetries() {
        AtomicInteger attempts = new AtomicInteger();
        LazyConstant<String> label = LazyConstant.of(() -> {
            if (attempts.incrementAndGet() == 1) {
                throw new IllegalStateException("catalog source unavailable");
            }
            return "standard";
        });

        assertThrows(IllegalStateException.class, label::get);

        assertEquals("standard", label.get());
        assertEquals(2, attempts.get());
    }
}
