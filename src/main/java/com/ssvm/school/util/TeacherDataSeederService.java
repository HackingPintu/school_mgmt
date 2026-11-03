package com.ssvm.school.util;

import com.ssvm.school.constants.BloodGroup;
import com.ssvm.school.constants.Gender;
import com.ssvm.school.constants.TeacherQualification;
import com.ssvm.school.constants.TeacherType;
import com.ssvm.school.constants.TeachingLevel;
import com.ssvm.school.entity.Address;
import com.ssvm.school.entity.Teacher;
import com.ssvm.school.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherDataSeederService {

    private final TeacherRepository teacherRepository;
    private final Faker faker = new Faker(new Locale("en-IND"));

    /**
     * Generates a fake teacher profile and saves it to the database.
     *
     * @return Saved Teacher entity
     */
    @Transactional
    public Teacher generateAndSaveFakeTeacher() {

        // 1. Pick random enums from your constants packages
        Gender randomGender = faker.options().option(Gender.class);
        BloodGroup randomBloodGroup = faker.options().option(BloodGroup.class);
        TeacherType randomTeacherType = faker.options().option(TeacherType.class);
        TeachingLevel randomTeachingLevel = faker.options().option(TeachingLevel.class);
        TeacherQualification randomQualification = faker.options().option(TeacherQualification.class);

        // 2. Truncate fields to respect database column lengths (defined in BaseEntity)
        String firstName = truncate(faker.name().firstName(), 20);
        String middleName = truncate(faker.name().firstName(), 20);
        String lastName = truncate(faker.name().lastName(), 20);
        String identityNumber = faker.number().digits(12); // Matches 12-digit Aadhaar

        // 3. Teacher-specific fields
        String phoneNumber = faker.number().digits(10); // Matches your length = 10 constraint
        String email = truncate(faker.internet().emailAddress(firstName.toLowerCase() + "." + lastName.toLowerCase()), 255);

        // Random salary between 25,000 and 85,000 INR
        BigDecimal salary = BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(25000, 85001));

        // 4. Safe Date calculations
        // Teachers age ranges between 23 and 60 years old
        int age = ThreadLocalRandom.current().nextInt(23, 61);
        int dayOfYear = ThreadLocalRandom.current().nextInt(1, 365);
        LocalDate dob = LocalDate.now().minusYears(age).withDayOfYear(dayOfYear);

        // Date of joining somewhere between 1 and 8 years ago
        LocalDate dateOfJoining = LocalDate.now().minusYears(ThreadLocalRandom.current().nextInt(1, 9));
        LocalDateTime createdAt = LocalDateTime.now();

        // 5. Embedded Address object
        Address address = Address.builder()
                .street(truncate(faker.address().streetAddress(), 100))
                .city(truncate(faker.address().city(), 50))
                .state(truncate(faker.address().state(), 50))
                .pinCode(faker.number().digits(6)) // Standard 6-digit Indian PIN code
                .build();

        // 6. Build Entity utilizing SuperBuilder across BaseEntity and Teacher
        Teacher teacher = Teacher.builder()
                // BaseEntity inherited fields
                .firstName(firstName)
                .middleName(middleName)
                .lastName(lastName)
                .aadhaarNumber(identityNumber)
                .dateOfBirth(dob)
                .gender(randomGender)
                .bloodGroup(randomBloodGroup)
                .address(address)
                .status(true) // Sets primitive boolean to active
                .createdAt(createdAt)

                // Teacher-specific fields
                .email(email)
                .salary(salary)
                .phoneNumber(phoneNumber)
                .dateOfJoining(dateOfJoining)
                .teacherType(randomTeacherType)
                .teachingLevel(randomTeachingLevel)
                .teacherQualification(randomQualification)
                .build();

        log.info("Seeding fake teacher: {} {} ({})", firstName, lastName, randomQualification);
        return teacherRepository.save(teacher);
    }

    private String truncate(String value, int maxLength) {
        if (value == null) return null;
        return value.length() > maxLength ? value.substring(0, maxLength) : value;
    }
}
