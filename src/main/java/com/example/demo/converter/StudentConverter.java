package com.example.demo.converter;

import com.example.demo.dao.Student;
import com.example.demo.dto.StudentDTO;

public class StudentConverter {

    public static StudentDTO convertStudent(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setAge(student.getAge());
        studentDTO.setAvatarUrl(student.getAvatarUrl());
        studentDTO.setPhone(student.getPhone());
        studentDTO.setCreateTime(student.getCreateTime());
        studentDTO.setUpdateTime(student.getUpdateTime());
        return studentDTO;
    }

    public static Student convertStudent(StudentDTO studentDTO) {
        Student student = new Student();
        if (studentDTO.getId() != null) {
            student.setId(studentDTO.getId());
        }
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        if (studentDTO.getAge() != null) {
            student.setAge(studentDTO.getAge());
        }
        student.setAvatarUrl(studentDTO.getAvatarUrl());
        student.setPhone(studentDTO.getPhone());
        return student;
    }
}
