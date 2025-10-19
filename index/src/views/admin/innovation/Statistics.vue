<template>
  <div class="statistics-page">
    <!-- 顶部概览卡片 -->
    <el-row :gutter="20" class="overview-section">
      <el-col :span="6">
        <el-card shadow="hover" class="overview-card competition-card">
          <div class="card-content">
            <div class="icon-wrapper">
              <el-icon class="card-icon"><Trophy /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">竞赛总数</div>
              <div class="card-number">{{ stats.competition.total }}</div>
              <div class="card-detail">
                待审核 {{ stats.competition.pending }} | 已通过 {{ stats.competition.approved }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card shadow="hover" class="overview-card project-card">
          <div class="card-content">
            <div class="icon-wrapper">
              <el-icon class="card-icon"><Operation /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">项目总数</div>
              <div class="card-number">{{ stats.project.total }}</div>
              <div class="card-detail">
                待审核 {{ stats.project.pending }} | 已通过 {{ stats.project.approved }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card shadow="hover" class="overview-card paper-card">
          <div class="card-content">
            <div class="icon-wrapper">
              <el-icon class="card-icon"><Document /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">论文总数</div>
              <div class="card-number">{{ stats.paper.total }}</div>
              <div class="card-detail">
                待审核 {{ stats.paper.pending }} | 已通过 {{ stats.paper.approved }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card shadow="hover" class="overview-card patent-card">
          <div class="card-content">
            <div class="icon-wrapper">
              <el-icon class="card-icon"><Files /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">专利总数</div>
              <div class="card-number">{{ stats.patent.total }}</div>
              <div class="card-detail">
                待审核 {{ stats.patent.pending }} | 已通过 {{ stats.patent.approved }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-section">
      <!-- 竞赛级别分布 -->
      <el-col :span="12">
        <el-card shadow="never" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>竞赛级别分布</span>
              <el-button type="primary" link @click="refreshCompetitionChart">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <div ref="competitionChartRef" class="chart-container" v-loading="loading.competition"></div>
        </el-card>
      </el-col>

      <!-- 项目级别分布 -->
      <el-col :span="12">
        <el-card shadow="never" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>项目级别分布</span>
              <el-button type="primary" link @click="refreshProjectChart">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <div ref="projectChartRef" class="chart-container" v-loading="loading.project"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="chart-section">
      <!-- 论文期刊级别分布 -->
      <el-col :span="12">
        <el-card shadow="never" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>论文期刊级别分布</span>
              <el-button type="primary" link @click="refreshPaperChart">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <div ref="paperChartRef" class="chart-container" v-loading="loading.paper"></div>
        </el-card>
      </el-col>

      <!-- 专利类型分布 -->
      <el-col :span="12">
        <el-card shadow="never" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>专利类型分布</span>
              <el-button type="primary" link @click="refreshPatentChart">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <div ref="patentChartRef" class="chart-container" v-loading="loading.patent"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 时间趋势图 -->
    <el-row :gutter="20" class="chart-section">
      <el-col :span="24">
        <el-card shadow="never" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>月度趋势统计</span>
              <div>
                <el-radio-group v-model="trendType" size="small" @change="refreshTrendChart">
                  <el-radio-button label="competition">竞赛</el-radio-button>
                  <el-radio-button label="project">项目</el-radio-button>
                  <el-radio-button label="paper">论文</el-radio-button>
                  <el-radio-button label="patent">专利</el-radio-button>
                </el-radio-group>
                <el-button type="primary" link @click="refreshTrendChart" style="margin-left: 10px;">
                  <el-icon><Refresh /></el-icon>
                  刷新
                </el-button>
              </div>
            </div>
          </template>
          <div ref="trendChartRef" class="trend-chart-container" v-loading="loading.trend"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 排行榜 -->
    <el-row :gutter="20" class="rank-section">
      <el-col :span="12">
        <el-card shadow="never" class="rank-card">
          <template #header>
            <div class="card-header">
              <span>🏆 学生科创成果排行榜（Top 10）</span>
              <el-button type="primary" link @click="refreshRankData">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <el-table :data="rankData.students" v-loading="loading.rank" max-height="400">
            <el-table-column type="index" label="排名" width="70" align="center">
              <template #default="{ $index }">
                <el-tag v-if="$index === 0" type="danger" effect="dark" size="small">🥇</el-tag>
                <el-tag v-else-if="$index === 1" type="warning" effect="dark" size="small">🥈</el-tag>
                <el-tag v-else-if="$index === 2" type="success" effect="dark" size="small">🥉</el-tag>
                <span v-else>{{ $index + 1 }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="studentName" label="姓名" width="120" align="center" />
            <el-table-column prop="studentId" label="学号" width="130" align="center" />
            <el-table-column label="成果统计" align="center">
              <template #default="{ row }">
                <el-space>
                  <el-tag type="danger" size="small">竞赛 {{ row.competitionCount }}</el-tag>
                  <el-tag type="warning" size="small">项目 {{ row.projectCount }}</el-tag>
                  <el-tag type="success" size="small">论文 {{ row.paperCount }}</el-tag>
                  <el-tag type="info" size="small">专利 {{ row.patentCount }}</el-tag>
                </el-space>
              </template>
            </el-table-column>
            <el-table-column prop="totalCount" label="总计" width="80" align="center" sortable>
              <template #default="{ row }">
                <el-tag type="primary" effect="dark">{{ row.totalCount }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="12">
        <el-card shadow="never" class="rank-card">
          <template #header>
            <div class="card-header">
              <span>🎯 指导教师排行榜（Top 10）</span>
              <el-button type="primary" link @click="refreshRankData">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <el-table :data="rankData.instructors" v-loading="loading.rank" max-height="400">
            <el-table-column type="index" label="排名" width="70" align="center">
              <template #default="{ $index }">
                <el-tag v-if="$index === 0" type="danger" effect="dark" size="small">🥇</el-tag>
                <el-tag v-else-if="$index === 1" type="warning" effect="dark" size="small">🥈</el-tag>
                <el-tag v-else-if="$index === 2" type="success" effect="dark" size="small">🥉</el-tag>
                <span v-else>{{ $index + 1 }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="instructorName" label="教师姓名" width="120" align="center" />
            <el-table-column label="指导统计" align="center">
              <template #default="{ row }">
                <el-space>
                  <el-tag type="danger" size="small">竞赛 {{ row.competitionCount }}</el-tag>
                  <el-tag type="warning" size="small">项目 {{ row.projectCount }}</el-tag>
                  <el-tag type="success" size="small">论文 {{ row.paperCount }}</el-tag>
                  <el-tag type="info" size="small">专利 {{ row.patentCount }}</el-tag>
                </el-space>
              </template>
            </el-table-column>
            <el-table-column prop="totalCount" label="总计" width="80" align="center" sortable>
              <template #default="{ row }">
                <el-tag type="primary" effect="dark">{{ row.totalCount }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted, nextTick } from 'vue'
import { ElMessage } from 'element-plus'
import { Trophy, Operation, Document, Files, Refresh } from '@element-plus/icons-vue'
import * as echarts from 'echarts'
import { 
  getAllCompetitions, 
  getAllProjects, 
  getAllPapers, 
  getAllPatents 
} from '@/api/competition'

// 统计数据
const stats = reactive({
  competition: { total: 0, pending: 0, approved: 0, rejected: 0 },
  project: { total: 0, pending: 0, approved: 0, rejected: 0 },
  paper: { total: 0, pending: 0, approved: 0, rejected: 0 },
  patent: { total: 0, pending: 0, approved: 0, rejected: 0 }
})

// 加载状态
const loading = reactive({
  competition: false,
  project: false,
  paper: false,
  patent: false,
  trend: false,
  rank: false
})

// 图表引用
const competitionChartRef = ref(null)
const projectChartRef = ref(null)
const paperChartRef = ref(null)
const patentChartRef = ref(null)
const trendChartRef = ref(null)

// 图表实例
let competitionChart = null
let projectChart = null
let paperChart = null
let patentChart = null
let trendChart = null

// 趋势图类型
const trendType = ref('competition')

// 排行榜数据
const rankData = reactive({
  students: [],
  instructors: []
})

// 原始数据
const rawData = reactive({
  competitions: [],
  projects: [],
  papers: [],
  patents: []
})

// 加载所有数据
const loadAllData = async () => {
  try {
    const [compRes, projRes, paperRes, patentRes] = await Promise.all([
      getAllCompetitions(),
      getAllProjects(),
      getAllPapers(),
      getAllPatents()
    ])

    console.log('统计页面数据返回:', {
      competitions: compRes,
      projects: projRes,
      papers: paperRes,
      patents: patentRes
    })

    rawData.competitions = Array.isArray(compRes) ? compRes : []
    rawData.projects = Array.isArray(projRes) ? projRes : []
    rawData.papers = Array.isArray(paperRes) ? paperRes : []
    rawData.patents = Array.isArray(patentRes) ? patentRes : []

    console.log('原始数据:', rawData)

    calculateStats()
    await nextTick()
    initAllCharts()
    calculateRankData()
    
    ElMessage.success('数据加载成功')
  } catch (error) {
    console.error('加载数据失败', error)
    ElMessage.error('加载数据失败: ' + error.message)
  }
}
// 计算统计数据
const calculateStats = () => {
  const calculate = (data) => ({
    total: data.length,
    pending: data.filter(item => !item.auditStatus || item.auditStatus === '待审核').length,
    approved: data.filter(item => item.auditStatus === '已通过').length,
    rejected: data.filter(item => item.auditStatus === '已驳回').length
  })

  stats.competition = calculate(rawData.competitions)
  stats.project = calculate(rawData.projects)
  stats.paper = calculate(rawData.papers)
  stats.patent = calculate(rawData.patents)
}

// 初始化所有图表
const initAllCharts = () => {
  initCompetitionChart()
  initProjectChart()
  initPaperChart()
  initPatentChart()
  initTrendChart()
}

// 竞赛级别分布图
const initCompetitionChart = () => {
  if (!competitionChartRef.value) return

  const levelCount = {}
  rawData.competitions.forEach(item => {
    const level = item.competitionLevel || '未知'
    levelCount[level] = (levelCount[level] || 0) + 1
  })

  const data = Object.entries(levelCount).map(([name, value]) => ({ name, value }))

  if (competitionChart) {
    competitionChart.dispose()
  }
  competitionChart = echarts.init(competitionChartRef.value)

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      right: 10,
      top: 'center'
    },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: true,
        formatter: '{b}: {c}'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 16,
          fontWeight: 'bold'
        }
      },
      data: data,
      color: ['#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de']
    }]
  }

  competitionChart.setOption(option)
}

// 项目级别分布图
const initProjectChart = () => {
  if (!projectChartRef.value) return

  const levelCount = {}
  rawData.projects.forEach(item => {
    const level = item.projectLevel || '未知'
    levelCount[level] = (levelCount[level] || 0) + 1
  })

  const data = Object.entries(levelCount).map(([name, value]) => ({ name, value }))

  if (projectChart) {
    projectChart.dispose()
  }
  projectChart = echarts.init(projectChartRef.value)

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      right: 10,
      top: 'center'
    },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: true,
        formatter: '{b}: {c}'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 16,
          fontWeight: 'bold'
        }
      },
      data: data,
      color: ['#fac858', '#91cc75', '#5470c6', '#ee6666', '#73c0de']
    }]
  }

  projectChart.setOption(option)
}

