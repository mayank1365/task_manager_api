package com.example.taskmanager.model;

import java.time.LocalDateTime;

/**
 * Task model representing a task entity. Uses explicit getters and setters (no Lombok) for CI/CD
 * linting clarity.
 */
public class Task {

  private Long id;
  private String title;
  private LocalDateTime createdAt;

  /** Default constructor. */
  public Task() {}

  /**
   * Constructor with all fields.
   *
   * @param id the task ID
   * @param title the task title
   * @param createdAt the creation timestamp
   */
  public Task(Long id, String title, LocalDateTime createdAt) {
    this.id = id;
    this.title = title;
    this.createdAt = createdAt;
  }

  /**
   * Gets the task ID.
   *
   * @return the task ID
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets the task ID.
   *
   * @param id the task ID
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the task title.
   *
   * @return the task title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Sets the task title.
   *
   * @param title the task title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the creation timestamp.
   *
   * @return the creation timestamp
   */
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the creation timestamp.
   *
   * @param createdAt the creation timestamp
   */
  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "Task{" + "id=" + id + ", title='" + title + '\'' + ", createdAt=" + createdAt + '}';
  }
}
