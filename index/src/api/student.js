import request from './request'

export function studentLogin(account, password) {
  return request.post('/student/login', { account, password })
} 