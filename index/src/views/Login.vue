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
        <el-form-item prop="account">
          <el-input
            v-model="loginForm.account"
            :placeholder="isAdminLogin ? '请输入用户名' : '请输入学号/身份证号'"
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
          <el-checkbox v-model="isAdminLogin" @change="handleLoginTypeChange">
            管理员登录
          </el-checkbox>
        </el-form-item>
        
        <el-form-item>
          <el-button
            type="primary"
            size="large"
            style="width: 100%"
            :loading="loading"
            @click="handleLogin"
          >
            {{ isAdminLogin ? '管理员登录' : '学生登录' }}
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="login-footer">
        <p v-if="!isAdminLogin">学生默认账号：admin / 密码：123456</p>
        <p v-else>管理员默认账号：admin / 密码：admin123</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { studentLogin } from '@/api/student'
import { adminLogin } from '@/api/admin'

const router = useRouter()
const store = useStore()
const loginFormRef = ref()
const loading = ref(false)
const isAdminLogin = ref(false)

const loginForm = reactive({
  account: '',
  password: ''
})

const loginRules = {
  account: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLoginTypeChange = () => {
  // 清空表单
  loginForm.account = ''
  loginForm.password = ''
  if (loginFormRef.value) {
    loginFormRef.value.clearValidate()
  }
}

const handleLogin = async () => {
  try {
    await loginFormRef.value.validate()
    loading.value = true
    
    let res
    if (isAdminLogin.value) {
      // 管理员登录
      res = await adminLogin(loginForm.account, loginForm.password)
    } else {
      // 学生登录
      res = await studentLogin(loginForm.account, loginForm.password)
    }
    
    if (res.code === 200) {
      // 设置用户信息和角色
      const userData = {
        ...res.data,
        userType: isAdminLogin.value ? 'admin' : 'student'
      }
      store.commit('setUser', userData)
      store.dispatch('setToken', 'mock-token') // 这里可替换为后端返回的token
      ElMessage.success(res.msg)
      
      // 根据用户类型跳转到不同页面
      if (isAdminLogin.value) {
        router.push('/admin') // 管理员页面
      } else {
        router.push('/') // 学生页面
      }
    } else {
      ElMessage.error(res.msg)
    }
  } catch (error) {
    ElMessage.error('登录失败')
  } finally {
    loading.value = false
  }
}

// 定时检查token是否过期，过期则自动登出并跳转
let timer = null
const checkTokenAndLogout = () => {
  store.dispatch('checkToken')
  if (!store.state.user) {
    ElMessage.warning('登录已过期，请重新登录')
    router.push('/login')
  }
}
onMounted(() => {
  timer = setInterval(checkTokenAndLogout, 5000) // 每5秒检查一次
})
onUnmounted(() => {
  if (timer) clearInterval(timer)
})
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