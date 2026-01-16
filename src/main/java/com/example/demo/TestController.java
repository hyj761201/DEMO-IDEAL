package com.example.demo;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class TestController {

    @GetMapping("/hello")
    public List<String> hello() {
        return List.of("Hello World");
    }

    @GetMapping("/")
    public Map<String, Object> index() {
        Map<String, Object> info = new HashMap<>();
        info.put("message", "学生信息管理系统 API - 移动端后端服务");
        info.put("status", "running");
        info.put("description", "专门为移动端（Android/iOS）设计的后端API服务");
        
        // API文档链接
        Map<String, String> docs = new HashMap<>();
        docs.put("swagger", "http://localhost:8080/swagger-ui.html");
        docs.put("android", "查看项目根目录下的 Android接入文档.md");
        docs.put("readme", "查看项目根目录下的 README.md");
        info.put("docs", docs);
        
        // API端点
        info.put("endpoints", Map.of(
            "GET /api/student/all", "获取所有学生列表",
            "GET /api/student?page=0&size=10", "分页查询学生列表（移动端推荐）",
            "GET /api/student/search?keyword=xxx", "搜索学生（移动端推荐）",
            "GET /api/student/{id}", "根据ID获取学生信息",
            "POST /api/student", "添加新学生",
            "PUT /api/student/{id}", "更新学生信息",
            "DELETE /api/student/{id}", "删除学生",
            "POST /api/student/{id}/avatar", "上传学生头像（移动端功能）"
        ));
        
        // 移动端特性
        Map<String, String> features = new HashMap<>();
        features.put("分页查询", "支持分页加载，返回hasNext/hasPrevious字段，便于实现上拉加载更多");
        features.put("搜索功能", "支持实时搜索，模糊匹配姓名和邮箱");
        features.put("文件上传", "支持头像上传，文件类型验证和大小限制");
        features.put("统一响应格式", "所有API返回统一格式，便于移动端统一处理");
        features.put("CORS跨域", "已配置CORS，支持Web、Android、iOS多端接入");
        info.put("mobileFeatures", features);
        
        // 前端访问
        info.put("frontend", "请打开 frontend/index-standalone.html 文件访问前端界面");
        
        return info;
    }
    
    /**
     * 获取文档信息
     */
    @GetMapping("/docs")
    public Map<String, Object> getDocs() {
        Map<String, Object> docs = new HashMap<>();
        docs.put("swagger", Map.of(
            "url", "http://localhost:8080/swagger-ui.html",
            "description", "Swagger API文档，可视化测试所有接口"
        ));
        docs.put("android", Map.of(
            "file", "Android接入文档.md",
            "location", "项目根目录",
            "description", "完整的Android集成指南，包含Retrofit、ViewModel等示例代码"
        ));
        docs.put("demo", Map.of(
            "file", "快速演示脚本.md",
            "location", "项目根目录",
            "description", "5分钟快速演示流程和话术"
        ));
        docs.put("readme", Map.of(
            "file", "README.md",
            "location", "项目根目录",
            "description", "项目说明和快速开始指南"
        ));
        return docs;
    }


}
