package com.baeldung.lnj.catalog;

import java.time.LocalDate;
import java.util.List;

import com.baeldung.lnj.domain.model.Task;

class CampaignPlaybook {

    private static final LocalDate ACTIVE_UNTIL = LocalDate.of(2050, 12, 31);

    private static final TaskCatalog STANDARD_CATALOG = TaskCatalogLoader.load();

    static LocalDate activeUntil() {
        return ACTIVE_UNTIL;
    }

    static List<Task> standardTasks() {
        return STANDARD_CATALOG.tasks();
    }
}
