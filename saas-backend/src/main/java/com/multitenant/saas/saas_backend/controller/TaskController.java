package com.multitenant.saas.saas_backend.controller;

import com.multitenant.saas.saas_backend.dto.TaskRequest;
import com.multitenant.saas.saas_backend.entity.Task;
import com.multitenant.saas.saas_backend.service.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequest request) {

        log.info("API: CREATE TASK called");

        return ResponseEntity.ok(taskService.createTask(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @Valid @RequestBody TaskRequest request) {

        log.info("API: UPDATE TASK called for id={}", id);

        return ResponseEntity.ok(taskService.updateTask(id, request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        log.info("API: DELETE TASK called for id={}", id);
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
    @GetMapping("/paginated")
    public ResponseEntity<Page<Task>> getTasksPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        log.info("API: GET TASKS called with page={}, size={}", page, size);

        return ResponseEntity.ok(taskService.getTasksWithPagination(page, size));
    }
}