// 论文期刊级别分布图
const initPaperChart = () => {
  if (!paperChartRef.value) return

  const levelCount = {}
  rawData.papers.forEach(item => {
    const level = item.journalLevel || '未知'
    levelCount[level] = (levelCount[level] || 0) + 1
  })

  const data = Object.entries(levelCount).map(([name, value]) => ({ name, value }))

  if (paperChart) {
    paperChart.dispose()
  }
  paperChart = echarts.init(paperChartRef.value)

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      right: 10,
      top: 'center'
    },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: true,
        formatter: '{b}: {c}'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 16,
          fontWeight: 'bold'
        }
      },
      data: data,
      color: ['#91cc75', '#5470c6', '#fac858', '#ee6666', '#73c0de']
    }]
  }

  paperChart.setOption(option)
}

// 专利类型分布图
const initPatentChart = () => {
  if (!patentChartRef.value) return

  const typeCount = {}
  rawData.patents.forEach(item => {
    const type = item.patentType || '未知'
    typeCount[type] = (typeCount[type] || 0) + 1
  })

  const data = Object.entries(typeCount).map(([name, value]) => ({ name, value }))

  if (patentChart) {
    patentChart.dispose()
  }
  patentChart = echarts.init(patentChartRef.value)

  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      right: 10,
      top: 'center'
    },
    series: [{
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: true,
        formatter: '{b}: {c}'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 16,
          fontWeight: 'bold'
        }
      },
      data: data,
      color: ['#ee6666', '#5470c6', '#fac858', '#91cc75', '#73c0de']
    }]
  }

  patentChart.setOption(option)
}

