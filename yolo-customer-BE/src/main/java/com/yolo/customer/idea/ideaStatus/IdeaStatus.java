package com.yolo.customer.idea.ideaStatus;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "idea_status")
public class IdeaStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", length = 16, nullable = false, unique = true)
    private String code;

    @Column(name = "\"value\"", length = 16, nullable = false)
    private String value;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public IdeaStatus(IdeaStatus other) {
        if (other != null) {
            this.id = other.id;
            this.code = other.code;
            this.value = other.value;
            this.isActive = other.isActive;
            this.createdAt = other.createdAt;
            this.updatedAt = other.updatedAt;
        }
    }

    public IdeaStatus() {}
}
