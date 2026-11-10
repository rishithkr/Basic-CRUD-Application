package com.example.crudSpringBootDemo.controllers;

import com.example.crudSpringBootDemo.dtos.GenericStudentResponseDto;
import com.example.crudSpringBootDemo.dtos.GenericStudentRequestDto;
import com.example.crudSpringBootDemo.dtos.UpdateStudentResponseDto;
import com.example.crudSpringBootDemo.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;// STUDENT CONTROLLER MUST KNOW THE STUDENT SERVICE ADDRESS

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<GenericStudentResponseDto> createStudent(@Valid @RequestBody GenericStudentRequestDto studentReq)

    {
        GenericStudentResponseDto createdStudent = studentService.createStudent(studentReq);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericStudentResponseDto> getStudent(@PathVariable long id)
    {
        GenericStudentResponseDto studentResp = studentService.getStudent(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<GenericStudentResponseDto>> getAllStudent()
    {
        List<GenericStudentResponseDto> studentList = studentService.getAllStudent();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@PathVariable long id, @Valid @RequestBody GenericStudentRequestDto studentReq)
    {
        UpdateStudentResponseDto studentResp = studentService.updateStudent(id, studentReq);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentResp);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> softDeleteStudent(@PathVariable long id)
    {
        studentService.softDeleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("The record was soft deleted");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> hardDeleteStudent(@PathVariable long id)
    {
        studentService.hardDeleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("The record was hard deleted");
    }
}
