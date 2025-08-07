import request from './request'

// 学生登录
export function studentLogin(account, password) {
  return request({
    url: '/student/login',
    method: 'post',
    data: { account, password }
  })
}

// 获取学生列表
export function getStudentList() {
  return request({
    url: '/student/list',
    method: 'get'
  })
}

// 根据ID获取学生
export function getStudentById(id) {
  return request({
    url: `/student/${id}`,
    method: 'get'
  })
}

// 创建学生
export function createStudent(studentData) {
  return request({
    url: '/student/create',
    method: 'post',
    data: studentData
  })
}

// 更新学生
export function updateStudent(studentData) {
  return request({
    url: '/student/update',
    method: 'put',
    data: studentData
  })
}

// 删除学生
export function deleteStudent(id) {
  return request({
    url: `/student/delete/${id}`,
    method: 'delete'
  })
}

// 获取当前用户信息（用于页面刷新后恢复）
export function getCurrentUser() {
  return request({
    url: '/student/current-user',
    method: 'get'
  })
}

// 获取个人信息（自动获取当前用户）
export function getProfile() {
  return request({
    url: '/student/profile',
    method: 'get'
  })
}

// 更新个人信息
export function updateProfile(data) {
  return request({
    url: '/student/profile/update',
    method: 'put',
    data
  })
}

// 获取学习记录
export function getStudyRecords() {
  return request({
    url: '/student/study-records',
    method: 'get'
  })
}

// 刷新token
export function refreshToken() {
  return request({
    url: '/student/refresh-token',
    method: 'post'
  })
} 