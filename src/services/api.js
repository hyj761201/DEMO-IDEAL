import axios from 'axios'

// 在开发环境中使用代理路径，生产环境使用完整URL
const API_BASE_URL = import.meta.env.DEV ? '/api' : 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

export const getAllStudents = async () => {
  try {
    const response = await api.get('/student/all')
    if (response.data.success) {
      return response.data.data
    }
    throw new Error(response.data.errorMsg || '获取学生列表失败')
  } catch (error) {
    // 处理网络错误
    if (error.code === 'ERR_NETWORK' || error.message.includes('Failed to fetch')) {
      throw new Error('Failed to fetch')
    }
    throw error
  }
}

export const getStudentById = async (id) => {
  const response = await api.get(`/student/${id}`)
  if (response.data.success) {
    return response.data.data
  }
  throw new Error(response.data.errorMsg || '获取学生信息失败')
}

export const createStudent = async (studentData) => {
  const response = await api.post('/student', studentData)
  if (response.data.success) {
    return response.data.data
  }
  throw new Error(response.data.errorMsg || '创建学生失败')
}

export const updateStudent = async (id, studentData) => {
  const params = new URLSearchParams()
  if (studentData.name) {
    params.append('name', studentData.name)
  }
  if (studentData.email) {
    params.append('email', studentData.email)
  }
  
  const response = await api.put(`/student/${id}?${params.toString()}`)
  if (response.data.success) {
    return response.data.data
  }
  throw new Error(response.data.errorMsg || '更新学生失败')
}

export const deleteStudent = async (id) => {
  await api.delete(`/student/${id}`)
}

