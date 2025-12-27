package com.baeldung.lnj;

import java.time.LocalDate;
import java.util.Set;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.Task;

class NewJavaFeaturesUnitTest {

    private static Campaign aTestCampaign(Task... tasks) {
        Campaign campaign = new Campaign("code", "name", "desc");
        campaign.setTasks(Set.of(tasks));
        return campaign;
    }

    private static Task aTestTask(String taskCode) {
        return new Task(taskCode, "task name", "task desc", LocalDate.now());
    }
}