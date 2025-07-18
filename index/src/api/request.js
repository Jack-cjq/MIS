import axios from 'axios'
import { ElMessage } from 'element-plus'

// 创建axios实例
const request = axios.create({
    baseURL: 'http://localhost:1010/SCSE@hbut/msi',
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json'
    }
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        // 可以在这里添加token等认证信息
        const token = localStorage.getItem('token')
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
    error => {
        console.error('请求错误:', error)
        return Promise.reject(error)
    }
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        // 只返回后端data，业务判断交给页面
        return response.data
    },
    error => {
        console.error('响应错误:', error)
        if (error.response) {
            const { status, data } = error.response
            switch (status) {
                case 401:
                    ElMessage.error('未授权，请重新登录')
                    break
                case 403:
                    ElMessage.error('拒绝访问')
                    break
                case 404:
                    ElMessage.error('请求的资源不存在')
                    break
                case 500:
                    ElMessage.error('服务器内部错误')
                    break
                default:
                    ElMessage.error(data?.message || '网络错误')
            }
        } else if (error.request) {
            ElMessage.error('网络连接失败，请检查网络')
        } else {
            ElMessage.error('请求配置错误')
        }
        return Promise.reject(error)
    }
)

export default request