package com.example.demo.service;

import com.example.demo.converter.StudentConverter;
import com.example.demo.dao.Student;
import com.example.demo.dao.StudentRepository;
import com.example.demo.dto.PageResult;
import com.example.demo.dto.StudentDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentDTO getStudentById(Long id) {
       Student student= studentRepository.findById(id).orElseThrow(RuntimeException::new);
       return StudentConverter.convertStudent(student);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(StudentConverter::convertStudent)
                .toList();
    }

    @Override
    public PageResult<StudentDTO> getStudentsByPage(int page, int size) {
        // 分页参数：page从0开始，按创建时间倒序
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Student> studentPage = studentRepository.findAll(pageable);
        
        List<StudentDTO> content = studentPage.getContent().stream()
                .map(StudentConverter::convertStudent)
                .collect(Collectors.toList());
        
        return new PageResult<>(
                content,
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages()
        );
    }

    @Override
    public PageResult<StudentDTO> searchStudents(String keyword, int page, int size) {
        if (!StringUtils.hasLength(keyword)) {
            return getStudentsByPage(page, size);
        }
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        Page<Student> studentPage = studentRepository.searchByNameOrEmail(keyword, pageable);
        
        List<StudentDTO> content = studentPage.getContent().stream()
                .map(StudentConverter::convertStudent)
                .collect(Collectors.toList());
        
        return new PageResult<>(
                content,
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages()
        );
    }

    @Override
    public Long addNewStudent(StudentDTO studentDTO) {
        List<Student> studentList=studentRepository.findByEmail(studentDTO.getEmail());
        if(!CollectionUtils.isEmpty(studentList)){
            throw new IllegalStateException("email:"+studentDTO.getEmail() + " has been taken");

        }
        Student student = studentRepository.save(StudentConverter.convertStudent(studentDTO));
        return student.getId();
    }

    @Override
    public void deleteStudentById(Long id){
        studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("id:"+id + " doesn't exist"));
        studentRepository.deleteById(id);

    }

    @Override
    @Transactional
    public StudentDTO updateStudentById(long id, String name, String email) {
        Student studentInDB = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("id:"+id + " doesn't exist"));

        if(StringUtils.hasLength(name)&& !studentInDB.getName().equals(name)){
            studentInDB.setName(name);
        }
        if(StringUtils.hasLength(email)&& !studentInDB.getEmail().equals(email)){
            studentInDB.setEmail(email);
        }
            Student student = studentRepository.save(studentInDB);
        return StudentConverter.convertStudent(student);
    }

    @Override
    @Transactional
    public StudentDTO updateStudentAvatar(Long id, String avatarUrl) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("id:" + id + " doesn't exist"));
        student.setAvatarUrl(avatarUrl);
        student = studentRepository.save(student);
        return StudentConverter.convertStudent(student);
    }
}
