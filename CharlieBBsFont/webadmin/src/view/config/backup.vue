<template>
    <el-container>
      <!-- 页面标题 -->
      <el-header>
        <h2>备份与恢复管理</h2>
      </el-header>
  
      <el-main>
        <el-tabs v-model="activeTab">
          <!-- 备份设置 Tab -->
          <el-tab-pane label="备份" name="backup">
            <el-form label-width="120px">
              <el-form-item label="备份文件列表">
                <el-table :data="backupFiles" style="width: 100%">
                  <el-table-column prop="name" label="文件名" width="300"></el-table-column>
                  <el-table-column prop="size" label="文件大小"></el-table-column>
                  <el-table-column prop="date" label="备份时间"></el-table-column>
                  <el-table-column label="操作">
                    <template #default="scope">
                      <el-button size="mini" @click="downloadBackup(scope.row)">下载</el-button>
                      <el-button size="mini" type="danger" @click="deleteBackup(scope.row)">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-form-item>
  
              <el-form-item>
                <el-button type="primary" @click="createBackup">立即备份</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
  
          <!-- 恢复设置 Tab -->
          <el-tab-pane label="恢复" name="restore">
            <el-form label-width="120px">
              <el-form-item label="选择备份文件">
                <el-select v-model="selectedBackup" placeholder="请选择备份文件">
                  <el-option
                    v-for="file in backupFiles"
                    :key="file.name"
                    :label="`${file.name} - ${file.date}`"
                    :value="file.name"
                  ></el-option>
                </el-select>
              </el-form-item>
  
              <el-form-item>
                <el-button type="primary" @click="restoreBackup" :disabled="!selectedBackup">恢复备份</el-button>
              </el-form-item>
  
              <el-form-item label="恢复日志">
                <el-table :data="restoreLogs" style="width: 100%">
                  <el-table-column prop="date" label="恢复时间"></el-table-column>
                  <el-table-column prop="status" label="状态"></el-table-column>
                </el-table>
              </el-form-item>
            </el-form>
          </el-tab-pane>
  
          <!-- 自动备份设置 Tab -->
          <el-tab-pane label="自动备份" name="auto-backup">
            <el-form label-width="120px">
              <el-form-item label="定时备份周期">
                <el-radio-group v-model="autoBackupSchedule">
                  <el-radio label="每天">每天</el-radio>
                  <el-radio label="每周">每周</el-radio>
                  <el-radio label="每月">每月</el-radio>
                </el-radio-group>
              </el-form-item>
  
              <el-form-item label="最大备份文件数">
                <el-input-number v-model="maxBackupFiles" :min="1" :max="100"></el-input-number>
              </el-form-item>
  
              <el-form-item>
                <el-button type="primary" @click="saveAutoBackupSettings">保存设置</el-button>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
  
        <!-- 操作按钮 -->
        <el-row style="margin-top: 20px; text-align: right;">
          <el-button @click="refreshPage">刷新页面</el-button>
        </el-row>
      </el-main>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        activeTab: 'backup',
        backupFiles: [
          { name: 'backup_20241001.zip', size: '5MB', date: '2024-10-01' },
          { name: 'backup_20240925.zip', size: '4MB', date: '2024-09-25' },
        ],
        selectedBackup: '',
        restoreLogs: [],
        autoBackupSchedule: '每天',
        maxBackupFiles: 10,
      };
    },
    methods: {
      createBackup() {
        // 模拟备份操作
        this.$message.success('备份已创建');
        this.backupFiles.push({
          name: `backup_${new Date().toISOString().slice(0, 10)}.zip`,
          size: '5MB',
          date: new Date().toISOString().slice(0, 10),
        });
      },
      downloadBackup(file) {
        this.$message.success(`开始下载：${file.name}`);
        // 下载备份文件逻辑
      },
      deleteBackup(file) {
        this.$confirm(`确定删除备份文件 ${file.name} 吗？`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          this.backupFiles = this.backupFiles.filter((item) => item.name !== file.name);
          this.$message.success('备份文件已删除');
        });
      },
      restoreBackup() {
        this.$message.success(`正在恢复备份：${this.selectedBackup}`);
        this.restoreLogs.push({
          date: new Date().toISOString(),
          status: `已成功恢复 ${this.selectedBackup}`,
        });
        this.selectedBackup = '';
      },
      saveAutoBackupSettings() {
        this.$message.success('自动备份设置已保存');
        // 保存自动备份设置逻辑
      },
      refreshPage() {
        this.$message.info('页面已刷新');
        // 实时刷新逻辑
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
  