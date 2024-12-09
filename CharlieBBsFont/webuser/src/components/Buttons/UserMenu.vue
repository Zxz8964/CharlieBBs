<template>
    <v-btn
      class="user-menu-button"
      elevation="2"
      color="transparent"
      dark
      @click.stop="$emit('toggleMenu')"
      style="padding: 0; min-width: 48px; height: 48px;"
    >
      <v-avatar size="40" class="user-avatar">
        <v-img src="@/assets/images/avatar.png" alt="User Avatar" />
      </v-avatar>
    </v-btn>
  </template>
  
  <script>
  export default {
    props: ['small']
  };
  </script>
  
  <style scoped>
  .user-menu-button {
    margin-right: 20px;
    border-radius: 50%;
    background-color: rgba(255, 255, 255, 0.9); /* 半透明背景 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15); /* 阴影效果 */
    transition: background-color 0.3s ease, box-shadow 0.3s ease;
  }
  
  .user-menu-button:hover {
    background-color: rgba(255, 255, 255, 1); /* 悬停时背景更亮 */
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2); /* 加深阴影 */
  }
  
  .user-avatar img {
    border-radius: 50%;
  }
  </style>
  