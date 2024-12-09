<template>
    <el-container>
      <!-- 搜索标签和操作按钮 -->
      <el-header>
        <el-row>
          <el-col :span="12">
            <el-input v-model="searchQuery" placeholder="搜索标签" clearable></el-input>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <el-button type="primary" @click="openTagDialog">添加标签</el-button>
            <el-button type="danger" @click="batchRemoveTags" :disabled="multipleSelection.length === 0">批量删除标签</el-button>
          </el-col>
        </el-row>
      </el-header>
  
      <!-- 帖子标签列表 -->
      <el-main>
        <el-table
          :data="filteredPosts"
          style="width: 100%"
          v-loading="loading"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column prop="postTitle" label="帖子标题" width="250"></el-table-column>
          <el-table-column prop="tags" label="标签" width="300">
            <template #default="scope">
              <el-tag
                v-for="tag in scope.row.tags"
                :key="tag"
                type="info"
                closable
                @close="removeTag(scope.row, tag)"
              >
                {{ tag }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <el-button type="primary" @click="openEditTagDialog(scope.row)" size="mini">编辑标签</el-button>
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
  
      <!-- 添加/编辑标签对话框 -->
      <el-dialog :title="dialogTitle" v-model="isTagDialogVisible">
        <el-form :model="currentPost">
          <el-form-item label="标签">
            <el-select
              v-model="currentTags"
              multiple
              placeholder="请选择标签"
              :filterable="true"
              style="width: 100%;"
            >
              <el-option
                v-for="tag in availableTags"
                :key="tag"
                :label="tag"
                :value="tag"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="isTagDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveTags">保存</el-button>
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
          { id: 1, postTitle: '帖子一', tags: ['JavaScript', 'Vue.js'], createdAt: '2024-10-01' },
          { id: 2, postTitle: '帖子二', tags: ['HTML', 'CSS'], createdAt: '2024-09-20' },
          { id: 3, postTitle: '帖子三', tags: ['JavaScript', 'React'], createdAt: '2024-09-30' },
        ],
        availableTags: ['JavaScript', 'Vue.js', 'React', 'HTML', 'CSS', 'Node.js', 'PHP'],
        currentPage: 1,
        pageSize: 5,
        isTagDialogVisible: false,
        dialogTitle: '',
        currentPost: {},
        currentTags: [],
        multipleSelection: [],
        loading: false,
      };
    },
    computed: {
      totalPosts() {
        return this.filteredPosts.length;
      },
      filteredPosts() {
        return this.posts
          .filter((post) => post.postTitle.includes(this.searchQuery))
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
      openTagDialog() {
        this.dialogTitle = '批量添加标签';
        this.currentPost = {};
        this.currentTags = [];
        this.isTagDialogVisible = true;
      },
      openEditTagDialog(post) {
        this.dialogTitle = '编辑标签';
        this.currentPost = { ...post };
        this.currentTags = [...post.tags];
        this.isTagDialogVisible = true;
      },
      saveTags() {
        if (this.currentPost.id) {
          const index = this.posts.findIndex((p) => p.id === this.currentPost.id);
          if (index !== -1) {
            this.posts[index].tags = [...this.currentTags];
          }
        } else {
          this.multipleSelection.forEach((post) => {
            post.tags = Array.from(new Set([...post.tags, ...this.currentTags]));
          });
        }
        this.isTagDialogVisible = false;
        this.$message.success('标签已保存');
      },
      removeTag(post, tag) {
        post.tags = post.tags.filter((t) => t !== tag);
        this.$message.success(`标签 "${tag}" 已删除`);
      },
      batchRemoveTags() {
        this.multipleSelection.forEach((post) => {
          post.tags = [];
        });
        this.$message.success('批量删除标签成功');
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
  