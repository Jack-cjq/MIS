/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="page-wrap">
    <!-- 顶部 Hero -->
    <section class="hero">
      <div class="hero-inner container">
        <div>
          <div class="hero-title">学生端 · 日常管理</div>
          <div class="hero-sub">学术活动 / 日常活动 / 荣誉 — 一站式提交与追踪</div>
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
      <!-- 模块切换 + 操作区 -->
      <div class="toolbar">
        <el-tabs v-model="moduleKey" type="card" @tab-change="onTabChange">
          <el-tab-pane label="学术活动" name="academic" />
          <el-tab-pane label="日常活动" name="daily" />
          <el-tab-pane label="荣誉" name="honor" />
        </el-tabs>
        <div class="toolbar-right">
          <el-input
              v-model="searchKey"
              placeholder="按标题/发起人搜索"
              clearable
              style="width: 240px"
              @keyup.enter="applySearch"
              @clear="applySearch"
          />
          <el-button type="primary" @click="openCreate">新增</el-button>
        </div>
      </div>

      <!-- 列表卡片 -->
      <el-card class="glass" shadow="hover">
        <template #header>
          <div class="card-header">
            <div class="card-title">
              {{ moduleTitle }} 列表
              <el-tag class="ml8" type="info" effect="plain">共 {{ rows.length }} 条</el-tag>
              <el-tag class="ml8" type="warning" effect="plain">待审 {{ stat.pending }}</el-tag>
              <el-tag class="ml8" type="success" effect="plain">通过 {{ stat.passed }}</el-tag>
            </div>
          </div>
        </template>

        <el-table :data="rows" style="width: 100%">
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
                <el-icon class="mr4">
                  <component :is="statusMeta(scope.row.auditStatus).icon" />
                </el-icon>
                {{ statusMeta(scope.row.auditStatus).text }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="120" align="right" fixed="right">
            <template #default="scope">
              <el-button link type="primary" @click="viewDetail(scope.row)">查看</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>

    <!-- 新增弹窗（随模块联动字段） -->
    <el-dialog v-model="createVisible" :title="'新增' + moduleTitle" width="760px" destroy-on-close>
      <el-form ref="createFormRef" :model="form" :rules="rules" label-width="110px">
        <!-- 学术活动 -->
        <template v-if="moduleKey === 'academic'">
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="学生ID" prop="studentId"><el-input v-model="form.studentId" /></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="学生姓名" prop="studentName"><el-input v-model="form.studentName" /></el-form-item></el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="活动标题" prop="title"><el-input v-model="form.title" /></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="活动类型" prop="type"><el-select v-model="form.type"><el-option v-for="t in dict.academicType" :key="t" :label="t" :value="t" /></el-select></el-form-item></el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="主办方" prop="organizer"><el-input v-model="form.organizer" /></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="主讲人" prop="speaker"><el-input v-model="form.speaker" /></el-form-item></el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="开始时间" prop="startDate"><el-date-picker v-model="form.startDate" type="date" style="width:100%" /></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="结束时间" prop="endDate"><el-date-picker v-model="form.endDate" type="date" style="width:100%" /></el-form-item></el-col>
          </el-row>
          <el-form-item label="附件URL" prop="attachmentUrl"><el-input v-model="form.attachmentUrl" placeholder="PDF/日程链接" /></el-form-item>
        </template>

        <!-- 日常活动 -->
        <template v-else-if="moduleKey === 'daily'">
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="学生ID" prop="studentId"><el-input v-model="form.studentId" /></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="学生姓名" prop="studentName"><el-input v-model="form.studentName" /></el-form-item></el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="活动标题" prop="title"><el-input v-model="form.title" /></el-form-item></el-col>
            <el-col :span="12">
              <el-form-item label="活动类型" prop="activityType">
                <el-select v-model="form.activityType"><el-option v-for="o in dict.activityType" :key="o.value" :label="o.label" :value="o.value" /></el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="主办/授予" prop="organizer"><el-input v-model="form.organizer" /></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="级别" prop="level"><el-select v-model="form.level"><el-option v-for="lv in dict.level" :key="lv" :label="lv" :value="lv" /></el-select></el-form-item></el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="开始时间" prop="startDate"><el-date-picker v-model="form.startDate" type="date" style="width:100%" /></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="结束时间" prop="endDate"><el-date-picker v-model="form.endDate" type="date" style="width:100%" /></el-form-item></el-col>
          </el-row>
          <el-form-item label="证明材料URL" prop="attachmentUrl"><el-input v-model="form.attachmentUrl" /></el-form-item>
        </template>

        <!-- 荣誉 -->
        <template v-else>
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="学生ID" prop="userId"><el-input v-model="form.userId" /></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="荣誉名称" prop="title"><el-input v-model="form.title" /></el-form-item></el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="类别" prop="category"><el-select v-model="form.category"><el-option v-for="c in dict.honorCategory" :key="c" :label="c" :value="c" /></el-select></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="级别" prop="level"><el-select v-model="form.level"><el-option v-for="lv in dict.level" :key="lv" :label="lv" :value="lv" /></el-select></el-form-item></el-col>
          </el-row>
          <el-row :gutter="16">
            <el-col :span="12"><el-form-item label="授予单位" prop="awardOrg"><el-input v-model="form.awardOrg" /></el-form-item></el-col>
            <el-col :span="12"><el-form-item label="获奖日期" prop="awardDate"><el-date-picker v-model="form.awardDate" type="date" style="width:100%" /></el-form-item></el-col>
          </el-row>
          <el-form-item label="佐证材料URL" prop="evidenceUrl"><el-input v-model="form.evidenceUrl" /></el-form-item>
          <el-form-item label="标签(逗号分隔)"><el-input v-model="form.tags" placeholder="示例：AI, 一作, Top会议" /></el-form-item>
          <el-form-item label="是否公开"><el-switch v-model="form.isPublic" /></el-form-item>
          <el-form-item label="荣誉描述"><el-input type="textarea" :rows="4" v-model="form.description" /></el-form-item>
        </template>
      </el-form>
      <template #footer>
        <el-button @click="createVisible = false">取消</el-button>
        <el-button type="primary" @click="submitCreate">确定</el-button>
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
  academicType: ['会议', '讲座', '研讨会'],
  activityType: [
    { label: '讲座', value: 'lecture' },
    { label: '会议', value: 'conference' },
    { label: '竞赛', value: 'competition' },
    { label: '荣誉', value: 'honor' }
  ],
  honorCategory: ['竞赛', '论文', '专利', '奖学金', '社会服务', '其他'],
  level: ['国家', '省级', '校级', '院级', '其他']
}

