
<template>
    <el-container>
      <!-- 搜索框和操作按钮 -->
      <el-header>
        <el-row>
          <el-col :span="12">
            <el-input v-model="searchQuery" placeholder="搜索评论" clearable></el-input>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <el-button type="danger" @click="batchDelete" :disabled="multipleSelection.length === 0">批量删除</el-button>
          </el-col>
        </el-row>
      </el-header>
  
      <!-- 评论列表 -->
      <el-main>
        <el-table
          :data="filteredComments"
          style="width: 100%"
          v-loading="loading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="postTitle" label="帖子标题" width="180"></el-table-column>
          <el-table-column prop="author" label="评论者" width="120"></el-table-column>
          <el-table-column prop="content" label="评论内容"></el-table-column>
          <el-table-column prop="createdAt" label="评论时间" width="180" sortable></el-table-column>
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <el-button type="primary" @click="openEditDialog(scope.row)" size="mini">编辑</el-button>
              <el-button type="danger" @click="confirmDelete(scope.row)" size="mini">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <!-- 分页 -->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="totalComments"
          v-model:current-page="currentPage"
          :page-size="pageSize"
          @current-change="handlePageChange"
        ></el-pagination>
      </el-main>
  
      <!-- 编辑评论对话框 -->
      <el-dialog :title="dialogTitle" v-model="isDialogVisible">
        <el-form :model="currentComment" ref="commentForm" :rules="rules">
          <el-form-item label="评论内容" prop="content">
            <el-input type="textarea" v-model="currentComment.content"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="isDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveComment">保存</el-button>
        </template>
      </el-dialog>
  
      <!-- 删除确认对话框 -->
      <el-dialog title="确认删除" v-model="isDeleteDialogVisible">
        <span>确认删除评论吗？</span>
        <template #footer>
          <el-button @click="isDeleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="deleteComment">确认</el-button>
        </template>
      </el-dialog>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        searchQuery: '',
        comments: [
          { id: 1, postTitle: '帖子一', author: '用户1', content: '这是第一个评论内容', createdAt: '2024-10-01' },
          { id: 2, postTitle: '帖子二', author: '用户2', content: '这是第二个评论内容', createdAt: '2024-09-15' },
          { id: 3, postTitle: '帖子三', author: '用户3', content: '这是第三个评论内容', createdAt: '2024-09-20' },
        ],
        currentPage: 1,
        pageSize: 5,
        isDialogVisible: false,
        isDeleteDialogVisible: false,
        dialogTitle: '',
        currentComment: {
          id: null,
          postTitle: '',
          author: '',
          content: '',
        },
        deleteCommentData: {},
        multipleSelection: [],
        loading: false,
        rules: {
          content: [{ required: true, message: '请输入评论内容', trigger: 'blur' }],
        },
      };
    },
    computed: {
      totalComments() {
        return this.filteredComments.length;
      },
      filteredComments() {
        return this.comments
          .filter((comment) => comment.content.includes(this.searchQuery))
          .slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
      },
    },
    methods: {
      handlePageChange(page) {
        this.currentPage = page;
      },
      openEditDialog(comment) {
        this.dialogTitle = '编辑评论';
        this.currentComment = { ...comment };
        this.isDialogVisible = true;
      },
      saveComment() {
        this.$refs.commentForm.validate((valid) => {
          if (valid) {
            if (this.currentComment.id) {
              const index = this.comments.findIndex((comment) => comment.id === this.currentComment.id);
              if (index !== -1) {
                this.comments[index] = { ...this.currentComment };
              }
            }
            this.isDialogVisible = false;
            this.$message.success('保存成功');
          }
        });
      },
      confirmDelete(comment) {
        this.deleteCommentData = comment;
        this.isDeleteDialogVisible = true;
      },
      deleteComment() {
        this.comments = this.comments.filter((comment) => comment.id !== this.deleteCommentData.id);
        this.isDeleteDialogVisible = false;
        this.$message.success('删除成功');
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      batchDelete() {
        this.comments = this.comments.filter((comment) => !this.multipleSelection.includes(comment));
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
  </style>
  