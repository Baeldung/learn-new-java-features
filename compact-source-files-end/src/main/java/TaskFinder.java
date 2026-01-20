List<String> tasks = List.of("Learn Java", "Write Lesson", "Go Running", "Learn Streams");

void main() {
    IO.println("Tasks that start with 'Learn':");
    var foundTasks = findTasksStartingWith("Learn");
    foundTasks.forEach(task -> IO.println(task));
}

List<String> findTasksStartingWith(String prefix) {
    return tasks.stream().filter(task -> task.startsWith(prefix)).toList();
} 