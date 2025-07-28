<template>
  <div class="internship-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>实习管理</span>
          <el-button type="primary" @click="handleAdd">添加实习</el-button>
        </div>
      </template>
      
      <el-table :data="internshipList" v-loading="loading" style="width: 100%">
        <el-table-column prop="studentName" label="学生姓名" width="120" />
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="company" label="实习公司" width="200" />
        <el-table-column prop="position" label="实习岗位" width="150" />
        <el-table-column prop="startDate" label="开始时间" width="120" />
        <el-table-column prop="endDate" label="结束时间" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '进行中' ? 'success' : 'info'">
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
const internshipList = ref([])

const handleAdd = () => {
  ElMessage.info('添加实习功能开发中...')
}

const handleEdit = (row) => {
  ElMessage.info('编辑实习功能开发中...')
}

const handleDelete = (row) => {
  ElMessage.info('删除实习功能开发中...')
}
</script>

<style scoped>
.internship-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 