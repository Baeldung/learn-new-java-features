package com.baeldung.lnj.domain.model;

import java.util.List;

public record CampaignOverview(Campaign campaign, List<Task> tasks) {
}
