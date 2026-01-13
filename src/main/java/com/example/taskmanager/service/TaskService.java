package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;

import java.util.List;

/**
 * Service interface for task management operations.
 */
public interface TaskService {

    /**
     * Creates a new task with the given title.
     *
     * @param title the task title
     * @return the created task
     */
    Task createTask(String title);

    /**
     * Retrieves all tasks.
     *
     * @return list of all tasks
     */
    List<Task> getAllTasks();
}
