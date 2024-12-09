import { createRouter, createWebHistory } from 'vue-router'; // 使用命名导入
import routes from './routes';

const router = createRouter({
  history: createWebHistory(), // 使用 createWebHistory() 创建历史记录模式
  routes,
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { x: 0, y: 0 };
    }
  }
});

export default router;
