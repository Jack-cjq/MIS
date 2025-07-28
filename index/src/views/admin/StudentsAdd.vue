<template>
  <div class="students-add">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>添加学生</span>
        </div>
      </template>
      
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="form.studentId" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number v-model="form.age" :min="0" :max="100" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="专业" prop="major">
              <el-select v-model="form.major" placeholder="请选择专业">
                <el-option label="计算机科学与技术" value="计算机科学与技术" />
                <el-option label="软件工程" value="软件工程" />
                <el-option label="信息安全" value="信息安全" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年级" prop="grade">
              <el-input v-model="form.grade" placeholder="请输入年级" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="班级" prop="className">
              <el-input v-model="form.className" placeholder="请输入班级" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="在读" value="在读" />
                <el-option label="毕业" value="毕业" />
                <el-option label="休学" value="休学" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">保存</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { createStudent } from '@/api/student'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  studentId: '',
  name: '',
  gender: '',
  age: 0,
  major: '',
  grade: '',
  className: '',
  phone: '',
  email: '',
  status: '在读',
  password: ''
})

const rules = {
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  major: [{ required: true, message: '请选择专业', trigger: 'change' }],
  grade: [{ required: true, message: '请输入年级', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    
    const res = await createStudent(form)
    if (res.code === 200) {
      ElMessage.success('添加成功')
      router.push('/admin/students/list')
    } else {
      ElMessage.error(res.msg || '添加失败')
    }
  } catch (error) {
    ElMessage.error('表单验证失败')
  } finally {
    loading.value = false
  }
}

const handleReset = () => {
  formRef.value?.resetFields()
}
</script>

<style scoped>
.students-add {
  padding: 20px;
}

.card-header {
  font-weight: 500;
  color: #303133;
}
</style> 