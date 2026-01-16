package com.example.demo

class StudentRepository {
    private val api = RetrofitClient.studentApi

    suspend fun getStudents(page: Int = 0, size: Int = 10): Result<PageResult<StudentDTO>> {
        return try {
            val response = api.getStudents(page, size)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.errorMsg ?: "获取数据失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchStudents(keyword: String, page: Int = 0, size: Int = 10): Result<PageResult<StudentDTO>> {
        return try {
            val response = api.searchStudents(keyword, page, size)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.errorMsg ?: "搜索失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addStudent(name: String, email: String): Result<Long> {
        return try {
            val student = StudentDTO(name = name, email = email)
            val response = api.addStudent(student)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.errorMsg ?: "添加失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateStudent(id: Long, name: String, email: String): Result<StudentDTO> {
        return try {
            val response = api.updateStudent(id, name, email)
            if (response.success && response.data != null) {
                Result.success(response.data)
            } else {
                Result.failure(Exception(response.errorMsg ?: "更新失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteStudent(id: Long): Result<Unit> {
        return try {
            val response = api.deleteStudent(id)
            if (response.success) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.errorMsg ?: "删除失败"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
