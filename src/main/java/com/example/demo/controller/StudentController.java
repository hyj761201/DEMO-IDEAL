package com.example.demo.controller;


import com.example.demo.Response;
import com.example.demo.dto.PageResult;
import com.example.demo.dto.StudentDTO;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // 文件上传目录
    private static final String UPLOAD_DIR = "uploads/avatars/";

    static {
        // 确保上传目录存在
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有学生（不分页，兼容旧接口）
     */
    @GetMapping("/student/all")
    public Response<List<StudentDTO>> getAllStudents() {
        return Response.newSuccess(studentService.getAllStudents());
    }

    /**
     * 分页获取学生列表（移动端推荐使用）
     * @param page 页码（从0开始）
     * @param size 每页数量
     */
    @GetMapping("/student")
    public Response<PageResult<StudentDTO>> getStudentsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Response.newSuccess(studentService.getStudentsByPage(page, size));
    }

    /**
     * 搜索学生（支持姓名或邮箱模糊搜索，分页）
     * @param keyword 搜索关键词
     * @param page 页码（从0开始）
     * @param size 每页数量
     */
    @GetMapping("/student/search")
    public Response<PageResult<StudentDTO>> searchStudents(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Response.newSuccess(studentService.searchStudents(keyword, page, size));
    }

    /**
     * 根据ID获取学生详情
     */
    @GetMapping("/student/{id}")
    public Response<StudentDTO> getStudentById(@PathVariable Long id) {
        return Response.newSuccess(studentService.getStudentById(id));
    }

    /**
     * 添加新学生
     */
    @PostMapping("/student")
    public Response<Long> addNewStudent(@RequestBody StudentDTO studentDTO) {
        return Response.newSuccess(studentService.addNewStudent(studentDTO));
    }

    /**
     * 删除学生
     */
    @DeleteMapping("/student/{id}")
    public Response<Void> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return Response.newSuccess(null);
    }

    /**
     * 更新学生信息
     */
    @PutMapping("/student/{id}")
    public Response<StudentDTO> updateStudentById(
            @PathVariable long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        return Response.newSuccess(studentService.updateStudentById(id, name, email));
    }

    /**
     * 上传学生头像（移动端常用功能）
     * @param id 学生ID
     * @param file 图片文件
     */
    @PostMapping("/student/{id}/avatar")
    public Response<StudentDTO> uploadAvatar(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            // 验证文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("只能上传图片文件");
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".") 
                ? originalFilename.substring(originalFilename.lastIndexOf(".")) 
                : ".jpg";
            String filename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 生成访问URL（实际项目中应该使用CDN或OSS）
            String avatarUrl = "/api/files/avatars/" + filename;

            // 更新学生头像
            StudentDTO updated = studentService.updateStudentAvatar(id, avatarUrl);
            return Response.newSuccess(updated);

        } catch (IOException e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }

    /**
     * 获取头像文件（实际项目中应该配置静态资源映射）
     */
    @GetMapping("/files/avatars/{filename}")
    public org.springframework.http.ResponseEntity<byte[]> getAvatar(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR + filename);
            byte[] fileContent = Files.readAllBytes(filePath);
            return org.springframework.http.ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(fileContent);
        } catch (IOException e) {
            return org.springframework.http.ResponseEntity.notFound().build();
        }
    }
}
