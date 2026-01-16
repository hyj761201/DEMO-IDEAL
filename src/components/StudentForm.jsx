import React, { useState, useEffect } from 'react'
import './StudentForm.css'

function StudentForm({ student, onSubmit, onCancel }) {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
  })
  const [errors, setErrors] = useState({})

  useEffect(() => {
    if (student) {
      setFormData({
        name: student.name || '',
        email: student.email || '',
      })
    }
  }, [student])

  const validate = () => {
    const newErrors = {}

    if (!formData.name.trim()) {
      newErrors.name = '姓名不能为空'
    }

    if (!formData.email.trim()) {
      newErrors.email = '邮箱不能为空'
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = '请输入有效的邮箱地址'
    }

    setErrors(newErrors)
    return Object.keys(newErrors).length === 0
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    if (validate()) {
      onSubmit(formData)
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }))
    // 清除对应字段的错误
    if (errors[name]) {
      setErrors((prev) => ({
        ...prev,
        [name]: '',
      }))
    }
  }

  return (
    <div className="form-container">
      <div className="form-header">
        <h2>{student ? '编辑学生信息' : '添加新学生'}</h2>
      </div>
      <form onSubmit={handleSubmit} className="student-form">
        <div className="form-group">
          <label htmlFor="name">
            姓名 <span className="required">*</span>
          </label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            className={errors.name ? 'input-error' : ''}
            placeholder="请输入学生姓名"
          />
          {errors.name && <span className="error-message">{errors.name}</span>}
        </div>

        <div className="form-group">
          <label htmlFor="email">
            邮箱 <span className="required">*</span>
          </label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            className={errors.email ? 'input-error' : ''}
            placeholder="请输入邮箱地址"
          />
          {errors.email && <span className="error-message">{errors.email}</span>}
        </div>

        <div className="form-actions">
          <button type="submit" className="btn btn-success">
            {student ? '✓ 更新' : '✓ 添加'}
          </button>
          <button type="button" className="btn btn-secondary" onClick={onCancel}>
            ✕ 取消
          </button>
        </div>
      </form>
    </div>
  )
}

export default StudentForm

