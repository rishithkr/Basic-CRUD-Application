package com.example.crudSpringBootDemo.controller;

import com.example.crudSpringBootDemo.entity.Student;
import com.example.crudSpringBootDemo.service.StudentService;
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
    public ResponseEntity<Student> createStudent(@RequestBody Student studentReq)

    {
        Student createdStudent = studentService.createStudent(studentReq);
        createdStudent.setDeleted(false);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id)
    {
        Student studentResp = studentService.getStudent(id);
        if(studentResp == null)
            return ResponseEntity.status(404).build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudent()
    {
        List<Student> studentList = studentService.getAllStudent();
        if(studentList.isEmpty())
            return ResponseEntity.status(404).build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable long id, @RequestBody Student studentReq)
    {
        Student studentResp = studentService.updateStudent(id, studentReq);
        if(studentResp == null)
            return ResponseEntity.status(404).build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentResp);
    }

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> softDeleteStudent(@PathVariable long id)
    {
        Boolean wasDeleted = studentService.softDeleteStudent(id);
        if(!wasDeleted)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body("The record was soft deleted");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> hardDeleteStudent(@PathVariable long id)
    {
        Boolean wasDeleted = studentService.hardDeleteStudent(id);
        if(!wasDeleted)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body("The record was hard deleted");
    }
}
