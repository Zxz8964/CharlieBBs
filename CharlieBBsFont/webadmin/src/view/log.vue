<template>
    <el-container>
      <!-- 页面标题 -->
      <el-header>
        <h2>系统日志管理</h2>
      </el-header>
  
      <el-main>
        <!-- 日志筛选 -->
        <el-row :gutter="20" style="margin-bottom: 20px;">
          <el-col :span="8">
            <el-date-picker v-model="logDateRange" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-col>
          <el-col :span="6">
            <el-select v-model="logType" placeholder="选择日志类型">
              <el-option label="所有日志" value=""></el-option>
              <el-option label="用户活动" value="user_activity"></el-option>
              <el-option label="系统事件" value="system_event"></el-option>
              <el-option label="安全日志" value="security"></el-option>
              <el-option label="管理员操作" value="admin_action"></el-option>
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-input v-model="searchQuery" placeholder="搜索日志"></el-input>
          </el-col>
          <el-col :span="4" style="text-align: right;">
            <el-button type="primary" @click="filterLogs">筛选</el-button>
            <el-button @click="resetFilters">重置</el-button>
          </el-col>
        </el-row>
  
        <!-- 日志列表 -->
        <el-table :data="filteredLogs" style="width: 100%" v-loading="loading">
          <el-table-column prop="date" label="时间" width="180"></el-table-column>
          <el-table-column prop="type" label="类型" width="150"></el-table-column>
          <el-table-column prop="user" label="用户" width="150"></el-table-column>
          <el-table-column prop="action" label="操作"></el-table-column>
          <el-table-column label="详情">
            <template #default="scope">
              <el-button @click="viewLogDetails(scope.row)" type="text">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <!-- 分页 -->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="totalLogs"
          v-model:current-page="currentPage"
          :page-size="pageSize"
          @current-change="handlePageChange"
        ></el-pagination>
  
        <!-- 日志详情对话框 -->
        <el-dialog title="日志详情" v-model="isLogDetailsVisible" width="600px">
          <pre>{{ logDetails }}</pre>
          <template #footer>
            <el-button @click="isLogDetailsVisible = false">关闭</el-button>
          </template>
        </el-dialog>
  
        <!-- 导出与清理按钮 -->
        <el-row :gutter="20" style="margin-top: 20px;">
          <el-col :span="12">
            <el-button type="success" @click="exportLogs('csv')">导出 CSV</el-button>
            <el-button type="info" @click="exportLogs('json')">导出 JSON</el-button>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <el-button type="danger" @click="cleanOldLogs">清理过期日志</el-button>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        logDateRange: [],
        logType: '',
        searchQuery: '',
        logs: [
          { date: '2024-10-23 10:00', type: '用户活动', user: 'admin', action: '登录成功', details: '管理员登录成功，IP地址：192.168.0.1' },
          { date: '2024-10-23 10:05', type: '系统事件', user: 'system', action: '数据库备份', details: '自动备份成功' },
          { date: '2024-10-23 11:00', type: '安全日志', user: 'user1', action: '密码错误', details: '用户user1尝试登录，密码输入错误3次' },
          { date: '2024-10-23 11:15', type: '管理员操作', user: 'admin', action: '删除帖子', details: '管理员删除帖子ID: 12345' },
        ],
        filteredLogs: [],
        currentPage: 1,
        pageSize: 10,
        loading: false,
        isLogDetailsVisible: false,
        logDetails: '',
        ws: null,  // WebSocket 实例
      };
    },
    computed: {
      totalLogs() {
        return this.filteredLogs.length;
      },
    },
    methods: {
      filterLogs() {
        this.loading = true;
        setTimeout(() => {
          // 模拟筛选操作
          this.filteredLogs = this.logs
            .filter(log => {
              const matchesType = this.logType ? log.type === this.logType : true;
              const matchesSearch = log.action.includes(this.searchQuery) || log.details.includes(this.searchQuery);
              const matchesDate = this.logDateRange.length
                ? new Date(log.date) >= new Date(this.logDateRange[0]) && new Date(log.date) <= new Date(this.logDateRange[1])
                : true;
              return matchesType && matchesSearch && matchesDate;
            })
            .slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
          this.loading = false;
        }, 500);
      },
      resetFilters() {
        this.logDateRange = [];
        this.logType = '';
        this.searchQuery = '';
        this.filterLogs();
      },
      handlePageChange(page) {
        this.currentPage = page;
        this.filterLogs();
      },
      viewLogDetails(log) {
        this.logDetails = log.details;
        this.isLogDetailsVisible = true;
      },
      exportLogs(format) {
        const exportedLogs = this.logs.map(log => ({
          date: log.date,
          type: log.type,
          user: log.user,
          action: log.action,
          details: log.details,
        }));
  
        const dataStr = format === 'csv'
          ? this.convertToCSV(exportedLogs)
          : JSON.stringify(exportedLogs, null, 2);
  
        const blob = new Blob([dataStr], { type: format === 'csv' ? 'text/csv' : 'application/json' });
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = `logs.${format}`;
        link.click();
        URL.revokeObjectURL(url);
      },
      convertToCSV(data) {
        const header = Object.keys(data[0]).join(',');
        const rows = data.map(row => Object.values(row).join(',')).join('\n');
        return `${header}\n${rows}`;
      },
      cleanOldLogs() {
        // 假设日志保留30天
        const THIRTY_DAYS = 30 * 24 * 60 * 60 * 1000;
        const now = Date.now();
        this.logs = this.logs.filter(log => new Date(log.date).getTime() >= now - THIRTY_DAYS);
        this.$message.success('过期日志已清理');
      },
      connectWebSocket() {
        this.ws = new WebSocket('wss://your-server-address/logs');
        this.ws.onmessage = (event) => {
          const newLog = JSON.parse(event.data);
          this.logs.unshift(newLog);
          this.filterLogs(); // 实时更新过滤结果
        };
      },
    },
    mounted() {
      this.filteredLogs = this.logs.slice(0, this.pageSize);
      this.connectWebSocket(); // 连接 WebSocket 以接收实时日志
    },
    beforeDestroy() {
      if (this.ws) {
        this.ws.close();
      }
    },
  };
  </script>
  
  <style scoped>
  .el-header {
    padding: 10px;
    background-color: #f5f7fa;
  }
  </style>
  