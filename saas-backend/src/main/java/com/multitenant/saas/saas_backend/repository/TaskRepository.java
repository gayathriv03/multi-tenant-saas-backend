package com.multitenant.saas.saas_backend.repository;

import com.multitenant.saas.saas_backend.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


    Page<Task> findByTenant_Name(String tenantName, Pageable pageable);
}