package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for task management operations. */
@RestController
@RequestMapping("/tasks")
public class TaskController {

  private final TaskService taskService;

  /**
   * Constructor with dependency injection.
   *
   * @param taskService the task service
   */
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  /**
   * Creates a new task.
   *
   * @param request the request body containing task title
   * @return the created task with 201 Created status
   */
  @PostMapping
  public ResponseEntity<Task> createTask(@RequestBody Map<String, String> request) {
    String title = request.get("title");
    Task task = taskService.createTask(title);
    return new ResponseEntity<>(task, HttpStatus.CREATED);
  }

  /**
   * Retrieves all tasks.
   *
   * @return list of all tasks with 200 OK status
   */
  @GetMapping
  public ResponseEntity<List<Task>> getAllTasks() {
    List<Task> tasks = taskService.getAllTasks();
    return new ResponseEntity<>(tasks, HttpStatus.OK);
  }
}
