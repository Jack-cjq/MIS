import { createRouter, createWebHistory } from 'vue-router'
import MainLayout from '../components/Layout.vue'

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

// 路由守卫
router.beforeEach((to, from, next) => {
  // 设置页面标题
  document.title = to.meta.title ? `${to.meta.title} - 学生管理系统` : '学生管理系统'
  
  // 这里可以添加登录验证逻辑
  // const token = localStorage.getItem('token')
  // if (to.path !== '/login' && !token) {
  //   next('/login')
  // } else {
  //   next()
  // }
  
  next()
})

export default router
