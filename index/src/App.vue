<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
import { useStore } from 'vuex'

export default {
  name: 'App',
  setup() {
    const store = useStore()
    
    let refreshTimeout = null
    let lastRefreshTime = 0
    const REFRESH_COOLDOWN = 4 * 60 * 1000 // 4分钟冷却时间
    
    // 用户活动检测，自动刷新token（带防抖）
    const handleUserActivity = () => {
      const token = localStorage.getItem('token')
      if (!token) return
      
      const now = Date.now()
      
      // 如果距离上次刷新时间不足4分钟，则不刷新
      if (now - lastRefreshTime < REFRESH_COOLDOWN) {
        return
      }
      
      // 清除之前的定时器
      if (refreshTimeout) {
        clearTimeout(refreshTimeout)
      }
      
      // 设置防抖延迟，避免频繁请求
      refreshTimeout = setTimeout(async () => {
        try {
          const success = await store.dispatch('refreshToken')
          if (success) {
            lastRefreshTime = Date.now()
          }
        } catch (error) {
          console.error('Token刷新失败:', error)
        }
      }, 2000) // 2秒防抖延迟
    }
    
    // 监听用户活动（减少监听的事件类型）
    const events = ['click', 'keypress'] // 只监听点击和按键事件
    events.forEach(event => {
      document.addEventListener(event, handleUserActivity, true)
    })
    
    // 定期检查token状态（增加间隔时间）
    setInterval(async () => {
      const token = localStorage.getItem('token')
      if (token) {
        const now = Date.now()
        if (now - lastRefreshTime >= REFRESH_COOLDOWN) {
          try {
            const success = await store.dispatch('refreshToken')
            if (success) {
              lastRefreshTime = Date.now()
            }
          } catch (error) {
            console.error('定期Token刷新失败:', error)
          }
        }
      }
    }, 10 * 60 * 1000) // 改为每10分钟检查一次
    
    return {}
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  font-family: 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
}

#app {
  height: 100%;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

/* 全局滚动条样式 */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}
</style>
