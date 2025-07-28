<template>
  <div class="competition-dict">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>竞赛字典管理</span>
          <el-button type="primary" @click="handleAdd">添加竞赛类型</el-button>
        </div>
      </template>
      
      <el-table :data="dictList" v-loading="loading" style="width: 100%">
        <el-table-column prop="name" label="竞赛名称" width="200" />
        <el-table-column prop="level" label="竞赛级别" width="100" />
        <el-table-column prop="category" label="竞赛类别" width="150" />
        <el-table-column prop="description" label="描述" width="300" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '启用' ? 'success' : 'info'">
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
const dictList = ref([])

const handleAdd = () => {
  ElMessage.info('添加竞赛类型功能开发中...')
}

const handleEdit = (row) => {
  ElMessage.info('编辑竞赛类型功能开发中...')
}

const handleDelete = (row) => {
  ElMessage.info('删除竞赛类型功能开发中...')
}
</script>

<style scoped>
.competition-dict {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 