// 趋势图
const initTrendChart = () => {
  if (!trendChartRef.value) return

  const dataMap = {
    competition: rawData.competitions,
    project: rawData.projects,
    paper: rawData.papers,
    patent: rawData.patents
  }

  const currentData = dataMap[trendType.value]
  
  // 按月统计
  const monthCount = {}
  currentData.forEach(item => {
    if (item.createTime) {
      const month = new Date(item.createTime).toISOString().slice(0, 7)
      monthCount[month] = (monthCount[month] || 0) + 1
    }
  })

  // 获取最近12个月
  const months = []
  const now = new Date()
  for (let i = 11; i >= 0; i--) {
    const d = new Date(now.getFullYear(), now.getMonth() - i, 1)
    months.push(d.toISOString().slice(0, 7))
  }

  const data = months.map(month => monthCount[month] || 0)

  if (trendChart) {
    trendChart.dispose()
  }
  trendChart = echarts.init(trendChartRef.value)

  const titleMap = {
    competition: '竞赛',
    project: '项目',
    paper: '论文',
    patent: '专利'
  }

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: months,
      axisLabel: {
        rotate: 45
      }
    },
    yAxis: {
      type: 'value',
      minInterval: 1
    },
    series: [{
      name: titleMap[trendType.value],
      type: 'line',
      smooth: true,
      areaStyle: {
        opacity: 0.3
      },
      data: data,
      itemStyle: {
        color: '#5470c6'
      }
    }]
  }

  trendChart.setOption(option)
}

