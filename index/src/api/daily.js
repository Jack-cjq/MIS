
import request from './request'

// 三个模块对应的后端路径
const URL = {
    academic: 'academic-events',
    daily: 'daily-activities',
    honor: 'honors'
}

// 工具：把 Date 转成 'yyyy-MM-dd'
function d2str(d) {
    if (!d) return ''
    const date = (d instanceof Date) ? d : new Date(d)
    const y = date.getFullYear()
    const m = String(date.getMonth() + 1).padStart(2, '0')
    const day = String(date.getDate()).padStart(2, '0')
    return `${y}-${m}-${day}`
}

// 工具：不同模块的 payload 规范化（后端是 LocalDate / List<String>）
function normalize(moduleKey, form) {
    const p = { ...form }
    if (p.startDate) p.startDate = d2str(p.startDate)
    if (p.endDate) p.endDate = d2str(p.endDate)
    if (p.awardDate) p.awardDate = d2str(p.awardDate)
    if (moduleKey === 'honor' && typeof p.tags === 'string') {
        p.tags = p.tags.split(',').map(s => s.trim()).filter(Boolean)
    }
    return p
}

// 列表（支持搜索/状态/分页）
export function listItems(moduleKey, { studentId, keyword, status, page = 1, size = 10 } = {}) {
    const url = `/api/${URL[moduleKey]}`
    const params = {
        // honor 用 userId，另外两个用 studentId
        ...(moduleKey === 'honor' ? { userId: studentId } : { studentId }),
        keyword,
        status,
        page,
        size
    }
    return request.get(url, { params }) // -> { code, message, data: {records,total,page,size} }
}

// 新增
export function createItem(moduleKey, form) {
    const url = `/api/${URL[moduleKey]}`
    return request.post(url, normalize(moduleKey, form)) // -> {code, message, data}
}

// 详情
export function getDetail(moduleKey, id) {
    const url = `/api/${URL[moduleKey]}/${id}`
    return request.get(url) // -> {code, message, data}
}

// 统计卡片
export function getStudentStats(studentId) {
    return request.get('/api/student-stats', { params: { studentId } }) // -> {data:{...}}
}
