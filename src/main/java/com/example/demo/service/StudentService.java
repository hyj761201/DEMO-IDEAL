package com.example.demo.service;

import com.example.demo.dto.PageResult;
import com.example.demo.dto.StudentDTO;

import java.util.List;

public interface StudentService {

   StudentDTO getStudentById(Long id);

   List<StudentDTO> getAllStudents();

   PageResult<StudentDTO> getStudentsByPage(int page, int size);

   PageResult<StudentDTO> searchStudents(String keyword, int page, int size);

   Long addNewStudent(StudentDTO studentDTO);

    void deleteStudentById(Long id);

    StudentDTO updateStudentById(long id, String name, String email);

    StudentDTO updateStudentAvatar(Long id, String avatarUrl);
}
