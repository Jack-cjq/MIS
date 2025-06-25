/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="internship-employment">
    <el-card>
      <div style="margin-bottom: 16px; display: flex; gap: 8px;">
        <el-input v-model="searchUserId" placeholder="按用户ID筛选" style="width: 200px" clearable />
        <el-button type="primary" @click="fetchByUserId">查询</el-button>
        <el-button @click="fetchAll">全部</el-button>
        <el-button type="success" @click="openAddDialog">新增</el-button>
      </div>
      <el-table :data="list" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="userId" label="用户ID" width="120" />
        <el-table-column prop="jobTitle" label="岗位名称" />
        <el-table-column prop="company" label="公司" />
        <el-table-column prop="status" label="状态" />
        <el-table-column label="操作" width="220">
          <template #default="scope">
            <el-button size="small" @click="viewDetail(scope.row)">详情</el-button>
            <el-button size="small" type="primary" @click="openEditDialog(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="showDialog" :title="dialogTitle" width="500px">
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" />
        </el-form-item>
        <el-form-item label="岗位名称" prop="jobTitle">
          <el-input v-model="form.jobTitle" />
        </el-form-item>
        <el-form-item label="公司" prop="company">
          <el-input v-model="form.company" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option label="待处理" value="待处理" />
            <el-option label="已通过" value="已通过" />
            <el-option label="已拒绝" value="已拒绝" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </template>
    </el-dialog>

    <!-- 详情弹窗 -->
    <el-dialog v-model="showDetailDialog" title="详情" width="400px">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="ID">{{ detail.id }}</el-descriptions-item>
        <el-descriptions-item label="用户ID">{{ detail.userId }}</el-descriptions-item>
        <el-descriptions-item label="岗位名称">{{ detail.jobTitle }}</el-descriptions-item>
        <el-descriptions-item label="公司">{{ detail.company }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ detail.status }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="showDetailDialog = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  addInternship,
  updateInternship,
  deleteInternship,
  getInternshipById,
  getInternshipsByUserId,
  getAllInternships
} from '@/api/internship'

const list = ref([])
const searchUserId = ref('')
const showDialog = ref(false)
const dialogTitle = ref('新增')
const form = reactive({
  id: '',
  userId: '',
  jobTitle: '',
  company: '',
  status: ''
})
const rules = {
  userId: [{ required: true, message: '请输入用户ID', trigger: 'blur' }],
  jobTitle: [{ required: true, message: '请输入岗位名称', trigger: 'blur' }],
  company: [{ required: true, message: '请输入公司', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }]
}
const formRef = ref()
const showDetailDialog = ref(false)
const detail = ref({})

const fetchAll = async () => {
  const res = await getAllInternships()
  list.value = res.data
}
const fetchByUserId = async () => {
  if (!searchUserId.value) {
    ElMessage.warning('请输入用户ID')
    return
  }
  const res = await getInternshipsByUserId(searchUserId.value)
  list.value = res.data
}
const openAddDialog = () => {
  dialogTitle.value = '新增'
  Object.assign(form, { id: '', userId: '', jobTitle: '', company: '', status: '' })
  showDialog.value = true
}
const openEditDialog = (row) => {
  dialogTitle.value = '编辑'
  Object.assign(form, row)
  showDialog.value = true
}
const submitForm = async () => {
  await formRef.value.validate()
  if (form.id) {
    await updateInternship(form)
    ElMessage.success('修改成功')
  } else {
    await addInternship(form)
    ElMessage.success('新增成功')
  }
  showDialog.value = false
  fetchAll()
}
const handleDelete = (id) => {
  ElMessageBox.confirm('确定要删除吗？', '提示', { type: 'warning' })
    .then(async () => {
      await deleteInternship(id)
      ElMessage.success('删除成功')
      fetchAll()
    })
}
const viewDetail = async (row) => {
  const res = await getInternshipById(row.id)
  detail.value = res.data
  showDetailDialog.value = true
}

// 初始化
fetchAll()
</script>

<style scoped>
.internship-employment {
  padding: 20px;
}
</style> 