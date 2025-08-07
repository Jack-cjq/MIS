// 写api接口时取消该行注释
// import request from './request'

// 校友管理模块
import request from "@/api/request";

export function searchAlumniList(searchValue, searchYear, currentPage, pageSize) {
    return request.post(
        '/alumni/searchAlumniList',
        {searchValue: searchValue, searchYear: searchYear, currentPage: currentPage, pageSize: pageSize},
        {headers: {'Content-Type': 'application/x-www-form-urlencoded',}}
    )
}

export function getTotalCount(searchValue, searchYear) {
    return request.post(
        '/alumni/getTotalCount',
        {searchValue: searchValue, searchYear: searchYear},
        {headers: {'Content-Type': 'application/x-www-form-urlencoded',}}
    )
}

export function updateAlumni(data) {
    return request.post('/alumni/updateAlumni', data)
}

export function insertAlumni(data) {
    return request.post('/alumni/insertAlumni', data)
}

// 获取我的校友信息（自动获取当前用户）
export function getMyAlumniInfo() {
    return request.post('/alumni/my-alumni-info')
}

// 根据学生ID获取校友信息（保留用于管理员功能）
export function findAlumniByStudentId(studentId) {
    return request.post(
        '/alumni/findAlumniByStudentId',
        {studentId: studentId},
        {headers: {'Content-Type': 'application/x-www-form-urlencoded',}}
    )
}