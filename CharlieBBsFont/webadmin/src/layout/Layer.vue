<template>
  <div>
    <el-container>
      <!-- 侧边导航栏 -->
      <el-aside :class="['menu-container', { 'is-collapse': isCollapse }]" @dblclick="toggleSidebar">
        <el-menu class="el-menu-vertical-demo" :collapse="isCollapse" @open="handleOpen" @close="handleClose">
          <el-menu-item index="1">
            <el-icon>
              <Odometer />
            </el-icon>
            <template #title>仪表盘</template>
          </el-menu-item>
          <el-sub-menu index="2">
            <template #title>
              <el-icon @dblclick.stop="toggleSidebar">
                <View />
              </el-icon>
              <span>用户管理</span>
            </template>
            <el-menu-item-group>
              <template #title><span>用户管理</span></template>
              <el-menu-item index="2-1">
                <a href="/system/role-auth/user/1">用户列表</a>
              </el-menu-item>
              <el-menu-item index="2-2">
                <a href="/system/permission-auth/user/1">权限管理</a>
              </el-menu-item>
              <el-menu-item index="2-3">
                <a href="/system/user-auth/role/1">角色管理</a>
              </el-menu-item>
              <el-menu-item index="2-3">
                <a href="/system/banned-auth/user/1">封禁记录</a>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-sub-menu index="3">
            <template #title>
              <el-icon @dblclick.stop="toggleSidebar">
                <icon-menu />
              </el-icon>
              <span>内容管理</span>
            </template>
            <el-menu-item-group>
              <template #title><span>内容管理</span></template>
              <el-menu-item index="3-1">
                <a href="/system/board-auth/user/1">板块管理</a>
              </el-menu-item>
              <el-menu-item index="3-2">
                <a href="/system/post-auth/user/1">帖子管理</a>
              </el-menu-item>
              <el-menu-item index="3-3">
                <a href="/system/comment-auth/user/1">评论管理</a>
              </el-menu-item>
              <el-menu-item index="3-4">
                <a href="/system/Category-auth/user/1">标签管理</a>
              </el-menu-item>
              <el-menu-item index="3-5">
                <a href="/system/report-auth/user/1">举报管理</a>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-menu-item index="4">
            <el-icon>
              <Guide />
            </el-icon>
            <template #title>
              <a href="/system/LabelManager-auth/user/1">分类与标签管理</a>
            </template>
          </el-menu-item>
          <el-sub-menu index="5">
            <template #title>
              <el-icon>
                <Operation />
              </el-icon>
              <span>系统设置</span>
            </template>
            <el-menu-item-group>
              <template #title><span>系统设置</span></template>
              <el-menu-item index="5-1">主题设置</el-menu-item>
              <el-menu-item index="5-2">备份与恢复</el-menu-item>
              <el-menu-item index="5-3">安全设置</el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
          <el-menu-item index="6">
            <el-icon>
              <Notebook />
            </el-icon>
            <template #title>系统日志</template>
          </el-menu-item>

        </el-menu>
      </el-aside>

      <el-container>
        <!-- 头导航区域 -->
        <el-header class="header">
          <ContentHeader></ContentHeader>

        </el-header>
        <!-- 主内容区域 -->
        <el-container @click="collapseSidebar">
          <el-main :class="['main-content', { 'is-collapse': isCollapse }]" style="padding: 18px;">
            <router-view style="border-radius: 4px;" />
          </el-main>
          <!-- Footer 区域 -->
          <el-footer class="footer">
            <CopyRight style="margin-top: auto;padding: 18px;"></CopyRight>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script lang="ts" setup name="Layout">
import CopyRight from '@/components/copyright.vue'
import ContentHeader from '@/components/content-header.vue'
import { ref, watch, onMounted } from 'vue'
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'
const isCollapse = ref(true)

// 切换侧边栏状态
const toggleSidebar = () => {
  isCollapse.value = !isCollapse.value; // 切换状态
};

// 点击空白区域折叠侧边栏
const collapseSidebar = (event) => {
  const menuContainer = event.target.closest('.menu-container');
  if (!menuContainer) {
    isCollapse.value = true; // 折叠侧边栏
  }
};

const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
// 监听 isCollapse 变化，动态调整 main-content 的宽度
watch(isCollapse, (newVal) => {
  const mainContent = document.querySelector('.main-content');
  const headerContent = document.querySelector('.header-actions');
  if (mainContent && headerContent) {
    mainContent.style.marginLeft = newVal ? '64px' : '168px';
    headerContent.style.marginLeft = newVal ? '90px' : '198px';
  }
});
// 页面加载时初始化 main-content 的宽度
onMounted(() => {
  const mainContent = document.querySelector('.main-content');
  const headerContent = document.querySelector('.header-actions');
  if (mainContent && headerContent) {
    mainContent.style.marginLeft = isCollapse.value ? '64px' : '168px';
    headerContent.style.marginLeft = isCollapse.value ? '90px' : '198px';
  }
});
</script>
