import { createVuetify } from 'vuetify'; // 更新导入
import 'vuetify/styles'; // 导入 Vuetify 样式
import * as components from 'vuetify/components'; // 导入所有组件
import * as directives from 'vuetify/directives'; // 导入所有指令

export default createVuetify({
  components, // 注册组件
  directives, // 注册指令
  theme: {
    defaultTheme: 'light', // 设置默认主题
    themes: {
      light: {
        // 可以在此处启用/禁用颜色
        primary: '#fff', // 启用 primary 主题颜色
        secondary: '#dae0e7',
        blue: '#0578d2',
        grey: '#9e9e9e',
        accent: '#8c9eff',
        error: '#b71c1c',
      },
    },
  },
});
