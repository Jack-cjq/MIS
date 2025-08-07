import request from './request'

// 获取当前用户个人信息
export function getCurrentUserProfile(studentId) {
  return request({
    url: '/student/profile',
    method: 'get',
    params: { studentId }
  })
}

// 更新个人信息
export function updateUserProfile(profileData) {
  return request({
    url: '/student/profile/update',
    method: 'put',
    data: profileData
  })
}

// 获取学习记录
export function getStudyRecords() {
  return request({
    url: '/student/study-records',
    method: 'get'
  })
}
