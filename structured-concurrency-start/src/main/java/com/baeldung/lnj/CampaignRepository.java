package com.baeldung.lnj;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.Task;

public class CampaignRepository {

    private static final long LOOKUP_DELAY_MILLIS = 200;

    private static final Map<String, String> CAMPAIGN_NAMES = Map.of("SUMMER", "Summer Sale", "WINTER", "Winter Clearance");

    private static final Map<String, String> TASK_NAMES = Map.of("DESIGN", "Design the banner", "COPY", "Write the ad copy", "LAUNCH", "Launch the campaign");

    public Campaign findCampaign(String code) throws InterruptedException {
        simulateSlowLookup();
        return new Campaign(code, CAMPAIGN_NAMES.get(code), "Seasonal promotion");
    }

    public List<Task> findTasks(String code) throws InterruptedException {
        simulateSlowLookup();
        return List.of(buildTask("DESIGN"), buildTask("COPY"), buildTask("LAUNCH"));
    }

    private Task buildTask(String taskCode) {
        return new Task(taskCode, TASK_NAMES.get(taskCode), "Campaign task", LocalDate.of(2026, 6, 30));
    }

    private void simulateSlowLookup() throws InterruptedException {
        Thread.sleep(LOOKUP_DELAY_MILLIS);
    }

}
