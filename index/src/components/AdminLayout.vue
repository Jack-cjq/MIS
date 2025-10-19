<template>
  <div class="admin-layout">
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="250px" class="sidebar">
        <div class="logo">
          <h2>学生管理系统</h2>
          <p>管理员控制台</p>
        </div>
        
        <el-menu
          :default-active="$route.path"
          class="sidebar-menu"
          background-color="#304156"
          text-color="#bfcbd9"
          active-text-color="#409EFF"
          router
        >
          <el-menu-item index="/admin/dashboard">
            <el-icon><DataBoard /></el-icon>
            <span>数据概览</span>
          </el-menu-item>
          
          <el-sub-menu index="/admin/students">
            <template #title>
              <el-icon><User /></el-icon>
              <span>学生管理</span>
            </template>
            <el-menu-item index="/admin/students/list">学生列表</el-menu-item>
            <el-menu-item index="/admin/students/add">添加学生</el-menu-item>
          </el-sub-menu>
          
          <el-sub-menu index="/admin/alumni">
            <template #title>
              <el-icon><Avatar /></el-icon>
              <span>校友管理</span>
            </template>
            <el-menu-item index="/admin/alumni/list">校友列表</el-menu-item>
            <el-menu-item index="/admin/alumni/add">添加校友</el-menu-item>
          </el-sub-menu>
          
          <el-sub-menu index="/admin/internship">
            <template #title>
              <el-icon><Briefcase /></el-icon>
              <span>实习就业</span>
            </template>
            <el-menu-item index="/admin/internship/list">实习列表</el-menu-item>
            <el-menu-item index="/admin/internship/analysis">数据分析</el-menu-item>
          </el-sub-menu>
            
          <!-- 科创竞赛模块 -->
          <el-sub-menu index="/admin/innovation">
            <template #title>
              <el-icon><Trophy /></el-icon>
              <span>科创竞赛</span>
              <el-badge 
                v-if="totalPendingCount > 0" 
                :value="totalPendingCount" 
                :max="99"
                class="menu-badge"
              />
            </template>
            
            <!-- 竞赛管理 -->
            <el-sub-menu index="/admin/innovation/competitions/list">
              <template #title>
                <el-icon><Trophy /></el-icon>
                <span>竞赛管理</span>
                <el-badge 
                  v-if="pendingCounts.competition > 0" 
                  :value="pendingCounts.competition" 
                  :max="99"
                  class="item-badge"
                />
              </template>
              <el-menu-item index="/admin/innovation/competitions/list">
                <span>竞赛列表</span>
              </el-menu-item>
              <el-menu-item index="/admin/innovation/competitions/dict">
                <span>竞赛字典</span>
              </el-menu-item>
            </el-sub-menu>
            
            <!-- 项目管理 -->
            <el-menu-item index="/admin/innovation/projects/list">
              <el-icon><Operation /></el-icon>
              <span>项目管理</span>
              <el-badge 
                v-if="pendingCounts.project > 0" 
                :value="pendingCounts.project" 
                :max="99"
                class="item-badge"
              />
            </el-menu-item>
            
            <!-- 论文管理 -->
            <el-menu-item index="/admin/innovation/papers/list">
              <el-icon><Document /></el-icon>
              <span>论文管理</span>
              <el-badge 
                v-if="pendingCounts.paper > 0" 
                :value="pendingCounts.paper" 
                :max="99"
                class="item-badge"
              />
            </el-menu-item>
            
            <!-- 专利管理 -->
            <el-menu-item index="/admin/innovation/patents/list">
              <el-icon><Files /></el-icon>
              <span>专利管理</span>
              <el-badge 
                v-if="pendingCounts.patent > 0" 
                :value="pendingCounts.patent" 
                :max="99"
                class="item-badge"
              />
            </el-menu-item>
            
            <!-- 数据统计 -->
            <el-menu-item index="/admin/innovation/statistics">
              <el-icon><DataAnalysis /></el-icon>
              <span>数据统计</span>
            </el-menu-item>
          </el-sub-menu>
          
          <el-sub-menu index="/admin/party">
            <template #title>
              <el-icon><Flag /></el-icon>
              <span>党员管理</span>
            </template>
            <el-menu-item index="/admin/party/list">党员列表</el-menu-item>
            <el-menu-item index="/admin/party/add">添加党员</el-menu-item>
          </el-sub-menu>

          <el-sub-menu index="/admin/daily">
            <template #title>
              <el-icon><Flag /></el-icon>
              <span>日常管理</span>
            </template>
            <el-menu-item index="/admin/daily/dailycontrol">活动审批列表</el-menu-item>

          </el-sub-menu>
          
          <el-sub-menu index="/admin/system">
            <template #title>
              <el-icon><Setting /></el-icon>
              <span>系统管理</span>
            </template>
            <el-menu-item index="/admin/admins">管理员管理</el-menu-item>
            <el-menu-item index="/admin/profile">个人信息</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>
      
      <!-- 主内容区 -->
      <el-container>
        <!-- 顶部导航 -->
        <el-header class="header">
          <div class="header-left">
            <el-breadcrumb separator="/">
              <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">首页</el-breadcrumb-item>
              <el-breadcrumb-item>{{ $route.meta.title || '页面' }}</el-breadcrumb-item>
            </el-breadcrumb>
          </div>

          <div class="header-right">
            <!-- 待审核提醒 -->
            <el-badge 
              :value="totalPendingCount" 
              :max="99" 
              :hidden="totalPendingCount === 0"
              class="header-badge"
            >
              <el-button 
                type="primary" 
                :icon="DocumentChecked" 
                circle 
                @click="goToAudit"
                title="待审核项目"
              />
            </el-badge>
        
            <el-dropdown @command="handleCommand">
              <span class="user-info">
                <el-avatar :size="32" :src="userAvatar">
                  {{ adminInfo?.realName?.charAt(0) || adminInfo?.username?.charAt(0) || adminInfo?.name?.charAt(0) || 'A' }}
                </el-avatar>
                <span class="username">{{ adminInfo?.realName || adminInfo?.username || adminInfo?.name || '管理员' }}</span>
                <el-icon><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                  <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        
        <!-- 内容区 -->
        <el-main class="main-content">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { computed, ref, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessageBox, ElMessage } from 'element-plus'
