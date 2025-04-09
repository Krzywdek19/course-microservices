package com.krzywdek19.courses.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseMember {
    @NotNull
    private LocalDateTime enrollmentDate;
    @NotBlank
    private String email;

    public CourseMember(String email) {
        enrollmentDate = LocalDateTime.now();
        this.email = email;
    }
}
