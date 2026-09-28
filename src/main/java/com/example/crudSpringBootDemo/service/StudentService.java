package com.example.crudSpringBootDemo.service;

import com.example.crudSpringBootDemo.entity.Student;
import com.example.crudSpringBootDemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student studentReq)
    {
        studentReq.setDeleted(false);
        Student studentResp = studentRepository.save(studentReq);
        return studentResp;
    }

    public Student getStudent(long id)
    {
       Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);
       if(studentResp.isPresent())
           return studentResp.get();

       return null;
    }

    public List<Student> getAllStudent()
    {
        Optional<List<Student>> studentList = studentRepository.findByDeletedIsFalse();
        if(studentList.isPresent())
            return studentList.get();
        return null;
    }

    public Student updateStudent(long id, Student studentReq)
    {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if(existingStudent.isEmpty())
            return null;

        Student studentToUpdate = existingStudent.get();
        studentToUpdate.setAge(studentReq.getAge());
        studentToUpdate.setEmail(studentReq.getEmail());
        studentToUpdate.setName(studentReq.getName());
        studentToUpdate.setSubject(studentReq.getSubject());
        studentToUpdate.setRollNo(studentReq.getRollNo());
        studentToUpdate.setDeleted(false);
        return studentRepository.save(studentToUpdate);
    }

    public Boolean hardDeleteStudent(long id)
    {
        Boolean isStudent = studentRepository.existsById(id);
        if (!isStudent) return false;

        studentRepository.deleteById(id);
        return true;
    }

    public Boolean softDeleteStudent(long id)
    {
        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedIsFalse(id);
        if(existingStudent.isEmpty())
            return false;
        Student studentToDelete = existingStudent.get();
        studentToDelete.setDeleted(true);
        studentRepository.save(studentToDelete);
        return true;

    }
}
