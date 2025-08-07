<template>
  <div class="party-member-list">
    <div class="page-header">
      <h2>党员管理</h2>
      <el-button type="primary" @click="showAddDialog = true">
        <el-icon><Plus /></el-icon>
        添加党员
      </el-button>
    </div>

    <!-- 搜索和筛选 -->
    <div class="search-section">
      <el-row :gutter="16">
        <el-col :span="6">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索姓名或学号"
            clearable
            @keyup.enter="handleSearch"
          >
            <template #append>
              <el-button @click="handleSearch">
                <el-icon><Search /></el-icon>
              </el-button>
            </template>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-select v-model="filterBranch" placeholder="选择党支部" clearable>
            <el-option label="计算机学院党支部" value="计算机学院党支部" />
            <el-option label="软件学院党支部" value="软件学院党支部" />
            <el-option label="信息学院党支部" value="信息学院党支部" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="filterStatus" placeholder="选择党员状态" clearable>
            <el-option label="正式党员" value="是" />
            <el-option label="预备党员" value="否" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-select v-model="filterAuditStatus" placeholder="选择审核状态" clearable>
            <el-option label="待审核" value="待审核" />
            <el-option label="已通过" value="通过" />
            <el-option label="已拒绝" value="拒绝" />
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-button @click="handleReset">重置</el-button>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 党员列表 -->
    <el-table
      :data="partyMembers"
      v-loading="loading"
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="studentId" label="学号" width="120" />
      <el-table-column prop="name" label="姓名" width="100" />
      <el-table-column prop="gender" label="性别" width="60" />
      <el-table-column prop="nation" label="民族" width="80" />
      <el-table-column prop="className" label="班级" width="150" />
      <el-table-column prop="branch" label="党支部" width="150" />
      <el-table-column prop="joinDate" label="入党时间" width="120" />
      <el-table-column prop="isRegular" label="党员状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.isRegular === '是' ? 'success' : 'warning'">
            {{ row.isRegular === '是' ? '正式党员' : '预备党员' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="position" label="党内职务" width="120" />
      <el-table-column prop="auditStatus" label="审核状态" width="100">
        <template #default="{ row }">
          <el-tag :type="getAuditStatusType(row.auditStatus)">
            {{ row.auditStatus || '未提交' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="160">
        <template #default="{ row }">
          {{ formatDate(row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="{ row }">
          <el-button size="small" @click="handleView(row)">查看</el-button>
          <el-button size="small" type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button 
            size="small" 
            type="warning" 
            @click="handleAudit(row)"
            v-if="row.auditStatus === '待审核'"
          >
            审核
          </el-button>
          <el-button size="small" type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-section">
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        @update:current-page="currentPage = $event"
        @update:page-size="pageSize = $event"
      />
    </div>

    <!-- 添加/编辑党员对话框 -->
    <el-dialog
      v-model="showAddDialog"
      :title="editingMember.id ? '编辑党员信息' : '添加党员信息'"
      width="800px"
    >
      <el-form
        ref="memberFormRef"
        :model="editingMember"
        :rules="memberFormRules"
        label-width="120px"
      >
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="editingMember.studentId" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="editingMember.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="editingMember.gender" placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="民族" prop="nation">
              <el-input v-model="editingMember.nation" placeholder="请输入民族" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birth">
              <el-date-picker
                v-model="editingMember.birth"
                type="date"
                placeholder="选择出生日期"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="籍贯" prop="nativePlace">
              <el-input v-model="editingMember.nativePlace" placeholder="请输入籍贯" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="班级" prop="className">
              <el-input v-model="editingMember.className" placeholder="请输入班级" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="党支部" prop="branch">
              <el-select v-model="editingMember.branch" placeholder="请选择党支部" style="width: 100%">
                <el-option label="计算机学院党支部" value="计算机学院党支部" />
                <el-option label="软件学院党支部" value="软件学院党支部" />
                <el-option label="信息学院党支部" value="信息学院党支部" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="入党时间" prop="joinDate">
              <el-date-picker
                v-model="editingMember.joinDate"
                type="date"
                placeholder="选择入党时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否正式党员" prop="isRegular">
              <el-select v-model="editingMember.isRegular" placeholder="请选择党员状态" style="width: 100%">
                <el-option label="正式党员" value="是" />
                <el-option label="预备党员" value="否" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="志愿书编号" prop="applicationNo">
              <el-input v-model="editingMember.applicationNo" placeholder="请输入志愿书编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="党内职务" prop="position">
              <el-input v-model="editingMember.position" placeholder="请输入党内职务" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="奖惩情况" prop="rewardsPunishments">
          <el-input
            v-model="editingMember.rewardsPunishments"
            type="textarea"
            :rows="3"
            placeholder="请输入党内外奖惩情况"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="editingMember.remark"
            type="textarea"
            :rows="2"
            placeholder="请输入备注"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAddDialog = false">取消</el-button>
        <el-button type="primary" @click="handleSaveMember" :loading="saveLoading">
          保存
        </el-button>
      </template>
    </el-dialog>

    <!-- 查看党员详情对话框 -->
    <el-dialog v-model="showViewDialog" title="党员详情" width="600px">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="学号">{{ viewingMember.studentId }}</el-descriptions-item>
        <el-descriptions-item label="姓名">{{ viewingMember.name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ viewingMember.gender }}</el-descriptions-item>
        <el-descriptions-item label="民族">{{ viewingMember.nation }}</el-descriptions-item>
        <el-descriptions-item label="出生日期">{{ viewingMember.birth }}</el-descriptions-item>
        <el-descriptions-item label="籍贯">{{ viewingMember.nativePlace }}</el-descriptions-item>
        <el-descriptions-item label="班级">{{ viewingMember.className }}</el-descriptions-item>
        <el-descriptions-item label="党支部">{{ viewingMember.branch }}</el-descriptions-item>
        <el-descriptions-item label="入党时间">{{ viewingMember.joinDate }}</el-descriptions-item>
        <el-descriptions-item label="党员状态">
          <el-tag :type="viewingMember.isRegular === '是' ? 'success' : 'warning'">
            {{ viewingMember.isRegular === '是' ? '正式党员' : '预备党员' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="志愿书编号">{{ viewingMember.applicationNo }}</el-descriptions-item>
        <el-descriptions-item label="党内职务">{{ viewingMember.position }}</el-descriptions-item>
        <el-descriptions-item label="奖惩情况">{{ viewingMember.rewardsPunishments }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">
          <el-tag :type="getAuditStatusType(viewingMember.auditStatus)">
            {{ viewingMember.auditStatus || '未提交' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="备注">{{ viewingMember.remark }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(viewingMember.createTime) }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>

    <!-- 审核对话框 -->
    <el-dialog v-model="showAuditDialog" title="审核党员信息" width="500px">
      <el-form :model="auditForm" label-width="100px">
        <el-form-item label="审核状态" required>
          <el-radio-group v-model="auditForm.auditStatus">
            <el-radio label="通过">通过</el-radio>
            <el-radio label="拒绝">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input
            v-model="auditForm.auditComment"
            type="textarea"
            :rows="4"
            placeholder="请输入审核意见"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAuditDialog = false">取消</el-button>
        <el-button type="primary" @click="handleAuditSubmit" :loading="auditLoading">
          提交审核
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Search } from '@element-plus/icons-vue'
import { partyMemberApi } from '@/api/party'

// 数据
const partyMembers = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

// 搜索和筛选
const searchKeyword = ref('')
const filterBranch = ref('')
const filterStatus = ref('')
const filterAuditStatus = ref('')

// 对话框控制
const showAddDialog = ref(false)
const showViewDialog = ref(false)
const showAuditDialog = ref(false)

// 表单数据
const editingMember = reactive({
  id: '',
  studentId: '',
  name: '',
  gender: '',
  nation: '',
  birth: '',
  nativePlace: '',
  className: '',
  branch: '',
  joinDate: '',
  isRegular: '',
  applicationNo: '',
  position: '',
  rewardsPunishments: '',
  remark: ''
})

const viewingMember = reactive({})
const auditForm = reactive({
  auditStatus: '',
  auditComment: ''
})

// 表单验证规则
const memberFormRules = {
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  nation: [{ required: true, message: '请输入民族', trigger: 'blur' }],
  birth: [{ required: true, message: '请选择出生日期', trigger: 'change' }],
  nativePlace: [{ required: true, message: '请输入籍贯', trigger: 'blur' }],
  className: [{ required: true, message: '请输入班级', trigger: 'blur' }],
  branch: [{ required: true, message: '请选择党支部', trigger: 'change' }],
  joinDate: [{ required: true, message: '请选择入党时间', trigger: 'change' }],
  isRegular: [{ required: true, message: '请选择党员状态', trigger: 'change' }]
}

// 加载状态
const saveLoading = ref(false)
const auditLoading = ref(false)

// 获取审核状态类型
const getAuditStatusType = (status) => {
  switch (status) {
    case '待审核':
      return 'warning'
    case '通过':
      return 'success'
    case '拒绝':
      return 'danger'
    default:
      return 'info'
  }
}

// 格式化日期
const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleString()
}

// 获取党员列表
const getPartyMembers = async () => {
  loading.value = true
  try {
    const response = await partyMemberApi.getAllPartyMembers()
    partyMembers.value = response.data || []
    total.value = partyMembers.value.length
  } catch (error) {
    console.error('获取党员列表失败:', error)
    ElMessage.error('获取党员列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = async () => {
  if (!searchKeyword.value && !filterBranch.value && !filterStatus.value && !filterAuditStatus.value) {
    await getPartyMembers()
    return
  }

  loading.value = true
  try {
    let filteredMembers = partyMembers.value

    if (searchKeyword.value) {
      const response = await partyMemberApi.searchPartyMembers(searchKeyword.value)
      filteredMembers = response.data || []
    }

    if (filterBranch.value) {
      const response = await partyMemberApi.getPartyMembersByBranch(filterBranch.value)
      filteredMembers = filteredMembers.filter(member => member.branch === filterBranch.value)
    }

    if (filterStatus.value) {
      const response = await partyMemberApi.getPartyMembersByStatus(filterStatus.value)
      filteredMembers = filteredMembers.filter(member => member.isRegular === filterStatus.value)
    }

    if (filterAuditStatus.value) {
      filteredMembers = filteredMembers.filter(member => member.auditStatus === filterAuditStatus.value)
    }

    partyMembers.value = filteredMembers
    total.value = filteredMembers.length
  } catch (error) {
    console.error('搜索失败:', error)
    ElMessage.error('搜索失败')
  } finally {
    loading.value = false
  }
}

// 重置
const handleReset = () => {
  searchKeyword.value = ''
  filterBranch.value = ''
  filterStatus.value = ''
  filterAuditStatus.value = ''
  getPartyMembers()
}

// 分页
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
}

const handleCurrentChange = (val) => {
  currentPage.value = val
}

// 选择
const handleSelectionChange = (selection) => {
  console.log('选中的党员:', selection)
}

// 查看
const handleView = (row) => {
  Object.assign(viewingMember, row)
  showViewDialog.value = true
}

// 编辑
const handleEdit = (row) => {
  Object.assign(editingMember, row)
  showAddDialog.value = true
}

// 删除
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该党员信息吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await partyMemberApi.deletePartyMember(row.id)
    ElMessage.success('删除成功')
    await getPartyMembers()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

// 审核
const handleAudit = (row) => {
  Object.assign(auditForm, {
    auditStatus: '',
    auditComment: ''
  })
  showAuditDialog.value = true
}

// 保存党员信息
const handleSaveMember = async () => {
  saveLoading.value = true
  try {
    if (editingMember.id) {
      await partyMemberApi.updatePartyMember(editingMember)
      ElMessage.success('更新成功')
    } else {
      await partyMemberApi.addPartyMember(editingMember)
      ElMessage.success('添加成功')
    }
    showAddDialog.value = false
    await getPartyMembers()
  } catch (error) {
    console.error('保存失败:', error)
    ElMessage.error('保存失败')
  } finally {
    saveLoading.value = false
  }
}

// 提交审核
const handleAuditSubmit = async () => {
  if (!auditForm.auditStatus) {
    ElMessage.warning('请选择审核状态')
    return
  }

  auditLoading.value = true
  try {
    await partyMemberApi.auditPartyMember(editingMember.id, auditForm)
    ElMessage.success('审核提交成功')
    showAuditDialog.value = false
    await getPartyMembers()
  } catch (error) {
    console.error('审核失败:', error)
    ElMessage.error('审核失败')
  } finally {
    auditLoading.value = false
  }
}

// 组件挂载时获取数据
onMounted(() => {
  getPartyMembers()
})
</script>

<style scoped>
.party-member-list {
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #303133;
}

.search-section {
  margin-bottom: 20px;
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.pagination-section {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

:deep(.el-table) {
  margin-top: 20px;
}

:deep(.el-dialog__body) {
  padding: 20px;
}
</style>