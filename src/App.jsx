import React, { useState, useEffect } from 'react'
import StudentList from './components/StudentList'
import StudentForm from './components/StudentForm'
import { getAllStudents, createStudent, updateStudent, deleteStudent } from './services/api'
import './App.css'

function App() {
  const [students, setStudents] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [editingStudent, setEditingStudent] = useState(null)
  const [showForm, setShowForm] = useState(false)

  useEffect(() => {
    loadStudents()
  }, [])

  const loadStudents = async () => {
    setLoading(true)
    setError(null)
    try {
      const data = await getAllStudents()
      setStudents(data || [])
    } catch (error) {
      const errorMessage = error.message || '未知错误'
      setError(errorMessage)
      console.error('加载学生列表失败:', error)
    } finally {
      setLoading(false)
    }
  }

  const handleAdd = async (studentData) => {
    try {
      await createStudent(studentData)
      await loadStudents()
      setShowForm(false)
      alert('添加成功！')
    } catch (error) {
      alert('添加失败: ' + (error.message || '未知错误'))
    }
  }

  const handleUpdate = async (id, studentData) => {
    try {
      await updateStudent(id, studentData)
      await loadStudents()
      setEditingStudent(null)
      setShowForm(false)
      alert('更新成功！')
    } catch (error) {
      alert('更新失败: ' + (error.message || '未知错误'))
    }
  }

  const handleDelete = async (id) => {
    if (!window.confirm('确定要删除这名学生吗？')) {
      return
    }
    try {
      await deleteStudent(id)
      await loadStudents()
      alert('删除成功！')
    } catch (error) {
      alert('删除失败: ' + (error.message || '未知错误'))
    }
  }

  const handleEdit = (student) => {
    setEditingStudent(student)
    setShowForm(true)
  }

  const handleCancel = () => {
    setShowForm(false)
    setEditingStudent(null)
  }

  return (
    <div className="app">
      <div className="container">
        <header className="header">
          <h1>🎓 学生信息管理系统</h1>
          {!showForm && (
            <button className="btn btn-primary" onClick={() => setShowForm(true)}>
              + 添加学生
            </button>
          )}
        </header>

        {showForm ? (
          <StudentForm
            student={editingStudent}
            onSubmit={editingStudent ? 
              (data) => handleUpdate(editingStudent.id, data) : 
              handleAdd
            }
            onCancel={handleCancel}
          />
        ) : (
          <StudentList
            students={students}
            loading={loading}
            error={error}
            onEdit={handleEdit}
            onDelete={handleDelete}
            onRefresh={loadStudents}
          />
        )}
      </div>
    </div>
  )
}

export default App

