<template>
    <el-container>
      <!-- 搜索框和操作按钮 -->
      <el-header>
        <el-row>
          <el-col :span="12">
            <el-input v-model="searchQuery" placeholder="搜索帖子" clearable></el-input>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <el-button type="primary" @click="openAddDialog">添加帖子</el-button>
            <el-button type="danger" @click="batchDelete" :disabled="multipleSelection.length === 0">批量删除</el-button>
          </el-col>
        </el-row>
      </el-header>
  
      <!-- 帖子列表 -->
      <el-main>
        <el-table
          :data="filteredPosts"
          style="width: 100%"
          v-loading="loading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="title" label="标题" width="180" sortable></el-table-column>
          <el-table-column prop="author" label="作者" width="120"></el-table-column>
          <el-table-column prop="createdAt" label="创建时间" width="180" sortable></el-table-column>
          <el-table-column prop="status" label="状态" width="120">
            <template #default="scope">
              <el-tag :type="scope.row.status === '发布' ? 'success' : 'info'">{{ scope.row.status }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <el-button type="primary" @click="openEditDialog(scope.row)" size="mini">编辑</el-button>
              <el-button type="danger" @click="confirmDelete(scope.row)" size="mini">删除</el-button>
              <el-button type="warning" @click="toggleStatus(scope.row)" size="mini">{{ scope.row.status === '发布' ? '下架' : '发布' }}</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <!-- 分页 -->
        <el-pagination
          background
          layout="prev, pager, next"
          :total="totalPosts"
          v-model:current-page="currentPage"
          :page-size="pageSize"
          @current-change="handlePageChange"
        ></el-pagination>
      </el-main>
  
      <!-- 添加/编辑帖子对话框 -->
      <el-dialog :title="dialogTitle" v-model="isDialogVisible">
        <el-form :model="currentPost" ref="postForm" :rules="rules">
          <el-form-item label="标题" prop="title">
            <el-input v-model="currentPost.title"></el-input>
          </el-form-item>
          <el-form-item label="作者" prop="author">
            <el-input v-model="currentPost.author"></el-input>
          </el-form-item>
          <el-form-item label="内容" prop="content">
            <el-input type="textarea" v-model="currentPost.content"></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="isDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="savePost">保存</el-button>
        </template>
      </el-dialog>
  
      <!-- 删除确认对话框 -->
      <el-dialog title="确认删除" v-model="isDeleteDialogVisible">
        <span>确认删除帖子 "{{ deletePostData.title }}" 吗？</span>
        <template #footer>
          <el-button @click="isDeleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="deletePost">确认</el-button>
        </template>
      </el-dialog>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        searchQuery: '',
        posts: [
          { id: 1, title: '帖子一', author: '用户1', content: '这是第一个帖子内容', createdAt: '2024-10-01', status: '发布' },
          { id: 2, title: '帖子二', author: '用户2', content: '这是第二个帖子内容', createdAt: '2024-09-15', status: '草稿' },
          { id: 3, title: '帖子三', author: '用户3', content: '这是第三个帖子内容', createdAt: '2024-09-20', status: '发布' },
        ],
        currentPage: 1,
        pageSize: 5,
        isDialogVisible: false,
        isDeleteDialogVisible: false,
        dialogTitle: '',
        currentPost: {
          id: null,
          title: '',
          author: '',
          content: '',
        },
        deletePostData: {},
        multipleSelection: [],
        loading: false,
        rules: {
          title: [{ required: true, message: '请输入帖子标题', trigger: 'blur' }],
          author: [{ required: true, message: '请输入作者名', trigger: 'blur' }],
          content: [{ required: true, message: '请输入帖子内容', trigger: 'blur' }],
        },
      };
    },
    computed: {
      totalPosts() {
        return this.filteredPosts.length;
      },
      filteredPosts() {
        return this.posts
          .filter((post) => post.title.includes(this.searchQuery))
          .slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize);
      },
    },
    methods: {
      handlePageChange(page) {
        this.currentPage = page;
      },
      openAddDialog() {
        this.dialogTitle = '添加帖子';
        this.currentPost = { id: null, title: '', author: '', content: '' };
        this.isDialogVisible = true;
      },
      openEditDialog(post) {
        this.dialogTitle = '编辑帖子';
        this.currentPost = { ...post };
        this.isDialogVisible = true;
      },
      savePost() {
        this.$refs.postForm.validate((valid) => {
          if (valid) {
            if (this.currentPost.id) {
              const index = this.posts.findIndex((post) => post.id === this.currentPost.id);
              if (index !== -1) {
                this.posts[index] = { ...this.currentPost };
              }
            } else {
              this.currentPost.id = this.posts.length + 1;
              this.posts.push({ ...this.currentPost, createdAt: new Date().toISOString().slice(0, 10), status: '发布' });
            }
            this.isDialogVisible = false;
            this.$message.success('保存成功');
          }
        });
      },
      confirmDelete(post) {
        this.deletePostData = post;
        this.isDeleteDialogVisible = true;
      },
      deletePost() {
        this.posts = this.posts.filter((post) => post.id !== this.deletePostData.id);
        this.isDeleteDialogVisible = false;
        this.$message.success('删除成功');
      },
      toggleStatus(post) {
        post.status = post.status === '发布' ? '草稿' : '发布';
        this.$message.success('状态已更新');
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      batchDelete() {
        this.posts = this.posts.filter((post) => !this.multipleSelection.includes(post));
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
  