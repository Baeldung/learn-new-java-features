package com.baeldung.lnj.domain.model;

public record SmsNotification(String phoneNumber) implements Notification { }