/* eslint-disable vue/multi-word-component-names */
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
                {{ fmtDate(scope.row.startDate) || '-' }}
                <span v-if="scope.row.endDate">至 {{ fmtDate(scope.row.endDate) }}</span>
              </span>
              <span v-else>{{ fmtDate(scope.row.awardDate) || '-' }}</span>
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
              <el-button link type="primary" @click="openReview(scope.row)">
                查看 & 审核
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>

    <!-- 合并的 查看 + 审核 弹窗（放大版） -->
    <el-dialog
        v-model="reviewVisible"
        :title="moduleTitle + ' · 查看与审核'"
        width="1080px"
        top="6vh"
        destroy-on-close
        class="review-dialog"
    >
      <!-- 顶部信息条 -->
      <div class="review-header">
        <div class="left">
          <div class="main-title">{{ reviewEntity.title || '-' }}</div>
          <div class="sub">
            <el-tag size="small" effect="plain">{{ moduleTitle }}</el-tag>
            <span class="dot">·</span>
            <span class="muted">发起人：</span>
            <span>{{ reviewEntity.studentName || reviewEntity.userId || '-' }}</span>
            <span class="dot">·</span>
            <span class="muted">时间：</span>
            <span v-if="moduleKey !== 'honor'">
              {{ fmtDate(reviewEntity.startDate) || '-' }}
              <span v-if="reviewEntity.endDate">至 {{ fmtDate(reviewEntity.endDate) }}</span>
            </span>
            <span v-else>{{ fmtDate(reviewEntity.awardDate) || '-' }}</span>
          </div>
        </div>
        <div class="right">
          <el-tag :type="statusMeta(reviewForm.auditStatus).type" effect="dark">
            <el-icon class="mr4"><component :is="statusMeta(reviewForm.auditStatus).icon" /></el-icon>
            {{ statusMeta(reviewForm.auditStatus).text }}
          </el-tag>
        </div>
      </div>

      <el-row :gutter="16">
        <!-- 左侧：详情 -->
        <el-col :span="16">
          <el-card shadow="never" class="section-card">
            <template #header><div class="section-title">详细信息</div></template>

            <el-descriptions :column="2" border>
              <template v-for="item in detailPairs" :key="item.label">
                <el-descriptions-item :label="item.label">
                  <template v-if="Array.isArray(item.value)">
                    <el-space wrap>
                      <el-tag v-for="t in item.value" :key="t" effect="plain">{{ t }}</el-tag>
                    </el-space>
                  </template>
                  <template v-else>
                    {{ item.value || '-' }}
                  </template>
                </el-descriptions-item>
              </template>
            </el-descriptions>

            <el-divider>材料预览</el-divider>
            <div class="proof-area" v-if="proofUrl">
              <el-image
                  v-if="isImage(proofUrl)"
                  :src="proofUrl"
                  fit="contain"
                  :preview-src-list="[proofUrl]"
                  style="width:100%;height:340px;border-radius:8px"
              />
              <div v-else class="file-box">
                <el-icon><Link /></el-icon>
                <div class="file-meta">
                  <div class="name">{{ fileName(proofUrl) }}</div>
                  <el-link :href="proofUrl" target="_blank" type="primary">在新窗口打开</el-link>
                </div>
              </div>
            </div>
            <div v-else class="empty-proof">未提供材料链接</div>
          </el-card>
        </el-col>

        <!-- 右侧：审批 -->
        <el-col :span="8">
          <el-card shadow="never" class="section-card review-pane">
            <template #header><div class="section-title">审批处理</div></template>

            <el-form :model="reviewForm" label-width="90px" class="review-form">
              <el-form-item label="审批状态">
                <el-segmented
                    v-model="reviewForm.auditStatus"
                    :options="[
                    { label: '待审核', value: 'pending' },
                    { label: '通过', value: 'approved' },
                    { label: '驳回', value: 'rejected' }
                  ]"
                />
              </el-form-item>
              <el-form-item label="审核意见">
                <el-input
                    v-model="reviewForm.auditComment"
                    type="textarea"
                    :rows="6"
                    maxlength="500"
                    show-word-limit
                    placeholder="请填写审核说明…"
                />
              </el-form-item>
              <el-form-item label="审核人ID">
                <el-input v-model="reviewForm.auditorId" placeholder="如：admin001" />
              </el-form-item>
              <el-form-item label="审核时间">
                <el-date-picker v-model="reviewForm.auditTime" type="datetime" style="width:100%" />
              </el-form-item>

              <div class="btns">
                <el-button @click="reviewVisible=false">取消</el-button>
                <el-button type="danger" plain @click="quickReject">驳回</el-button>
                <el-button type="success" plain @click="quickApprove">通过</el-button>
                <el-button type="primary" @click="submitReview">提交</el-button>
              </div>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Clock, CircleCheck, CircleClose, Link } from '@element-plus/icons-vue'

