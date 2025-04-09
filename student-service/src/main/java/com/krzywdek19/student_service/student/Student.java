package com.krzywdek19.student_service.student;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@SequenceGenerator(name = "seqIdGen", initialValue = 20000, allocationSize = 1)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqIdGen")
    private Long id;
    private String name;
    private String lastname;
    @Column(unique = true)
    private String email;
    private StudentStatus status;

}
