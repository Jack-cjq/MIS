<template>
  <div class="admin-dashboard">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="statistics-cards">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.totalStudents || 0 }}</div>
              <div class="stat-label">学生总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon internship">
              <el-icon><Briefcase /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.internshipCount || 0 }}</div>
              <div class="stat-label">实习记录</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon competition">
              <el-icon><Trophy /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.competitionCount || 0 }}</div>
              <div class="stat-label">竞赛记录</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon alumni">
              <el-icon><Connection /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.alumniCount || 0 }}</div>
              <div class="stat-label">校友数量</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表和详细信息 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>学生专业分布</span>
            </div>
          </template>
          <div class="chart-container">
            <div ref="majorChart" style="height: 300px;"></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>系统概览</span>
            </div>
          </template>
          <div class="overview-list">
            <div class="overview-item">
              <span class="overview-label">在线用户</span>
              <span class="overview-value">{{ systemInfo.onlineUsers }}</span>
            </div>
            <div class="overview-item">
              <span class="overview-label">今日访问</span>
              <span class="overview-value">{{ systemInfo.todayVisits }}</span>
            </div>
            <div class="overview-item">
              <span class="overview-label">系统运行时间</span>
              <span class="overview-value">{{ systemInfo.uptime }}</span>
            </div>
            <div class="overview-item">
              <span class="overview-label">数据库状态</span>
              <span class="overview-value status-normal">正常</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最新动态和待审批 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>最新动态</span>
            </div>
          </template>
          <el-timeline>
            <el-timeline-item
              v-for="activity in recentActivities"
              :key="activity.id"
              :timestamp="activity.time"
              placement="top"
            >
              <el-card>
                <h4>{{ activity.title }}</h4>
                <p>{{ activity.description }}</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>待审批事项</span>
            </div>
          </template>
          <div class="approval-list">
            <div
              v-for="approval in pendingApprovals"
              :key="approval.id"
              class="approval-item"
            >
              <div class="approval-info">
                <div class="approval-title">{{ approval.title }}</div>
                <div class="approval-desc">{{ approval.description }}</div>
                <div class="approval-time">{{ approval.time }}</div>
              </div>
              <div class="approval-actions">
                <el-button size="small" type="success" @click="handleApprove(approval)">
                  通过
                </el-button>
                <el-button size="small" type="danger" @click="handleReject(approval)">
                  拒绝
                </el-button>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快速操作 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>快速操作</span>
            </div>
          </template>
          <div class="quick-actions">
            <el-button type="primary" @click="goToStudents">
              <el-icon><User /></el-icon>
              学生管理
            </el-button>
            <el-button type="success" @click="goToInternships">
              <el-icon><Briefcase /></el-icon>
              实习就业管理
            </el-button>
            <el-button type="warning" @click="goToCompetitions">
              <el-icon><Trophy /></el-icon>
              竞赛管理
            </el-button>
            <el-button type="info" @click="goToAlumni">
              <el-icon><Connection /></el-icon>
              校友管理
            </el-button>
            <el-button @click="goToSystem">
              <el-icon><Setting /></el-icon>
              系统设置
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { 
  User, 
  Briefcase, 
  Trophy, 
  Connection, 
  Setting 
} from '@element-plus/icons-vue'
import * as echarts from 'echarts'

const router = useRouter()

// 统计数据
const statistics = ref({
  totalStudents: 0,
  internshipCount: 0,
  competitionCount: 0,
  alumniCount: 0
})

// 系统信息
const systemInfo = ref({
  onlineUsers: 15,
  todayVisits: 234,
  uptime: '7天 12小时',
  dbStatus: '正常'
})

// 最近活动
const recentActivities = ref([
  {
    id: 1,
    title: '新学生注册',
    description: '张三同学完成了系统注册',
    time: '2024-01-15 10:30'
  },
  {
    id: 2,
    title: '实习申请提交',
    description: '李四同学提交了实习申请',
    time: '2024-01-15 09:15'
  },
  {
    id: 3,
    title: '竞赛获奖',
    description: '王五同学在ACM竞赛中获得一等奖',
    time: '2024-01-14 16:20'
  }
])

// 待审批事项
const pendingApprovals = ref([
  {
    id: 1,
    title: '实习申请',
    description: '张三申请腾讯公司实习岗位',
    time: '2024-01-15 10:30'
  },
  {
    id: 2,
    title: '竞赛报名',
    description: '李四报名参加编程竞赛',
    time: '2024-01-15 09:15'
  }
])

// 图表引用
const majorChart = ref(null)

// 加载统计数据
const loadStatistics = async () => {
  try {
    // 这里可以调用后端API获取真实数据
    statistics.value = {
      totalStudents: 1250,
      internshipCount: 45,
      competitionCount: 12,
      alumniCount: 320
    }
  } catch (error) {
    console.error('加载统计数据失败:', error)
  }
}

// 初始化图表
const initChart = () => {
  if (majorChart.value) {
    const chart = echarts.init(majorChart.value)
    const option = {
      title: {
        text: '学生专业分布',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: '专业分布',
          type: 'pie',
          radius: '50%',
          data: [
            { value: 400, name: '计算机科学与技术' },
            { value: 300, name: '软件工程' },
            { value: 200, name: '信息安全' },
            { value: 150, name: '人工智能' },
            { value: 100, name: '其他' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    }
    chart.setOption(option)
  }
}

// 处理审批
const handleApprove = (approval) => {
  console.log('审批通过:', approval)
  // 这里可以调用后端API进行审批
}

const handleReject = (approval) => {
  console.log('审批拒绝:', approval)
  // 这里可以调用后端API进行审批
}

// 快速操作跳转
const goToStudents = () => {
  router.push('/admin/students/list')
}

const goToInternships = () => {
  router.push('/admin/internship/list')
}

const goToCompetitions = () => {
  router.push('/admin/competition/list')
}

const goToAlumni = () => {
  router.push('/admin/alumni/list')
}

const goToSystem = () => {
  router.push('/admin/system')
}

onMounted(() => {
  loadStatistics()
  setTimeout(() => {
    initChart()
  }, 100)
})
</script>

<style scoped>
.admin-dashboard {
  padding: 20px;
}

.statistics-cards {
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 10px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  font-size: 24px;
  color: white;
}

.stat-icon:not(.internship):not(.competition):not(.alumni) {
  background-color: #409eff;
}

.stat-icon.internship {
  background-color: #67c23a;
}

.stat-icon.competition {
  background-color: #e6a23c;
}

.stat-icon.alumni {
  background-color: #f56c6c;
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chart-container {
  height: 300px;
}

.overview-list {
  max-height: 300px;
  overflow-y: auto;
}

.overview-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.overview-item:last-child {
  border-bottom: none;
}

.overview-label {
  font-size: 14px;
  color: #606266;
}

.overview-value {
  font-size: 14px;
  font-weight: bold;
  color: #303133;
}

.status-normal {
  color: #67c23a;
}

.approval-list {
  max-height: 300px;
  overflow-y: auto;
}

.approval-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.approval-item:last-child {
  border-bottom: none;
}

.approval-info {
  flex: 1;
}

.approval-title {
  font-size: 14px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 4px;
}

.approval-desc {
  font-size: 12px;
  color: #606266;
  margin-bottom: 4px;
}

.approval-time {
  font-size: 12px;
  color: #909399;
}

.approval-actions {
  display: flex;
  gap: 8px;
}

.quick-actions {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.quick-actions .el-button {
  display: flex;
  align-items: center;
  gap: 5px;
}
</style> 