package com.baeldung.lnj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.jupiter.api.Test;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.CampaignOverview;
import com.baeldung.lnj.domain.model.Task;

class StructuredConcurrencyUnitTest {

    @Test
    void whenExecutorSubtaskFails_thenSiblingIsNotCancelled() throws Exception {
        CountDownLatch started = new CountDownLatch(1);
        CountDownLatch release = new CountDownLatch(1);
        AtomicBoolean siblingFinished = new AtomicBoolean();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                started.countDown();
                release.await();
                siblingFinished.set(true);
                return new Campaign("SUMMER", "Summer Sale", "Seasonal promotion");
            });
            Future<List<Task>> tasks = executor.submit(() -> {
                started.await();
                throw new IllegalStateException("tasks lookup failed");
            });

            assertThrows(ExecutionException.class, tasks::get);
            assertFalse(siblingFinished.get());
            release.countDown();
        }

        assertTrue(siblingFinished.get());
    }

    @Test
    void whenLoadingOverview_thenCampaignAndTasksAreReturned() throws Exception {
        CampaignOverviewService service = new CampaignOverviewService(new CampaignRepository());

        CampaignOverview overview = service.loadOverview("SUMMER");

        assertEquals("Summer Sale", overview.campaign().getName());
        assertEquals(List.of("DESIGN", "COPY", "LAUNCH"), overview.tasks().stream().map(Task::getCode).toList());
    }

    @Test
    void whenScopedSubtaskFails_thenSiblingIsCancelled() throws Exception {
        CountDownLatch started = new CountDownLatch(1);
        CountDownLatch release = new CountDownLatch(1);
        AtomicBoolean siblingInterrupted = new AtomicBoolean();

        try (var scope = StructuredTaskScope.open()) {
            scope.fork(() -> {
                started.countDown();
                try {
                    release.await();
                } catch (InterruptedException e) {
                    siblingInterrupted.set(true);
                    throw e;
                }
                return new Campaign("SUMMER", "Summer Sale", "Seasonal promotion");
            });
            scope.fork(() -> {
                started.await();
                throw new IllegalStateException("tasks lookup failed");
            });

            assertThrows(StructuredTaskScope.FailedException.class, scope::join);
        }

        assertTrue(siblingInterrupted.get());
    }

    @Test
    void whenLoadingTasksByCode_thenAllTasksAreReturned() throws Exception {
        CampaignOverviewService service = new CampaignOverviewService(new CampaignRepository());

        List<Task> tasks = service.loadTasks(List.of("DESIGN", "COPY", "LAUNCH"));

        assertEquals(List.of("Design the banner", "Write the ad copy", "Launch the campaign"), tasks.stream().map(Task::getName).toList());
    }

}
