import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../components/Layout.vue'
import AdminLayout from '../components/AdminLayout.vue'
import { getCurrentUser } from '@/api/student'
import { getCurrentAdmin } from '@/api/admin'
import store from '@/store'

const routes = [
  {
    path: '/',
    component: MainLayout,
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: 'home',
        component: () => import('../views/Home.vue'),
        meta: { title: '主页面' }
      },
      {
        path: '/profile',
        name: 'profile',
        component: () => import('../views/Profile.vue'),
        meta: { title: '个人信息模块' }
      },
      {
        path: '/daily',
        name: 'daily',
        component: () => import('../views/Daily.vue'),
        meta: { title: '日常管理模块' }
      },
      {
        path: '/internship',
        name: 'internship',
        component: () => import('../views/Internship.vue'),
        meta: { title: '实习就业模块' }
      },
      {
        path: '/competition',
        name: 'competition',
        component: () => import('../views/Competition.vue'),
        meta: { title: '科创竞赛模块' }
      },
      {
        path: '/alumni',
        name: 'alumni',
        component: () => import('../views/Alumni.vue'),
        meta: { title: '校友管理模块' }
      },
      {
        path: '/party',
        name: 'party',
        component: () => import('../views/Party.vue'),
        meta: { title: '党员管理模块' }
      }
    ]
  },
  
  // 管理员路由
  {
    path: '/admin',
    component: AdminLayout,
    redirect: '/admin/dashboard',
    meta: { requiresAuth: true, userType: 'admin' },
    children: [
      {
        path: '/admin/dashboard',
        name: 'adminDashboard',
        component: () => import('../views/admin/Dashboard.vue'),
        meta: { title: '管理员仪表板' }
      },
      {
        path: '/admin/students/list',
        name: 'adminStudentsList',
        component: () => import('../views/admin/StudentsList.vue'),
        meta: { title: '学生列表' }
      },
      {
        path: '/admin/students/add',
        name: 'adminStudentsAdd',
        component: () => import('../views/admin/StudentsAdd.vue'),
        meta: { title: '添加学生' }
      },
      {
        path: '/admin/alumni/list',
        name: 'adminAlumniList',
        component: () => import('../views/admin/AlumniList.vue'),
        meta: { title: '校友列表' }
      },
      {
        path: '/admin/alumni/add',
        name: 'adminAlumniAdd',
        component: () => import('../views/admin/AlumniAdd.vue'),
        meta: { title: '添加校友' }
      },
      {
        path: '/admin/internship/list',
        name: 'adminInternshipList',
        component: () => import('../views/admin/InternshipList.vue'),
        meta: { title: '实习就业列表' }
      },
      {
        path: '/admin/internship/analysis',
        name: 'adminInternshipAnalysis',
        component: () => import('../views/admin/InternshipAnalysis.vue'),
        meta: { title: '实习就业分析' }
      },
      {
        path: '/admin/competition/list',
        name: 'adminCompetitionList',
        component: () => import('../views/admin/CompetitionList.vue'),
        meta: { title: '竞赛列表' }
      },
      {
        path: '/admin/competition/add',
        name: 'adminCompetitionAdd',
        component: () => import('../views/admin/CompetitionAdd.vue'),
        meta: { title: '添加竞赛' }
      },
      {
        path: '/admin/competition/dict',
        name: 'adminCompetitionDict',
        component: () => import('../views/admin/CompetitionDict.vue'),
        meta: { title: '竞赛字典管理' }
      },
      {
        path: '/admin/party/list',
        name: 'adminPartyList',
        component: () => import('../views/admin/PartyList.vue'),
        meta: { title: '党员列表' }
      },
      {
        path: '/admin/party/add',
        name: 'adminPartyAdd',
        component: () => import('../views/admin/PartyAdd.vue'),
        meta: { title: '添加党员' }
      },
      {
        path: '/admin/daily/dailycontrol',
        name: 'dailycontrol',
        component: () => import('../views/admin/DailyControl.vue'),
        meta: { title: '活动审批列表' }
      },
      {
        path: '/admin/papers',
        name: 'adminPapers',
        component: () => import('../views/admin/Papers.vue'),
        meta: { title: '论文管理' }
      },
      {
        path: '/admin/patents',
        name: 'adminPatents',
        component: () => import('../views/admin/Patents.vue'),
        meta: { title: '专利管理' }
      },
      {
        path: '/admin/projects',
        name: 'adminProjects',
        component: () => import('../views/admin/Projects.vue'),
        meta: { title: '项目管理' }
      },
      {
        path: '/admin/students',
        name: 'adminStudents',
        component: () => import('../views/admin/StudentsList.vue'),
        meta: { title: '学生管理' }
      },
      {
        path: '/admin/admins',
        name: 'adminAdmins',
        component: () => import('../views/admin/Admins.vue'),
        meta: { title: '管理员管理' }
      },
      {
        path: '/admin/profile',
        name: 'adminProfile',
        component: () => import('../views/admin/Profile.vue'),
        meta: { title: '管理员个人信息' }
      }
    ]
  },
  
  // 登录页面
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue'),
    meta: { title: '登录' }
  },
  
  // 校友-学生页面
  {
    path: '/alumni-student',
    name: 'alumniStudent',
    component: () => import('../views/Alumni-Student.vue'),
    meta: { title: '校友-学生' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局前置守卫：未登录自动跳转到登录页
router.beforeEach(async (to, from, next) => {
  if (to.path === '/login') {
    next()
    return
  }
  
  const token = localStorage.getItem('token')
  
  // 如果没有token，跳转到登录页
  if (!token) {
    if (from.path !== '/login') {
      console.log('Token不存在，跳转到登录页')
    }
    next('/login')
    return
  }
  
  // 检查token是否过期
  const expire = localStorage.getItem('token_expire')
  const isExpired = expire && Date.now() > Number(expire)
  
  if (isExpired) {
    if (from.path !== '/login') {
      console.log('Token已过期，跳转到登录页')
    }
    localStorage.removeItem('token')
    localStorage.removeItem('token_expire')
    next('/login')
    return
  }
  
  // 获取用户信息（从store）
  let user = store.state.user
  
  // 如果没有用户信息，尝试从后端获取
  if (!user) {
    try {
      // 根据当前路由判断用户类型，调用对应的API
      let response
      if (to.path.startsWith('/admin')) {
        // 管理员路由，调用管理员API
        response = await getCurrentAdmin()
      } else {
        // 学生路由，调用学生API
        response = await getCurrentUser()
      }
      
      if (response.code === 200) {
        user = response.data
        store.commit('setUser', user)
      } else {
        // 获取用户信息失败，清除token并跳转到登录页
        localStorage.removeItem('token')
        localStorage.removeItem('token_expire')
        next('/login')
        return
      }
    } catch (error) {
      console.error('获取用户信息失败:', error)
      localStorage.removeItem('token')
      localStorage.removeItem('token_expire')
      next('/login')
      return
    }
  }
  
  const userType = user ? user.userType : null
  
  // 检查用户类型和路由权限
  if (to.meta.requiresAuth) {
    if (to.meta.userType && to.meta.userType !== userType) {
      // 用户类型不匹配，重定向到对应的首页
      if (userType === 'admin') {
        next('/admin/dashboard')
      } else {
        next('/home')
      }
      return
    }
  }
  
  next()
})

export default router
