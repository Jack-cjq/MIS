<template>
  <div class="competition-management">
    <!-- 顶部区域：统计 + 审核入口 -->
    <el-row :gutter="20" class="top-section">
      <!-- 左侧：数据统计卡片 -->
      <el-col :span="16">
        <el-row :gutter="15">
          <el-col :span="6">
            <el-card shadow="hover" class="stats-card total-card">
              <div class="stats-content">
                <el-icon class="stats-icon"><DataBoard /></el-icon>
                <div class="stats-info">
                  <div class="stats-number">{{ stats.total }}</div>
                  <div class="stats-label">总数</div>
                </div>
              </div>
            </el-card>
          </el-col>
          
          <el-col :span="6">
            <el-card shadow="hover" class="stats-card pending-card">
              <div class="stats-content">
                <el-icon class="stats-icon"><Clock /></el-icon>
                <div class="stats-info">
                  <div class="stats-number">{{ stats.pending }}</div>
                  <div class="stats-label">待审核</div>
                </div>
              </div>
            </el-card>
          </el-col>
          
          <el-col :span="6">
            <el-card shadow="hover" class="stats-card approved-card">
              <div class="stats-content">
                <el-icon class="stats-icon"><CircleCheck /></el-icon>
                <div class="stats-info">
                  <div class="stats-number">{{ stats.approved }}</div>
                  <div class="stats-label">已通过</div>
                </div>
              </div>
            </el-card>
          </el-col>
          
          <el-col :span="6">
            <el-card shadow="hover" class="stats-card rejected-card">
              <div class="stats-content">
                <el-icon class="stats-icon"><CircleClose /></el-icon>
                <div class="stats-info">
                  <div class="stats-number">{{ stats.rejected }}</div>
                  <div class="stats-label">已驳回</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
      
      <!-- 右侧：快速审核入口 -->
      <el-col :span="8">
        <el-card shadow="hover" class="audit-card">
          <div class="audit-entrance">
            <div class="audit-info">
              <el-icon class="audit-icon"><DocumentChecked /></el-icon>
              <div>
                <div class="audit-title">待审核竞赛</div>
                <div class="audit-count">{{ stats.pending }} 条记录</div>
              </div>
            </div>
            <el-button type="primary" @click="showPendingOnly" :disabled="stats.pending === 0">
              立即审核
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 主内容区：竞赛列表 -->
    <el-card shadow="never" class="main-card">
      <template #header>
        <div class="card-header">
          <span>竞赛列表</span>
        </div>
      </template>

      <!-- 搜索筛选 -->
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="审核状态">
          <el-select v-model="searchForm.auditStatus" placeholder="全部" clearable style="width: 120px">
            <el-option label="全部" value="" />
            <el-option label="待审核" value="待审核" />
            <el-option label="已通过" value="已通过" />
            <el-option label="已驳回" value="已驳回" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="学生姓名">
          <el-input v-model="searchForm.studentName" placeholder="请输入" clearable style="width: 150px" />
        </el-form-item>
        
        <el-form-item label="竞赛名称">
          <el-input v-model="searchForm.competitionName" placeholder="请输入" clearable style="width: 200px" />
        </el-form-item>
        
        <el-form-item label="竞赛级别">
          <el-select v-model="searchForm.competitionLevel" placeholder="全部" clearable style="width: 130px">
            <el-option label="全部" value="" />
            <el-option label="国家级" value="国家级" />
            <el-option label="省（部）级" value="省（部）级" />
            <el-option label="校（市）级" value="校（市）级" />
          </el-select>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 数据表格 -->
      <el-table 
        :data="tableData" 
        v-loading="loading" 
        style="width: 100%"
        :row-class-name="tableRowClassName"
      >
        <el-table-column type="expand">
          <template #default="{ row }">
            <div class="expand-content">
              <el-descriptions :column="2" border size="small">
                <el-descriptions-item label="学号">{{ row.studentId }}</el-descriptions-item>
                <el-descriptions-item label="学生姓名">{{ row.studentName }}</el-descriptions-item>
                <el-descriptions-item label="竞赛名称" :span="2">{{ row.competitionName }}</el-descriptions-item>
                <el-descriptions-item label="竞赛级别">{{ row.competitionLevel }}</el-descriptions-item>
                <el-descriptions-item label="获奖等级">{{ row.awardLevel }}</el-descriptions-item>
                <el-descriptions-item label="排名">{{ row.ranking }}</el-descriptions-item>
                <el-descriptions-item label="获奖时间">{{ row.awardDate }}</el-descriptions-item>
                <el-descriptions-item label="指导老师" :span="2">{{ row.instructorName || '-' }}</el-descriptions-item>
                <el-descriptions-item label="团队成员" :span="2">{{ row.teamMembers || '-' }}</el-descriptions-item>
                <el-descriptions-item label="提交时间" :span="2">{{ formatDateTime(row.createTime) }}</el-descriptions-item>
                <el-descriptions-item label="审核意见" :span="2" v-if="row.auditComment">
                  <el-tag :type="getStatusType(row.auditStatus)" effect="plain">
                    {{ row.auditComment }}
                  </el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="附件" :span="2" v-if="row.attachments">
                  <el-link :href="row.attachments" target="_blank" type="primary">
                    <el-icon><Paperclip /></el-icon>
                    查看附件
                  </el-link>
                </el-descriptions-item>
              </el-descriptions>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="审核状态" width="100" align="center" fixed="left">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.auditStatus)" effect="dark" size="small">
              {{ row.auditStatus || '待审核' }}
            </el-tag>
          </template>
        </el-table-column>
        
        <el-table-column prop="studentName" label="学生" width="100" align="center" />
        <el-table-column prop="competitionName" label="竞赛名称" min-width="200" show-overflow-tooltip />
        <el-table-column prop="competitionLevel" label="级别" width="120" align="center" />
        <el-table-column prop="awardLevel" label="获奖" width="100" align="center" />
        <el-table-column prop="ranking" label="排名" width="70" align="center" />
        <el-table-column prop="awardDate" label="获奖时间" width="120" align="center" />
        <el-table-column label="提交时间" width="180" align="center">
          <template #default="{ row }">
            {{ formatDateTime(row.createTime) }}
          </template>
        </el-table-column>
        
        <el-table-column label="操作" width="250" fixed="right" align="center">
          <template #default="{ row }">
            <!-- 待审核：显示审核按钮 -->
            <template v-if="!row.auditStatus || row.auditStatus === '待审核'">
              <el-button type="success" size="small" @click="handleApprove(row)">
                <el-icon><Select /></el-icon>
                通过
              </el-button>
              <el-button type="danger" size="small" @click="handleReject(row)">
                <el-icon><Close /></el-icon>
                驳回
              </el-button>
            </template>
            
            <!-- 所有状态：查看和删除 -->
            <el-button type="primary" size="small" link @click="handleView(row)">
              查看
            </el-button>
            <el-button type="danger" size="small" link @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSearch"
        @current-change="handleSearch"
        style="margin-top: 20px; justify-content: flex-end"
      />
    </el-card>

    <!-- 审核对话框 -->
    <el-dialog 
      v-model="auditDialogVisible" 
      :title="auditDialogTitle" 
      width="600px"
      :close-on-click-modal="false"
    >
      <el-form :model="auditForm" label-width="100px">
        <el-form-item label="学生姓名">
          <el-input v-model="currentRow.studentName" disabled />
        </el-form-item>
        <el-form-item label="竞赛名称">
          <el-input v-model="currentRow.competitionName" disabled />
        </el-form-item>
        <el-form-item label="获奖等级">
          <el-input v-model="currentRow.awardLevel" disabled />
        </el-form-item>
        <el-form-item label="审核意见" :required="auditForm.auditStatus === '已驳回'">
          <el-input 
            v-model="auditForm.auditComment" 
            type="textarea" 
            :rows="4"
            :placeholder="auditForm.auditStatus === '已通过' ? '审核意见（选填）' : '请输入驳回原因'"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="auditDialogVisible = false">取消</el-button>
        <el-button 
          :type="auditForm.auditStatus === '已通过' ? 'success' : 'danger'" 
          @click="submitAudit" 
          :loading="submitting"
        >
          确定{{ auditForm.auditStatus === '已通过' ? '通过' : '驳回' }}
        </el-button>
      </template>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog 
      v-model="detailDialogVisible" 
      title="竞赛详情" 
      width="800px"
    >
      <el-descriptions :column="2" border v-if="currentRow">
        <el-descriptions-item label="学号">{{ currentRow.studentId }}</el-descriptions-item>
        <el-descriptions-item label="学生姓名">{{ currentRow.studentName }}</el-descriptions-item>
        <el-descriptions-item label="竞赛名称" :span="2">{{ currentRow.competitionName }}</el-descriptions-item>
        <el-descriptions-item label="竞赛级别">{{ currentRow.competitionLevel }}</el-descriptions-item>
        <el-descriptions-item label="获奖等级">{{ currentRow.awardLevel }}</el-descriptions-item>
        <el-descriptions-item label="排名">{{ currentRow.ranking }}</el-descriptions-item>
        <el-descriptions-item label="获奖时间">{{ currentRow.awardDate }}</el-descriptions-item>
        <el-descriptions-item label="指导老师" :span="2">{{ currentRow.instructorName || '-' }}</el-descriptions-item>
        <el-descriptions-item label="团队成员" :span="2">{{ currentRow.teamMembers || '-' }}</el-descriptions-item>
        <el-descriptions-item label="审核状态">
          <el-tag :type="getStatusType(currentRow.auditStatus)">
            {{ currentRow.auditStatus || '待审核' }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="提交时间">{{ formatDateTime(currentRow.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="审核意见" :span="2" v-if="currentRow.auditComment">
          {{ currentRow.auditComment }}
        </el-descriptions-item>
        <el-descriptions-item label="附件" :span="2" v-if="currentRow.attachments">
          <el-link :href="currentRow.attachments" target="_blank" type="primary">
            <el-icon><Paperclip /></el-icon>
            下载附件
          </el-link>
        </el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { 
  DataBoard, Clock, CircleCheck, CircleClose, DocumentChecked,
  Search, Select, Close, Paperclip
} from '@element-plus/icons-vue'
import { getAllCompetitions, auditCompetition, deleteCompetition } from '@/api/competition'
import { useStore } from 'vuex'

const store = useStore()
const loading = ref(false)
const submitting = ref(false)
const currentAdmin = computed(() => store.state.user || {})
const currentAdminId = computed(() => currentAdmin.value.id || currentAdmin.value.adminId || 'admin')

// 统计数据
const stats = ref({
  total: 0,
  pending: 0,
  approved: 0,
  rejected: 0
})

// 表格数据
const allData = ref([])
const tableData = ref([])

// 搜索表单
const searchForm = reactive({
  auditStatus: '',
  studentName: '',
  competitionName: '',
  competitionLevel: ''
})

// 分页
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

// 对话框
const auditDialogVisible = ref(false)
const auditDialogTitle = ref('')
const detailDialogVisible = ref(false)
const currentRow = ref({})
const pendingCount = ref(0)

const auditForm = reactive({
  auditStatus: '',
  auditComment: ''
})

// 表格行类名
const tableRowClassName = ({ row }) => {
  if (!row.auditStatus || row.auditStatus === '待审核') {
    return 'pending-row'
  }
  return ''
}

// 获取状态类型
const getStatusType = (status) => {
  const typeMap = {
    '待审核': 'warning',
    '已通过': 'success',
    '已驳回': 'danger'
  }
  return typeMap[status] || 'warning'
}

// 格式化时间
const formatDateTime = (dateTime) => {
  if (!dateTime) return '-'
  return new Date(dateTime).toLocaleString('zh-CN')
}

// 加载数据
const loadData = async () => {
  loading.value = true
  try {
    console.log('=== 开始加载竞赛数据 ===')
    
    const res = await getAllCompetitions()
    console.log('竞赛数据返回:', res)
    
    // ✅ 关键修改：res 本身就是数组（Axios 响应拦截器已处理）
    if (Array.isArray(res)) {
      console.log('>>> res 本身是数组')
      allData.value = res
    } else if (res.data && Array.isArray(res.data)) {
      console.log('>>> res.data 是数组')
      allData.value = res.data
    } else {
      console.error('❌ 数据格式错误:', res)
      ElMessage.error('数据格式错误')
      allData.value = []
      return
    }
    
    console.log('✅ allData 赋值成功')
    console.log('allData.value:', allData.value)
    console.log('allData 长度:', allData.value.length)
    
    // ✅ 确保这两个方法被调用
    calculateStats()
    console.log('✅ 统计完成:', stats.value)
    
    filterAndPaginate()
    console.log('✅ 分页完成，tableData 长度:', tableData.value.length)
    
    ElMessage.success(`加载成功，共 ${allData.value.length} 条数据`)
  } catch (error) {
    console.error('=== 加载数据失败 ===')
    console.error('错误:', error)
    ElMessage.error('加载数据失败: ' + error.message)
    allData.value = []
  } finally {
    loading.value = false
  }
}

// 计算统计
const calculateStats = () => {
  stats.value = {
    total: allData.value.length,
    pending: allData.value.filter(item => !item.auditStatus || item.auditStatus === '待审核').length,
    approved: allData.value.filter(item => item.auditStatus === '已通过').length,
    rejected: allData.value.filter(item => item.auditStatus === '已驳回').length
  }
}

// 过滤和分页
const filterAndPaginate = () => {
  let filtered = [...allData.value]
  
  // 筛选
  if (searchForm.auditStatus) {
    filtered = filtered.filter(item => (item.auditStatus || '待审核') === searchForm.auditStatus)
  }
  if (searchForm.studentName) {
    filtered = filtered.filter(item => item.studentName?.includes(searchForm.studentName))
  }
  if (searchForm.competitionName) {
    filtered = filtered.filter(item => item.competitionName?.includes(searchForm.competitionName))
  }
  if (searchForm.competitionLevel) {
    filtered = filtered.filter(item => item.competitionLevel === searchForm.competitionLevel)
  }
  
  // 分页
  pagination.total = filtered.length
  const start = (pagination.page - 1) * pagination.pageSize
  const end = start + pagination.pageSize
  tableData.value = filtered.slice(start, end)
}

// 显示待审核
const showPendingOnly = () => {
  searchForm.auditStatus = '待审核'
  pagination.page = 1
  filterAndPaginate()
}

// 搜索
const handleSearch = () => {
  pagination.page = 1
  filterAndPaginate()
}

// 重置
const handleReset = () => {
  Object.assign(searchForm, {
    auditStatus: '',
    studentName: '',
    competitionName: '',
    competitionLevel: ''
  })
  handleSearch()
}

// 审核通过
const handleApprove = (row) => {
  currentRow.value = row
  auditDialogTitle.value = '审核通过'
  auditForm.auditStatus = '已通过'
  auditForm.auditComment = ''
  auditDialogVisible.value = true
}

// 审核驳回
const handleReject = (row) => {
  currentRow.value = row
  auditDialogTitle.value = '审核驳回'
  auditForm.auditStatus = '已驳回'
  auditForm.auditComment = ''
  auditDialogVisible.value = true
}

// 提交审核
const submitAudit = async () => {
  if (auditForm.auditStatus === '已驳回' && !auditForm.auditComment.trim()) {
    ElMessage.warning('请填写驳回原因')
    return
  }
  
  submitting.value = true
  try {
    const res = await auditCompetition({
      id: currentRow.value.id,
      auditStatus: auditForm.auditStatus,
      auditComment: auditForm.auditComment,
      auditorId: currentAdminId.value, 
      auditorName: currentAdmin.value.name || currentAdmin.value.username
    })
    
    console.log('审核结果:', res)

    ElMessage.success('审核成功')
    auditDialogVisible.value = false
    await loadData()
  } catch (error) {
    console.error('审核失败', error)
    ElMessage.error('审核失败')
  } finally {
    submitting.value = false
  }
}

// 查看详情
const handleView = (row) => {
  currentRow.value = row
  detailDialogVisible.value = true
}

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除 ${row.studentName} 的竞赛记录吗？`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await deleteCompetition(row.id)
      ElMessage.success('删除成功')
      loadData()
    } catch (error) {
      console.error('删除失败', error)
      ElMessage.error('删除失败')
    }
  }).catch(() => {})
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.competition-management {
  padding: 20px;
}

/* 顶部区域 */
.top-section {
  margin-bottom: 20px;
}

/* 统计卡片 */
.stats-card {
  height: 100px;
  cursor: pointer;
  transition: transform 0.2s;
}

.stats-card:hover {
  transform: translateY(-5px);
}

.stats-content {
  display: flex;
  align-items: center;
  height: 100%;
}

.stats-icon {
  font-size: 40px;
  margin-right: 15px;
}

.total-card .stats-icon { color: #409EFF; }
.pending-card .stats-icon { color: #E6A23C; }
.approved-card .stats-icon { color: #67C23A; }
.rejected-card .stats-icon { color: #F56C6C; }

.stats-info {
  flex: 1;
}

.stats-number {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  line-height: 1;
}

.stats-label {
  font-size: 14px;
  color: #909399;
  margin-top: 8px;
}

/* 审核卡片 */
.audit-card {
  height: 100px;
}

.audit-entrance {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.audit-info {
  display: flex;
  align-items: center;
}

.audit-icon {
  font-size: 40px;
  color: #E6A23C;
  margin-right: 15px;
}

.audit-title {
  font-size: 16px;
  font-weight: 500;
  color: #303133;
}

.audit-count {
  font-size: 24px;
  font-weight: bold;
  color: #E6A23C;
  margin-top: 5px;
}

/* 主卡片 */
.main-card {
  margin-top: 20px;
}

.card-header {
  font-weight: 500;
  font-size: 16px;
}

/* 搜索表单 */
.search-form {
  padding: 20px;
  background-color: #f5f7fa;
  border-radius: 4px;
  margin-bottom: 20px;
}

/* 展开内容 */
.expand-content {
  padding: 20px;
  background-color: #fafafa;
}

/* 待审核行高亮 */
:deep(.pending-row) {
  background-color: #fef0f0;
}

:deep(.pending-row:hover > td) {
  background-color: #fde2e2 !important;
}
</style>