package com.baeldung.lnj;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.CampaignOverview;
import com.baeldung.lnj.domain.model.Task;

public class CampaignOverviewService {

    private final CampaignRepository repository;

    public CampaignOverviewService(CampaignRepository repository) {
        this.repository = repository;
    }

    public CampaignOverview loadOverviewWithExecutor(String code) throws Exception {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Campaign> campaign = executor.submit(() -> repository.findCampaign(code));
            Future<List<Task>> tasks = executor.submit(() -> repository.findTasks(code));

            return new CampaignOverview(campaign.get(), tasks.get());
        }
    }

}
