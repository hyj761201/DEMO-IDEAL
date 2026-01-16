# Android 学生信息管理系统

这是一个接入学生信息管理系统后端API的Android应用示例。

## 🚀 快速开始

### 📋 完整启动流程

#### 第一步：启动后端服务

1. **打开后端项目目录**
   ```bash
   cd "d:\IDEAL\IDEA Program\demo"
   ```

2. **启动后端服务**
   ```bash
   mvn spring-boot:run
   ```
   或者使用 IDE（IntelliJ IDEA）运行 `BootDemoApplication.java`

3. **验证后端启动成功**
   - 访问：`http://localhost:8080/` 应该看到 API 信息
   - 访问：`http://localhost:8080/swagger-ui.html` 查看 API 文档

**⚠️ 重要：后端服务需要一直运行，不要关闭！**

#### 第二步：启动 Android 应用

1. **在 Android Studio 中打开项目**
   - 打开目录：`d:\Project\Android\Demo`
   - 点击 "Sync Now" 同步依赖

2. **配置 API 地址（如需要）**
   - **模拟器**：默认已配置 `http://10.0.2.2:8080/api/`，无需修改
   - **真机**：修改 `ApiConfig.kt` 中的 BASE_URL 为电脑 IP 地址

3. **运行应用**
   - 选择模拟器或连接真机
   - 点击运行按钮（绿色三角形）
   - **应用会自动在设备上启动，无需输入地址**

**⚠️ 重要说明：**
- Android 应用**不是 Web 应用**，不能通过浏览器访问
- Android 应用**没有访问地址和端口**
- 必须通过 **Android Studio 运行**或**安装 APK** 到设备上
- 应用启动后直接显示界面，无需输入任何地址

**详细步骤：** 
- 查看 [启动指南.md](./启动指南.md)
- 查看 [如何访问Android应用.md](./如何访问Android应用.md) - 详细说明访问方式

### 2. 配置API地址

**模拟器使用：**
- 默认配置：`http://10.0.2.2:8080/api/`
- 无需修改，直接运行

**真机使用：**
1. 确保手机和电脑在同一WiFi
2. 获取电脑IP地址：
   - Windows: `ipconfig`
   - Mac/Linux: `ifconfig`
3. 修改 `ApiConfig.kt`：
   ```kotlin
   const val BASE_URL = "http://192.168.1.100:8080/api/"  // 替换为你的电脑IP
   ```

### 3. 运行应用

1. 在 Android Studio 中打开项目
2. 点击 "Sync Now" 同步依赖
3. 运行应用（模拟器或真机）

## 📱 功能特性

- ✅ 分页加载学生列表
- ✅ 下拉刷新
- ✅ 上拉加载更多
- ✅ 搜索学生
- ✅ 添加学生
- ✅ 删除学生
- ✅ 错误处理

## 🏗️ 项目结构

```
app/src/main/java/com/example/demo/
├── ApiConfig.kt              # API配置
├── StudentDTO.kt            # 数据模型
├── Response.kt               # 响应格式
├── StudentApiService.kt      # Retrofit接口
├── RetrofitClient.kt         # Retrofit客户端
├── StudentRepository.kt     # Repository层
├── StudentViewModel.kt       # ViewModel
├── StudentAdapter.kt         # RecyclerView适配器
└── MainActivity.kt           # 主Activity
```

## 📚 技术栈

- **Kotlin** - 开发语言
- **Retrofit** - HTTP客户端
- **OkHttp** - 网络请求库
- **ViewModel** - 数据管理
- **StateFlow** - 响应式数据流
- **Coroutines** - 协程
- **RecyclerView** - 列表展示
- **SwipeRefreshLayout** - 下拉刷新

## 🔧 依赖说明

所有依赖已在 `gradle/libs.versions.toml` 和 `app/build.gradle.kts` 中配置：

- Retrofit 2.9.0
- OkHttp 4.12.0
- Glide 4.16.0
- Lifecycle 2.6.2
- Coroutines 1.7.3

## 📝 使用说明

### 加载学生列表

应用启动后会自动加载第一页数据（10条）

### 下拉刷新

向下滑动列表顶部可以刷新数据

### 上拉加载更多

滚动到列表底部会自动加载下一页数据

### 删除学生

点击列表项右侧的"删除"按钮可以删除学生

## ⚠️ 注意事项

1. **网络权限**：已在 `AndroidManifest.xml` 中添加网络权限
2. **API地址**：确保 `ApiConfig.kt` 中的地址正确
3. **后端服务**：确保后端服务正在运行
4. **防火墙**：真机测试时可能需要关闭防火墙或添加例外

## 🐛 常见问题

### 无法连接到服务器

- **模拟器**：使用 `http://10.0.2.2:8080/api/`（不要用localhost）
- **真机**：确保手机和电脑在同一WiFi，使用电脑IP地址

### 编译错误

- 点击 "Sync Now" 同步项目
- 确保所有依赖已正确下载

### 运行时错误

- 检查后端服务是否正在运行
- 查看 Logcat 中的错误信息
- 检查网络权限是否已添加

## 📖 更多信息

- 后端API文档：`http://localhost:8080/swagger-ui.html`
- 详细接入文档：查看后端项目的 `Android接入文档.md`
