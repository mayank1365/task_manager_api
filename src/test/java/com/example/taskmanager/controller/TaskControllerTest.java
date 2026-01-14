package com.example.taskmanager.controller;

import com.example.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for TaskController.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    /**
     * Tests creating a task with valid payload.
     */
    @Test
    public void testCreateTask_ValidPayload() throws Exception {
        String requestBody = "{\"title\":\"Sample Task\"}";

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Sample Task"))
                .andExpect(jsonPath("$.createdAt").exists());
    }

    /**
     * Tests creating a task with empty title.
     */
    @Test
    public void testCreateTask_EmptyTitle() throws Exception {
        String requestBody = "{\"title\":\"\"}";

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Task title cannot be null or empty"));
    }

    /**
     * Tests creating a task with null title.
     */
    @Test
    public void testCreateTask_NullTitle() throws Exception {
        String requestBody = "{}";

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Task title cannot be null or empty"));
    }

    /**
     * Tests retrieving all tasks.
     */
    @Test
    public void testGetAllTasks() throws Exception {
        // Create a task first
        taskService.createTask("Test Task");

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
