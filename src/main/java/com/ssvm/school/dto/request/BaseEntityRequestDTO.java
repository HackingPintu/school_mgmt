package com.ssvm.school.dto.request;

import com.ssvm.school.constants.BloodGroup;
import com.ssvm.school.constants.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntityRequestDTO {

    @NotBlank(message = "First name is required")
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z ]")
    private String firstName;

    @NotBlank(message = "Middle name is required")
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z ]")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 20)
    @Pattern(regexp = "^[a-zA-Z ]")
    private String lastName;

    @NotBlank(message = "ID number is required")
    @Pattern(regexp = "^[0-9]$")
    @Size(min = 12, max = 12)
    private String aadhaarNumber;

    @NotNull(message = "Date of birth is required. Enter in 'DD-MM-YYYY format'")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Blood Group is needed")
    private BloodGroup bloodGroup;

    // Embedded address properties
    private String street;
    private String city;
    private String state;
    private String pinCode;

    private boolean status;

}