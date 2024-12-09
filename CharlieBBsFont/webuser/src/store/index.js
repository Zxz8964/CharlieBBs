import { createStore } from 'vuex';

const store = createStore({
  state: {
    isAuthenticated: !!localStorage.getItem('token'), // 初始状态从localStorage获取
    username: null,
    modal: null
  },
  mutations: {
    setModal(state, modal) {
      state.modal = modal;
    },
    setAuthenticated(state, status) {
      console.log('Setting authenticated status:', status);
      state.isAuthenticated = status;
    },
    setUsername(state, username) {
      state.username = username;
    }
  },
  actions: {
    // 你的 actions
  },
  modules: {
    // 你的模块
  }
});

export default store;
