package com.ssvm.school.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "academic_years")
@ToString
public class AcademicYear {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @Column(name = "year_label", nullable = false, unique = true, length = 10)
    private String yearLabel; // 2024-25, 2025-26

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}