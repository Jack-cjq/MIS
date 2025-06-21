/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="common-layout">
    <el-container class="layout-container">
      <el-header class="header">
        <div class="header-content">
          <h2>学生管理系统</h2>
          <div class="user-info">
            <el-dropdown>
              <span class="user-dropdown">
                管理员 <el-icon><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      <el-container>
        <el-aside width="250px" class="aside">
          <el-menu
            :default-active="activeMenu"
            class="menu"
            router
            background-color="#304156"
            text-color="#bfcbd9"
            active-text-color="#409EFF"
          >
            <el-menu-item index="/home">
              <el-icon><House /></el-icon>
              <span>主页面</span>
            </el-menu-item>
            <el-menu-item index="/profile">
              <el-icon><User /></el-icon>
              <span>个人信息模块</span>
            </el-menu-item>
            <el-menu-item index="/daily">
              <el-icon><Calendar /></el-icon>
              <span>日常管理模块</span>
            </el-menu-item>
            <el-menu-item index="/internship">
              <el-icon><Briefcase /></el-icon>
              <span>实习就业模块</span>
            </el-menu-item>
            <el-menu-item index="/competition">
              <el-icon><Trophy /></el-icon>
              <span>科创竞赛模块</span>
            </el-menu-item>
            <el-menu-item index="/alumni">
              <el-icon><Connection /></el-icon>
              <span>校友管理模块</span>
            </el-menu-item>
            <el-menu-item index="/party">
              <el-icon><Flag /></el-icon>
              <span>党员管理模块</span>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main class="main">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { 
  House, 
  User, 
  Calendar, 
  Briefcase, 
  Trophy, 
  Connection, 
  ArrowDown,
  Flag
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const activeMenu = computed(() => route.path)

const logout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  ElMessage.success('退出登录成功')
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.header {
  background-color: #fff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
}

.header-content h2 {
  margin: 0;
  color: #303133;
}

.user-dropdown {
  cursor: pointer;
  color: #606266;
  display: flex;
  align-items: center;
  gap: 4px;
}

.aside {
  background-color: #304156;
}

.menu {
  border-right: none;
  height: 100%;
}

.main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style> 