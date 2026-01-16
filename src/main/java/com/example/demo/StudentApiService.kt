package com.example.demo

import okhttp3.MultipartBody
import retrofit2.http.*

interface StudentApiService {
    // 分页获取学生列表（移动端推荐）
    @GET("student")
    suspend fun getStudents(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): Response<PageResult<StudentDTO>>

    // 搜索学生
    @GET("student/search")
    suspend fun searchStudents(
        @Query("keyword") keyword: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10
    ): Response<PageResult<StudentDTO>>

    // 获取学生详情
    @GET("student/{id}")
    suspend fun getStudentById(@Path("id") id: Long): Response<StudentDTO>

    // 添加学生
    @POST("student")
    suspend fun addStudent(@Body student: StudentDTO): Response<Long>

    // 更新学生
    @PUT("student/{id}")
    suspend fun updateStudent(
        @Path("id") id: Long,
        @Query("name") name: String? = null,
        @Query("email") email: String? = null
    ): Response<StudentDTO>

    // 删除学生
    @DELETE("student/{id}")
    suspend fun deleteStudent(@Path("id") id: Long): Response<Void>

    // 上传头像
    @Multipart
    @POST("student/{id}/avatar")
    suspend fun uploadAvatar(
        @Path("id") id: Long,
        @Part file: MultipartBody.Part
    ): Response<StudentDTO>
}
