package com.ssvm.school.util;

import com.ssvm.school.constants.BloodGroup;
import com.ssvm.school.constants.Gender;
import com.ssvm.school.entity.Address;
import com.ssvm.school.entity.Student;
import com.ssvm.school.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentDataSeederService {

    private final StudentRepository studentRepository;
    private final Faker faker = new Faker(new Locale("en-IND"));

    /**
     * Generates a fake student profile and loads a photo from a local PC file path.
     *
     * @return Saved Student entity
     */

    @Transactional
    public Student generateAndSaveFakeStudent() {

        // Pick random enums from your constants packages
        Gender randomGender = faker.options().option(Gender.class);
        BloodGroup randomBloodGroup = faker.options().option(BloodGroup.class);

        // Truncate fields to respect database column lengths (defined in BaseEntity)
        String firstName = truncate(faker.name().firstName(), 20);
        String middleName = truncate(faker.name().firstName(), 20);
        String lastName = truncate(faker.name().lastName(), 20);

        // Student-specific fields (Assuming lengths from your DTO)
        String fatherName = truncate(faker.name().fullName(), 50);
        String motherName = truncate(faker.name().fullName(), 50);

        String identityNumber = faker.number().digits(12); // Matches 12 digit Aadhaar
        String guardianNumber = faker.number().digits(10);

        // Safe age calculation (age 5 to 18)
        int age = ThreadLocalRandom.current().nextInt(5, 19);
        int dayOfYear = ThreadLocalRandom.current().nextInt(1, 365); // 365 is safer for non-leap years
        LocalDate dob = LocalDate.now().minusYears(age).withDayOfYear(dayOfYear);

        // Fake admission date (somewhere between 1 and 4 years ago)
        LocalDate admissionDate = LocalDate.now().minusYears(ThreadLocalRandom.current().nextInt(1, 5));
        LocalDateTime createdAt = LocalDateTime.now();

        Address address = Address.builder()
                .street(truncate(faker.address().streetAddress(), 100))
                .city(truncate(faker.address().city(), 50))
                .state(truncate(faker.address().state(), 50))
                .pinCode(faker.number().digits(6)) // Matches standard 6-digit Indian PIN codes
                .build();

        // Utilizing SuperBuilder to set fields across both BaseEntity and Student
        Student student = Student.builder()
                // BaseEntity inherited fields
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .aadhaarNumber(identityNumber)
                .dateOfBirth(dob)
                .gender(randomGender)
                .bloodGroup(randomBloodGroup) // Added to match BaseEntity
                .address(address)
                .status(true) // Set default active status as it's primitive 'boolean' and nullable=false

                // Student-specific fields
                .fatherName(fatherName)
                .motherName(motherName)
                .guardianNumber(guardianNumber)
                .admissionDate(admissionDate)
                .createdAt(createdAt)
                .build();

        return studentRepository.save(student);
    }


    private String truncate(String value, int maxLength) {
        if (value == null) return null;
        return value.length() > maxLength ? value.substring(0, maxLength) : value;
    }
}