package com.baeldung.lnj.domain.model;

public record ImmutableTask(String title, int priority) {

    public ImmutableTask(String title) {
        int defaultPriority = title.contains("Urgent") ? 1 : 5;
        this(title, defaultPriority);
    }
}