package com.example.crudSpringBootDemo.services;

import com.example.crudSpringBootDemo.dtos.GenericStudentRequestDto;
import com.example.crudSpringBootDemo.dtos.GenericStudentResponseDto;
import com.example.crudSpringBootDemo.dtos.UpdateStudentResponseDto;
import com.example.crudSpringBootDemo.entities.Student;
import com.example.crudSpringBootDemo.exceptions.DuplicateResourceException;
import com.example.crudSpringBootDemo.exceptions.ResourceNotFoundException;
import com.example.crudSpringBootDemo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public GenericStudentResponseDto createStudent(GenericStudentRequestDto studentReq)
    {
        if(studentRepository.existsByEmail(studentReq.getEmail()))
            throw new DuplicateResourceException("Student with email " + studentReq.getEmail() + " already exists.");

        Student student = mapToEntity(studentReq);
        GenericStudentResponseDto responseDto = mapToDto(studentRepository.save(student) );
        responseDto.setMessage("Student saved successfully.");
        return responseDto;
    }

    public GenericStudentResponseDto getStudent(long id)
    {
       GenericStudentResponseDto responseDto = mapToDto(studentRepository.findByIdAndDeletedIsFalse(id).orElseThrow( () -> new ResourceNotFoundException(
               "Student with id " + id + " does not exists."
               ) ) );
       responseDto.setMessage("Student found.");
       return responseDto;
    }

    public List<GenericStudentResponseDto> getAllStudent()
    {
        GenericStudentResponseDto responseDto;
        List<Student> studentList = studentRepository.findByDeletedIsFalse();
        List<GenericStudentResponseDto> responseDtoList = new ArrayList<>();
        for( Student student: studentList) {
            responseDto = mapToDto(student);
            responseDto.setMessage("Student found.");
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    public UpdateStudentResponseDto updateStudent(long id, GenericStudentRequestDto studentReq)
    {
        if(studentRepository.existsByEmail(studentReq.getEmail()))
            throw new DuplicateResourceException("Student with email " + studentReq.getEmail() + " already exists.");

        Student existingStudent = studentRepository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new ResourceNotFoundException(
                "Student with id " + id + " does not exists."
        ));

        existingStudent.setAge(studentReq.getAge());
        existingStudent.setEmail(studentReq.getEmail());
        existingStudent.setName(studentReq.getName());
        existingStudent.setSubject(studentReq.getSubject());
        existingStudent.setRollNo(studentReq.getRollNo());
        existingStudent.setUpdatedAt(LocalDateTime.now());
        existingStudent.setDeleted(false);
        return mapToUpdateDto(studentRepository.save(existingStudent) );
    }

    public void hardDeleteStudent(long id)
    {
        Student student = studentRepository.findByIdAndDeletedIsFalse(id).orElseThrow( () -> new ResourceNotFoundException(
                "Student with id " + id + " does not exists."
        ));
        studentRepository.deleteById(id);
    }

    public void softDeleteStudent(long id)
    {
        Student student = studentRepository.findByIdAndDeletedIsFalse(id).orElseThrow( () -> new ResourceNotFoundException(
                "Student with id " + id + " does not exists."
        ));
        student.setDeleted(true);
        studentRepository.save(student);
    }

    private Student mapToEntity(GenericStudentRequestDto requestDto){
        Student student = new  Student();
        student.setDeleted(false);
        student.setName(requestDto.getName());
        student.setAge(requestDto.getAge());
        student.setEmail(requestDto.getEmail());
        student.setRollNo(requestDto.getRollNo());
        student.setSubject(requestDto.getSubject());
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());

        return student;
    }

    private GenericStudentResponseDto mapToDto(Student student){
        GenericStudentResponseDto responseDto = new GenericStudentResponseDto();
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setName(student.getName());
        responseDto.setSubject(student.getSubject());
        responseDto.setId(student.getId());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());

        return responseDto;
    }

    private UpdateStudentResponseDto mapToUpdateDto(Student student) {
        UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setSubject(student.getSubject());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setUpdatedAt(student.getUpdatedAt());
        responseDto.setMessage("Student updated successfully.");
        return responseDto;
    }

}
