/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="home">
    <!-- 欢迎信息 -->
    <el-card style="margin-bottom: 20px;">
      <div class="welcome-section">
        <div class="welcome-info">
          <h2>欢迎回来，{{ userInfo.name }}！</h2>
          <p>学号：{{ userInfo.studentId }} | 专业：{{ userInfo.major }} | 年级：{{ userInfo.grade }}</p>
        </div>
        <div class="welcome-time">
          <p>{{ currentTime }}</p>
        </div>
      </div>
    </el-card>

    <!-- 个人统计卡片 -->
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon><Document /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ personalStats.internships }}</div>
              <div class="stat-label">我的实习</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon><Trophy /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ personalStats.competitions }}</div>
              <div class="stat-label">我的竞赛</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon><Document /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ personalStats.papers }}</div>
              <div class="stat-label">我的论文</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon><Connection /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ personalStats.projects }}</div>
              <div class="stat-label">我的项目</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>我的最近活动</span>
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
              <span>待办事项</span>
            </div>
          </template>
          <div class="todo-list">
            <div
              v-for="todo in todoList"
              :key="todo.id"
              class="todo-item"
            >
              <el-checkbox v-model="todo.completed" @change="handleTodoChange(todo)">
                {{ todo.title }}
              </el-checkbox>
              <span class="todo-time">{{ todo.time }}</span>
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
            <el-button type="primary" @click="goToInternship">
              <el-icon><Briefcase /></el-icon>
              申请实习
            </el-button>
            <el-button type="success" @click="goToCompetition">
              <el-icon><Trophy /></el-icon>
              参加竞赛
            </el-button>
            <el-button type="warning" @click="goToPaper">
              <el-icon><Document /></el-icon>
              提交论文
            </el-button>
            <el-button type="info" @click="goToProject">
              <el-icon><Connection /></el-icon>
              项目管理
            </el-button>
            <el-button @click="goToProfile">
              <el-icon><User /></el-icon>
              个人信息
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { 
  User, 
  Briefcase, 
  Trophy, 
  Connection, 
  Document 
} from '@element-plus/icons-vue'
import { getHomeData, getPersonalStats } from '@/api/home'

const store = useStore()
const router = useRouter()

// 用户信息
const userInfo = computed(() => store.state.user || {})

// 当前时间
const currentTime = ref('')

// 个人统计数据
const personalStats = ref({
  internships: 0,
  competitions: 0,
  papers: 0,
  projects: 0
})

// 最近活动
const recentActivities = ref([])

// 待办事项
const todoList = ref([
  {
    id: 1,
    title: '完善个人信息',
    completed: false,
    time: '今天'
  },
  {
    id: 2,
    title: '提交实习申请',
    completed: false,
    time: '明天'
  },
  {
    id: 3,
    title: '参加科创竞赛',
    completed: false,
    time: '本周'
  }
])

// 更新时间
const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

// 加载个人数据
const loadPersonalData = async () => {
  try {
    // 获取当前登录学生的ID
    const studentId = userInfo.value.studentId
    if (!studentId) {
      console.error('无法获取当前用户学号')
      return
    }
    
    const response = await getPersonalStats(studentId)
    if (response.code === 200) {
      const data = response.data
      personalStats.value = {
        internships: data.internshipCount || 0,
        competitions: data.competitionCount || 0,
        papers: data.paperCount || 0,
        projects: data.projectCount || 0
      }
    }
  } catch (error) {
    console.error('加载个人数据失败:', error)
    // 使用模拟数据
    personalStats.value = {
      internships: 2,
      competitions: 1,
      papers: 0,
      projects: 1
    }
  }
}

// 加载最近活动
const loadRecentActivities = () => {
  recentActivities.value = [
    {
      id: 1,
      title: '提交实习申请',
      description: '已向腾讯公司提交实习申请，等待审核',
      time: '2024-01-15 10:30'
    },
    {
      id: 2,
      title: '参加编程竞赛',
      description: '参加了ACM程序设计竞赛，获得三等奖',
      time: '2024-01-14 15:20'
    },
    {
      id: 3,
      title: '更新个人信息',
      description: '更新了个人基本信息和联系方式',
      time: '2024-01-13 09:15'
    }
  ]
}

// 处理待办事项变化
const handleTodoChange = (todo) => {
  console.log('待办事项状态变化:', todo)
}

// 快速操作跳转
const goToInternship = () => {
  router.push('/internship')
}

const goToCompetition = () => {
  router.push('/competition')
}

const goToPaper = () => {
  router.push('/paper')
}

const goToProject = () => {
  router.push('/project')
}

const goToProfile = () => {
  router.push('/profile')
}

onMounted(() => {
  updateTime()
  loadPersonalData()
  loadRecentActivities()
  
  // 每秒更新时间
  setInterval(updateTime, 1000)
})
</script>

<style scoped>
.home {
  padding: 20px;
}

.welcome-section {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.welcome-info h2 {
  margin: 0 0 10px 0;
  color: #303133;
}

.welcome-info p {
  margin: 0;
  color: #606266;
  font-size: 14px;
}

.welcome-time p {
  margin: 0;
  color: #909399;
  font-size: 14px;
}

.stat-card {
  height: 120px;
}

.stat-content {
  display: flex;
  align-items: center;
  height: 100%;
}

.stat-icon {
  font-size: 48px;
  color: #409EFF;
  margin-right: 20px;
}

.stat-info {
  flex: 1;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  color: #303133;
  line-height: 1;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-top: 8px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.todo-list {
  max-height: 300px;
  overflow-y: auto;
}

.todo-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.todo-item:last-child {
  border-bottom: none;
}

.todo-time {
  font-size: 12px;
  color: #909399;
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