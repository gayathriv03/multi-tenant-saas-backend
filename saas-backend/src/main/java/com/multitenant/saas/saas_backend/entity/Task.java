package com.multitenant.saas.saas_backend.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tasks", indexes = {
        @Index(name = "idx_tenant_id", columnList = "tenant_id")
})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;
}