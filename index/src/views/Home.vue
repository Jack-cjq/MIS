/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="home">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.students }}</div>
              <div class="stat-label">学生总数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card">
          <div class="stat-content">
            <div class="stat-icon">
              <el-icon><Briefcase /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-number">{{ stats.internships }}</div>
              <div class="stat-label">实习岗位</div>
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
              <div class="stat-number">{{ stats.competitions }}</div>
              <div class="stat-label">竞赛项目</div>
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
              <div class="stat-number">{{ stats.alumni }}</div>
              <div class="stat-label">校友数量</div>
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
              <span>最近活动</span>
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
              <span>系统公告</span>
            </div>
          </template>
          <div class="announcement-list">
            <div
              v-for="announcement in announcements"
              :key="announcement.id"
              class="announcement-item"
            >
              <div class="announcement-title">{{ announcement.title }}</div>
              <div class="announcement-time">{{ announcement.time }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { User, Briefcase, Trophy, Connection } from '@element-plus/icons-vue'
import { getHomeData } from '@/api/home'

const stats = ref({
  students: 0,
  internships: 0,
  competitions: 0,
  alumni: 0
})

const recentActivities = ref([])
const announcements = ref([])

const loadData = async () => {
  try {
    const data = await getHomeData()
    stats.value = data.stats
    recentActivities.value = data.activities
    announcements.value = data.announcements
  } catch (error) {
    console.error('加载数据失败:', error)
    // 使用模拟数据
    stats.value = {
      students: 1250,
      internships: 45,
      competitions: 12,
      alumni: 320
    }
    recentActivities.value = [
      {
        id: 1,
        title: '新学生注册',
        description: '张三同学完成了系统注册',
        time: '2024-01-15 10:30'
      },
      {
        id: 2,
        title: '实习申请',
        description: '李四同学提交了实习申请',
        time: '2024-01-15 09:15'
      }
    ]
    announcements.value = [
      {
        id: 1,
        title: '关于2024年春季实习安排的通知',
        time: '2024-01-14'
      },
      {
        id: 2,
        title: '科创竞赛报名开始',
        time: '2024-01-13'
      }
    ]
  }
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.home {
  padding: 20px;
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

.announcement-list {
  max-height: 300px;
  overflow-y: auto;
}

.announcement-item {
  padding: 12px 0;
  border-bottom: 1px solid #f0f0f0;
}

.announcement-item:last-child {
  border-bottom: none;
}

.announcement-title {
  font-size: 14px;
  color: #303133;
  margin-bottom: 4px;
}

.announcement-time {
  font-size: 12px;
  color: #909399;
}
</style> 