package com.baeldung.lnj.catalog;

import java.time.LocalDate;
import java.util.List;

import com.baeldung.lnj.domain.model.Task;

class CampaignPlaybook {

    private static final LocalDate ACTIVE_UNTIL = LocalDate.of(2050, 12, 31);

    private static final LazyConstant<TaskCatalog> STANDARD_CATALOG = LazyConstant.of(TaskCatalogLoader::load);

    private static final List<Task> STANDARD_TASKS = List.ofLazy(TaskCatalogLoader.STANDARD_TASK_COUNT, TaskCatalogLoader::loadTask);

    static LocalDate activeUntil() {
        return ACTIVE_UNTIL;
    }

    static List<Task> standardTasks() {
        return STANDARD_CATALOG.get().tasks();
    }

    static Task standardTask(int index) {
        return STANDARD_TASKS.get(index);
    }
}
