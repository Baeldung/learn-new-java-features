package com.baeldung.lnj.domain.model;

// "permits" is inferred for this interface
public sealed interface TaskResult {
    boolean hasError();
    
    public static final class Success implements TaskResult {
        @Override
        public boolean hasError() {
            return false;
        }
    }

    public static final class Failure implements TaskResult {
        @Override
        public boolean hasError() {
            return true;
        }
    }
}

