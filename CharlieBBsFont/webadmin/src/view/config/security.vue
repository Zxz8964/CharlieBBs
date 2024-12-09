<template>
    <el-container>
      <!-- 页面标题 -->
      <el-header>
        <h2>安全设置管理</h2>
      </el-header>
  
      <el-main>
        <el-tabs v-model="activeTab">
          <!-- 用户认证设置 Tab -->
          <el-tab-pane label="用户认证" name="authentication">
            <el-form label-width="150px">
              <el-form-item label="启用双重验证 (2FA)">
                <el-switch v-model="enable2FA"></el-switch>
              </el-form-item>
  
              <el-form-item label="密码强度要求">
                <el-select v-model="passwordStrength" placeholder="选择密码强度">
                  <el-option label="弱 (6字符)" value="weak"></el-option>
                  <el-option label="中 (8字符, 至少一个数字)" value="medium"></el-option>
                  <el-option label="强 (10字符, 至少一个数字和符号)" value="strong"></el-option>
                </el-select>
              </el-form-item>
  
              <el-form-item>
                <el-button type="primary" @click="saveAuthenticationSettings">保存设置</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
  
          <!-- 审计日志设置 Tab -->
          <el-tab-pane label="安全日志" name="audit-logs">
            <el-table :data="securityLogs" style="width: 100%">
              <el-table-column prop="date" label="时间" width="180"></el-table-column>
              <el-table-column prop="action" label="操作"></el-table-column>
              <el-table-column prop="user" label="用户"></el-table-column>
              <el-table-column prop="ip" label="IP 地址"></el-table-column>
            </el-table>
          </el-tab-pane>
  
          <!-- IP 管理设置 Tab -->
          <el-tab-pane label="IP 管理" name="ip-management">
            <el-form label-width="150px">
              <el-form-item label="IP 白名单">
                <el-input v-model="ipWhitelist" placeholder="输入允许访问的 IP (多个 IP 使用逗号分隔)"></el-input>
              </el-form-item>
  
              <el-form-item label="IP 黑名单">
                <el-input v-model="ipBlacklist" placeholder="输入封禁的 IP (多个 IP 使用逗号分隔)"></el-input>
              </el-form-item>
  
              <el-form-item>
                <el-button type="primary" @click="saveIPSettings">保存 IP 设置</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
  
          <!-- 账户保护设置 Tab -->
          <el-tab-pane label="账户保护" name="account-protection">
            <el-form label-width="150px">
              <el-form-item label="登录失败锁定">
                <el-input-number v-model="failedLoginLimit" :min="1" :max="10"></el-input-number>
                <span>次失败后锁定账户</span>
              </el-form-item>
  
              <el-form-item label="强制密码更换周期 (天)">
                <el-input-number v-model="passwordExpirationDays" :min="30" :max="365"></el-input-number>
              </el-form-item>
  
              <el-form-item>
                <el-button type="primary" @click="saveAccountProtectionSettings">保存设置</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        activeTab: 'authentication',
        enable2FA: false,
        passwordStrength: 'medium',
        securityLogs: [
          { date: '2024-10-23 10:00', action: '登录成功', user: 'admin', ip: '192.168.0.1' },
          { date: '2024-10-23 10:05', action: '密码更改', user: 'user1', ip: '192.168.0.2' },
        ],
        ipWhitelist: '',
        ipBlacklist: '',
        failedLoginLimit: 5,
        passwordExpirationDays: 90,
      };
    },
    methods: {
      saveAuthenticationSettings() {
        this.$message.success('用户认证设置已保存');
        // 保存用户认证设置逻辑
      },
      saveIPSettings() {
        this.$message.success('IP 管理设置已保存');
        // 保存 IP 设置逻辑
      },
      saveAccountProtectionSettings() {
        this.$message.success('账户保护设置已保存');
        // 保存账户保护设置逻辑
      },
    },
  };
  </script>
  
  <style scoped>
  .el-header {
    padding: 10px;
    background-color: #f5f7fa;
  }
  </style>
  