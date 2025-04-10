package com.krzywdek19.courses.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnrollmentStudent {
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotBlank
    private String email;
}
