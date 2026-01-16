package com.example.demo

data class Response<T>(
    val success: Boolean,
    val data: T? = null,
    val errorMsg: String? = null
)

data class PageResult<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)
