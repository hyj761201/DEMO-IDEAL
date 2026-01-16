import React from 'react'
import './StudentList.css'

function StudentList({ students, loading, error, onEdit, onDelete, onRefresh }) {
  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
        <p>加载中...</p>
      </div>
    )
  }

  if (error) {
    return (
      <div className="error-state">
        <div className="error-icon">⚠️</div>
        <h3>加载失败</h3>
        <p className="error-message">加载失败: {error}</p>
        <p className="error-hint">请确保后端服务已启动在 http://localhost:8080</p>
        <button className="btn btn-secondary" onClick={onRefresh}>
          🔄 刷新
        </button>
      </div>
    )
  }

  if (students.length === 0) {
    return (
      <div className="empty-state">
        <div className="empty-icon">📚</div>
        <h3>暂无学生信息</h3>
        <p>点击"添加学生"按钮开始添加学生信息</p>
      </div>
    )
  }

  return (
    <div className="student-list-container">
      <div className="list-header">
        <h2>学生列表 ({students.length})</h2>
        <button className="btn btn-secondary" onClick={onRefresh}>
          🔄 刷新
        </button>
      </div>
      <div className="table-wrapper">
        <table className="student-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>姓名</th>
              <th>邮箱</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            {students.map((student) => (
              <tr key={student.id}>
                <td>{student.id}</td>
                <td>{student.name}</td>
                <td>{student.email}</td>
                <td>
                  <div className="action-buttons">
                    <button
                      className="btn btn-edit btn-sm"
                      onClick={() => onEdit(student)}
                    >
                      ✏️ 编辑
                    </button>
                    <button
                      className="btn btn-danger btn-sm"
                      onClick={() => onDelete(student.id)}
                    >
                      🗑️ 删除
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default StudentList

