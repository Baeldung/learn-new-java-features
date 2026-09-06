package com.baeldung.lnj.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.lnj.domain.model.Task;

class LazyConstantsUnitTest {

    @Test
    void whenCampaignManagerIsRead_thenCatalogIsBuiltOnceOnFirstAccess() {
        CampaignManager.activeUntil();
        assertEquals(0, TaskCatalogLoader.catalogLoadCount());

        CampaignManager.standardTasks();
        assertEquals(1, TaskCatalogLoader.catalogLoadCount());

        CampaignManager.standardTasks();
        assertEquals(1, TaskCatalogLoader.catalogLoadCount());
    }

    @Test
    void whenOneStandardTaskIsRead_thenOnlyThatTaskIsBuilt() {
        assertEquals(0, TaskCatalogLoader.taskLoadCount());

        Task task = CampaignManager.standardTask(0);

        assertEquals("Design the banner", task.getName());
        assertEquals(1, TaskCatalogLoader.taskLoadCount());
    }

}
