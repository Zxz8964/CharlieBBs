<template>
    <el-container>
      <!-- 标签搜索框和操作按钮 -->
      <el-header>
        <el-row>
          <el-col :span="12">
            <el-input v-model="searchQuery" placeholder="搜索标签" clearable></el-input>
          </el-col>
          <el-col :span="12" style="text-align: right;">
            <el-button type="primary" @click="openAddDialog">添加父标签</el-button>
            <el-button type="danger" @click="batchDelete" :disabled="multipleSelection.length === 0">批量删除</el-button>
          </el-col>
        </el-row>
      </el-header>
  
      <!-- 标签树状结构展示 -->
      <el-main>
        <el-tree
          :data="filteredTags"
          :props="defaultProps"
          node-key="id"
          highlight-current
          @node-click="handleNodeClick"
          :expand-on-click-node="false"
          ref="tagTree"
        >
          <template #default="{ node, data }">
            <span>{{ data.name }}</span>
            <el-button type="primary" @click="openEditDialog(data)" size="mini">编辑</el-button>
            <el-button type="danger" @click="confirmDelete(data)" size="mini">删除</el-button>
            <el-button type="success" @click="openAddChildDialog(data)" size="mini">添加子标签</el-button>
          </template>
        </el-tree>
      </el-main>
  
      <!-- 添加/编辑标签对话框 -->
      <el-dialog :title="dialogTitle" v-model="isDialogVisible">
        <el-form :model="currentTag" ref="tagForm" :rules="rules">
          <el-form-item label="标签名称" prop="name">
            <el-input v-model="currentTag.name"></el-input>
          </el-form-item>
          <el-form-item label="标签描述" prop="description">
            <el-input v-model="currentTag.description"></el-input>
          </el-form-item>
          <el-form-item label="父标签" v-if="isAddingChild">
            <el-input v-model="parentTag.name" disabled></el-input>
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="isDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="saveTag">保存</el-button>
        </template>
      </el-dialog>
  
      <!-- 删除确认对话框 -->
      <el-dialog title="确认删除" v-model="isDeleteDialogVisible">
        <span>确认删除标签 "{{ deleteTagData.name }}" 吗？此操作将同时删除其所有子标签。</span>
        <template #footer>
          <el-button @click="isDeleteDialogVisible = false">取消</el-button>
          <el-button type="danger" @click="deleteTag">确认</el-button>
        </template>
      </el-dialog>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        searchQuery: '',
        tags: [
          { id: 1, name: '技术', description: '与技术相关的内容', children: [
              { id: 2, name: '编程语言', description: '编程语言讨论' },
              { id: 3, name: '操作系统', description: '操作系统相关' }
            ] 
          },
          { id: 4, name: '娱乐', description: '娱乐内容' }
        ],
        currentTag: {
          id: null,
          name: '',
          description: '',
        },
        parentTag: null, // 当前的父标签数据
        dialogTitle: '',
        isDialogVisible: false,
        isDeleteDialogVisible: false,
        deleteTagData: {},
        multipleSelection: [],
        rules: {
          name: [{ required: true, message: '请输入标签名称', trigger: 'blur' }],
          description: [{ required: true, message: '请输入标签描述', trigger: 'blur' }],
        },
        isAddingChild: false, // 标记当前是否是在添加子标签
      };
    },
    computed: {
      filteredTags() {
        if (!this.searchQuery) return this.tags;
        const searchLower = this.searchQuery.toLowerCase();
        return this.tags.filter(tag => tag.name.toLowerCase().includes(searchLower) || tag.children.some(child => child.name.toLowerCase().includes(searchLower)));
      },
      defaultProps() {
        return {
          children: 'children',
          label: 'name',
        };
      }
    },
    methods: {
      handleNodeClick(data) {
        console.log('节点点击', data);
      },
      openAddDialog() {
        this.dialogTitle = '添加父标签';
        this.currentTag = { id: null, name: '', description: '' };
        this.isAddingChild = false;
        this.isDialogVisible = true;
      },
      openAddChildDialog(parent) {
        this.dialogTitle = `为 ${parent.name} 添加子标签`;
        this.currentTag = { id: null, name: '', description: '' };
        this.parentTag = parent;
        this.isAddingChild = true;
        this.isDialogVisible = true;
      },
      openEditDialog(tag) {
        this.dialogTitle = '编辑标签';
        this.currentTag = { ...tag };
        this.isDialogVisible = true;
      },
      saveTag() {
        this.$refs.tagForm.validate((valid) => {
          if (valid) {
            if (this.isAddingChild) {
              if (!this.parentTag.children) {
                this.parentTag.children = [];
              }
              this.parentTag.children.push({ ...this.currentTag, id: Date.now() });
            } else {
              if (this.currentTag.id) {
                // 编辑已有标签
                const tagIndex = this.tags.findIndex(tag => tag.id === this.currentTag.id);
                if (tagIndex !== -1) {
                  this.tags[tagIndex] = { ...this.currentTag };
                }
              } else {
                // 添加新父标签
                this.currentTag.id = Date.now();
                this.tags.push({ ...this.currentTag });
              }
            }
            this.isDialogVisible = false;
            this.$message.success('标签保存成功');
          }
        });
      },
      confirmDelete(tag) {
        this.deleteTagData = tag;
        this.isDeleteDialogVisible = true;
      },
      deleteTag() {
        const deleteRecursive = (tags, tagToDelete) => {
          return tags.filter(tag => tag.id !== tagToDelete.id).map(tag => {
            if (tag.children) {
              tag.children = deleteRecursive(tag.children, tagToDelete);
            }
            return tag;
          });
        };
        this.tags = deleteRecursive(this.tags, this.deleteTagData);
        this.isDeleteDialogVisible = false;
        this.$message.success('删除成功');
      },
      batchDelete() {
        this.tags = this.tags.filter(tag => !this.multipleSelection.includes(tag));
        this.$message.success('批量删除成功');
      },
    },
  };
  </script>
  
  <style scoped>
  .el-header {
    padding: 10px;
    background-color: #f5f7fa;
  }
  .el-tree-node__content {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  </style>
  