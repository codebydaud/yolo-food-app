package com.yolo.customer.idea.dietaryRestriction;

import com.yolo.customer.idea.Idea;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class DietaryRestriction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "description", length = 32, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idea_id", nullable = false)
    private Idea idea;

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

    // Defensive copy for mutable internal state
    public Idea getIdea() {
        return idea != null ? new Idea(idea) : null;
    }

    public void setIdea(Idea idea) {
        this.idea = idea != null ? new Idea(idea) : null;
    }

    // Copy constructor
    public DietaryRestriction(DietaryRestriction other) {
        if (other != null) {
            this.id = other.id;
            this.description = other.description;
            this.idea = other.idea != null ? new Idea(other.idea) : null;
            this.createdAt = other.createdAt;
            this.updatedAt = other.updatedAt;
        }
    }

    // Default constructor
    public DietaryRestriction() {}
}
