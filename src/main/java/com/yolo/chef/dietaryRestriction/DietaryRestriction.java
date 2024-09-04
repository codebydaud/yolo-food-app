package com.yolo.chef.dietaryRestriction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity(name = "dietary_restriction")
public class DietaryRestriction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    @Column(name = "idea_id")
    private Integer ideaId;


}
