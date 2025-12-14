package com.baeldung.lnj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.baeldung.lnj.domain.model.Task;

class NewJavaFeaturesUnitTest {

    private final static Task TASK_1 = new Task("task-1", "Task one", "This is the task one", null);
    private final static Task TASK_2 = new Task("task-2", "Task two", "This is the task two", null);
    private final static Task TASK_3 = new Task("task-3", "Task three", "This is the task three", null);
    private final static Task TASK_4 = new Task("task-4", "Task four", "This is the task four", null);

    @Test
    void whenUsingCollectionsUnmodifiableList_thenReflectChangesInTheOriginalList() {
        List<Task> original = new ArrayList<>();
        original.add(TASK_1);
        original.add(TASK_2);

        List<Task> unmodifiableList = Collections.unmodifiableList(original);
        assertEquals(original, unmodifiableList);

        original.add(TASK_3);
        // The unmodifiableList reflects changes made to the original list
        assertEquals(original, unmodifiableList);
    }

    @Test
    void whenListOf_thenCorrect() {
        List<Task> tasks = List.of(TASK_1, TASK_2, TASK_3);

        assertEquals(Arrays.asList(TASK_1, TASK_2, TASK_3), tasks);

        assertThrows(UnsupportedOperationException.class, () -> tasks.add(TASK_4));
        assertThrows(UnsupportedOperationException.class, () -> tasks.set(1, TASK_4));
        assertThrows(UnsupportedOperationException.class, () -> tasks.remove(TASK_1));
    }

    @Test
    void whenListOfWithNull_thenThrowsException() {
        assertThrows(NullPointerException.class, () -> List.of(TASK_1, TASK_2, null));
    }

    @Test
    void whenSetOfWithDuplicates_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Set.of(TASK_1, TASK_1));
    }

    @Test
    void whenMapOf_thenCorrect() {
        //@formatter:off
        Map<String, Task> taskMap = Map.of(
            TASK_1.getCode(), TASK_1,
            TASK_2.getCode(), TASK_2,
            TASK_3.getCode(), TASK_3
        );
        //@formatter:on
        assertEquals(3, taskMap.size());
    }

    @Test
    void whenCreatingMapWithOfEntries_thenIsCreated() {
        Map.Entry<String, Task> entry1 = Map.entry(TASK_1.getCode(), TASK_1);
        Map.Entry<String, Task> entry2 = Map.entry(TASK_2.getCode(), TASK_2);
        Map.Entry<String, Task> entry3 = Map.entry(TASK_3.getCode(), TASK_3);
        Map<String, Task> taskMap = Map.ofEntries(entry1, entry2, entry3);
        assertEquals(3, taskMap.size());
        assertEquals(TASK_1, taskMap.get("task-1"));
    }

    @Test
    void whenUsingListCopyOf_thenCreatesUnmodifiableCopy() {
        List<Task> original = new ArrayList<>();
        original.add(TASK_1);
        original.add(TASK_2);
        original.add(TASK_3);

        List<Task> unmodifiableCopy = List.copyOf(original);
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableCopy.add(TASK_4));

        original.add(TASK_4); // change the original List
        assertEquals(Arrays.asList(TASK_1, TASK_2, TASK_3), unmodifiableCopy);
    }

    @Test
    void whenCollectingToUnmodifiableList_thenListIsUnmodifiable() {
        List<Task> list = List.of(TASK_1, TASK_2, TASK_3);
        List<Task> unmodifiableList = list.stream()
            .collect(Collectors.toUnmodifiableList());

        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add(TASK_4));
    }
}