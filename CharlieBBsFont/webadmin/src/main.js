//导入vue中创建vue实例的方法
import { createApp } from 'vue'
//导入最大组件,以便加载其它组件内容
import App from './App.vue'
//导入自定义路由文件简写from './router'
import router from "./router/router" // 导入路由
//全局导入element-plus 
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './style.css'; // 引入全局样式
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//创建vue实例
const app=createApp(App);
app.use(ElementPlus)
app.use(router);//绑定路由对象
app.mount('#app');
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}