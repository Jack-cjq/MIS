// 写api接口时取消该行注释
// import request from './request'

// 校友管理模块
import request from "@/api/request";

export function getAlumniList(searchValue) {
    return request.post(
        '/alumni/findAllAlumni',
        {searchValue: searchValue},
        {headers: {'Content-Type': 'application/x-www-form-urlencoded',}}
    )
}

export function getTotalCount(searchValue) {
    return request.post(
        '/alumni/getTotalCount',
        {searchValue: searchValue},
        {headers: {'Content-Type': 'application/x-www-form-urlencoded',}}
    )
}


export function getAlumniActivities() {
    return []
}


export function joinAlumniActivity() {

}
