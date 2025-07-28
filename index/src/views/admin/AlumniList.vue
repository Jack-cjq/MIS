<template>
  <div class="alumni-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>校友管理</span>
          <el-button type="primary" @click="handleAdd">添加校友</el-button>
        </div>
      </template>
      
      <el-table :data="alumniList" v-loading="loading" style="width: 100%">
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="major" label="专业" width="150" />
        <el-table-column prop="graduationYear" label="毕业年份" width="100" />
        <el-table-column prop="currentCompany" label="现工作单位" width="200" />
        <el-table-column prop="position" label="职位" width="120" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const alumniList = ref([])

const handleAdd = () => {
  ElMessage.info('添加校友功能开发中...')
}

const handleEdit = (row) => {
  ElMessage.info('编辑校友功能开发中...')
}

const handleDelete = (row) => {
  ElMessage.info('删除校友功能开发中...')
}
</script>

<style scoped>
.alumni-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 