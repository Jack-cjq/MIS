import request from './request'

// 党建管理模块 - 党员相关API
export const partyMemberApi = {
  // 学生端API
  // 添加党员信息
  addPartyMember: (data) => request.post('/party-member/add', data),
  
  // 更新党员信息
  updatePartyMember: (data) => request.put('/party-member/update', data),
  
  // 删除党员信息
  deletePartyMember: (id) => request.delete(`/party-member/delete/${id}`),
  
  // 获取学生党员信息
  getStudentPartyMembers: (studentId) => request.get(`/party-member/student/${studentId}`),
  
  // 根据ID获取党员信息
  getPartyMemberById: (id) => request.get(`/party-member/${id}`),
  
  // 管理员端API
  // 获取所有党员列表
  getAllPartyMembers: () => request.get('/party-member/list'),
  
  // 获取待审核党员列表
  getPendingPartyMembers: () => request.get('/party-member/pending'),
  
  // 审核党员信息
  auditPartyMember: (id, auditData) => request.post(`/party-member/audit/${id}`, auditData),
  
  // 搜索党员
  searchPartyMembers: (keyword) => request.get(`/party-member/search?keyword=${keyword}`),
  
  // 根据党支部获取党员列表
  getPartyMembersByBranch: (branch) => request.get(`/party-member/branch/${branch}`),
  
  // 根据党员状态获取列表
  getPartyMembersByStatus: (isRegular) => request.get(`/party-member/status/${isRegular}`)
}

// 思想汇报相关API
export const thoughtReportApi = {
  // 添加思想汇报
  addThoughtReport: (data) => request.post('/thought-report/add', data),
  
  // 更新思想汇报
  updateThoughtReport: (data) => request.put('/thought-report/update', data),
  
  // 删除思想汇报
  deleteThoughtReport: (id) => request.delete(`/thought-report/delete/${id}`),
  
  // 获取学生思想汇报
  getStudentThoughtReports: (studentId) => request.get(`/thought-report/student/${studentId}`),
  
  // 根据ID获取思想汇报
  getThoughtReportById: (id) => request.get(`/thought-report/${id}`),
  
  // 管理员端API
  // 获取所有思想汇报列表
  getAllThoughtReports: () => request.get('/thought-report/list'),
  
  // 获取待审核思想汇报列表
  getPendingThoughtReports: () => request.get('/thought-report/pending'),
  
  // 审核思想汇报
  auditThoughtReport: (id, auditData) => request.post(`/thought-report/audit/${id}`, auditData),
  
  // 搜索思想汇报
  searchThoughtReports: (keyword) => request.get(`/thought-report/search?keyword=${keyword}`),
  
  // 根据汇报类型获取思想汇报列表
  getThoughtReportsByType: (reportType) => request.get(`/thought-report/type/${reportType}`),
  
  // 根据状态获取思想汇报列表
  getThoughtReportsByStatus: (status) => request.get(`/thought-report/status/${status}`),
  
  // 根据审核人获取思想汇报列表
  getThoughtReportsByReviewer: (reviewerId) => request.get(`/thought-report/reviewer/${reviewerId}`)
}

// 党课相关API
export const partyCourseApi = {
  // 添加党课记录
  addPartyCourse: (data) => request.post('/party-course/add', data),
  
  // 更新党课记录
  updatePartyCourse: (data) => request.put('/party-course/update', data),
  
  // 删除党课记录
  deletePartyCourse: (id) => request.delete(`/party-course/delete/${id}`),
  
  // 获取学生党课记录
  getStudentPartyCourses: (studentId) => request.get(`/party-course/student/${studentId}`),
  
  // 根据ID获取党课记录
  getPartyCourseById: (id) => request.get(`/party-course/${id}`),
  
  // 管理员端API
  // 获取所有党课列表
  getAllPartyCourses: () => request.get('/party-course/list'),
  
  // 获取待审核党课列表
  getPendingPartyCourses: () => request.get('/party-course/pending'),
  
  // 审核党课记录
  auditPartyCourse: (id, auditData) => request.post(`/party-course/audit/${id}`, auditData),
  
  // 搜索党课
  searchPartyCourses: (keyword) => request.get(`/party-course/search?keyword=${keyword}`),
  
  // 根据课程类型获取党课列表
  getPartyCoursesByType: (courseType) => request.get(`/party-course/type/${courseType}`),
  
  // 根据课程状态获取党课列表
  getPartyCoursesByStatus: (status) => request.get(`/party-course/status/${status}`),
  
  // 根据授课教师获取党课列表
  getPartyCoursesByInstructor: (instructor) => request.get(`/party-course/instructor/${instructor}`)
}

// 志愿服务相关API
export const volunteerServiceApi = {
  // 添加志愿服务记录
  addVolunteerService: (data) => request.post('/volunteer-service/add', data),
  
  // 更新志愿服务记录
  updateVolunteerService: (data) => request.put('/volunteer-service/update', data),
  
  // 删除志愿服务记录
  deleteVolunteerService: (id) => request.delete(`/volunteer-service/delete/${id}`),
  
  // 获取学生志愿服务记录
  getStudentVolunteerServices: (studentId) => request.get(`/volunteer-service/student/${studentId}`),
  
  // 根据ID获取志愿服务记录
  getVolunteerServiceById: (id) => request.get(`/volunteer-service/${id}`),
  
  // 管理员端API
  // 获取所有志愿服务列表
  getAllVolunteerServices: () => request.get('/volunteer-service/list'),
  
  // 获取待审核志愿服务列表
  getPendingVolunteerServices: () => request.get('/volunteer-service/pending'),
  
  // 审核志愿服务记录
  auditVolunteerService: (id, auditData) => request.post(`/volunteer-service/audit/${id}`, auditData),
  
  // 搜索志愿服务
  searchVolunteerServices: (keyword) => request.get(`/volunteer-service/search?keyword=${keyword}`),
  
  // 根据服务类型获取志愿服务列表
  getVolunteerServicesByType: (serviceType) => request.get(`/volunteer-service/type/${serviceType}`),
  
  // 根据状态获取志愿服务列表
  getVolunteerServicesByStatus: (status) => request.get(`/volunteer-service/status/${status}`),
  
  // 根据组织获取志愿服务列表
  getVolunteerServicesByOrganization: (organization) => request.get(`/volunteer-service/organization/${organization}`)
}