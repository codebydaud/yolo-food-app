package com.yolo.chef.geminiApi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
public class AIRequest {
    private String AITitle;
    private String AIDescription;
    private String AIIntrests;
    private String AIDietaryRestrictions;
}