<template>
  <div class="admin-dashboard">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :span="6">
        <el-card class="stats-card">
          <div class="stats-content">
            <div class="stats-icon student">
              <el-icon><User /></el-icon>
            </div>
            <div class="stats-info">
              <div class="stats-number">{{ stats.studentCount }}</div>
              <div class="stats-label">学生总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stats-card">
          <div class="stats-content">
            <div class="stats-icon alumni">
              <el-icon><Avatar /></el-icon>
            </div>
            <div class="stats-info">
              <div class="stats-number">{{ stats.alumniCount }}</div>
              <div class="stats-label">校友总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stats-card">
          <div class="stats-content">
            <div class="stats-icon internship">
              <el-icon><Briefcase /></el-icon>
            </div>
            <div class="stats-info">
              <div class="stats-number">{{ stats.internshipCount }}</div>
              <div class="stats-label">实习记录</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card class="stats-card">
          <div class="stats-content">
            <div class="stats-icon competition">
              <el-icon><Trophy /></el-icon>
            </div>
            <div class="stats-info">
              <div class="stats-number">{{ stats.competitionCount }}</div>
              <div class="stats-label">竞赛记录</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="charts-row">
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>学生专业分布</span>
            </div>
          </template>
          <div class="chart-container">
            <div ref="majorChart" class="chart"></div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>实习就业趋势</span>
            </div>
          </template>
          <div class="chart-container">
            <div ref="trendChart" class="chart"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最新动态 -->
    <el-row :gutter="20" class="activity-row">
      <el-col :span="12">
        <el-card class="activity-card">
          <template #header>
            <div class="card-header">
              <span>最新学生</span>
            </div>
          </template>
          <div class="activity-list">
            <div v-for="student in latestStudents" :key="student.id" class="activity-item">
              <el-avatar :size="32">{{ student.name?.charAt(0) }}</el-avatar>
              <div class="activity-info">
                <div class="activity-title">{{ student.name }}</div>
                <div class="activity-desc">{{ student.major }} - {{ student.grade }}</div>
              </div>
              <div class="activity-time">{{ formatTime(student.createTime) }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card class="activity-card">
          <template #header>
            <div class="card-header">
              <span>系统公告</span>
            </div>
          </template>
          <div class="notice-list">
            <div v-for="notice in notices" :key="notice.id" class="notice-item">
              <el-icon class="notice-icon"><Bell /></el-icon>
              <div class="notice-content">
                <div class="notice-title">{{ notice.title }}</div>
                <div class="notice-time">{{ formatTime(notice.createTime) }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { useStore } from 'vuex'
import { ElMessage } from 'element-plus'
import { User, Avatar, Briefcase, Trophy, Bell } from '@element-plus/icons-vue'
import * as echarts from 'echarts'

const store = useStore()

// 统计数据
const stats = ref({
  studentCount: 0,
  alumniCount: 0,
  internshipCount: 0,
  competitionCount: 0
})

// 最新学生
const latestStudents = ref([])

// 系统公告
const notices = ref([
  {
    id: 1,
    title: '系统维护通知',
    createTime: new Date('2024-01-15')
  },
  {
    id: 2,
    title: '新功能上线',
    createTime: new Date('2024-01-10')
  }
])

// 图表引用
const majorChart = ref(null)
const trendChart = ref(null)

// 格式化时间
const formatTime = (time) => {
  if (!time) return ''
  const date = new Date(time)
  return date.toLocaleDateString()
}

// 初始化图表
const initCharts = () => {
  // 专业分布图
  const majorChartInstance = echarts.init(majorChart.value)
  const majorOption = {
    tooltip: {
      trigger: 'item'
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
          { value: 35, name: '计算机科学与技术' },
          { value: 28, name: '软件工程' },
          { value: 22, name: '信息安全' },
          { value: 15, name: '其他专业' }
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
  majorChartInstance.setOption(majorOption)

  // 趋势图
  const trendChartInstance = echarts.init(trendChart.value)
  const trendOption = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['实习人数', '就业人数']
    },
    xAxis: {
      type: 'category',
      data: ['1月', '2月', '3月', '4月', '5月', '6月']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '实习人数',
        type: 'line',
        data: [12, 19, 15, 25, 30, 35]
      },
      {
        name: '就业人数',
        type: 'line',
        data: [8, 15, 12, 20, 25, 30]
      }
    ]
  }
  trendChartInstance.setOption(trendOption)
}

// 加载统计数据
const loadStats = async () => {
  try {
    // 这里应该调用实际的API
    stats.value = {
      studentCount: 1250,
      alumniCount: 850,
      internshipCount: 320,
      competitionCount: 180
    }
  } catch (error) {
    ElMessage.error('加载统计数据失败')
  }
}

// 加载最新学生
const loadLatestStudents = async () => {
  try {
    // 模拟数据
    latestStudents.value = [
      {
        id: 1,
        name: '张三',
        major: '计算机科学与技术',
        grade: '2024级',
        createTime: new Date('2024-01-15')
      },
      {
        id: 2,
        name: '李四',
        major: '软件工程',
        grade: '2024级',
        createTime: new Date('2024-01-14')
      }
    ]
  } catch (error) {
    ElMessage.error('加载最新学生失败')
  }
}

onMounted(() => {
  loadStats()
  loadLatestStudents()
  initCharts()
})
</script>

<style scoped>
.admin-dashboard {
  padding: 20px;
}

.stats-row {
  margin-bottom: 20px;
}

.stats-card {
  height: 120px;
}

.stats-content {
  display: flex;
  align-items: center;
  height: 100%;
}

.stats-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  font-size: 24px;
  color: white;
}

.stats-icon.student {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stats-icon.alumni {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stats-icon.internship {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stats-icon.competition {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stats-info {
  flex: 1;
}

.stats-number {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.stats-label {
  font-size: 14px;
  color: #909399;
}

.charts-row {
  margin-bottom: 20px;
}

.chart-card {
  height: 400px;
}

.chart-container {
  height: 320px;
}

.chart {
  width: 100%;
  height: 100%;
}

.activity-row {
  margin-bottom: 20px;
}

.activity-card {
  height: 400px;
}

.activity-list {
  max-height: 320px;
  overflow-y: auto;
}

.activity-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.activity-item:last-child {
  border-bottom: none;
}

.activity-info {
  flex: 1;
  margin-left: 15px;
}

.activity-title {
  font-weight: 500;
  color: #303133;
  margin-bottom: 5px;
}

.activity-desc {
  font-size: 12px;
  color: #909399;
}

.activity-time {
  font-size: 12px;
  color: #c0c4cc;
}

.notice-list {
  max-height: 320px;
  overflow-y: auto;
}

.notice-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
}

.notice-item:last-child {
  border-bottom: none;
}

.notice-icon {
  color: #409eff;
  margin-right: 15px;
  font-size: 16px;
}

.notice-content {
  flex: 1;
}

.notice-title {
  font-weight: 500;
  color: #303133;
  margin-bottom: 5px;
}

.notice-time {
  font-size: 12px;
  color: #c0c4cc;
}

.card-header {
  font-weight: 500;
  color: #303133;
}
</style> 