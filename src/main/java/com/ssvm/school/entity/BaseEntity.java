package com.ssvm.school.entity;

import com.ssvm.school.constants.BloodGroup;
import com.ssvm.school.constants.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false, length = 36)
    private String id;

    @Column(nullable = false, name = "first_name", length = 20)
    private String firstName;

    @Column(name = "middle_name", length = 20)
    private String middleName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "aadhaar_number", nullable = false, length = 12, unique = true)
    private String aadhaarNumber;

    @Column(name = "dob", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_grp")
    private BloodGroup bloodGroup;

    @Embedded
    @Column(name = "address", nullable = false)
    private Address address;

    @Column(name = "status", nullable = false)
    private boolean status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}