// 计算排行榜数据
const calculateRankData = () => {
  // 学生排行
  const studentMap = {}
  
  const processData = (data, key) => {
    data.forEach(item => {
      if (item.studentId && item.auditStatus === '已通过') {
        if (!studentMap[item.studentId]) {
          studentMap[item.studentId] = {
            studentId: item.studentId,
            studentName: item.studentName,
            competitionCount: 0,
            projectCount: 0,
            paperCount: 0,
            patentCount: 0,
            totalCount: 0
          }
        }
        studentMap[item.studentId][key]++
        studentMap[item.studentId].totalCount++
      }
    })
  }

  processData(rawData.competitions, 'competitionCount')
  processData(rawData.projects, 'projectCount')
  processData(rawData.papers, 'paperCount')
  processData(rawData.patents, 'patentCount')

  rankData.students = Object.values(studentMap)
    .sort((a, b) => b.totalCount - a.totalCount)
    .slice(0, 10)

  // 教师排行
  const instructorMap = {}
  
  const processInstructorData = (data, key) => {
    data.forEach(item => {
      if (item.instructorName && item.auditStatus === '已通过') {
        if (!instructorMap[item.instructorName]) {
          instructorMap[item.instructorName] = {
            instructorName: item.instructorName,
            competitionCount: 0,
            projectCount: 0,
            paperCount: 0,
            patentCount: 0,
            totalCount: 0
          }
        }
        instructorMap[item.instructorName][key]++
        instructorMap[item.instructorName].totalCount++
      }
    })
  }

  processInstructorData(rawData.competitions, 'competitionCount')
  processInstructorData(rawData.projects, 'projectCount')
  processInstructorData(rawData.papers, 'paperCount')
  processInstructorData(rawData.patents, 'patentCount')

  rankData.instructors = Object.values(instructorMap)
    .sort((a, b) => b.totalCount - a.totalCount)
    .slice(0, 10)
}

