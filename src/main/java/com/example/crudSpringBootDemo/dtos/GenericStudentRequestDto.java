package com.example.crudSpringBootDemo.dtos;

import jakarta.validation.constraints.*;

public class GenericStudentRequestDto {

    @NotBlank(message =  "Name cannot be empty or blank.")
    private String name;

    @NotBlank(message =  "Email cannot be empty or blank.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message =  "Subject cannot be empty or blank.")
    private String subject;

    @NotNull
    @Min(value = 5, message = "Invalid age.")
    @Max(value = 80, message = "Invalid age.")
    private Integer age;

    @NotNull
    @Positive
    private Integer rollNo;

    public GenericStudentRequestDto(String name, String email, String subject, int age, int rollNo) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.age = age;
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }
}
