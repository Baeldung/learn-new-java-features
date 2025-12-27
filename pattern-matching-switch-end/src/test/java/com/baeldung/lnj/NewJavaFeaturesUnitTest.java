package com.baeldung.lnj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.EmailNotification;
import com.baeldung.lnj.domain.model.Notification;
import com.baeldung.lnj.domain.model.SmsNotification;
import com.baeldung.lnj.domain.model.Task;
import com.baeldung.lnj.domain.model.TaskRecord;

class NewJavaFeaturesUnitTest {

    private String getObjectDescriptionOld(Object obj) {
        if (obj instanceof Task) {
            Task task = (Task) obj;
            return "Task: " + task.getName();
        } else if (obj instanceof Campaign) {
            Campaign campaign = (Campaign) obj;
            return "Campaign: " + campaign.getName();
        } else if (obj instanceof String) {
            String s = (String) obj;
            if (s.length() > 5) {           // Additional guarded condition
                return "length higher then 5: " + s;
            }
            return s;
        } else {
            return "Unknown object";
        }
    }

    private String getObjectDescriptionNew(Object obj) {
        return switch (obj) {
            case Task task -> "Task: " + task.getName();
            case Campaign campaign -> "Campaign: " + campaign.getName();
            case String s -> "String: " + s;
            case null -> "Object is null";
            default -> "Unknown object";
        };
    }

    @Test
    void whenSwitchingOnRecord_thenCanDestructure() {
        Object taskRec = new TaskRecord("task-1", "My Record");
        String result = switch (taskRec) {
            case Task task -> "Task: " + task.getName();
            case Campaign campaign -> "Campaign: " + campaign.getName();
            case TaskRecord(var code, var name) -> "Record: " + name;
            case String s -> "String: " + s;
            case null -> "Object is null";
            default -> "Unknown object";
        };
        assertEquals("Record: My Record", result);
    }

    @Test
    void whenUsingGuardedPattern_thenMatchesCaseWithWhen() {
        Task longTask = new Task("t1", "A very long task name", "Desc", null);
        Task shortTask = new Task("t2", "Short", "Desc", null);
        String result = switch (shortTask) {
            case Task task when task.getName().length() > 10 -> "Long Task";
            case Task task -> "Regular Task";
        };
        assertEquals("Regular Task", result);
    }

    @Test
    void whenSwitchingOnNull_thenCaseNullHandlesIt() {
        Object obj = null;
        String result = switch (obj) {
            case String s -> "String";
            case null -> "It is null";
            default -> "Other";
        };
        assertEquals("It is null", result);
    }

    @Test
    void whenSwitchingOnSealedType_thenNoDefaultIsNeeded() {
        Notification notification = new EmailNotification("test@baeldung.com", "Test");
        String channelInfo = switch (notification) {
            case EmailNotification e -> "Email: " + e.recipient();
            case SmsNotification s -> "SMS: " + s.phoneNumber();
        };
        assertEquals("Email: test@baeldung.com", channelInfo);
    }

}