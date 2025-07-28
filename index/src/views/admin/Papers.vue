<template>
  <div class="papers">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>论文管理</span>
          <el-button type="primary" @click="handleAdd">添加论文</el-button>
        </div>
      </template>
      
      <el-table :data="papersList" v-loading="loading" style="width: 100%">
        <el-table-column prop="title" label="论文标题" width="250" />
        <el-table-column prop="studentName" label="学生姓名" width="120" />
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="journal" label="发表期刊" width="200" />
        <el-table-column prop="publishDate" label="发表时间" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '已发表' ? 'success' : 'warning'">
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
const papersList = ref([])

const handleAdd = () => {
  ElMessage.info('添加论文功能开发中...')
}

const handleEdit = (row) => {
  ElMessage.info('编辑论文功能开发中...')
}

const handleDelete = (row) => {
  ElMessage.info('删除论文功能开发中...')
}
</script>

<style scoped>
.papers {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 