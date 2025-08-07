import request from './request'

// 学科竞赛相关接口
export function addCompetition(data) {
    return request({
        url: '/competition/add',
        method: 'post',
        data
    })
}

export function updateCompetition(data) {
    return request({
        url: '/competition/update',
        method: 'put',
        data
    })
}

export function deleteCompetition(id) {
    return request({
        url: `/competition/delete/${id}`,
        method: 'delete'
    })
}

// 获取我的学科竞赛记录（自动获取当前用户）
export function getMyCompetitions() {
    return request({
        url: '/competition/my-competitions',
        method: 'get'
    })
}

// 根据学生ID获取学科竞赛记录（保留用于管理员功能）
export function getStudentCompetitions(studentId) {
    return request({
        url: `/competition/student/${studentId}`,
        method: 'get'
    })
}

export function getAllCompetitions() {
    return request({
        url: '/competition/all',
        method: 'get'
    })
}

// 发表文章相关接口
export function addPaper(data) {
    return request({
        url: '/paper/add',
        method: 'post',
        data
    })
}

export function updatePaper(data) {
    return request({
        url: '/paper/update',
        method: 'put',
        data
    })
}

export function deletePaper(id) {
    return request({
        url: `/paper/delete/${id}`,
        method: 'delete'
    })
}

// 获取我的发表文章记录（自动获取当前用户）
export function getMyPapers() {
    return request({
        url: '/paper/my-papers',
        method: 'get'
    })
}

// 根据学生ID获取发表文章记录（保留用于管理员功能）
export function getStudentPapers(studentId) {
    return request({
        url: `/paper/student/${studentId}`,
        method: 'get'
    })
}

// 专利相关接口
export function addPatent(data) {
    return request({
        url: '/patent/add',
        method: 'post',
        data
    })
}

export function updatePatent(data) {
    return request({
        url: '/patent/update',
        method: 'put',
        data
    })
}

export function deletePatent(id) {
    return request({
        url: `/patent/delete/${id}`,
        method: 'delete'
    })
}

// 获取我的专利记录（自动获取当前用户）
export function getMyPatents() {
    return request({
        url: '/patent/my-patents',
        method: 'get'
    })
}

// 根据学生ID获取专利记录（保留用于管理员功能）
export function getStudentPatents(studentId) {
    return request({
        url: `/patent/student/${studentId}`,
        method: 'get'
    })
}

// 项目相关接口
export function addProject(data) {
    return request({
        url: '/project/add',
        method: 'post',
        data
    })
}

export function updateProject(data) {
    return request({
        url: '/project/update',
        method: 'put',
        data
    })
}

export function deleteProject(id) {
    return request({
        url: `/project/delete/${id}`,
        method: 'delete'
    })
}

// 获取我的项目记录（自动获取当前用户）
export function getMyProjects() {
    return request({
        url: '/project/my-projects',
        method: 'get'
    })
}

// 根据学生ID获取项目记录（保留用于管理员功能）
export function getStudentProjects(studentId) {
    return request({
        url: `/project/student/${studentId}`,
        method: 'get'
    })
}

// 竞赛字典相关接口
export function searchCompetitions(keyword) {
    return request({
        url: `/competition-dict/search`,
        method: 'get',
        params: { keyword }
    })
}

export function getAllActiveCompetitions() {
    return request({
        url: '/competition-dict/active',
        method: 'get'
    })
}

export function getCompetitionByName(name) {
    return request({
        url: `/competition-dict/name/${encodeURIComponent(name)}`,
        method: 'get'
    })
}

// 竞赛报名相关接口
export function registerForCompetition(data) {
    return request({
        url: '/competition/register',
        method: 'post',
        data
    })
}