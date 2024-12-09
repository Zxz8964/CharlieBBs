<template>
  <div class="header-container">
    <!-- 导航栏 -->
    <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
      <div class="logo">
        <el-menu-item index="1" class="logoLink" style="background-color: transparent !important;">CHARLIE
          Admin</el-menu-item>
      </div>
      <!-- 全局搜索框 -->
      <el-menu-item index="2">
        <el-input v-model="globalSearchQuery" placeholder="搜索..." class="search-input"
          @keyup.enter="handleGlobalSearch" />
      </el-menu-item>
      <el-menu-item index="3">帮助</el-menu-item>
    </el-menu>
    <!-- 提醒🔔️ -->
    <el-dropdown style="right: 50px;position: absolute;">
      <el-badge :value="pendingContentCount" class="icon-badge" max="99">
        <el-icon :size="20" style="margin-top">
          <BellFilled />
        </el-icon>
      </el-badge>
      <template #dropdown>
        <el-dropdown-menu style="display:flex;flex-direction:column">
          <el-badge :value="reportedMixCount" class="icon-badge" max="99">
            <el-dropdown-item>举报审核</el-dropdown-item>
          </el-badge>
          <el-badge :value="voteresultCount" class="icon-badge" max="99">
            <el-dropdown-item>投票信息</el-dropdown-item>
          </el-badge>
          <el-badge :value="messagetoadmin" class="icon-badge" max="99">
            <el-dropdown-item>私信</el-dropdown-item>
          </el-badge>
        </el-dropdown-menu>
      </template>

    </el-dropdown>
    <!-- 用户头像组件 -->
    <UserAvater class="user-avatar"></UserAvater>
  </div>

  <!-- 面包屑导航 -->
  <div class="header-actions">
    <el-page-header @back="goBack" style="width: 90px;">
    </el-page-header>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/' }">
        <a href="/">homepage</a>
      </el-breadcrumb-item>
      <el-breadcrumb-item>
        <a href="/">promotion management</a>
      </el-breadcrumb-item>
      <el-breadcrumb-item>promotion list</el-breadcrumb-item>
      <el-breadcrumb-item>promotion detail</el-breadcrumb-item>
    </el-breadcrumb>
  </div>

  <div class="toolbar">

  </div>
</template>

<script lang="ts" setup>
import UserAvater from './UserAvater.vue';
import { ref } from 'vue';
const goBack = () => {
  console.log('go back')
}
const activeIndex = ref('1')

// 模拟待审核内容数量
const pendingContentCount = ref(5) // 替换为真实数据源
const voteresultCount = ref(3)
const reportedMixCount = ref(3)     // 模拟用户举报数量
const messagetoadmin = ref(2)    // 模拟内容审核数量

// 全局搜索相关
const globalSearchQuery = ref('')
const handleGlobalSearch = () => {
  console.log('全局搜索关键字:', globalSearchQuery.value)
  // 执行全局搜索逻辑，可以根据需求替换为实际实现
}

const handleSelect = (key: string, keyPath: string[]) => {
  console.log(key, keyPath)
}
</script>

<style scoped>
.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-input {
  width: 300px;
}

.icon-badge {
  margin-right: 20px;
}
</style>
