<template>
  <div class="admin-profile">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>个人信息</span>
        </div>
      </template>
      
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="120px"
        class="profile-form"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="管理员ID" prop="adminId">
              <el-input v-model="form.adminId" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="真实姓名" prop="realName">
              <el-input v-model="form.realName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色" prop="role">
              <el-select v-model="form.role" disabled>
                <el-option label="超级管理员" value="super_admin" />
                <el-option label="普通管理员" value="admin" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="所属部门" prop="department">
              <el-input v-model="form.department" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="职位" prop="position">
              <el-input v-model="form.position" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入备注信息"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            保存修改
          </el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { updateAdmin } from '@/api/admin'

const store = useStore()
const formRef = ref()
const loading = ref(false)

// 表单数据
const form = reactive({
  id: '',
  adminId: '',
  username: '',
  realName: '',
  email: '',
  phone: '',
  role: '',
  department: '',
  position: '',
  remark: ''
})

// 表单验证规则
const rules = {
  realName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
  ]
}

// 加载用户信息
const loadUserInfo = () => {
  const user = store.state.user
  if (user) {
    Object.assign(form, {
      id: user.id,
      adminId: user.adminId,
      username: user.username,
      realName: user.realName,
      email: user.email,
      phone: user.phone,
      role: user.role,
      department: user.department,
      position: user.position,
      remark: user.remark
    })
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    
    const res = await updateAdmin(form)
    if (res.code === 200) {
      ElMessage.success('个人信息更新成功')
      // 更新store中的用户信息
      store.commit('setUser', { ...store.state.user, ...form })
    } else {
      ElMessage.error(res.msg || '更新失败')
    }
  } catch (error) {
    ElMessage.error('表单验证失败')
  } finally {
    loading.value = false
  }
}

// 重置表单
const handleReset = () => {
  loadUserInfo()
  formRef.value?.resetFields()
}

onMounted(() => {
  loadUserInfo()
})
</script>

<style scoped>
.admin-profile {
  padding: 20px;
}

.card-header {
  font-weight: 500;
  color: #303133;
}

.profile-form {
  max-width: 800px;
  margin: 0 auto;
}
</style> 