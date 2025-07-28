import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../components/Layout.vue'
import AdminLayout from '../components/AdminLayout.vue'

const routes = [
  // 学生路由
  {
    path: '/',
    component: MainLayout,
    redirect: '/home',
    meta: { requiresAuth: true, userType: 'student' },
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
        meta: { title: '实习列表' }
      },
      {
        path: '/admin/internship/add',
        name: 'adminInternshipAdd',
        component: () => import('../views/admin/InternshipAdd.vue'),
        meta: { title: '添加实习' }
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
        meta: { title: '竞赛字典' }
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
        path: '/admin/admins',
        name: 'adminAdmins',
        component: () => import('../views/admin/Admins.vue'),
        meta: { title: '管理员管理' }
      },
      {
        path: '/admin/profile',
        name: 'adminProfile',
        component: () => import('../views/admin/Profile.vue'),
        meta: { title: '个人信息' }
      }
    ]
  },
  
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/home'
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 全局前置守卫：未登录自动跳转到登录页
router.beforeEach((to, from, next) => {
  if (to.path === '/login') {
    next()
    return
  }
  
  const token = localStorage.getItem('token')
  if (!token) {
    next('/login')
    return
  }
  
  // 检查用户类型和路由权限
  const user = JSON.parse(localStorage.getItem('user') || '{}')
  const userType = user.userType
  
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