/** ========= 字典 ========= */
const dict = {
  academicType: ['会议', '讲座', '研讨会'],
  level: ['国家', '省级', '校级', '院级', '其他'],
  activityTypeLabel: {
    lecture: '讲座',
    conference: '会议',
    competition: '竞赛',
    honor: '荣誉',
    training: '培训',
  }
}

/** ========= 模块 ========= */
const moduleKey = ref('honor') // 默认演示“荣誉”，可改：'academic' | 'daily' | 'honor'
const moduleTitle = computed(() =>
    moduleKey.value === 'academic' ? '学术活动' : moduleKey.value === 'daily' ? '日常活动' : '荣誉'
)

/** ========= 列表数据 ========= */
const allRows = reactive({ academic: [], daily: [], honor: [] })
const rows = computed(() => {
  const list = allRows[moduleKey.value] || []
  if (!searchKey.value) return list
  const key = searchKey.value.trim()
  return list.filter(r =>
      (r.title && r.title.includes(key)) ||
      (r.studentName && r.studentName.includes(key)) ||
      (r.userId && r.userId.includes(key))
  )
})
const stat = computed(() => ({
  pending: rows.value.filter(x => x.auditStatus === 'pending').length,
  passed: rows.value.filter(x => x.auditStatus === 'approved').length
}))

/** ========= 状态筛选 ========= */
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

/** ========= 搜索 ========= */
const searchKey = ref('')
const applySearch = () => {}

/** ========= 多选 ========= */
const multipleSelection = ref([])
const onSelectionChange = (val) => (multipleSelection.value = val)

/** ========= 状态渲染 ========= */
const statusMeta = (v) => {
  const map = {
    pending: { text: '待审核', type: 'warning', icon: Clock },
    approved: { text: '已通过', type: 'success', icon: CircleCheck },
    rejected: { text: '已驳回', type: 'danger', icon: CircleClose }
  }
  return map[v] || { text: v, type: 'info', icon: Clock }
}

/** ========= 合并的查看+审核 ========= */
const reviewVisible = ref(false)
const reviewEntity = reactive({})
const reviewForm = reactive({
  id: '',
  title: '',
  studentName: '',
  userId: '',
  auditStatus: 'pending',
  auditComment: '',
  auditorId: '',
  auditTime: new Date()
})

const openReview = (row) => {
  Object.assign(reviewEntity, row)
  Object.assign(reviewForm, {
    id: row.id || '',
    title: row.title || '',
    studentName: row.studentName || '',
    userId: row.userId || '',
    auditStatus: row.auditStatus || 'pending',
    auditComment: row.auditComment || '',
    auditorId: row.auditorId || '',
    auditTime: row.auditTime ? new Date(row.auditTime) : new Date()
  })
  reviewVisible.value = true
}

const quickApprove = () => { reviewForm.auditStatus = 'approved'; submitReview() }
const quickReject  = () => { reviewForm.auditStatus = 'rejected'; submitReview() }

const submitReview = async () => {
  // TODO: 调用后端接口（根据 moduleKey 分别调用 reviewAcademic / reviewDaily / reviewHonor）
  // 本地更新
  const list = allRows[moduleKey.value]
  const idx = list.findIndex(it =>
      (reviewForm.id && it.id === reviewForm.id) ||
      (!reviewForm.id && it.title === reviewForm.title && ((it.studentName||'') === (reviewForm.studentName||'') || (it.userId||'') === (reviewForm.userId||'')))
  )
  if (idx > -1) list[idx] = { ...list[idx], ...reviewForm }
  reviewVisible.value = false
  ElMessage.success('审核已提交')
}

/** ========= 详情展示 ========= */
const detailPairs = computed(() => {
  const e = reviewEntity
  if (moduleKey.value === 'academic') {
    return [
      { label: '学生ID', value: e.studentId },
      { label: '学生姓名', value: e.studentName },
      { label: '活动类型', value: e.type },
      { label: '主办方', value: e.organizer },
      { label: '主讲人', value: e.speaker },
      { label: '开始时间', value: fmtDate(e.startDate) },
      { label: '结束时间', value: fmtDate(e.endDate) }
    ]
  } else if (moduleKey.value === 'daily') {
    return [
      { label: '学生ID', value: e.studentId },
      { label: '学生姓名', value: e.studentName },
      { label: '活动类型', value: dict.activityTypeLabel[e.activityType] || e.activityType },
      { label: '主办/授予', value: e.organizer },
      { label: '级别', value: e.level },
      { label: '开始时间', value: fmtDate(e.startDate) },
      { label: '结束时间', value: fmtDate(e.endDate) }
    ]
  } else {
    return [
      { label: '学生ID', value: e.userId },
      { label: '荣誉类别', value: e.category },
      { label: '级别', value: e.level },
      { label: '授予单位', value: e.awardOrg },
      { label: '获奖日期', value: fmtDate(e.awardDate) },
      { label: '荣誉描述', value: e.description },
      { label: '标签', value: Array.isArray(e.tags) ? e.tags : (e.tags ? String(e.tags).split(',').map(s=>s.trim()) : []) }
    ]
  }
})

