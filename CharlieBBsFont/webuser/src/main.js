import { createApp } from 'vue';
import App from './App.vue';
import '@fortawesome/fontawesome-free/css/all.min.css';
import router from './router';
import ElementPlus from 'element-plus'; 
import vuetify from './plugins/vuetify';
import store from './store';
import axios from 'axios';

// 配置 axios 的默认值
axios.defaults.baseURL = 'http://localhost:8080';

const app = createApp(App);

// 使用 provide 将 axios 实例提供给所有子组件
app.provide('axios', axios);

app.use(vuetify);
app.use(ElementPlus);
app.use(store);
app.use(router);

// 调试信息
console.log('Initial isAuthenticated:', store.state.isAuthenticated);
console.log('Initial token in localStorage:', localStorage.getItem('token'));

app.mount('#app');

