/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-header">
        <h2>学生管理系统</h2>
        <p>欢迎登录</p>
      </div>
      
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
        label-width="0"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            size="large"
          />
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            size="large"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading" style="width: 100%">
            {{ isAdminLogin ? '管理员登录' : '学生登录' }}
          </el-button>
        </el-form-item>
        
        <el-form-item>
          <el-button type="info" @click="handleReset" style="width: 100%">
            重置系统状态
          </el-button>
        </el-form-item>
      </el-form>
      
             <div class="login-footer">
         <p v-if="!isAdminLogin">学生默认账号：10240001 / 密码：123456</p>
         <p v-else>管理员默认账号：admin / 密码：admin123</p>
       </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { adminLogin } from '@/api/admin'
import { studentLogin } from '@/api/student'

const router = useRouter()
const store = useStore()
const loginFormRef = ref()
const loading = ref(false)
const isAdminLogin = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  try {
    await loginFormRef.value.validate()
    loading.value = true
    
    let res
    if (isAdminLogin.value) {
      // 管理员登录
      res = await adminLogin(loginForm.username, loginForm.password)
    } else {
      // 学生登录
      res = await studentLogin(loginForm.username, loginForm.password)
    }
    
    if (res.code === 200) {
      // 设置用户信息和token
      const userData = {
        id: res.data.userId,
        studentId: res.data.studentId || res.data.adminId,
        name: res.data.name || res.data.realName,
        userType: res.data.userType
      }
      
      // 存储token和用户信息
      store.commit('setUser', userData)
      store.dispatch('setToken', res.data.token)
      
      ElMessage.success(res.msg)
      
      // 根据用户类型跳转到不同页面
      if (isAdminLogin.value) {
        router.push('/admin/dashboard') // 管理员页面
      } else {
        router.push('/home') // 学生页面
      }
    }
    loading.value = false
  } catch (error) {
    console.error('登录失败:', error)
    ElMessage.error('登录失败，请检查网络连接')
  } finally {
    loading.value = false
  }
}

const handleReset = () => {
  // 清除所有存储
  localStorage.clear()
  // 清除store状态
  store.commit('logout')
  ElMessage.success('系统状态已重置，请重新登录')
  // 刷新页面以确保完全重置
  window.location.reload()
}

// 移除登录页面的token检查，因为登录页面不需要检查token
// 定时检查token是否过期，过期则自动登出并跳转
// 这个功能应该在其他页面组件中实现，而不是在登录页面
</script>

<style scoped>
.login-container {
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-box {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header h2 {
  color: #303133;
  margin-bottom: 8px;
}

.login-header p {
  color: #909399;
  font-size: 14px;
}

.login-form {
  margin-bottom: 20px;
}

.login-footer {
  text-align: center;
}

.login-footer p {
  color: #909399;
  font-size: 12px;
}
</style> 