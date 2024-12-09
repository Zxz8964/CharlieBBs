<template>
    <el-container>
      <!-- 搜索框和操作按钮 -->
      <el-header>
        <el-row>
          <el-col :span="12">
            <el-input v-model="searchQuery" placeholder="搜索板块" clearable></el-input>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <el-button type="primary" @click="openAddDialog">添加板块</el-button>
            <el-button type="danger" @click="batchDelete" :disabled="multipleSelection.length === 0">批量删除</el-button>
          </el-col>
        </el-row>
      </el-header>
  
      <!-- 板块列表 -->
      <el-main>
        <el-table
          :data="filteredSections"
          style="width: 100%"
          v-loading="loading"
          @selection-change="handleSelectionChange"
          :empty-text="searchQuery ? '未找到相关板块' : '暂无数据'"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="name" label="板块名称" width="180" sortable></el-table-column>
          <el-table-column prop="description" label="板块描述"></el-table-column>
          <el-table-column prop="createdAt" label="创建时间" width="180" sortable></el-table-column>
          <el-table-column label="状态" width="120">
            <template #default="scope">
              <el-tag :type="scope.row.status === '启用' ? 'success' : 'info'">{{ scope.row.status }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <el-button type="primary" @click="openEditDialog(scope.row)" size="mini">编辑</el-button>
              <el-button type="danger" @click="confirmDelete(scope.row)" size="mini">删除</el-button>
              <el-button type="warning" @click="toggleStatus(scope.row)" size="mini">{{ scope.row.status === '启用' ? '禁用' : '启用' }}</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <!-- 分页 -->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="totalSections"
          v-model:current-page="currentPage"
          :page-size="pageSize"
          @current-change="handlePageChange"
        ></el-pagination>
      </el-main>
  
      <!-- 添加/编辑板块对话框 -->
      <el-dialog :title="dialogTitle" v-model="isDialogVisible">
        <el-form :model="currentSection" ref="sectionForm" :rules="rules">
          <el-form-item label="板块名称" prop="name">
            <el-input v-model="currentSection.name"></el-input>
          </el-form-item>
          <el-form-item label="板块描述" prop="description">
            <el-input v-model="currentSection.description"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="isDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveSection">保存</el-button>
        </template>
      </el-dialog>
  
      <!-- 删除确认对话框 -->
      <el-dialog title="确认删除" v-model="isDeleteDialogVisible">
        <span>确认删除板块 "{{ deleteSectionData.name }}" 吗？</span>
        <template #footer>
          <el-button @click="isDeleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="deleteSection">确认</el-button>
        </template>
      </el-dialog>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        searchQuery: '',
        sections: [
          { id: 1, name: '板块一', description: '这是第一个板块', createdAt: '2024-10-01', status: '启用' },
          { id: 2, name: '板块二', description: '这是第二个板块', createdAt: '2024-09-15', status: '禁用' },
          { id: 3, name: '板块三', description: '这是第三个板块', createdAt: '2024-09-20', status: '启用' },
        ],
        currentPage: 1,
        pageSize: 5,
        isDialogVisible: false,
        isDeleteDialogVisible: false,
        dialogTitle: '',
        currentSection: {
          id: null,
          name: '',
          description: '',
        },
        deleteSectionData: {},
        multipleSelection: [],
        loading: false,
        rules: {
          name: [{ required: true, message: '请输入板块名称', trigger: 'blur' }],
          description: [{ required: true, message: '请输入板块描述', trigger: 'blur' }],
        },
      };
    },
    computed: {
      totalSections() {
        return this.filteredSections.length;
      },
      filteredSections() {
        return this.sections
          .filter((section) => section.name.includes(this.searchQuery))
          .slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
      },
    },
    methods: {
      handlePageChange(page) {
        this.currentPage = page;
      },
      openAddDialog() {
        this.dialogTitle = '添加板块';
        this.currentSection = { id: null, name: '', description: '' };
        this.isDialogVisible = true;
      },
      openEditDialog(section) {
        this.dialogTitle = '编辑板块';
        this.currentSection = { ...section };
        this.isDialogVisible = true;
      },
      saveSection() {
        this.$refs.sectionForm.validate((valid) => {
          if (valid) {
            if (this.currentSection.id) {
              const index = this.sections.findIndex((section) => section.id === this.currentSection.id);
              if (index !== -1) {
                this.sections[index] = { ...this.currentSection };
              }
            } else {
              this.currentSection.id = this.sections.length + 1;
              this.sections.push({ ...this.currentSection, createdAt: new Date().toISOString().slice(0, 10), status: '启用' });
            }
            this.isDialogVisible = false;
            this.$message.success('保存成功');
          }
        });
      },
      confirmDelete(section) {
        this.deleteSectionData = section;
        this.isDeleteDialogVisible = true;
      },
      deleteSection() {
        this.sections = this.sections.filter((section) => section.id !== this.deleteSectionData.id);
        this.isDeleteDialogVisible = false;
        this.$message.success('删除成功');
      },
      toggleStatus(section) {
        section.status = section.status === '启用' ? '禁用' : '启用';
        this.$message.success('状态已更新');
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      batchDelete() {
        this.sections = this.sections.filter((section) => !this.multipleSelection.includes(section));
        this.$message.success('批量删除成功');
        this.multipleSelection = [];
      },
    },
  };
  </script>
  
  <style scoped>
  .el-header {
    padding: 10px;
    background-color: #f5f7fa;
  }
  
  .el-button {
    border-radius: 5px;
  }
  
  .el-input {
    border-radius: 5px;
  }
  </style>
  