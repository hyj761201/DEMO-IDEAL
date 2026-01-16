package com.example.demo.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByEmail(String email);

    // 分页查询
    Page<Student> findAll(Pageable pageable);

    // 根据姓名模糊查询（分页）
    Page<Student> findByNameContaining(String name, Pageable pageable);

    // 根据邮箱模糊查询（分页）
    Page<Student> findByEmailContaining(String email, Pageable pageable);

    // 综合搜索：姓名或邮箱（分页）
    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword% OR s.email LIKE %:keyword%")
    Page<Student> searchByNameOrEmail(@Param("keyword") String keyword, Pageable pageable);
}