// 模块
const moduleKey = ref('academic')
const moduleTitle = computed(() =>
    moduleKey.value === 'academic' ? '学术活动' : moduleKey.value === 'daily' ? '日常活动' : '荣誉'
)

// 列表数据（按模块存）
const allRows = reactive({ academic: [], daily: [], honor: [] })
const rows = computed(() => allRows[moduleKey.value] || [])
const stat = computed(() => ({
  pending: rows.value.filter(x => x.auditStatus === 'pending').length,
  passed: rows.value.filter(x => x.auditStatus === 'approved').length
}))

// 搜索
const searchKey = ref('')
const applySearch = () => {
  // 如需后端搜索，在此传参请求；当前仅演示
}

// 新增
const createVisible = ref(false)
const createFormRef = ref()
const initAcademic = {
  id: '', studentId: '', studentName: '', title: '', type: '讲座',
  organizer: '', speaker: '', attachmentUrl: '',
  startDate: '', endDate: '',
  auditStatus: 'pending', auditComment: '', auditorId: '', auditTime: '',
  createTime: '', updateTime: ''
}
const initDaily = {
  id: '', studentId: '', studentName: '', activityType: 'lecture', title: '',
  organizer: '', level: '校级', attachmentUrl: '',
  startDate: '', endDate: '',
  auditStatus: 'pending', auditComment: '', auditorId: '', auditTime: '',
  createTime: '', updateTime: ''
}
const initHonor = {
  id: '', userId: '', title: '', category: '竞赛', level: '校级',
  awardOrg: '', awardDate: '', description: '',
  evidenceUrl: '', tags: '', isPublic: false,
  auditStatus: 'pending', auditComment: '', auditorId: '', auditTime: '',
  createTime: '', updateTime: ''
}
const form = reactive({ ...initAcademic })
const rules = reactive({
  title: [{ required: true, message: '请填写标题', trigger: 'blur' }],
  type: [{ required: computed(() => moduleKey.value === 'academic').value, message: '请选择类型', trigger: 'change' }],
  startDate: [{ required: computed(() => moduleKey.value !== 'honor').value, message: '请选择开始时间', trigger: 'change' }],
  awardDate: [{ required: computed(() => moduleKey.value === 'honor').value, message: '请选择获奖日期', trigger: 'change' }]
})
const openCreate = () => {
  createVisible.value = true
  Object.assign(form,
      moduleKey.value === 'academic' ? { ...initAcademic }
          : moduleKey.value === 'daily' ? { ...initDaily }
              : { ...initHonor })
}
const submitCreate = async () => {
  await createFormRef.value.validate()
  const payload = { ...form }
  allRows[moduleKey.value].unshift(payload) // TODO: 替换为真实接口
  createVisible.value = false
  ElMessage.success('提交成功，待审核')
}

// 状态渲染（更人性化）
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

const onTabChange = () => { searchKey.value = '' }

// mock 数据
const loadMock = () => {
  allRows.academic = [
    { title: '机器学习前沿讲座', studentId: '20231234', studentName: '张三', type: '讲座', organizer: '计算机学院', speaker: '李教授', startDate: '2025-03-01', endDate: '2025-03-01', attachmentUrl: '', auditStatus: 'pending' }
  ]
  allRows.daily = [
    { title: '志愿服务-马拉松', studentId: '20239876', studentName: '李四', activityType: 'training', organizer: '校团委', level: '校级', startDate: '2025-04-10', endDate: '2025-04-12', attachmentUrl: '', auditStatus: 'approved' }
  ]
  allRows.honor = [
    { title: '数学建模国赛二等奖', userId: '20230001', category: '竞赛', level: '国家', awardOrg: '教育部', awardDate: '2025-05-15', description: '', evidenceUrl: '', tags: '数学建模, 二作', isPublic: true, auditStatus: 'pending' }
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
