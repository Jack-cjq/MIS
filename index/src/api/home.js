import request from './request'

// 获取首页数据
export function getHomeData() {
  return request({
    url: '/student/statistics',
    method: 'get'
  })
}

// 获取统计数据
export function getStatistics() {
  return request({
    url: '/student/statistics',
    method: 'get'
  })
}

// 获取最新动态
export function getLatestNews() {
  return request({
    url: '/student/news',
    method: 'get'
  })
}

// 获取图表数据
export function getChartData() {
  return request({
    url: '/student/chart',
    method: 'get'
  })
}

// 获取学生个人统计数据
export function getPersonalStats(studentId) {
  return request({
    url: `/student/personal-stats/${studentId}`,
    method: 'get'
  })
}
