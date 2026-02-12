package com.baeldung.lnj.api;

import com.baeldung.lnj.domain.model.Task;

public class Main {
    public static void main(String[] args) {
        // using JPMS project functionality
        Task task = new Task("T10", "Code Review", "Reviewing a team member's code", null);
        System.out.println(task);
    }
}