// 刷新各个图表
const refreshCompetitionChart = () => {
  loading.competition = true
  setTimeout(() => {
    initCompetitionChart()
    loading.competition = false
  }, 500)
}

const refreshProjectChart = () => {
  loading.project = true
  setTimeout(() => {
    initProjectChart()
    loading.project = false
  }, 500)
}

const refreshPaperChart = () => {
  loading.paper = true
  setTimeout(() => {
    initPaperChart()
    loading.paper = false
  }, 500)
}

const refreshPatentChart = () => {
  loading.patent = true
  setTimeout(() => {
    initPatentChart()
    loading.patent = false
  }, 500)
}

const refreshTrendChart = () => {
  loading.trend = true
  setTimeout(() => {
    initTrendChart()
    loading.trend = false
  }, 500)
}

const refreshRankData = () => {
  loading.rank = true
  setTimeout(() => {
    calculateRankData()
    loading.rank = false
  }, 500)
}

// 窗口大小改变时重新渲染图表
const handleResize = () => {
  competitionChart?.resize()
  projectChart?.resize()
  paperChart?.resize()
  patentChart?.resize()
  trendChart?.resize()
}

onMounted(() => {
  loadAllData()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  competitionChart?.dispose()
  projectChart?.dispose()
  paperChart?.dispose()
  patentChart?.dispose()
  trendChart?.dispose()
})
</script>

<style scoped>
.statistics-page {
  padding: 20px;
}

/* 概览卡片 */
.overview-section {
  margin-bottom: 20px;
}

.overview-card {
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  border-top: 3px solid;
}

.overview-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.competition-card {
  border-top-color: #f56c6c;
}

.project-card {
  border-top-color: #e6a23c;
}

.paper-card {
  border-top-color: #67c23a;
}

.patent-card {
  border-top-color: #409eff;
}

.card-content {
  display: flex;
  align-items: center;
}

.icon-wrapper {
  margin-right: 20px;
}

.card-icon {
  font-size: 50px;
}

.competition-card .card-icon {
  color: #f56c6c;
}

.project-card .card-icon {
  color: #e6a23c;
}

.paper-card .card-icon {
  color: #67c23a;
}

.patent-card .card-icon {
  color: #409eff;
}

.card-info {
  flex: 1;
}

.card-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.card-number {
  font-size: 32px;
  font-weight: bold;
  color: #303133;
  line-height: 1;
  margin-bottom: 8px;
}

.card-detail {
  font-size: 12px;
  color: #909399;
}

/* 图表区域 */
.chart-section {
  margin-bottom: 20px;
}

.chart-card {
  height: 100%;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: 500;
}

.chart-container {
  width: 100%;
  height: 350px;
}

.trend-chart-container {
  width: 100%;
  height: 400px;
}

/* 排行榜 */
.rank-section {
  margin-top: 20px;
}

.rank-card {
  height: 100%;
}

:deep(.el-table .cell) {
  padding: 8px 0;
}
</style>