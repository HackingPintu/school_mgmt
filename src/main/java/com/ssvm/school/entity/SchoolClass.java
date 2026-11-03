package com.ssvm.school.entity;

import com.ssvm.school.constants.Standards;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "classes")
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "class_name", nullable = false, unique = true)
    private Standards className;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_teacher_id", unique = true)
    private Teacher classTeacher;

}