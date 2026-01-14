package com.example.taskmanager.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.taskmanager.exception.TaskValidationException;
import com.example.taskmanager.model.Task;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for TaskServiceImpl. */
public class TaskServiceImplTest {

  private TaskService taskService;

  @BeforeEach
  public void setUp() {
    taskService = new TaskServiceImpl();
  }

  /** Tests creating a task with valid title. */
  @Test
  public void testCreateTask_ValidTitle() {
    Task task = taskService.createTask("Test Task");

    assertNotNull(task);
    assertNotNull(task.getId());
    assertEquals("Test Task", task.getTitle());
    assertNotNull(task.getCreatedAt());
  }

  /** Tests creating a task with empty title throws exception. */
  @Test
  public void testCreateTask_EmptyTitle() {
    TaskValidationException exception =
        assertThrows(TaskValidationException.class, () -> taskService.createTask(""));

    assertEquals("Task title cannot be null or empty", exception.getMessage());
  }

  /** Tests creating a task with null title throws exception. */
  @Test
  public void testCreateTask_NullTitle() {
    TaskValidationException exception =
        assertThrows(TaskValidationException.class, () -> taskService.createTask(null));

    assertEquals("Task title cannot be null or empty", exception.getMessage());
  }

  /** Tests creating a task with whitespace-only title throws exception. */
  @Test
  public void testCreateTask_WhitespaceTitle() {
    TaskValidationException exception =
        assertThrows(TaskValidationException.class, () -> taskService.createTask("   "));

    assertEquals("Task title cannot be null or empty", exception.getMessage());
  }

  /** Tests retrieving all tasks. */
  @Test
  public void testGetAllTasks() {
    taskService.createTask("Task 1");
    taskService.createTask("Task 2");
    taskService.createTask("Task 3");

    List<Task> tasks = taskService.getAllTasks();

    assertNotNull(tasks);
    assertEquals(3, tasks.size());
  }

  /** Tests that task IDs are unique and auto-incremented. */
  @Test
  public void testCreateTask_UniqueIds() {
    Task task1 = taskService.createTask("Task 1");
    Task task2 = taskService.createTask("Task 2");

    assertNotEquals(task1.getId(), task2.getId());
    assertTrue(task2.getId() > task1.getId());
  }
}
