<template>
    <div class="login-page">
      <!-- 背景图片 -->
      <div class="main-bg" :style="{ backgroundImage: 'url(../assets/images/bg1.jpg)' }"></div>
  
      <!-- 登录容器 -->
      <div class="login-container">
        <div class="login-form">
          <!-- Logo -->
          <a class="brand" href="/">
            <img src="../assets/images/argon.svg" class="icon" />
            <span class="brand-text">CharlieBBSFont</span>
          </a>
  
          <!-- 登录表单 -->
          <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="form-login">
            <div class="input-container">
              <el-form-item prop="username">
                <el-input
                  v-model="loginForm.username"
                  type="text"
                  size="large"
                  auto-complete="off"
                  placeholder="账号"
                  class="cbi-input-user"
                />
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="loginForm.password"
                  type="password"
                  size="large"
                  auto-complete="off"
                  placeholder="密码"
                  @keyup.enter="handleLogin"
                  class="cbi-input-password"
                />
              </el-form-item>
            </div>
            <div>
              <el-button
                size="large"
                type="primary"
                class="cbi-button cbi-button-apply"
                @click.prevent="handleLogin"
              >
                登录
              </el-button>
            </div>
          </el-form>
        </div>
  
        <footer>
          <div>
            <a class="luci-link" href="https://github.com/openwrt/luci" target="_blank">Powered by LuCI openwrt-23.05 branch</a> /
            <a href="https://github.com/jerrykuku/luci-theme-argon" target="_blank">ArgonTheme v2.3.1</a> /
            ImmortalWrt 23.05-SNAPSHOT
          </div>
        </footer>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import Cookies from 'js-cookie';
  
  const loginForm = ref({
    username: "root",
    password: "",
  });
  
  const loginRules = {
    username: [{ required: true, trigger: "blur", message: "请输入您的账号" }],
    password: [{ required: true, trigger: "blur", message: "请输入您的密码" }],
  };
  
  function handleLogin() {
    console.log('登录信息:', loginForm.value);
  }
  
  function getCookie() {
    const username = Cookies.get("username");
    loginForm.value.username = username || loginForm.value.username;
  }
  
  getCookie();
  </script>
  
  <style lang='scss' scoped>
  @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');
  
  :root {
    --primary: #5e72e4;
    --dark-primary: #483d8b;
  }
  
  .login-page {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    overflow: hidden;
    position: relative;
    font-family: 'Roboto', sans-serif;
  }
  
  .main-bg {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-size: cover;
    background-position: center;
    z-index: -1;
  }
  
  .login-container {
    width: 400px;
    padding: 30px; /* 增加内边距 */
    background: rgba(255, 255, 255, 0.9);
    border-radius: 8px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  }
  
  .brand {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 20px;
  }
  
  .brand-text {
    font-size: 28px; /* 增加字体大小 */
    font-weight: 500;
    color: var(--primary);
    margin-left: 10px;
  }
  
  .input-container {
    margin-bottom: 20px; /* 增加输入框间距 */
  }
  
  .cbi-input-user,
  .cbi-input-password {
    width: 100%;
    border-radius: 5px;
    font-size: 16px; /* 增加输入框字体大小 */
    border: 1px solid #ccc; /* 添加边框 */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
    transition: border-color 0.3s; /* 添加过渡效果 */
  }
  
  .cbi-input-user:focus,
  .cbi-input-password:focus {
    border-color: var(--primary); /* 获取焦点时改变边框颜色 */
  }
  
  .cbi-button {
    width: 100%;
    border-radius: 5px;
    font-size: 16px; /* 增加按钮字体大小 */
    background: linear-gradient(90deg, #5e72e4 0%, #4c63d2 100%); /* 渐变背景 */
    border: none; /* 去掉边框 */
    color: white; /* 字体颜色 */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2); /* 增加阴影 */
    transition: background 0.3s, box-shadow 0.3s; /* 添加过渡效果 */
  }
  
  .cbi-button:hover {
    background: linear-gradient(90deg, #4c63d2 0%, #5e72e4 100%); /* 悬停时改变背景颜色 */
    box-shadow: 0 6px 15px rgba(0, 0, 0, 0.3); /* 悬停时增强阴影 */
  }
  
  footer {
    margin-top: 20px;
    text-align: center;
    font-size: 12px;
    color: #333;
  }
  
  .luci-link {
    color: var(--dark-primary);
    text-decoration: none;
  }
  
  .luci-link:hover {
    text-decoration: underline;
  }
  </style>
  