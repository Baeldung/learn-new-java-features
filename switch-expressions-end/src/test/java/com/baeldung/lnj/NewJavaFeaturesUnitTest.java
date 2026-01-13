package com.baeldung.lnj;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.lnj.domain.model.TaskStatus;

class NewJavaFeaturesUnitTest {
    
    @Test
    void whenStackingCaseLabels_thenOldSwitchGroupsStatuses() {
        TaskStatus status = TaskStatus.ON_HOLD;
        String type;
        
        switch (status) {
            case TO_DO:
            case IN_PROGRESS: type = "Active";  break;
            case ON_HOLD:
            case DONE:        type = "Inactive"; break;
            default:          type = "Unknown"; break;
        }

        assertEquals("Inactive", type);
    }
    
    @Test
    void whenSwitchingWithExpression_thenGroupsStatuses() {
        TaskStatus status = TaskStatus.ON_HOLD;

        String type = switch (status) {
            case TO_DO, IN_PROGRESS -> "Active";
            case ON_HOLD, DONE -> "Inactive";
            default -> "Unknown";
        };

        assertEquals("Inactive", type);
    }
    
    @Test
    void whenUsingArrowSyntaxInStatement_thenActionsAreIsolated() {
        List<String> audit = new ArrayList<>();
        TaskStatus status = TaskStatus.DONE;

        switch (status) {
            case TO_DO, IN_PROGRESS -> audit.add("Active workflow");
            case ON_HOLD -> audit.add("Paused workflow");
            case DONE -> audit.add("Completed workflow");
            default -> audit.add("Unknown workflow");
        }

        assertEquals(List.of("Completed workflow"), audit);
    }
    
    @Test
    void whenUsingBlockInSwitchExpression_thenYieldProvidesBranchValue() {
        List<String> audit = new ArrayList<>();
        TaskStatus status = TaskStatus.DONE;

        String label = switch (status) {
            case DONE -> {
                audit.add("Completed workflow");
                String result = "Task is complete";
                yield result;
            }
            default -> "Unknown status";
        };

        assertEquals("Task is complete", label);
        assertEquals(List.of("Completed workflow"), audit);
    }



}