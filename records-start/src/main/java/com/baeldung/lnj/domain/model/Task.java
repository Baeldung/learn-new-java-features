package com.baeldung.lnj.domain.model;

import java.util.Objects;

public class Task {
    
    private String code;
    private String name;

    public Task(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(code, task.code) &&
           Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return "Task [code= " + code + "name=" + name + "]";
    }
}
