<template>
  <div class="competition-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>竞赛管理</span>
          <el-button type="primary" @click="handleAdd">添加竞赛</el-button>
        </div>
      </template>
      
      <el-table :data="competitionList" v-loading="loading" style="width: 100%">
        <el-table-column prop="name" label="竞赛名称" width="200" />
        <el-table-column prop="studentName" label="学生姓名" width="120" />
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="level" label="竞赛级别" width="100" />
        <el-table-column prop="award" label="获奖情况" width="120" />
        <el-table-column prop="date" label="参赛时间" width="120" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '已完成' ? 'success' : 'warning'">
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
const competitionList = ref([])

const handleAdd = () => {
  ElMessage.info('添加竞赛功能开发中...')
}

const handleEdit = (row) => {
  ElMessage.info('编辑竞赛功能开发中...')
}

const handleDelete = (row) => {
  ElMessage.info('删除竞赛功能开发中...')
}
</script>

<style scoped>
.competition-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 