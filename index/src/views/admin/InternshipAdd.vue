<template>
  <div class="internship-add">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>添加实习</span>
        </div>
      </template>
      
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学生姓名" prop="studentName">
              <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="form.studentId" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="实习公司" prop="company">
              <el-input v-model="form.company" placeholder="请输入实习公司" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实习岗位" prop="position">
              <el-input v-model="form.position" placeholder="请输入实习岗位" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startDate">
              <el-date-picker v-model="form.startDate" type="date" placeholder="选择开始时间" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endDate">
              <el-date-picker v-model="form.endDate" type="date" placeholder="选择结束时间" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="进行中" value="进行中" />
            <el-option label="已完成" value="已完成" />
            <el-option label="已取消" value="已取消" />
          </el-select>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSubmit">保存</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const formRef = ref()
const form = reactive({
  studentName: '',
  studentId: '',
  company: '',
  position: '',
  startDate: '',
  endDate: '',
  status: '进行中'
})

const rules = {
  studentName: [{ required: true, message: '请输入学生姓名', trigger: 'blur' }],
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  company: [{ required: true, message: '请输入实习公司', trigger: 'blur' }]
}

const handleSubmit = () => {
  ElMessage.info('添加实习功能开发中...')
}

const handleReset = () => {
  formRef.value?.resetFields()
}
</script>

<style scoped>
.internship-add {
  padding: 20px;
}

.card-header {
  font-weight: 500;
  color: #303133;
}
</style> 