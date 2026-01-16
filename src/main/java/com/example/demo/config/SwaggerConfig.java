package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("学生信息管理系统 API")
                        .version("1.0.0")
                        .description("为学生信息管理系统提供RESTful API服务，支持移动端（Android/iOS）接入\n\n" +
                                "📱 Android接入文档：查看项目根目录下的 `Android接入文档.md`\n" +
                                "📚 项目文档：查看项目根目录下的 `README.md`\n" +
                                "🎯 演示指南：查看项目根目录下的 `快速演示脚本.md`\n\n" +
                                "**移动端特性：**\n" +
                                "- ✅ 分页查询：返回hasNext/hasPrevious字段，便于实现上拉加载更多\n" +
                                "- ✅ 搜索功能：支持实时搜索，模糊匹配\n" +
                                "- ✅ 文件上传：支持头像上传，文件类型验证\n" +
                                "- ✅ 统一响应格式：便于移动端统一处理")
                        .contact(new Contact()
                                .name("开发团队")
                                .email("dev@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
