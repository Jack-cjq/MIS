import request from './request'

/**
 * 实习就业相关API接口
 * 提供实习就业模块的所有前端接口调用
 */

// 基础CRUD操作
// 获取所有实习就业记录
export function getInternshipList() {
  return request({
    url: '/internship-employment/all',
    method: 'get'
  })
}

// 获取我的实习就业记录（自动获取当前用户）
export function getMyInternshipRecords() {
  return request({
    url: '/internship-employment/my-records',
    method: 'get'
  })
}

// 根据用户ID获取实习就业记录（保留用于管理员功能）
export function getInternshipByUserId(userId) {
  return request({
    url: `/internship-employment/user/${userId}`,
    method: 'get'
  })
}

// 根据学生ID获取实习就业记录
export function getInternshipByStudentId(studentId) {
  return request({
    url: `/internship-employment/student/${studentId}`,
    method: 'get'
  })
}

// 根据ID获取实习就业记录
export function getInternshipById(id) {
  return request({
    url: `/internship-employment/get/${id}`,
    method: 'get'
  })
}

// 添加实习就业记录
export function addInternship(data) {
  return request({
    url: '/internship-employment/add',
    method: 'post',
    data
  })
}

// 更新实习就业记录
export function updateInternship(data) {
  return request({
    url: '/internship-employment/update',
    method: 'put',
    data
  })
}

// 删除实习就业记录
export function deleteInternship(id) {
  return request({
    url: `/internship-employment/delete/${id}`,
    method: 'delete'
  })
}

// 基础查询接口
// 根据状态获取记录
export function getInternshipByStatus(status) {
  return request({
    url: `/internship-employment/status/${status}`,
    method: 'get'
  })
}

// 根据审批状态获取记录
export function getInternshipByApprovalStatus(approvalStatus) {
  return request({
    url: `/internship-employment/approval-status/${approvalStatus}`,
    method: 'get'
  })
}

// 根据类型获取记录
export function getInternshipByType(type) {
  return request({
    url: `/internship-employment/type/${type}`,
    method: 'get'
  })
}

// 根据公司类型获取记录
export function getInternshipByCompanyType(companyType) {
  return request({
    url: `/internship-employment/company-type/${companyType}`,
    method: 'get'
  })
}

// 根据行业获取记录
export function getInternshipByIndustry(industry) {
  return request({
    url: `/internship-employment/industry/${industry}`,
    method: 'get'
  })
}

// 根据工作类型获取记录
export function getInternshipByWorkType(workType) {
  return request({
    url: `/internship-employment/work-type/${workType}`,
    method: 'get'
  })
}

// 根据学生ID和状态获取记录
export function getInternshipByStudentIdAndStatus(studentId, status) {
  return request({
    url: '/internship-employment/student-status',
    method: 'get',
    params: { studentId, status }
  })
}

// 根据学生ID和审批状态获取记录
export function getInternshipByStudentIdAndApprovalStatus(studentId, approvalStatus) {
  return request({
    url: '/internship-employment/student-approval',
    method: 'get',
    params: { studentId, approvalStatus }
  })
}

// 审批相关接口
// 审批通过
export function approveInternship(id, approvalRemark, approvalBy) {
  return request({
    url: `/internship-employment/approve/${id}`,
    method: 'post',
    data: { approvalRemark, approvalBy }
  })
}

// 审批拒绝
export function rejectInternship(id, approvalRemark, approvalBy) {
  return request({
    url: `/internship-employment/reject/${id}`,
    method: 'post',
    data: { approvalRemark, approvalBy }
  })
}

// 基础统计接口
// 获取统计信息
export function getInternshipStatistics() {
  return request({
    url: '/internship-employment/statistics',
    method: 'get'
  })
}

// 获取状态统计
export function getStatusStatistics() {
  return request({
    url: '/internship-employment/statistics/status',
    method: 'get'
  })
}

// 获取行业统计
export function getIndustryStatistics() {
  return request({
    url: '/internship-employment/statistics/industry',
    method: 'get'
  })
}

// 获取公司类型统计
export function getCompanyTypeStatistics() {
  return request({
    url: '/internship-employment/statistics/company-type',
    method: 'get'
  })
}

// 获取审批状态统计
export function getApprovalStatusStatistics() {
  return request({
    url: '/internship-employment/statistics/approval-status',
    method: 'get'
  })
}

// 新增统计接口
// 获取技术栈统计
export function getTechnologyStackStatistics() {
  return request({
    url: '/internship-employment/statistics/technology-stack',
    method: 'get'
  })
}

// 获取薪资范围统计
export function getSalaryRangeStatistics() {
  return request({
    url: '/internship-employment/statistics/salary-range',
    method: 'get'
  })
}

// 获取满意度统计
export function getSatisfactionStatistics() {
  return request({
    url: '/internship-employment/statistics/satisfaction',
    method: 'get'
  })
}

// 获取就业趋势分析
export function getEmploymentTrendAnalysis() {
  return request({
    url: '/internship-employment/statistics/employment-trend',
    method: 'get'
  })
}

// 获取热门技术栈
export function getTopTechnologyStacks(limit = 10) {
  return request({
    url: '/internship-employment/statistics/top-technology-stacks',
    method: 'get',
    params: { limit }
  })
}

// 获取热门行业
export function getTopIndustries(limit = 10) {
  return request({
    url: '/internship-employment/statistics/top-industries',
    method: 'get',
    params: { limit }
  })
}

// 搜索相关接口
// 搜索记录
export function searchInternship(keyword) {
  return request({
    url: '/internship-employment/search',
    method: 'get',
    params: { keyword }
  })
}

// 根据学生姓名搜索
export function searchByStudentName(studentName) {
  return request({
    url: '/internship-employment/search/student',
    method: 'get',
    params: { studentName }
  })
}

// 根据公司名称搜索
export function searchByCompany(company) {
  return request({
    url: '/internship-employment/search/company',
    method: 'get',
    params: { company }
  })
}

// 根据职位搜索
export function searchByPosition(position) {
  return request({
    url: '/internship-employment/search/position',
    method: 'get',
    params: { position }
  })
}

// 高级查询接口
// 根据技术栈搜索
export function searchByTechnologyStack(technologyStack) {
  return request({
    url: `/internship-employment/search/technology-stack`,
    method: 'get',
    params: { technologyStack }
  })
}

// 根据薪资范围搜索
export function searchBySalaryRange(salaryRange) {
  return request({
    url: `/internship-employment/search/salary-range`,
    method: 'get',
    params: { salaryRange }
  })
}

// 根据项目类型搜索
export function searchByProjectType(projectType) {
  return request({
    url: `/internship-employment/search/project-type`,
    method: 'get',
    params: { projectType }
  })
}

// 数据导出接口
// 导出实习就业数据
export function exportInternshipData(params) {
  return request({
    url: '/internship-employment/export',
    method: 'get',
    params,
    responseType: 'blob'
  })
}

// 导出统计报告
export function exportStatisticsReport(params) {
  return request({
    url: '/internship-employment/export/statistics',
    method: 'get',
    params,
    responseType: 'blob'
  })
}

// 数据导入接口
// 批量导入实习就业数据
export function importInternshipData(data) {
  return request({
    url: '/internship-employment/import',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 数据验证接口
// 验证实习就业数据
export function validateInternshipData(data) {
  return request({
    url: '/internship-employment/validate',
    method: 'post',
    data
  })
}
