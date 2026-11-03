package com.ssvm.school.controller;

import com.ssvm.school.constants.TeacherQualification;
import com.ssvm.school.constants.TeacherType;
import com.ssvm.school.constants.TeachingLevel;
import com.ssvm.school.dto.request.TeacherRequestDTO;
import com.ssvm.school.dto.response.TeacherResponseDTO;
import com.ssvm.school.entity.Teacher;
import com.ssvm.school.services.TeacherService;
import com.ssvm.school.util.TeacherDataSeederService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${endpoint.teacher:teacher}")
public class TeacherController {

    private final TeacherService teacherService;
    private final TeacherDataSeederService teacherDataSeederService;

    public TeacherController(TeacherService teacherService, TeacherDataSeederService teacherDataSeederService) {
        this.teacherService = teacherService;
        this.teacherDataSeederService = teacherDataSeederService;
    }

    @GetMapping("/level")
    public ResponseEntity<List<TeacherResponseDTO>> getTeacherListByLevel(@RequestParam TeachingLevel level) {
        List<TeacherResponseDTO> result = teacherService.getTeacherByLevel(level);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/type")
    public ResponseEntity<List<TeacherResponseDTO>> getTeacherListByType(@RequestParam TeacherType type) {
        List<TeacherResponseDTO> result = teacherService.getTeacherByType(type);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/qualification")
    public ResponseEntity<List<TeacherResponseDTO>> getTeacherListByQualification(@RequestParam TeacherQualification qualification) {
        List<TeacherResponseDTO> result = teacherService.getTeacherByQualification(qualification);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable String id,
                                                            @RequestBody TeacherRequestDTO teacherRequestDTO) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, teacherRequestDTO));
    }

    @PostMapping("/create")
    public ResponseEntity<TeacherResponseDTO> createTeacher(@RequestBody TeacherRequestDTO teacherRequestDTO) {
        return new ResponseEntity<>(teacherService.createTeacher(teacherRequestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/fake")
    public ResponseEntity<Teacher> createFakeTeacher() {
        return ResponseEntity.ok(teacherDataSeederService.generateAndSaveFakeTeacher());
    }

    @GetMapping("/allActive")
    public ResponseEntity<List<TeacherResponseDTO>> getAllActiveTeachers() {
        return ResponseEntity.ok(teacherService.getAllActiveTeachers());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable String id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.ok("Teacher has been deleted");
    }
}
