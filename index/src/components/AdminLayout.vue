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
            <el-menu-item index="/admin/internship/add">添加实习</el-menu-item>
          </el-sub-menu>
          
          <el-sub-menu index="/admin/competition">
            <template #title>
              <el-icon><Trophy /></el-icon>
              <span>科创竞赛</span>
            </template>
            <el-menu-item index="/admin/competition/list">竞赛列表</el-menu-item>
            <el-menu-item index="/admin/competition/add">添加竞赛</el-menu-item>
            <el-menu-item index="/admin/competition/dict">竞赛字典</el-menu-item>
          </el-sub-menu>
          
          <el-sub-menu index="/admin/academic">
            <template #title>
              <el-icon><Document /></el-icon>
              <span>学术成果</span>
            </template>
            <el-menu-item index="/admin/papers">论文管理</el-menu-item>
            <el-menu-item index="/admin/patents">专利管理</el-menu-item>
            <el-menu-item index="/admin/projects">项目管理</el-menu-item>
          </el-sub-menu>
          
          <el-sub-menu index="/admin/party">
            <template #title>
              <el-icon><Flag /></el-icon>
              <span>党员管理</span>
            </template>
            <el-menu-item index="/admin/party/list">党员列表</el-menu-item>
            <el-menu-item index="/admin/party/add">添加党员</el-menu-item>
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
            <el-dropdown @command="handleCommand">
              <span class="user-info">
                <el-avatar :size="32" :src="userAvatar">
                  {{ userInfo?.realName?.charAt(0) || userInfo?.username?.charAt(0) || 'A' }}
                </el-avatar>
                <span class="username">{{ userInfo?.realName || userInfo?.username }}</span>
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
import { computed } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessageBox } from 'element-plus'
import {
  DataBoard,
  User,
  Avatar,
  Briefcase,
  Trophy,
  Document,
  Flag,
  Setting,
  ArrowDown
} from '@element-plus/icons-vue'

const store = useStore()
const router = useRouter()

const userInfo = computed(() => store.state.user)
const userAvatar = computed(() => userInfo.value?.avatar || '')

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
</style> 