import { createStore } from 'vuex'
import { refreshToken as refreshStudentToken } from '@/api/student'
import { refreshAdminToken } from '@/api/admin'

function setToken(token) {
  localStorage.setItem('token', token)
  localStorage.setItem('token_expire', Date.now() + 24 * 60 * 60 * 1000) // 24小时
}

function clearToken() {
  localStorage.removeItem('token')
  localStorage.removeItem('token_expire')
}

function isTokenExpired() {
  const expire = localStorage.getItem('token_expire')
  if (!expire) return true
  return Date.now() > Number(expire)
}

export default createStore({
  state: {
    user: null,
    token: localStorage.getItem('token') || null,
    isRefreshing: false // 添加刷新状态
  },
  getters: {
    user: state => state.user,
    isAdmin: state => state.user && state.user.userType === 'admin',
    isStudent: state => state.user && state.user.userType === 'student',
    userType: state => state.user ? state.user.userType : null,
    isRefreshing: state => state.isRefreshing
  },
  mutations: {
    setUser(state, user) {
      state.user = user
    },
    setUserType(state, userType) {
      if (state.user) {
        state.user.userType = userType
      }
    },
    logout(state) {
      state.user = null
      state.token = null
      clearToken()
    },
    setToken(state, token) {
      state.token = token
      setToken(token)
    },
    clearUser(state) {
      state.user = null
    },
    clearToken(state) {
      state.token = null
      clearToken()
    },
    updateToken(state, token) {
      state.token = token
      setToken(token)
    },
    setRefreshing(state, isRefreshing) {
      state.isRefreshing = isRefreshing
    }
  },
  actions: {
    setUser({ commit }, user) {
      commit('setUser', user)
    },
    logout({ commit }) {
      commit('logout')
    },
    checkToken({ dispatch }) {
      if (isTokenExpired()) {
        dispatch('logout')
      }
    },
    setToken({ commit }, token) {
      commit('setToken', token)
    },
    // 刷新token（优化版本）
    async refreshToken({ commit, state }) {
      // 如果正在刷新，直接返回
      if (state.isRefreshing) {
        return false
      }
      
      try {
        commit('setRefreshing', true)
        
        const token = localStorage.getItem('token')
        if (!token) {
          return false
        }
        
        // 根据用户类型调用对应的刷新接口
        const userType = state.user ? state.user.userType : null
        let response
        
        if (userType === 'admin') {
          response = await refreshAdminToken()
        } else {
          response = await refreshStudentToken()
        }
        
        if (response.code === 200 && response.data) {
          commit('updateToken', response.data.token)
          return true
        } else {
          console.warn('Token刷新失败，响应:', response)
          return false
        }
      } catch (error) {
        console.error('Token刷新失败:', error)
        // 如果刷新失败，可能需要重新登录
        if (error.response && error.response.status === 401) {
          commit('logout')
        }
        return false
      } finally {
        commit('setRefreshing', false)
      }
    }
  },
  modules: {
  }
})
