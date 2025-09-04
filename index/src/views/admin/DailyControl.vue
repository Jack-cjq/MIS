<template>
  <div class="page-wrap">
    <!-- Hero -->
    <section class="hero">
      <div class="hero-inner container">
        <div>
          <div class="hero-title">管理员端 · 审核中心</div>
          <div class="hero-sub">对学生提交的学术/日常/荣誉进行统一审批</div>
        </div>
        <div class="hero-stats">
          <el-card class="stat-card" shadow="never">
            <div class="stat-title">待审核</div>
            <div class="stat-value warn">{{ stat.pending }}</div>
          </el-card>
          <el-card class="stat-card" shadow="never">
            <div class="stat-title">已通过</div>
            <div class="stat-value ok">{{ stat.passed }}</div>
          </el-card>
          <el-card class="stat-card" shadow="never">
            <div class="stat-title">总记录</div>
            <div class="stat-value">{{ rows.length }}</div>
          </el-card>
        </div>
      </div>
    </section>

    <div class="container">
      <!-- 模块 + 状态筛选 + 搜索 -->
      <div class="toolbar">
        <el-tabs v-model="moduleKey" type="card" @tab-change="onTabChange">
          <el-tab-pane label="学术活动" name="academic" />
          <el-tab-pane label="日常活动" name="daily" />
          <el-tab-pane label="荣誉" name="honor" />
        </el-tabs>

        <div class="toolbar-right">
          <el-segmented v-model="statusFilter" :options="statusOptions" size="small" />
          <el-input
              v-model="searchKey"
              placeholder="按标题/发起人搜索"
              clearable
              style="width: 240px"
              @keyup.enter="applySearch"
              @clear="applySearch"
          />
        </div>
      </div>

      <!-- 列表 -->
      <el-card class="glass" shadow="hover">
        <template #header>
          <div class="card-header">
            <div class="card-title">
              {{ moduleTitle }} 待办列表
              <el-tag class="ml8" type="info" effect="plain">共 {{ rows.length }} 条</el-tag>
              <el-tag class="ml8" type="warning" effect="plain">待审 {{ stat.pending }}</el-tag>
              <el-tag class="ml8" type="success" effect="plain">通过 {{ stat.passed }}</el-tag>
            </div>
            <el-space>
              <el-button @click="batchApprove" :disabled="!multipleSelection.length" type="success" plain>批量通过</el-button>
              <el-button @click="batchReject" :disabled="!multipleSelection.length" type="danger" plain>批量驳回</el-button>
            </el-space>
          </div>
        </template>

        <el-table
            :data="filteredRows"
            style="width: 100%"
            @selection-change="onSelectionChange"
        >
          <el-table-column type="selection" width="48" />
          <el-table-column prop="title" :label="moduleTitle + '标题'" min-width="240" show-overflow-tooltip />
          <el-table-column label="时间" min-width="220">
            <template #default="scope">
              <span v-if="moduleKey !== 'honor'">
                {{ scope.row.startDate || '-' }} <span v-if="scope.row.endDate">至 {{ scope.row.endDate }}</span>
              </span>
              <span v-else>{{ scope.row.awardDate || '-' }}</span>
            </template>
          </el-table-column>
          <el-table-column :prop="moduleKey === 'honor' ? 'userId' : 'studentName'" label="发起人" min-width="140" />
          <el-table-column prop="auditStatus" label="状态" width="160">
            <template #default="scope">
              <el-tag :type="statusMeta(scope.row.auditStatus).type" effect="light" class="status-tag">
                <el-icon class="mr4"><component :is="statusMeta(scope.row.auditStatus).icon" /></el-icon>
                {{ statusMeta(scope.row.auditStatus).text }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="160" align="right" fixed="right">
            <template #default="scope">
              <el-space>
                <el-button link type="primary" @click="viewDetail(scope.row)">查看</el-button>
                <el-button link type="primary" @click="openReview(scope.$index)">审核</el-button>
              </el-space>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>

    <!-- 审核弹窗 -->
    <el-dialog v-model="reviewVisible" title="审核处理" width="620px" destroy-on-close>
      <el-form :model="reviewForm" label-width="100px">
        <el-form-item label="标题"><el-input v-model="reviewForm.title" disabled /></el-form-item>
        <el-form-item label="发起人"><el-input :model-value="reviewForm.studentName || reviewForm.userId || '-'" disabled /></el-form-item>
        <el-form-item label="审核状态">
          <el-select v-model="reviewForm.auditStatus">
            <el-option label="待审核" value="pending" />
            <el-option label="已通过" value="approved" />
            <el-option label="已驳回" value="rejected" />
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见"><el-input v-model="reviewForm.auditComment" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="审核人ID"><el-input v-model="reviewForm.auditorId" /></el-form-item>
        <el-form-item label="审核时间"><el-date-picker v-model="reviewForm.auditTime" type="datetime" style="width:100%" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reviewVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Clock, CircleCheck, CircleClose } from '@element-plus/icons-vue'

// 字典
const dict = {
  honorCategory: ['竞赛','论文','专利','奖学金','社会服务','其他']
}

// 模块
const moduleKey = ref('academic')
const moduleTitle = computed(() =>
    moduleKey.value === 'academic' ? '学术活动' : moduleKey.value === 'daily' ? '日常活动' : '荣誉'
)

// 数据
const allRows = reactive({ academic: [], daily: [], honor: [] })
const rows = computed(() => allRows[moduleKey.value] || [])
const stat = computed(() => ({
  pending: rows.value.filter(x => x.auditStatus === 'pending').length,
  passed: rows.value.filter(x => x.auditStatus === 'approved').length
}))

