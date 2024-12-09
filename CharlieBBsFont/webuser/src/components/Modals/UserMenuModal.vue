<template>
  <v-menu offset-y style="position: fixed !important; /* 保持固定位置 */
  top: 70px;  /* 你可以调整这个值来确定与 header 的距离 */
  right: 0;  /* 或者 left: 0，根据需要设置 */
  z-index: 1000;  /* 确保它显示在前面 */
  /* 初始状态设置为透明和缩小 */
  transition: transform 0.3s ease, opacity 0.3s ease; /* 设置动画 */">
    <template v-slot:activator="{ on, attrs }">
      <slot name="activator" v-bind="attrs" v-on="on"></slot>
    </template>
    <v-list class="menu-list">
      <v-list-item @click="viewProfile">
        <v-list-item-avatar>
          <v-icon>mdi-account</v-icon>
        </v-list-item-avatar>
        <v-list-item-title>View Profile</v-list-item-title>
      </v-list-item>
      <v-list-item @click="editAvatar">
        <v-list-item-avatar>
          <v-icon>mdi-account-edit</v-icon>
        </v-list-item-avatar>
        <v-list-item-title>Edit Avatar</v-list-item-title>
      </v-list-item>
      <v-list-item @click="viewAchievements">
        <v-list-item-avatar>
          <v-icon>mdi-trophy</v-icon>
        </v-list-item-avatar>
        <v-list-item-title>Achievements</v-list-item-title>
      </v-list-item>
      <v-list-item @click="viewContributorProgram">
        <v-list-item-avatar>
          <v-icon>mdi-gift</v-icon>
        </v-list-item-avatar>
        <v-list-item-title>Contributor Program</v-list-item-title>
      </v-list-item>
      <v-list-item>
        <v-list-item-avatar>
          <v-icon>mdi-theme-light-dark</v-icon>
        </v-list-item-avatar>
        <v-list-item-title>Dark Mode</v-list-item-title>
        <v-switch v-model="darkMode" @change="toggleDarkMode"></v-switch>
      </v-list-item>
      <v-list-item @click="logout">
        <v-list-item-avatar>
          <v-icon>mdi-logout</v-icon>
        </v-list-item-avatar>
        <v-list-item-title>Log Out</v-list-item-title>
      </v-list-item>
      <v-list-item @click="advertiseOnReddit">
        <v-list-item-avatar>
          <v-icon>mdi-bullhorn</v-icon>
        </v-list-item-avatar>
        <v-list-item-title>Advertise on Reddit</v-list-item-title>
      </v-list-item>
      <v-list-item @click="goToSettings">
        <v-list-item-avatar>
          <v-icon>mdi-cog</v-icon>
        </v-list-item-avatar>
        <v-list-item-title>Settings</v-list-item-title>
      </v-list-item>
      <v-list-item @click="goToPremium">
        <v-list-item-avatar>
          <v-icon>mdi-star</v-icon>
        </v-list-item-avatar>
        <v-list-item-title>Premium</v-list-item-title>
      </v-list-item>
    </v-list>
  </v-menu>
</template>

<script>
export default {
  data() {
    return {
      darkMode: false // 假设默认是关闭状态
    };
  },
  methods: {
    viewProfile() {
      // 添加你的逻辑
    },
    editAvatar() {
      // 添加你的逻辑
    },
    viewAchievements() {
      // 添加你的逻辑
    },
    viewContributorProgram() {
      // 添加你的逻辑
    },
    toggleDarkMode() {
      // 添加切换暗模式的逻辑
      this.darkMode = !this.darkMode;
    },
    logout() {
      this.$emit('logout');
    },
    advertiseOnReddit() {
      // 添加广告逻辑
    },
    goToSettings() {
      this.$emit('goToSettings');
    },
    goToPremium() {
      // 添加跳转至高级服务的逻辑
    }
  }
}
</script>

<style scoped>
.menu-list {
  min-width: 270px; /* 设置宽度 */
  margin-left: 430%; /* 居中或右对齐 */
}
.v-list-item {
  cursor: pointer;
}
.v-list-item:hover {
  background-color: #f5f5f5;
}
</style>
