# 学生信息管理系统 - 前端

这是一个基于 React + Vite 构建的学生信息管理系统前端应用。

## 功能特性

- ✅ 学生列表展示
- ✅ 添加新学生
- ✅ 编辑学生信息
- ✅ 删除学生
- ✅ 响应式设计，支持移动端

## 技术栈

- React 18
- Vite
- Axios
- CSS3

## 安装和运行

### 1. 安装依赖

```bash
cd frontend
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

前端应用将在 http://localhost:3000 启动

### 3. 构建生产版本

```bash
npm run build
```

## 配置

确保后端 SpringBoot 应用运行在 `http://localhost:8080`

如果需要修改后端地址，请编辑 `src/services/api.js` 文件中的 `API_BASE_URL`

