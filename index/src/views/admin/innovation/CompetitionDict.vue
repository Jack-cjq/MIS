<template>
  <div class="competition-dict">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>竞赛字典管理</span>
          <el-button type="primary" :icon="Plus" @click="handleAdd">添加竞赛类型</el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="竞赛名称">
          <el-input v-model="searchForm.name" placeholder="请输入竞赛名称" clearable />
        </el-form-item>
        <el-form-item label="竞赛级别">
          <el-select v-model="searchForm.level" placeholder="请选择" clearable>
            <el-option label="国家级" value="国家级" />
            <el-option label="省级" value="省级" />
            <el-option label="市级" value="市级" />
            <el-option label="校级" value="校级" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择" clearable>
            <el-option label="启用" value="启用" />
            <el-option label="禁用" value="禁用" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="handleSearch">查询</el-button>
          <el-button :icon="Refresh" @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" stripe style="width: 100%">
        <el-table-column type="index" label="序号" width="60" align="center" />
        <el-table-column prop="competitionName" label="竞赛名称" min-width="200" show-overflow-tooltip />
        <el-table-column prop="competitionLevel" label="竞赛级别" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getLevelType(row.competitionLevel)">{{ row.competitionLevel }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="competitionStatus" label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="row.competitionStatus === '启用' ? 'success' : 'info'">
              {{ row.competitionStatus }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="primary" link @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button 
              size="small" 
              :type="row.competitionStatus === '启用' ? 'warning' : 'success'" 
              link 
              @click="handleToggleStatus(row)"
            >
              {{ row.competitionStatus === '启用' ? '禁用' : '启用' }}
            </el-button>
            <el-button size="small" type="danger" link @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pagination.current"
        v-model:page-size="pagination.size"
        :page-sizes="[10, 20, 50, 100]"
        :total="pagination.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑竞赛类型' : '添加竞赛类型'"
      width="600px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="竞赛名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入竞赛名称" />
        </el-form-item>
        <el-form-item label="竞赛级别" prop="level">
          <el-select v-model="form.level" placeholder="请选择竞赛级别" style="width: 100%">
            <el-option label="国家级" value="国家级" />
            <el-option label="省级" value="省级" />
            <el-option label="市级" value="市级" />
            <el-option label="校级" value="校级" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="启用">启用</el-radio>
            <el-radio label="禁用">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>

      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleSubmit">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search, Refresh } from '@element-plus/icons-vue'
import {
  getAllCompetitionDicts,
  addCompetitionDict,
  updateCompetitionDict,
  deleteCompetitionDict
} from '@/api/competition'

// 数据
const loading = ref(false)
const submitting = ref(false)
const tableData = ref([])
const allData = ref([])

// 搜索表单
const searchForm = reactive({
  name: '',
  level: '',
  status: ''
})

// 分页
const pagination = reactive({
  current: 1,
  size: 10,
  total: 0
})

// 对话框
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref(null)

// 表单数据
const form = reactive({
  id: '',
  competitionName: '',
  competitionLevel: '',
  competitionStatus: '启用'
})

// 表单验证规则
const rules = {
  competitionName: [
    { required: true, message: '请输入竞赛名称', trigger: 'blur' }
  ],
  competitionLevel: [
    { required: true, message: '请选择竞赛级别', trigger: 'change' }
  ],
}

// 获取级别类型
const getLevelType = (level) => {
  const typeMap = {
    '国家级': 'danger',
    '省级': 'warning',
    '市级': 'success',
    '校级': 'info'
  }
  return typeMap[level] || 'info'
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    const res = await getAllCompetitionDicts()
    console.log('竞赛字典数据:', res)

    // 处理响应数据
    allData.value = Array.isArray(res) ? res : (res.data || [])

    // 搜索过滤
    filterAndPaginate()
  } catch (error) {
    console.error('加载数据失败:', error)
    ElMessage.error('加载数据失败: ' + error.message)
    allData.value = []
    tableData.value = []
  } finally {
    loading.value = false
  }
}

// 过滤和分页
const filterAndPaginate = () => {
  let filtered = allData.value

  // 应用搜索条件
  if (searchForm.name) {
    filtered = filtered.filter(item =>
      item.competitionName.includes(searchForm.name)
    )
  }
  if (searchForm.level) {
    filtered = filtered.filter(item => item.level === searchForm.level)
  }
  if (searchForm.status) {
    filtered = filtered.filter(item => item.status === searchForm.status)
  }

  // 更新总数
  pagination.total = filtered.length

  // 分页
  const start = (pagination.current - 1) * pagination.size
  const end = start + pagination.size
  tableData.value = filtered.slice(start, end)
}

// 搜索
const handleSearch = () => {
  pagination.current = 1
  filterAndPaginate()
}

// 重置
const handleReset = () => {
  searchForm.name = ''
  searchForm.level = ''
  searchForm.status = ''
  pagination.current = 1
  filterAndPaginate()
}

// 添加
const handleAdd = () => {
  isEdit.value = false
  resetForm()
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  isEdit.value = true
  Object.assign(form, {
    id: row.id,
    competitionName: row.competitionName,
    competitionLevel: row.competitionLevel,
    competitionStatus: row.competitionStatus
  })
  dialogVisible.value = true
}

// 切换状态
const handleToggleStatus = async (row) => {
  const newStatus = row.status === '启用' ? '禁用' : '启用'
  try {
    await ElMessageBox.confirm(
      `确定要${newStatus}该竞赛类型吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    await updateCompetitionDict(row.id, {
      ...row,
      status: newStatus
    })

    ElMessage.success(`${newStatus}成功`)
    await loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('操作失败:', error)
      ElMessage.error('操作失败')
    }
  }
}

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(
      '确定要删除该竞赛类型吗？删除后不可恢复！',
      '警告',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    await deleteCompetitionDict(row.id)
    ElMessage.success('删除成功')
    await loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate()

    submitting.value = true
    const data = {
      id: form.id,
      competitionName: form.competitionName,
      competitionLevel: form.competitionLevel,
      competitionStatus: form.competitionStatus
    }

    if (isEdit.value) {
      await updateCompetitionDict(data.id, data)
      ElMessage.success('更新成功')
    } else {
      await addCompetitionDict(data)
      ElMessage.success('添加成功')
    }

    dialogVisible.value = false
    await loadData()
  } catch (error) {
    if (error !== false) {
      console.error('提交失败:', error)
      ElMessage.error('提交失败: ' + (error.response?.data?.message || error.message))
    }
  } finally {
    submitting.value = false
  }
}

// 重置表单
const resetForm = () => {
  form.id = ''
  form.competitionName = ''
  form.competitionLevel = ''
  form.competitionStatus = '启用'
  formRef.value?.clearValidate()
}

// 初始化
onMounted(() => {
  loadData()
})
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

.search-form {
  margin-bottom: 20px;
}
</style>