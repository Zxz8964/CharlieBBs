<template>
    <div class="register-page">
      <!-- 背景图片 -->
      <div class="main-bg" :style="{ backgroundImage: 'url(../assets/images/bg1.jpg)' }"></div>
  
      <!-- 注册容器 -->
      <div class="register-container">
        <div class="register-form">
          <!-- Logo -->
          <a class="brand" href="/">
            <img src="../assets/images/argon.svg" class="icon" />
            <span class="brand-text">CharlieBBSFont</span>
          </a>
  
          <!-- 注册表单 -->
          <el-form ref="registerRef" :model="registerForm" :rules="registerRules" class="form-register">
            <div class="input-container">
              <el-form-item prop="username">
                <el-input
                  v-model="registerForm.username"
                  type="text"
                  size="large"
                  auto-complete="off"
                  placeholder="账号"
                  class="cbi-input-user"
                />
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  v-model="registerForm.password"
                  type="password"
                  size="large"
                  auto-complete="off"
                  placeholder="密码"
                  class="cbi-input-password"
                />
              </el-form-item>
              <el-form-item prop="confirmPassword">
                <el-input
                  v-model="registerForm.confirmPassword"
                  type="password"
                  size="large"
                  auto-complete="off"
                  placeholder="确认密码"
                  class="cbi-input-password"
                />
              </el-form-item>
              <el-form-item prop="code" v-if="captchaEnabled">
                <div class="register-code">
                  <img :src="codeUrl" @click="getCode" class="register-code-img" />
                </div>
                <el-input style="width: 50%;margin-left: auto;"
                  size="large" 
                  v-model="registerForm.code"
                  auto-complete="off"
                  placeholder="验证码"
                  class="cbi-input-code"
                />
                
              </el-form-item>
            </div>
            <div>
              <el-button
                size="large"
                type="primary"
                class="cbi-button cbi-button-apply"
                @click.prevent="handleRegister"
                :loading="loading"
              >
                <span v-if="!loading">注 册</span>
                <span v-else>注 册 中...</span>
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
  import { ElMessageBox } from "element-plus";
  // import { getCodeImg, register } from "@/api/login";
  
  const registerForm = ref({
    username: "",
    password: "",
    confirmPassword: "",
    code: "",
    uuid: ""
  });
  
  const equalToPassword = (rule, value, callback) => {
    if (registerForm.value.password !== value) {
      callback(new Error("两次输入的密码不一致"));
    } else {
      callback();
    }
  };
  
  const registerRules = {
    username: [
      { required: true, trigger: "blur", message: "请输入您的账号" },
      { min: 2, max: 20, message: "用户账号长度必须介于 2 和 20 之间", trigger: "blur" }
    ],
    password: [
      { required: true, trigger: "blur", message: "请输入您的密码" },
      { min: 5, max: 20, message: "用户密码长度必须介于 5 和 20 之间", trigger: "blur" },
      { pattern: /^[^<>"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\\ |", trigger: "blur" }
    ],
    confirmPassword: [
      { required: true, trigger: "blur", message: "请再次输入您的密码" },
      { required: true, validator: equalToPassword, trigger: "blur" }
    ],
    code: [{ required: true, trigger: "change", message: "请输入验证码" }]
  };
  
  const codeUrl = ref("");
  const loading = ref(false);
  const captchaEnabled = ref(true);
  
  function handleRegister() {
    // 模拟注册逻辑
    console.log('注册信息:', registerForm.value);
    // 注册成功后展示提示框
    ElMessageBox.alert("注册成功！", "提示", {
      type: "success",
    });
  }
  
  function getCode() {
    // TODO: 处理获取验证码逻辑
    console.log("获取验证码的逻辑");
    // 模拟验证码获取
    codeUrl.value = "data:image/gif;base64,模拟验证码图像";
  }
  
  getCode();
  </script>
  
  <style lang='scss' scoped>
  @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap');
  
  :root {
    --primary: #5e72e4;
    --dark-primary: #483d8b;
  }
  
  .register-page {
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
  
  .register-container {
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
  .cbi-input-password,
  .cbi-input-code {
    
    width: 100%;
    border-radius: 5px;
    font-size: 16px; /* 增加输入框字体大小 */
    border: 1px solid #ccc; /* 添加边框 */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
    transition: border-color 0.3s; /* 添加过渡效果 */
  }
  
  .cbi-input-user:focus,
  .cbi-input-password:focus,
  .cbi-input-code:focus {
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
  
  .register-code {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .register-code-img {
    cursor: pointer;
    width: 100px; /* 设置验证码图片的宽度 */
    height: auto; /* 高度自适应 */
  }
  </style>
  