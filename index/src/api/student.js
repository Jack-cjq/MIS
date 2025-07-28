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