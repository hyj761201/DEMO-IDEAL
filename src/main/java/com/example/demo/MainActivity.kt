package com.example.demo

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: StudentViewModel
    private lateinit var adapter: StudentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化 ViewModel
        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]

        // 初始化视图
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        addButton = findViewById(R.id.addButton)

        // 初始化 Adapter
        adapter = StudentAdapter(
            onEditClick = { student ->
                showStudentDialog(student)
            },
            onDeleteClick = { student ->
                showDeleteConfirmDialog(student)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // 添加按钮点击
        addButton.setOnClickListener {
            showStudentDialog(null)
        }

        // 观察学生列表
        lifecycleScope.launch {
            viewModel.students.collect { students ->
                adapter.submitList(students)
            }
        }

        // 观察加载状态
        lifecycleScope.launch {
            viewModel.loading.collect { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        // 观察错误信息
        lifecycleScope.launch {
            viewModel.error.collect { error ->
                error?.let {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // 下拉刷新
        swipeRefresh.setOnRefreshListener {
            viewModel.loadStudents(refresh = true)
            swipeRefresh.isRefreshing = false
        }

        // 上拉加载更多
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadStudents(refresh = false)
                }
            }
        })

        // 加载数据
        viewModel.loadStudents(refresh = true)
    }

    // 显示学生表单对话框
    private fun showStudentDialog(student: StudentDTO?) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_student_form, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.nameInput)
        val emailInput = dialogView.findViewById<EditText>(R.id.emailInput)
        val dialogTitle = dialogView.findViewById<android.widget.TextView>(R.id.dialogTitle)
        val saveButton = dialogView.findViewById<Button>(R.id.saveButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.cancelButton)

        // 如果是编辑模式，填充数据
        if (student != null) {
            dialogTitle.text = "编辑学生"
            nameInput.setText(student.name ?: "")
            emailInput.setText(student.email ?: "")
        } else {
            dialogTitle.text = "添加学生"
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        saveButton.setOnClickListener {
            val name = nameInput.text?.toString()?.trim() ?: ""
            val email = emailInput.text?.toString()?.trim() ?: ""

            if (name.isEmpty()) {
                Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                Toast.makeText(this, "请输入邮箱", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "请输入有效的邮箱地址", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (student != null) {
                // 更新学生
                student.id?.let { id ->
                    viewModel.updateStudent(id, name, email)
                }
            } else {
                // 添加学生
                viewModel.addStudent(name, email)
            }

            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    // 显示删除确认对话框
    private fun showDeleteConfirmDialog(student: StudentDTO) {
        AlertDialog.Builder(this)
            .setTitle("确认删除")
            .setMessage("确定要删除学生 ${student.name} 吗？")
            .setPositiveButton("删除") { _, _ ->
                student.id?.let {
                    viewModel.deleteStudent(it)
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
}
