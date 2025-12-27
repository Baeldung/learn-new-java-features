package com.baeldung.lnj;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.baeldung.lnj.domain.model.Campaign;
import com.baeldung.lnj.domain.model.Task;

class NewJavaFeaturesUnitTest {

    @Test
    void whenUsingMapMulti_thenExpandsElements() {
        // given
        Campaign campaign1 = aTestCampaign(aTestTask("t1"), aTestTask("t2"));
        Campaign campaign2 = aTestCampaign(aTestTask("t3"), aTestTask("t4"));
        campaign2.setClosed(true);

//        Stream<Task> tasks = Stream.of(campaign1, campaign2)
//            .flatMap(campaign -> {
//                if (!campaign.isClosed()) {
//                    return campaign.getTasks()
//                        .stream();
//                }
//                return Stream.empty();
//            });

        // when
        Stream<Task> tasks = Stream.of(campaign1, campaign2)
            .mapMulti((campaign, downstream) -> {
                if (!campaign.isClosed()) {
                    campaign.getTasks()
                        .forEach(downstream::accept);
                }
            });

        // then
        assertEquals(2, tasks.count());
    }

    @Test
    void whenUsingStreamToList_thenCreatesUnmodifiableList() {
        // given
        List<Task> tasks = List.of(aTestTask("t1"), aTestTask("t2"), aTestTask("t3"));

        // when
        List<Task> unmodifiableList = tasks.stream()
            .toList();

        // then
        assertEquals(3, unmodifiableList.size());
        assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.remove(1));
    }

    @Test
    void whenUsingTeeingCollector_thenMergesTwoResults() {
        // given
        List<Task> tasks = List.of(aTestTask("t1"), aTestTask("t2"), aTestTask("t3"));

        // when
        String result = tasks.stream()
            .map(Task::getCode)
            .collect(Collectors.teeing(Collectors.counting(), Collectors.joining(", "), (count, codes) -> String.format("There are %s tasks to complete: %s",
                count, codes)));

        // then
        assertEquals("There are 3 tasks to complete: t1, t2, t3", result);
    }

    private static Campaign aTestCampaign(Task... tasks) {
        Campaign campaign = new Campaign("code", "name", "desc");
        campaign.setTasks(Set.of(tasks));
        return campaign;
    }

    private static Task aTestTask(String taskCode) {
        return new Task(taskCode, "task name", "task desc", LocalDate.now());
    }
}