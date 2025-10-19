import request from './request'

// ==================== 学生端 API ====================

// 获取学生的竞赛数据
export const getStudentCompetitions = (studentId) => {
  return request({
    url: `/competitions/student/${studentId}`,
    method: 'get'
  })
}

// 获取学生的论文数据
export const getStudentPapers = (studentId) => {
  return request({
    url: `/papers/student/${studentId}`,
    method: 'get'
  })
}

// 获取学生的专利数据
export const getStudentPatents = (studentId) => {
  return request({
    url: `/patents/student/${studentId}`,
    method: 'get'
  })
}

// 获取学生的项目数据
export const getStudentProjects = (studentId) => {
  return request({
    url: `/projects/student/${studentId}`,
    method: 'get'
  })
}

// 添加竞赛
export const addCompetition = (data) => {
  return request({
    url: '/competitions',
    method: 'post',
    data
  })
}

// 添加论文
export const addPaper = (data) => {
  return request({
    url: '/papers',
    method: 'post',
    data
  })
}

// 添加专利
export const addPatent = (data) => {
  return request({
    url: '/patents',
    method: 'post',
    data
  })
}

// 添加项目
export const addProject = (data) => {
  return request({
    url: '/projects',
    method: 'post',
    data
  })
}

// 更新竞赛
export const updateCompetition = (id, data) => {
  return request({
    url: `/competitions/${id}`,
    method: 'put',
    data
  })
}

// 更新论文
export const updatePaper = (id, data) => {
  return request({
    url: `/papers/${id}`,
    method: 'put',
    data
  })
}

// 更新专利
export const updatePatent = (id, data) => {
  return request({
    url: `/patents/${id}`,
    method: 'put',
    data
  })
}

// 更新项目
export const updateProject = (id, data) => {
  return request({
    url: `/projects/${id}`,
    method: 'put',
    data
  })
}

// 删除竞赛
export const deleteCompetition = (id) => {
  return request({
    url: `/competitions/${id}`,
    method: 'delete'
  })
}

// 删除论文
export const deletePaper = (id) => {
  return request({
    url: `/papers/${id}`,
    method: 'delete'
  })
}

// 删除专利
export const deletePatent = (id) => {
  return request({
    url: `/patents/${id}`,
    method: 'delete'
  })
}

// 删除项目
export const deleteProject = (id) => {
  return request({
    url: `/projects/${id}`,
    method: 'delete'
  })
}

// ==================== 管理员端 API ====================

// 获取所有竞赛数据（管理员）
export const getAllCompetitions = () => {
  return request({
    url: '/competitions/admin/all',
    method: 'get'
  })
}

// 获取所有论文数据（管理员）
export const getAllPapers = () => {
  return request({
    url: '/papers/admin/all',
    method: 'get'
  })
}

// 获取所有专利数据（管理员）
export const getAllPatents = () => {
  return request({
    url: '/patents/admin/all',
    method: 'get'
  })
}

// 获取所有项目数据（管理员）
export const getAllProjects = () => {
  return request({
    url: '/projects/admin/all',
    method: 'get'
  })
}

// 审核竞赛
export const auditCompetition = (data) => {
  return request({
    url: `/competitions/admin/${data.id}/audit`,
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
    url: `/papers/admin/${data.id}/audit`,
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
    url: `/patents/admin/${data.id}/audit`,
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
    url: `/projects/admin/${data.id}/audit`,
    method: 'put',
    data: {
      auditStatus: data.auditStatus,
      auditComment: data.auditComment
    }
  })
}

// ==================== 竞赛字典管理 API（管理员端）====================

// 获取所有竞赛字典（管理后台，含禁用的）
export const getAllCompetitionDicts = () => {
  return request({
    url: '/competition-dict/admin/all',
    method: 'get'
  })
}

// 添加竞赛字典
export const addCompetitionDict = (data) => {
  return request({
    url: '/competition-dict/admin/add',
    method: 'post',
    data
  })
}

// 更新竞赛字典
export const updateCompetitionDict = (id, data) => {
  return request({
    url: `/competition-dict/admin/update`,
    method: 'put',
    data: { ...data, id }
  })
}

// 删除竞赛字典
export const deleteCompetitionDict = (id) => {
  return request({
    url: `/competition-dict/admin/delete/${id}`,
    method: 'delete'
  })
}

// ==================== 其他 API ====================

// 获取所有激活的竞赛（用于下拉选择）
export const getAllActiveCompetitions = () => {
  return request({
    url: '/competition-dict/active',  
    method: 'get'
  })
}

// 搜索竞赛字典（用于学生填表时的下拉提示）
export const searchCompetitionDict = (keyword) => {
  return request({
    url: '/competition-dict/search',
    method: 'get',
    params: { keyword }
  })
}

// 搜索竞赛
export const searchCompetitions = (params) => {
  return request({
    url: '/competitions/search',
    method: 'get',
    params
  })
}

// 文件上传
export const uploadFile = (formData) => {
  return request({
    url: '/upload',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