// 状态筛选
const statusOptions = [
  { label: '全部', value: 'all' },
  { label: '待审', value: 'pending' },
  { label: '通过', value: 'approved' },
  { label: '驳回', value: 'rejected' }
]
const statusFilter = ref('all')
const filteredRows = computed(() => {
  const key = statusFilter.value
  const base = rows.value
  if (key === 'all') return base
  return base.filter(r => r.auditStatus === key)
})

// 搜索
const searchKey = ref('')
const applySearch = () => {
  // 如果接后端，建议用查询参数；这里演示时仅保留UI
}

// 多选
const multipleSelection = ref([])
const onSelectionChange = (val) => (multipleSelection.value = val)

// 审核弹窗
const reviewVisible = ref(false)
const reviewIndex = ref(-1)
const reviewForm = reactive({
  title: '', studentName: '', userId: '',
  auditStatus: 'pending', auditComment: '', auditorId: '', auditTime: ''
})
const openReview = (idx) => {
  reviewIndex.value = idx
  const r = filteredRows.value[idx]
  Object.assign(reviewForm, {
    title: r.title,
    studentName: r.studentName,
    userId: r.userId,
    auditStatus: r.auditStatus || 'pending',
    auditComment: r.auditComment || '',
    auditorId: r.auditorId || '',
    auditTime: r.auditTime || ''
  })
  reviewVisible.value = true
}
const submitReview = async () => {
  const list = rows.value
  const realIndex = list.findIndex(item => item.title === reviewForm.title && (item.studentName === reviewForm.studentName || item.userId === reviewForm.userId))
  if (realIndex > -1) list[realIndex] = { ...list[realIndex], ...reviewForm }
  reviewVisible.value = false
  ElMessage.success('审核已提交')
}

// 批量操作
const batchApprove = () => {
  multipleSelection.value.forEach(it => (it.auditStatus = 'approved'))
  ElMessage.success('已批量通过')
}
const batchReject = () => {
  multipleSelection.value.forEach(it => (it.auditStatus = 'rejected'))
  ElMessage.success('已批量驳回')
}

// 状态渲染
const statusMeta = (v) => {
  const map = {
    pending: { text: '待审核', type: 'warning', icon: Clock },
    approved: { text: '已通过', type: 'success', icon: CircleCheck },
    rejected: { text: '已驳回', type: 'danger', icon: CircleClose }
  }
  return map[v] || { text: v, type: 'info', icon: Clock }
}

// 查看
const viewDetail = (row) => {
  const txt = moduleKey.value === 'honor'
      ? `【荣誉】${row.title}<br/>授予单位：${row.awardOrg || '-'}<br/>获奖日期：${row.awardDate || '-'}<br/>状态：${statusMeta(row.auditStatus).text}`
      : `【${moduleTitle.value}】${row.title}<br/>开始：${row.startDate || '-'}  结束：${row.endDate || '-'}<br/>发起人：${row.studentName || row.userId || '-'}<br/>状态：${statusMeta(row.auditStatus).text}`
  ElMessage.info({ message: txt, dangerouslyUseHTMLString: true })
}

const onTabChange = () => { searchKey.value = ''; statusFilter.value = 'all' }

// mock
const loadMock = () => {
  allRows.academic = [
    { title: '机器学习前沿讲座', studentId: '20231234', studentName: '张三', type: '讲座', organizer: '计算机学院', speaker: '李教授', startDate: '2025-03-01', endDate: '2025-03-01', attachmentUrl: '', auditStatus: 'pending' }
  ]
  allRows.daily = [
    { title: '志愿服务-马拉松', studentId: '20239876', studentName: '李四', activityType: 'training', organizer: '校团委', level: '校级', startDate: '2025-04-10', endDate: '2025-04-12', attachmentUrl: '', auditStatus: 'approved' }
  ]
  allRows.honor = [
    { title: '数学建模国赛二等奖', userId: '20230001', category: '竞赛', level: '国家', awardOrg: '教育部', awardDate: '2025-05-15', description: '', evidenceUrl: '', tags: '数学建模, 二作', isPublic: true, auditStatus: 'rejected' }
  ]
}
onMounted(loadMock)
</script>

<style scoped>
.container{max-width:1200px;margin:0 auto;}
.page-wrap{min-height:100vh;background:linear-gradient(180deg,#f7f9ff, #ffffff 40%);}
.hero{padding:28px 0;background: radial-gradient(1200px 220px at 50% -40px, #e8eeff, transparent);}
.hero-inner{display:flex;align-items:flex-end;justify-content:space-between;gap:20px;}
.hero-title{font-size:26px;font-weight:700;color:#1f2d3d;}
.hero-sub{color:#6b7280;margin-top:6px;}
.hero-stats{display:flex;gap:12px;}
.stat-card{width:150px;border:1px solid #eef2ff;background:rgba(255,255,255,.7);backdrop-filter: blur(4px);}
.stat-title{font-size:12px;color:#64748b;}
.stat-value{font-weight:700;font-size:20px;}
.stat-value.ok{color:#16a34a}.stat-value.warn{color:#d97706}
.toolbar{display:flex;align-items:center;justify-content:space-between;margin:14px 0;}
.toolbar-right{display:flex;align-items:center;gap:10px;}
.glass{border:1px solid #eef2ff;background:rgba(255,255,255,.8);backdrop-filter: blur(6px);}
.card-header{display:flex;align-items:center;justify-content:space-between}
.card-title{display:flex;align-items:center}
.ml8{margin-left:8px}
.status-tag .el-icon{vertical-align:middle}
.mr4{margin-right:4px}
</style>
