package com.example.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StudentViewModel : ViewModel() {
    private val repository = StudentRepository()

    private val _students = MutableStateFlow<List<StudentDTO>>(emptyList())
    val students: StateFlow<List<StudentDTO>> = _students

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var currentPage = 0
    private var hasMore = true

    // 加载学生列表
    fun loadStudents(refresh: Boolean = false) {
        if (refresh) {
            currentPage = 0
            hasMore = true
        }
        if (!hasMore && !refresh) return

        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            repository.getStudents(currentPage, 10).fold(
                onSuccess = { pageResult ->
                    if (refresh) {
                        _students.value = pageResult.content
                    } else {
                        _students.value = _students.value + pageResult.content
                    }
                    hasMore = pageResult.hasNext
                    currentPage++
                },
                onFailure = { e ->
                    _error.value = e.message ?: "加载失败"
                }
            )
            _loading.value = false
        }
    }

    // 搜索学生
    fun searchStudents(keyword: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            repository.searchStudents(keyword, 0, 10).fold(
                onSuccess = { pageResult ->
                    _students.value = pageResult.content
                },
                onFailure = { e ->
                    _error.value = e.message ?: "搜索失败"
                }
            )
            _loading.value = false
        }
    }

    // 添加学生
    fun addStudent(name: String, email: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            repository.addStudent(name, email).fold(
                onSuccess = {
                    loadStudents(refresh = true)  // 刷新列表
                },
                onFailure = { e ->
                    _error.value = e.message ?: "添加失败"
                }
            )
            _loading.value = false
        }
    }

    // 更新学生
    fun updateStudent(id: Long, name: String, email: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            repository.updateStudent(id, name, email).fold(
                onSuccess = {
                    loadStudents(refresh = true)  // 刷新列表
                },
                onFailure = { e ->
                    _error.value = e.message ?: "更新失败"
                }
            )
            _loading.value = false
        }
    }

    // 删除学生
    fun deleteStudent(id: Long) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            repository.deleteStudent(id).fold(
                onSuccess = {
                    loadStudents(refresh = true)  // 刷新列表
                },
                onFailure = { e ->
                    _error.value = e.message ?: "删除失败"
                }
            )
            _loading.value = false
        }
    }
}
