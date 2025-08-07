import request from './request'

// 管理员登录
export function adminLogin(username, password) {
  return request({
    url: '/admin/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

// 获取当前管理员信息
export function getCurrentAdmin() {
  return request({
    url: '/admin/current-user',
    method: 'get'
  })
}

// 刷新管理员token
export function refreshAdminToken() {
  return request({
    url: '/admin/refresh-token',
    method: 'post'
  })
}

// 获取管理员列表
export function getAdminList() {
  return request({
    url: '/admin/list',
    method: 'get'
  })
}

// 创建管理员
export function createAdmin(adminData) {
  return request({
    url: '/admin/create',
    method: 'post',
    data: adminData
  })
}

// 更新管理员
export function updateAdmin(adminData) {
  return request({
    url: '/admin/update',
    method: 'put',
    data: adminData
  })
}

// 删除管理员
export function deleteAdmin(adminId) {
  return request({
    url: `/admin/delete/${adminId}`,
    method: 'delete'
  })
}

// 获取管理员详情
export function getAdminById(id) {
  return request({
    url: `/admin/${id}`,
    method: 'get'
  })
}

// 根据角色获取管理员
export function getAdminsByRole(role) {
  return request({
    url: `/admin/role/${role}`,
    method: 'get'
  })
}

// 更新管理员状态
export function updateAdminStatus(adminId, isActive) {
  return request({
    url: `/admin/status/${adminId}`,
    method: 'put',
    data: { isActive }
  })
}

// 检查权限
export function checkPermission(adminId, permission) {
  return request({
    url: `/admin/permission/${adminId}/${permission}`,
    method: 'get'
  })
} 