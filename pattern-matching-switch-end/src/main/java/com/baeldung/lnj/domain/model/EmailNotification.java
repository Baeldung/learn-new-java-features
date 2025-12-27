package com.baeldung.lnj.domain.model;

public record EmailNotification(String recipient, String subject) implements Notification { }