/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="profile">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>个人信息</span>
          <el-button type="primary" @click="handleEdit">
            {{ editMode ? '保存' : '编辑' }}
          </el-button>
        </div>
      </template>
      
      <el-form
        ref="formRef"
        :model="profileForm"
        :rules="rules"
        label-width="120px"
        :disabled="!editMode"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="profileForm.studentId" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="profileForm.name" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="profileForm.gender" placeholder="请选择性别">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birthDate">
              <el-date-picker
                v-model="profileForm.birthDate"
                type="date"
                placeholder="选择日期"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="专业" prop="major">
              <el-input v-model="profileForm.major" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="班级" prop="className">
              <el-input v-model="profileForm.className" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="profileForm.phone" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="profileForm.email" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="家庭住址" prop="address">
              <el-input v-model="profileForm.address" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="政治面貌" prop="politicalStatus">
              <el-select v-model="profileForm.politicalStatus" placeholder="请选择政治面貌">
                <el-option label="中共党员" value="中共党员" />
                <el-option label="中共预备党员" value="中共预备党员" />
                <el-option label="共青团员" value="共青团员" />
                <el-option label="群众" value="群众" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="导师" prop="supervisor">
              <el-input v-model="profileForm.supervisor" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="研究方向" prop="researchDirection">
              <el-input v-model="profileForm.researchDirection" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="个人简介" prop="introduction">
          <el-input
            v-model="profileForm.introduction"
            type="textarea"
            :rows="4"
            placeholder="请输入个人简介"
          />
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top: 20px;">
      <template #header>
        <div class="card-header">
          <span>学习记录</span>
        </div>
      </template>
      
      <el-table :data="studyRecords" style="width: 100%">
        <el-table-column prop="semester" label="学期" width="120" />
        <el-table-column prop="course" label="课程名称" />
        <el-table-column prop="score" label="成绩" width="100" />
        <el-table-column prop="credit" label="学分" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '已通过' ? 'success' : 'warning'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { getProfile, updateProfile, getStudyRecords } from '@/api/student'

const store = useStore()
const formRef = ref()
const editMode = ref(false)

// 获取当前用户信息
const userInfo = computed(() => store.state.user || {})

const profileForm = reactive({
  studentId: '',
  name: '',
  gender: '',
  birthDate: '',
  major: '',
  className: '',
  phone: '',
  email: '',
  address: '',
  politicalStatus: '',
  supervisor: '',
  researchDirection: '',
  introduction: ''
})

const rules = {
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  birthDate: [
    { type: 'date', message: '请选择正确的出生日期', trigger: 'change' }
  ]
}

const studyRecords = ref([])

const loadProfile = async () => {
  try {
    // 获取个人信息（自动获取当前用户）
    const profileResponse = await getProfile()
    
    if (profileResponse.code === 200) {
      const profileData = profileResponse.data
      // 将后端数据映射到表单字段
      Object.assign(profileForm, {
        studentId: profileData.studentId || '',
        name: profileData.name || '',
        gender: profileData.gender || '',
        birthDate: profileData.birthDate ? new Date(profileData.birthDate) : '',
        major: profileData.major || '',
        className: profileData.className || '',
        phone: profileData.phone || '',
        email: profileData.email || '',
        address: profileData.address || '',
        politicalStatus: profileData.politicalStatus || '',
        supervisor: profileData.supervisor || '',
        researchDirection: profileData.researchDirection || '',
        introduction: profileData.researchDirection || '热爱编程，擅长Java和Python开发'
      })
    } else {
      ElMessage.error(profileResponse.msg || '获取个人信息失败')
    }

    // 获取学习记录
    const recordsResponse = await getStudyRecords()
    if (recordsResponse.code === 200) {
      studyRecords.value = recordsResponse.data
    }
  } catch (error) {
    console.error('加载个人信息失败:', error)
    ElMessage.error('加载个人信息失败')
    // 如果API调用失败，只显示基本信息
    if (userInfo.value) {
      Object.assign(profileForm, {
        studentId: userInfo.value.studentId || '',
        name: userInfo.value.name || '',
        // 其他敏感信息不显示，需要重新登录获取
      })
    }
  }
}

const handleEdit = async () => {
  if (!editMode.value) {
    editMode.value = true
    return
  }

  try {
    await formRef.value.validate()
    
    // 准备要更新的数据
    const updateData = {
      ...profileForm,
      birthDate: profileForm.birthDate instanceof Date ? profileForm.birthDate : new Date(profileForm.birthDate)
    }
    
    const response = await updateProfile(updateData)
    if (response.code === 200) {
      ElMessage.success('保存成功')
      editMode.value = false
      // 更新store中的用户信息
      store.commit('setUser', { ...userInfo.value, ...updateData })
    } else {
      ElMessage.error(response.msg || '保存失败')
    }
  } catch (error) {
    console.error('保存失败:', error)
    ElMessage.error('保存失败')
  }
}

onMounted(() => {
  loadProfile()
})
</script>

<style scoped>
.profile {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 