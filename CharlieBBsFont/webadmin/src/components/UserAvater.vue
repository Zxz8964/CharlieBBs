<template>
  <div class="nav-right">
    <div class="demo-type">
      <!-- 头像组件，加载失败时显示备用头像 -->
      <el-avatar 
        :size="50" 
        :src="avatarSrc" 
        @error="errorHandler"
      />
    </div>
  </div>
</template>

<script setup name="UserAvater" lang="ts" >
import { ref } from 'vue';

// 用户头像图片地址
const avatarSrc = ref('https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png');

// 默认的回退头像
const fallbackSrc = 'https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png';

// 错误处理函数，头像加载失败时调用
const errorHandler = () => {
  avatarSrc.value = fallbackSrc; // 将头像源切换为备用头像
};
</script>

<style scoped>
.nav-right {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.demo-type {
  display: flex;
}

.demo-type > div {
  flex: 1;
  text-align: center;
}

.demo-type > div:not(:last-child) {
  border-right: 1px solid var(--el-border-color);
}
</style>