import request from '@/api/request'
import {
  DataBoard,
  User,
  Avatar,
  Briefcase,
  Trophy,
  Document,
  Flag,
  Setting,
  ArrowDown,
  Operation,
  Files,
  DataAnalysis,
  Calendar,
  DocumentChecked,
} from '@element-plus/icons-vue'
import { getAuditStats } from '@/api/admin'

const store = useStore()
const router = useRouter()

// 存储管理员详细信息
const adminInfo = ref(null)

// 待审核数量统计
const pendingCounts = ref({
  competition: 0,
  project: 0,
  paper: 0,
  patent: 0
})

// 总待审核数量
const totalPendingCount = computed(() => {
  return pendingCounts.value.competition + 
         pendingCounts.value.project + 
         pendingCounts.value.paper + 
         pendingCounts.value.patent
})

const userInfo = computed(() => {
  const user = store.state.user
  // console.log('当前用户信息:', user)
  return user
})

const userAvatar = computed(() => adminInfo.value?.avatar || '')

// 根据ID获取管理员详细信息
const fetchAdminInfo = async () => {
  if (userInfo.value?.id && userInfo.value?.userType === 'admin') {
    try {
      // 使用request工具调用后端API获取管理员详细信息
      const result = await request.get(`/admin/${userInfo.value.id}`)
      if (result.code === 200) {
        adminInfo.value = result.data
        // console.log('获取到的管理员信息:', adminInfo.value)
      } else {
        console.error('获取管理员信息失败:', result.msg)
      }
    } catch (error) {
      console.error('获取管理员信息失败:', error)
    }
  }
}

