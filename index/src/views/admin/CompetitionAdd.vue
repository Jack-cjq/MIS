<template>
  <div class="competition-add">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>添加竞赛</span>
        </div>
      </template>
      
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="竞赛名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入竞赛名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学生姓名" prop="studentName">
              <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="form.studentId" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="竞赛级别" prop="level">
              <el-select v-model="form.level" placeholder="请选择竞赛级别">
                <el-option label="国家级" value="国家级" />
                <el-option label="省级" value="省级" />
                <el-option label="校级" value="校级" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="获奖情况" prop="award">
              <el-select v-model="form.award" placeholder="请选择获奖情况">
                <el-option label="一等奖" value="一等奖" />
                <el-option label="二等奖" value="二等奖" />
                <el-option label="三等奖" value="三等奖" />
                <el-option label="参与奖" value="参与奖" />
                <el-option label="未获奖" value="未获奖" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="参赛时间" prop="date">
              <el-date-picker v-model="form.date" type="date" placeholder="选择参赛时间" />
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
  name: '',
  studentName: '',
  studentId: '',
  level: '',
  award: '',
  date: '',
  status: '进行中'
})

const rules = {
  name: [{ required: true, message: '请输入竞赛名称', trigger: 'blur' }],
  studentName: [{ required: true, message: '请输入学生姓名', trigger: 'blur' }],
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }]
}

const handleSubmit = () => {
  ElMessage.info('添加竞赛功能开发中...')
}

const handleReset = () => {
  formRef.value?.resetFields()
}
</script>

<style scoped>
.competition-add {
  padding: 20px;
}

.card-header {
  font-weight: 500;
  color: #303133;
}
</style> 