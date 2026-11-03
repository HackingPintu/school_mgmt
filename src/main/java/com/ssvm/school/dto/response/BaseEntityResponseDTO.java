package com.ssvm.school.dto.response;

import com.ssvm.school.constants.Gender;
import lombok.*;
import lombok.experimental.SuperBuilder;
import net.datafaker.providers.base.BloodType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntityResponseDTO {

    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String aadhaarNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private BloodType bloodType;

    // Address fields
    private String street;
    private String city;
    private String state;
    private String pinCode;

    private boolean status;

}