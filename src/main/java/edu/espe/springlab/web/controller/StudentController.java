package edu.espe.springlab.web.controller;// Contenido parcial del archivo StudentController.java
import edu.espe.springlab.dto.StudentRequestData;
import edu.espe.springlab.dto.StudentResponse;
import edu.espe.springlab.dto.StudentUpdateData;
import edu.espe.springlab.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequestData request) {
        StudentResponse studentResponse = studentService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentResponse);
    }

    @GetMapping( "/{id}")
    public ResponseEntity<StudentResponse> getbById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAll() {
        return ResponseEntity.ok(studentService.list());
    }

    // Desactivar estudiante
    @PatchMapping("/{id}/desactivate")
    public ResponseEntity<StudentResponse> desactivate(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.desactivate(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody StudentUpdateData request
    ) {
        return ResponseEntity.ok(studentService.update(id, request));
    }


}