// 加载待审核统计数据
const loadPendingStats = async () => {
  try {
    const result = await getAuditStats()
    console.log('=== 统计数据返回 ===')
    console.log('完整响应:', result)
    
    // ✅ 根据响应拦截器返回格式处理
    let data = null
    
    if (result && typeof result === 'object') {
      // 情况1: 响应拦截器返回 response.data（直接是数据对象）
      if (result.competition !== undefined) {
        data = result
      }
      // 情况2: 包裹在 { code, data } 中
      else if (result.code === 200 && result.data) {
        data = result.data
      }
      // 情况3: 包裹在 { data: { code, data } } 中
      else if (result.data?.data) {
        data = result.data.data
      }
    }
    
    console.log('提取的数据:', data)
    
    if (data) {
      pendingCounts.value = {
        competition: data.competition?.pending || 0,
        project: data.project?.pending || 0,
        paper: data.paper?.pending || 0,
        patent: data.patent?.pending || 0
      }
      
      console.log('待审核数量:', pendingCounts.value)
    } else {
      console.warn('未能提取到统计数据')
      // 设置为 0
      pendingCounts.value = {
        competition: 0,
        project: 0,
        paper: 0,
        patent: 0
      }
    }
  } catch (error) {
    console.error('加载待审核统计失败:', error)
    // 失败时设置为 0
    pendingCounts.value = {
      competition: 0,
      project: 0,
      paper: 0,
      patent: 0
    }
  }
}

// 跳转到第一个有待审核的页面
const goToAudit = () => {
  if (pendingCounts.value.competition > 0) {
    router.push('/admin/innovation/competitions/list')
  } else if (pendingCounts.value.project > 0) {
    router.push('/admin/innovation/projects/list')
  } else if (pendingCounts.value.paper > 0) {
    router.push('/admin/innovation/papers/list')
  } else if (pendingCounts.value.patent > 0) {
    router.push('/admin/innovation/patents/list')
  } else {
    ElMessage.info('暂无待审核项目')
  }
}

// 定时器ID
let refreshTimer = null

onMounted(() => {
  fetchAdminInfo()
  loadPendingStats()
  
  // 每30秒刷新一次待审核数量
  refreshTimer = setInterval(() => {
    loadPendingStats()
  }, 30000)
})

onUnmounted(() => {
  // 清除定时器
  if (refreshTimer) {
    clearInterval(refreshTimer)
  }
})


const handleCommand = async (command) => {
  if (command === 'profile') {
    router.push('/admin/profile')
  } else if (command === 'logout') {
    try {
      await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
      store.dispatch('logout')
      router.push('/login')
    } catch {
      // 用户取消
    }
  }
}
</script>

<style scoped>
.admin-layout {
  height: 100vh;
  width: 100%;
}

.sidebar {
  background-color: #304156;
  color: #bfcbd9;
  height: 100vh;
  overflow-y: auto;
}

.logo {
  padding: 20px;
  text-align: center;
  border-bottom: 1px solid #434a50;
}

.logo h2 {
  color: #fff;
  margin: 0 0 5px 0;
  font-size: 18px;
}

.logo p {
  color: #bfcbd9;
  margin: 0;
  font-size: 12px;
}

.sidebar-menu {
  border: none;
  height: calc(100vh - 120px);
  overflow-y: auto;
}

/* 菜单徽章样式 */
.menu-badge {
  margin-left: 8px;
}

.item-badge {
  margin-left: 8px;
}

:deep(.el-badge__content) {
  top: 8px;
  right: -8px;
}

/* 子菜单中的徽章 */
:deep(.el-sub-menu__title .menu-badge) {
  position: absolute;
  right: 50px;
}

:deep(.el-menu-item .item-badge) {
  position: absolute;
  right: 20px;
}

.header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px;
  min-height: 60px;
}

.header-left {
  flex: 1;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

/* 头部徽章 */
.header-badge {
  margin-right: 10px;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.user-info:hover {
  background-color: #f5f5f5;
}

.username {
  margin: 0 8px;
  color: #333;
}

.main-content {
  background-color: #f0f2f5;
  padding: 20px;
  height: calc(100vh - 60px);
  overflow-y: auto;
}

/* 确保el-container占满整个视口 */
:deep(.el-container) {
  height: 100vh;
}

:deep(.el-aside) {
  height: 100vh;
}

:deep(.el-main) {
  padding: 0;
  height: 100vh;
}

/* 滚动条美化 */
.sidebar::-webkit-scrollbar,
.sidebar-menu::-webkit-scrollbar {
  width: 6px;
}

.sidebar::-webkit-scrollbar-thumb,
.sidebar-menu::-webkit-scrollbar-thumb {
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 3px;
}

.sidebar::-webkit-scrollbar-thumb:hover,
.sidebar-menu::-webkit-scrollbar-thumb:hover {
  background-color: rgba(255, 255, 255, 0.3);
}
</style>