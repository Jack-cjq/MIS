<template>
  <div class="patents">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>专利管理</span>
          <el-button type="primary" @click="handleAdd">添加专利</el-button>
        </div>
      </template>
      
      <el-table :data="patentsList" v-loading="loading" style="width: 100%">
        <el-table-column prop="title" label="专利名称" width="250" />
        <el-table-column prop="studentName" label="学生姓名" width="120" />
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="patentNumber" label="专利号" width="150" />
        <el-table-column prop="applyDate" label="申请时间" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '已授权' ? 'success' : 'warning'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
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
const patentsList = ref([])

const handleAdd = () => {
  ElMessage.info('添加专利功能开发中...')
}

const handleEdit = (row) => {
  ElMessage.info('编辑专利功能开发中...')
}

const handleDelete = (row) => {
  ElMessage.info('删除专利功能开发中...')
}
</script>

<style scoped>
.patents {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 