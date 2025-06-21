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
            <el-form-item label="出生日期" prop="birthday">
              <el-date-picker
                v-model="profileForm.birthday"
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
            <el-form-item label="班级" prop="class">
              <el-input v-model="profileForm.class" />
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
        
        <el-form-item label="家庭住址" prop="address">
          <el-input v-model="profileForm.address" />
        </el-form-item>
        
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getProfile, updateProfile } from '@/api/profile'

const formRef = ref()
const editMode = ref(false)

const profileForm = reactive({
  studentId: '',
  name: '',
  gender: '',
  birthday: '',
  major: '',
  class: '',
  phone: '',
  email: '',
  address: '',
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
  ]
}

const studyRecords = ref([])

const loadProfile = async () => {
  try {
    const data = await getProfile()
    Object.assign(profileForm, data.profile)
    studyRecords.value = data.studyRecords
  } catch (error) {
    console.error('加载个人信息失败:', error)
    // 使用模拟数据
    Object.assign(profileForm, {
      studentId: '2021001',
      name: '张三',
      gender: '男',
      birthday: '2002-05-15',
      major: '计算机科学与技术',
      class: '计科2101',
      phone: '13800138000',
      email: 'zhangsan@example.com',
      address: '湖北省武汉市洪山区',
      introduction: '热爱编程，擅长Java和Python开发'
    })
    studyRecords.value = [
      {
        semester: '2023-2024-1',
        course: '数据结构',
        score: 85,
        credit: 4,
        status: '已通过'
      },
      {
        semester: '2023-2024-1',
        course: '计算机网络',
        score: 78,
        credit: 3,
        status: '已通过'
      }
    ]
  }
}

const handleEdit = async () => {
  try {
    await formRef.value.validate()
    await updateProfile(profileForm)
    ElMessage.success('保存成功')
    editMode.value = false
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