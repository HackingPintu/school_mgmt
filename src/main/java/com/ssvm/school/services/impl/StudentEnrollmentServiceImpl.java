package com.ssvm.school.services.impl;

import com.ssvm.school.constants.Standards;
import com.ssvm.school.dto.request.StudentEnrollmentRequestDTO;
import com.ssvm.school.dto.response.StudentEnrollmentResponseDTO;
import com.ssvm.school.entity.*;
import com.ssvm.school.event.StudentEnrolledEvent;
import com.ssvm.school.repository.StudentEnrollmentRepository;
import com.ssvm.school.services.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class StudentEnrollmentServiceImpl implements StudentEnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final StudentService studentService;
    private final SchoolClassService schoolClassService;
    private final AcademicYearService academicYearService;
    private final FeeCalculationService feeCalculationService;
    private final ApplicationEventPublisher eventPublisher;


    public StudentEnrollmentServiceImpl(StudentEnrollmentRepository studentEnrollmentRepository,
                                        StudentService studentService,
                                        SchoolClassService schoolClassService,
                                        AcademicYearService academicYearService,
                                        @Lazy FeeCalculationService feeCalculationService,
                                        ApplicationEventPublisher eventPublisher) {
        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.studentService = studentService;
        this.schoolClassService = schoolClassService;
        this.academicYearService = academicYearService;
        this.feeCalculationService = feeCalculationService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public StudentEnrollmentResponseDTO enrollment(StudentEnrollmentRequestDTO enrollment) {
        Student student = studentService.findByIdAndStatusTrue(enrollment.getStudentId());
        SchoolClass schoolClass = schoolClassService.findByStandardAndStatusTrue(enrollment.getSchoolClass());
        AcademicYear academicYear = academicYearService.findByYearLabelAndStatusTrue(enrollment.getAcademicYear());
        LocalDate enrollmentDate = LocalDate.now();
        StudentEnrollment studentEnrollment = StudentEnrollment.builder()
                .student(student)
                .schoolClass(schoolClass)
                .academicYear(academicYear)
                .enrollmentDate(enrollmentDate)
                .status(true)
                .build();
        StudentEnrollment saved = studentEnrollmentRepository.saveAndFlush(studentEnrollment);
        log.info("Student enrollment done with id {}", saved.getId());
        eventPublisher.publishEvent(new StudentEnrolledEvent(saved.getId()));
        return getStudentEnrollmentResponseDTO(saved);

    }

    @Override
    public List<StudentEnrollmentResponseDTO> findBySchoolClass(Standards standards) {
        SchoolClass schoolClass = schoolClassService.findByStandardAndStatusTrue(standards);
        if (schoolClass == null) {
            throw new IllegalArgumentException("School class not found");
        }
        List<StudentEnrollment> list = studentEnrollmentRepository.findBySchoolClassAndStatusTrue(schoolClass);
        return list.stream()
                .map(StudentEnrollmentServiceImpl::getStudentEnrollmentResponseDTO)
                .sorted((e1, e2) -> {
                    String firstStudentName = e1.getStudentName();
                    String secondStudentName = e2.getStudentName();
                    return firstStudentName.compareToIgnoreCase(secondStudentName);
                })
                .toList();
    }

    @Override
    public List<StudentEnrollmentResponseDTO> findByAcademicYear(String yearLabel) {
        AcademicYear academicYear = academicYearService.findByYearLabelAndStatusTrue(yearLabel);
        if (academicYear == null) {
            throw new IllegalArgumentException("Academic year is not active");
        }

        List<StudentEnrollment> list = studentEnrollmentRepository.findByAcademicYearAndStatusTrue(academicYear);

        list.sort(Comparator
                .comparing((StudentEnrollment e) -> e.getSchoolClass().getClassName().ordinal())
                .thenComparing((StudentEnrollment e) ->
                {
                    var s = e.getStudent();
                    String fullName = s.getFirstName() + " " +
                            (s.getMiddleName() != null ? s.getMiddleName() + " " : "") +
                            s.getLastName();
                    return fullName.trim();
                }, String.CASE_INSENSITIVE_ORDER)
        );

        return list.stream()
                .map(StudentEnrollmentServiceImpl::getStudentEnrollmentResponseDTO)
                .toList();
    }


    @Override
    public List<StudentEnrollment> findByStatusTrue() {
        return studentEnrollmentRepository.findByStatusIsTrue();
    }

    @Override
    public StudentEnrollment findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Student id cannot be null");
        }
        return studentEnrollmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such student enrolled"));
    }

    @Override
    public List<StudentEnrollment> findAll() {
        return studentEnrollmentRepository.findAllByStatusIsTrue();
    }

    @Override
    @Transactional
    public void removeStudentEnrollment(String id) {
        BigDecimal pendingFee = feeCalculationService.calculatePendingFeeForStudent(id);
        if (pendingFee.compareTo(BigDecimal.ZERO) == 0) {
            StudentEnrollment enrollment = findByIdAndStatusTrue(id);
            enrollment.setStatus(false);
            studentEnrollmentRepository.save(enrollment);
        } else {
            throw new IllegalStateException("Student fee due pending : ₹" + pendingFee);
        }
    }

    @Override
    public StudentEnrollment findByIdAndStatusTrue(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Student id cannot be null");
        }
        return studentEnrollmentRepository.findByIdAndStatusIsTrue(id)
                .orElseThrow(() -> new IllegalArgumentException("No such student enrolled"));
    }

    private static @NotNull StudentEnrollmentResponseDTO getStudentEnrollmentResponseDTO(StudentEnrollment studentEnrollment) {
        StudentEnrollmentResponseDTO responseDTO = new StudentEnrollmentResponseDTO();
        String middle = studentEnrollment.getStudent().getMiddleName();
        String fullName = studentEnrollment.getStudent().getFirstName() + " " +
                (middle != null && !middle.isBlank() ? middle + " " : "") +
                studentEnrollment.getStudent().getLastName();
        responseDTO.setStudentName(fullName);
        responseDTO.setClassName(studentEnrollment.getSchoolClass().getClassName().toString());
        responseDTO.setAcademicYear(studentEnrollment.getAcademicYear().getYearLabel());

        return responseDTO;
    }
}
