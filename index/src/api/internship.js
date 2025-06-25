import request from './request'

// 新增实习就业
export function addInternship(data) {
    return request.post('/internship-employment/add', data)
  }
  
  // 更新实习就业
  export function updateInternship(data) {
    return request.put('/internship-employment/update', data)
  }
  
  // 删除实习就业
  export function deleteInternship(id) {
    return request.delete(`/internship-employment/delete/${id}`)
  }
  
  // 根据ID获取
  export function getInternshipById(id) {
    return request.get(`/internship-employment/get/${id}`)
  }
  
  // 根据用户ID获取
  export function getInternshipsByUserId(userId) {
    return request.get(`/internship-employment/user/${userId}`)
  }
  
  // 获取全部
  export function getAllInternships() {
    return request.get('/internship-employment/all')
  }
