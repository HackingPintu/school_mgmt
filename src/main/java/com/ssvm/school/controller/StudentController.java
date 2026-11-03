package com.ssvm.school.controller;

import com.ssvm.school.dto.response.BaseEntityResponseDTO;
import com.ssvm.school.dto.request.StudentRequestDTO;
import com.ssvm.school.dto.response.StudentResponseDTO;
import com.ssvm.school.entity.Student;
import com.ssvm.school.services.StudentService;
import com.ssvm.school.util.StudentDataSeederService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/${endpoint.student:student}")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Student Controller", description = "Endpoints for managing student profiles and records")
public class StudentController {

    private final StudentService studentService;
    private final StudentDataSeederService seederService;

    @Operation(summary = "Register a new student", description = "Uploads student personal info.")
    @PostMapping(path = "/addStudent", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseEntityResponseDTO> createStudent(
            @Valid @ModelAttribute StudentRequestDTO request) {
        BaseEntityResponseDTO response = studentService.createStudent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Faker data upload", description = "Creates a dummy student and uploads it.")
    @PostMapping("/fake")
    public ResponseEntity<String> createFakeStudentWithLocalPhoto() {
        Student student = seederService.generateAndSaveFakeStudent();
        return ResponseEntity.ok("Created student ID: " + student.getId());
    }

    @Operation(summary = "Get all student data", description = "Get all student data in pages sorted by given parameter.")
    @GetMapping("/getAll")
    public ResponseEntity<Page<StudentResponseDTO>> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        if (size <= 0) {
            log.info("Requested size was <= 0. Defaulting size to 20.");
            size = 20;
        }
        if (page < 0) {
            page = 0;
        }
        Page<StudentResponseDTO> studentPage = studentService.getPaginatedStudents(page, size, sortBy, direction);
        return ResponseEntity.ok(studentPage);
    }

    @Operation(summary = "Update student", description = "Update a student with id and the passed parameters")
    @PatchMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable String id,
                                                            @RequestBody StudentRequestDTO request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));

    }

    @Operation(summary = "Delete a student", description = "Hard delete a student with id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted");
    }

    @GetMapping
    public ResponseEntity<Student> getStudentById(@RequestParam String id) {
        return ResponseEntity.ok(studentService.findByIdAndStatusTrue(id));
    }


}