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

// 获取所有竞赛数据（管理员）
export const getAllCompetitions = () => {
  return request({
    url: '/admin/competitions',
    method: 'get'
  })
}

// 获取所有论文数据（管理员）
export const getAllPapers = () => {
  return request({
    url: '/admin/papers',
    method: 'get'
  })
}

// 获取所有专利数据（管理员）
export const getAllPatents = () => {
  return request({
    url: '/admin/patents',
    method: 'get'
  })
}

// 获取所有项目数据（管理员）
export const getAllProjects = () => {
  return request({
    url: '/admin/projects',
    method: 'get'
  })
}

// 审核竞赛
export const auditCompetition = (data) => {
  return request({
    url: `/admin/competitions/${data.id}/audit`,
    method: 'put',
    data: {
      auditStatus: data.auditStatus,
      auditComment: data.auditComment
    }
  })
}

// 审核论文
export const auditPaper = (data) => {
  return request({
    url: `/admin/papers/${data.id}/audit`,
    method: 'put',
    data: {
      auditStatus: data.auditStatus,
      auditComment: data.auditComment
    }
  })
}

// 审核专利
export const auditPatent = (data) => {
  return request({
    url: `/admin/patents/${data.id}/audit`,
    method: 'put',
    data: {
      auditStatus: data.auditStatus,
      auditComment: data.auditComment
    }
  })
}

// 审核项目
export const auditProject = (data) => {
  return request({
    url: `/admin/projects/${data.id}/audit`,
    method: 'put',
    data: {
      auditStatus: data.auditStatus,
      auditComment: data.auditComment
    }
  })
}

// 获取待审核统计
export const getAuditStats = () => {
  return request({
    url: '/admin/audit/stats',
    method: 'get'
  })
}