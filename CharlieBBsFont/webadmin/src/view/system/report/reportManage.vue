<template>
    <el-container>
      <!-- 搜索举报和操作按钮 -->
      <el-header>
        <el-row>
          <el-col :span="12">
            <el-input v-model="searchQuery" placeholder="搜索举报内容" clearable></el-input>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <el-button type="primary" @click="batchResolve" :disabled="multipleSelection.length === 0">批量标记为已处理</el-button>
            <el-button type="danger" @click="batchDelete" :disabled="multipleSelection.length === 0">批量删除</el-button>
          </el-col>
        </el-row>
      </el-header>
  
      <!-- 举报列表 -->
      <el-main>
        <el-table
          :data="filteredReports"
          style="width: 100%"
          v-loading="loading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="reportedItem" label="举报项" width="180" sortable></el-table-column>
          <el-table-column prop="reporter" label="举报人" width="120"></el-table-column>
          <el-table-column prop="reason" label="举报原因"></el-table-column>
          <el-table-column prop="status" label="状态" width="120">
            <template #default="scope">
              <el-tag :type="scope.row.status === '已处理' ? 'success' : 'warning'">{{ scope.row.status }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createdAt" label="举报时间" width="180" sortable></el-table-column>
          <el-table-column label="操作" width="220">
            <template #default="scope">
              <el-button type="primary" @click="resolveReport(scope.row)" size="mini">标记为已处理</el-button>
              <el-button type="danger" @click="confirmDelete(scope.row)" size="mini">删除</el-button>
              <el-button type="info" @click="viewDetails(scope.row)" size="mini">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <!-- 分页 -->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="totalReports"
          v-model:current-page="currentPage"
          :page-size="pageSize"
          @current-change="handlePageChange"
        ></el-pagination>
      </el-main>
  
      <!-- 删除确认对话框 -->
      <el-dialog title="确认删除" v-model="isDeleteDialogVisible">
        <span>确认删除举报内容 "{{ deleteReportData.reportedItem }}" 吗？</span>
        <template #footer>
          <el-button @click="isDeleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="deleteReport">确认</el-button>
        </template>
      </el-dialog>
  
      <!-- 举报详情对话框 -->
      <el-dialog title="举报详情" v-model="isDetailDialogVisible">
        <el-form :model="detailReport">
          <el-form-item label="举报项">
            <el-input v-model="detailReport.reportedItem" disabled></el-input>
          </el-form-item>
          <el-form-item label="举报人">
            <el-input v-model="detailReport.reporter" disabled></el-input>
          </el-form-item>
          <el-form-item label="举报原因">
            <el-input type="textarea" v-model="detailReport.reason" disabled></el-input>
          </el-form-item>
          <el-form-item label="举报时间">
            <el-input v-model="detailReport.createdAt" disabled></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="isDetailDialogVisible = false">关闭</el-button>
        </template>
      </el-dialog>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        searchQuery: '',
        reports: [
          { id: 1, reportedItem: '帖子: 如何学习编程', reporter: '用户A', reason: '包含不当内容', status: '未处理', createdAt: '2024-10-01' },
          { id: 2, reportedItem: '评论: 编程太难了', reporter: '用户B', reason: '侮辱性语言', status: '未处理', createdAt: '2024-10-02' },
          { id: 3, reportedItem: '帖子: 最新技术趋势', reporter: '用户C', reason: '广告内容', status: '已处理', createdAt: '2024-10-03' },
        ],
        currentPage: 1,
        pageSize: 5,
        isDeleteDialogVisible: false,
        isDetailDialogVisible: false,
        deleteReportData: {},
        detailReport: {},
        multipleSelection: [],
        loading: false,
      };
    },
    computed: {
      totalReports() {
        return this.filteredReports.length;
      },
      filteredReports() {
        return this.reports
          .filter((report) => report.reportedItem.includes(this.searchQuery) || report.reporter.includes(this.searchQuery))
          .slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
      },
    },
    methods: {
      handlePageChange(page) {
        this.currentPage = page;
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      resolveReport(report) {
        report.status = '已处理';
        this.$message.success('举报已标记为已处理');
      },
      batchResolve() {
        this.multipleSelection.forEach((report) => {
          report.status = '已处理';
        });
        this.$message.success('批量标记为已处理成功');
        this.multipleSelection = [];
      },
      confirmDelete(report) {
        this.deleteReportData = report;
        this.isDeleteDialogVisible = true;
      },
      deleteReport() {
        this.reports = this.reports.filter((report) => report.id !== this.deleteReportData.id);
        this.isDeleteDialogVisible = false;
        this.$message.success('举报已删除');
      },
      batchDelete() {
        this.reports = this.reports.filter((report) => !this.multipleSelection.includes(report));
        this.$message.success('批量删除成功');
        this.multipleSelection = [];
      },
      viewDetails(report) {
        this.detailReport = { ...report };
        this.isDetailDialogVisible = true;
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
  