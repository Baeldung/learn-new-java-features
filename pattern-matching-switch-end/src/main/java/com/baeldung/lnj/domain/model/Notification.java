package com.baeldung.lnj.domain.model;

public sealed interface Notification permits EmailNotification, SmsNotification { }