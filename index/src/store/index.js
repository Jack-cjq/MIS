import { createStore } from 'vuex'

function setToken(token) {
  localStorage.setItem('token', token)
  localStorage.setItem('token_expire', Date.now() + 10 * 60 * 1000)
}

function clearToken() {
  localStorage.removeItem('token')
  localStorage.removeItem('token_expire')
  localStorage.removeItem('user')
}

function isTokenExpired() {
  const expire = localStorage.getItem('token_expire')
  if (!expire) return true
  return Date.now() > Number(expire)
}

export default createStore({
  state: {
    user: JSON.parse(localStorage.getItem('user')) || null
  },
  getters: {
    user: state => state.user
  },
  mutations: {
    setUser(state, user) {
      state.user = user
      localStorage.setItem('user', JSON.stringify(user))
    },
    logout(state) {
      state.user = null
      clearToken()
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
        // 这里不能直接用router，需要在组件中跳转
      }
    },
    setToken(_, token) {
      setToken(token)
    }
  },
  modules: {
  }
})
