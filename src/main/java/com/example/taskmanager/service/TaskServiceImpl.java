package com.example.taskmanager.service;

import com.example.taskmanager.exception.TaskValidationException;
import com.example.taskmanager.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementation of TaskService with in-memory storage.
 * Uses thread-safe data structures for concurrent access.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final Map<Long, Task> taskStore = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Creates a new task with the given title.
     * Validates that the title is not null or empty.
     *
     * @param title the task title
     * @return the created task
     * @throws TaskValidationException if title is null or empty
     */
    @Override
    public Task createTask(String title) {
        // Validate title
        if (title == null || title.trim().isEmpty()) {
            throw new TaskValidationException("Task title cannot be null or empty");
        }

        // Create new task
        Long id = idGenerator.getAndIncrement();
        Task task = new Task(id, title.trim(), LocalDateTime.now());

        // Store task
        taskStore.put(id, task);

        return task;
    }

    /**
     * Retrieves all tasks.
     *
     * @return list of all tasks
     */
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskStore.values());
    }
}
