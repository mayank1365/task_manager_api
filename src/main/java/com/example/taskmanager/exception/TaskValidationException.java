package com.example.taskmanager.exception;

/** Custom exception for task validation errors. */
public class TaskValidationException extends RuntimeException {

  /**
   * Constructor with error message.
   *
   * @param message the error message
   */
  public TaskValidationException(String message) {
    super(message);
  }

  /**
   * Constructor with error message and cause.
   *
   * @param message the error message
   * @param cause the cause of the exception
   */
  public TaskValidationException(String message, Throwable cause) {
    super(message, cause);
  }
}
