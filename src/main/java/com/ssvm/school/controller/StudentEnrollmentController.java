package com.ssvm.school.controller;

import com.ssvm.school.constants.Standards;
import com.ssvm.school.dto.request.StudentEnrollmentRequestDTO;
import com.ssvm.school.dto.response.StudentEnrollmentResponseDTO;
import com.ssvm.school.services.StudentEnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/${endpoint.studentenrollment:enrollment}")
public class StudentEnrollmentController {

    private final StudentEnrollmentService studentEnrollmentService;

    public StudentEnrollmentController(StudentEnrollmentService studentEnrollmentService) {
        this.studentEnrollmentService = studentEnrollmentService;
    }

    @PostMapping("/create")
    public ResponseEntity<StudentEnrollmentResponseDTO> enrollStudent(@RequestBody StudentEnrollmentRequestDTO studentEnrollment) {
        return ResponseEntity.ok(studentEnrollmentService.enrollment(studentEnrollment));
    }

    @GetMapping("/get/byclass")
    public ResponseEntity<List<StudentEnrollmentResponseDTO>> getStudentEnrollmentByClass(
            @RequestParam("standards")
            Standards standards) {
        return ResponseEntity.ok(studentEnrollmentService.findBySchoolClass(standards));
    }

    @GetMapping("/get/byyear")
    public ResponseEntity<List<StudentEnrollmentResponseDTO>> getStudentEnrollmentByAcademicYear(
            @RequestParam("year")
            String yearLabel) {
        return ResponseEntity.ok(studentEnrollmentService.findByAcademicYear(yearLabel));
    }

    @PatchMapping("/remove")
    public ResponseEntity<String> removeStudentEnrollment(@RequestParam("studentEnrollmentId") String studentEnrollmentId) {
        studentEnrollmentService.removeStudentEnrollment(studentEnrollmentId);
        return ResponseEntity.ok("Removed student enrollment");
    }

}
