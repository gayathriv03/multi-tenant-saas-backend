package com.multitenant.saas.saas_backend.service.impl;

import com.multitenant.saas.saas_backend.config.TenantContext;
import com.multitenant.saas.saas_backend.dto.TaskRequest;
import com.multitenant.saas.saas_backend.entity.Task;
import com.multitenant.saas.saas_backend.entity.Tenant;
import com.multitenant.saas.saas_backend.repository.TaskRepository;
import com.multitenant.saas.saas_backend.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;


@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl {

    private final TaskRepository taskRepository;
    private final TenantRepository tenantRepository;

    public Task createTask(TaskRequest request) {


        String tenantName = TenantContext.getTenant();
        log.info("Tenant: {} | Creating task", tenantName);

        Tenant tenant = tenantRepository.findByName(tenantName)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .tenant(tenant)
                .build();

        return taskRepository.save(task);
    }

    public Page<Task> getTasksWithPagination(int page, int size) {


        String tenant = TenantContext.getTenant();
        log.info("Tenant: {} | Fetching tasks", tenant);

        Pageable pageable = PageRequest.of(page, size);

        return taskRepository.findByTenant_Name(tenant, pageable);
    }

    public Task updateTask(Long id, TaskRequest request) {


        String tenantName = TenantContext.getTenant();
        log.info("Tenant: {} | Updating task id={}", tenantName, id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Tenant tenant = tenantRepository.findByName(tenantName)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));


        if (!task.getTenant().getId().equals(tenant.getId())) {
            throw new RuntimeException("Access denied");
        }

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());

        return taskRepository.save(task);
    }
    public void deleteTask(Long id) {


        String tenantName = TenantContext.getTenant();
        log.info("Tenant: {} | Deleting task id={}", tenantName, id);

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Tenant tenant = tenantRepository.findByName(tenantName)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        //  Tenant validation
        if (!task.getTenant().getId().equals(tenant.getId())) {
            throw new RuntimeException("Access denied");
        }

        taskRepository.delete(task);
    }
}