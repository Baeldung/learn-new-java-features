package com.baeldung.lnj;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;

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

    public CampaignOverview loadOverview(String code) throws Exception {
        try (var scope = StructuredTaskScope.open()) {
            Subtask<Campaign> campaign = scope.fork(() -> repository.findCampaign(code));
            Subtask<List<Task>> tasks = scope.fork(() -> repository.findTasks(code));

            scope.join();

            return new CampaignOverview(campaign.get(), tasks.get());
        }
    }

    public List<Task> loadTasks(List<String> taskCodes) throws Exception {
        try (var scope = StructuredTaskScope.open(Joiner.<Task> allSuccessfulOrThrow())) {
            for (String taskCode : taskCodes) {
                scope.fork(() -> repository.findTask(taskCode));
            }

            return scope.join();
        }
    }

}
