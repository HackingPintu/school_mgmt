package com.ssvm.school.controller;

import com.ssvm.school.dto.request.SchoolClassRequestDTO;
import com.ssvm.school.dto.response.SchoolClassResponseDTO;
import com.ssvm.school.services.SchoolClassService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/${endpoint.class:class}")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @PostMapping("/create")
    public ResponseEntity<SchoolClassResponseDTO> createClass(@RequestBody SchoolClassRequestDTO schoolClassRequestDTO) {
        SchoolClassResponseDTO responseDTO = schoolClassService.create(schoolClassRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<SchoolClassResponseDTO>> getAllClasses() {
        return ResponseEntity.ok(schoolClassService.findAll());
    }

    @PatchMapping("/update")
    public ResponseEntity<SchoolClassResponseDTO> updateClass(@RequestBody SchoolClassRequestDTO schoolClassRequestDTO) {
        return ResponseEntity.ok(schoolClassService.update(schoolClassRequestDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable String id) {
        schoolClassService.delete(id);
        return ResponseEntity.ok().build();
    }
}