/** ========= 附件/材料预览 ========= */
const proofUrl = computed(() => {
  if (moduleKey.value === 'honor') return reviewEntity.evidenceUrl
  if (moduleKey.value === 'academic') return reviewEntity.attachmentUrl
  if (moduleKey.value === 'daily') return reviewEntity.attachmentUrl
  return ''
})
const isImage = (url) => /\.(png|jpe?g|gif|webp|bmp|svg)(\?.*)?$/i.test(url || '')
const fileName = (url) => {
  if (!url) return ''
  try { return decodeURIComponent(url.split('/').pop()) } catch { return url }
}

/** ========= 批量操作 ========= */
const batchApprove = () => { multipleSelection.value.forEach(it => (it.auditStatus = 'approved')); ElMessage.success('已批量通过') }
const batchReject  = () => { multipleSelection.value.forEach(it => (it.auditStatus = 'rejected')); ElMessage.success('已批量驳回') }

/** ========= 公共 ========= */
const fmtDate = (d) => {
  if (!d) return ''
  const date = (d instanceof Date) ? d : new Date(d)
  if (Number.isNaN(+date)) return String(d)
  const y = date.getFullYear()
  const m = String(date.getMonth()+1).padStart(2,'0')
  const day = String(date.getDate()).padStart(2,'0')
  return `${y}-${m}-${day}`
}
const onTabChange = () => { searchKey.value = ''; statusFilter.value = 'all' }

/** ========= mock 数据 ========= */
const loadMock = () => {
  allRows.academic = [
    { id:'a1', title: '机器学习前沿讲座', studentId: '20231234', studentName: '张三', type: '讲座', organizer: '计算机学院', speaker: '李教授', startDate: '2025-03-01', endDate: '2025-03-01', attachmentUrl: 'https://dummyimage.com/900x420/edf2ff/222.png&text=%E6%97%A5%E7%A8%8B', auditStatus: 'pending' }
  ]
  allRows.daily = [
    { id:'d1', title: '志愿服务-马拉松', studentId: '20239876', studentName: '李四', activityType: 'training', organizer: '校团委', level: '校级', startDate: '2025-04-10', endDate: '2025-04-12', attachmentUrl: '', auditStatus: 'approved' }
  ]
  allRows.honor = [
    { id:'h1', title: '数学建模国赛二等奖', userId: '20230001', category: '竞赛', level: '国家', awardOrg: '教育部', awardDate: '2025-05-15', description: '团队荣誉', evidenceUrl: 'https://dummyimage.com/960x520/fff1f2/cc0000.png&text=%E8%AF%81%E4%B9%A6', tags: ['数学建模','二作'], isPublic: true, auditStatus: 'rejected' }
  ]
}
onMounted(loadMock)
</script>

<style scoped>
/* 页面结构 */
.container{max-width:1200px;margin:0 auto;}
.page-wrap{min-height:100vh;background:linear-gradient(180deg,#f7f9ff,#ffffff 40%);}
.hero{padding:28px 0;background: radial-gradient(1200px 220px at 50% -40px,#e8eeff,transparent);}
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
.card-header{display:flex;align-items:center;justify-content:space-between;}
.card-title{display:flex;align-items:center;}
.ml8{margin-left:8px;}
.status-tag .el-icon{vertical-align:middle}
.mr4{margin-right:4px;}

/* 弹窗放大 + 自适应 */
.review-dialog :deep(.el-dialog){
  max-width:1280px;
}
@media (max-width:1280px){
  .review-dialog :deep(.el-dialog){ width:96vw !important; }
}
.review-dialog :deep(.el-dialog__header){
  background: linear-gradient(90deg,#eef2ff,#ffffff);
  border-bottom:1px solid #eef2ff;
}

/* 弹窗内部样式 */
.review-header{display:flex;justify-content:space-between;align-items:flex-start;margin-bottom:12px}
.review-header .main-title{font-size:18px;font-weight:700;color:#1f2937}
.review-header .sub{margin-top:6px;color:#6b7280;display:flex;align-items:center;gap:6px;flex-wrap:wrap}
.review-header .dot{margin:0 6px;color:#cbd5e1}
.review-header .muted{color:#94a3b8}

.section-card{border:1px solid #eef2ff;background:rgba(255,255,255,.95)}
.section-title{font-weight:600}
.proof-area{margin-top:6px}
.file-box{display:flex;gap:10px;align-items:center;padding:12px;border:1px dashed #e5e7eb;border-radius:8px;background:#fafafa}
.file-meta .name{font-weight:600;margin-bottom:2px}
.empty-proof{color:#94a3b8;background:#f8fafc;border:1px dashed #e5e7eb;padding:12px;border-radius:8px;text-align:center}
.review-pane{position:sticky;top:0}
.review-form .btns{display:flex;justify-content:flex-end;gap:10px;margin-top:8px}
</style>
