package com.baeldung.lnj;

import com.baeldung.lnj.domain.model.Task;
import com.baeldung.lnj.domain.model.Campaign;

// Imports for JUnit 5
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Comparator;
import java.util.List;

class NewJavaFeaturesUnitTest {

    // Test for record's automatic features (accessors, equals, hashCode, toString)
    @Test
    void givenATask_whenCreatingRecord_thenFieldsAreAccessible() {
         
        Task task1 = new Task("task-1", "Test Record");
        Task task2 = new Task("task-1", "Test Record"); // Same values
        Task task3 = new Task("task-2", "Another Record"); // Different values

        // Accessors (code(), name())
        assertEquals("task-1", task1.code(), "Code accessor should return 'task-1'");
        assertEquals("Test Record", task1.name(), "Name accessor should return 'Test Record'");

        // equals() and hashCode()
        assertEquals(task1, task2, "Tasks with same values should be equal");
        assertNotEquals(task1, task3, "Tasks with different values should not be equal");

        assertEquals(task1.hashCode(), task2.hashCode(), "Equal objects must have equal hash codes");
        assertNotEquals(task1.hashCode(), task3.hashCode(), "Unequal objects should ideally have different hash codes");

        // toString() check now includes the description
        assertTrue(task1.toString().contains("code=task-1"), "toString() should contain code value");
        assertTrue(task1.toString().contains("name=Test Record"), "toString() should contain name value");
    }

    // Test for Local Records (Records defined inside a method)
    @Test
    void givenATaskAndCampaign_whenUsingLocalRecord_thenCanBeDefinedInMethod() {

        // Local Record definition remains unchanged as it doesn't directly use the Task description field
        record CampaignAndTask(Campaign campaign, Task task) {
            int combinedNameLength() {
                // Assuming Campaign has a getName() method and Task has a name() method
                return campaign.getName().length() + task.name().length();
            }
        }

        Campaign campaign = new Campaign("c1", "Campaign 1", "Desc");
        
        // Task creations now include the description field
        Task task1 = new Task("t1", "Task 1");
        Task task2 = new Task("t2", "Task 2 is longer"); // This one has the longer name

        List<Task> tasks = List.of(task1, task2);

        // Use the local record to map and find the longest combined name
        CampaignAndTask longestPair = tasks.stream()
            .map(task -> new CampaignAndTask(campaign, task))
            .max(Comparator.comparing(CampaignAndTask::combinedNameLength))
            .orElseThrow();

        assertEquals("Task 2 is longer", longestPair.task().name(), "The task with the longest combined name length should be 'Task 2 is longer'");
    }
}
