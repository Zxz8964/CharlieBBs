<template>
  <div class="app-container" style="background-color: #f5f7fa; padding: 20px;">
    <!-- 搜索表单 -->
    <el-form :model="queryParams" ref="queryRef" v-show="showSearch" :inline="true" style="margin-bottom: 20px;">
      <el-form-item label="搜索关键字" prop="searchQuery" label-width="120px">
        <el-input v-model="queryParams.searchQuery" placeholder="请输入用户名称、角色、手机号、邮箱或状态" clearable style="width: 320px;"
          @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery" style="margin-right: 10px;">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="20" class="mb8" style="margin-bottom: 20px;">
      <el-col :span="3">
        <el-button type="primary" plain icon="Plus" @click="openSelectUser" v-hasPermi="['system:role:add']"
          style="width: 100%;">添加用户</el-button>
        <selectUser ref="selectRef"></selectUser>
      </el-col>
      <el-col :span="3">
        <el-button type="danger" plain icon="CircleClose" :disabled="multiple" @click="cancelAuthUserAll"
          v-hasPermi="['system:role:remove']" style="width: 100%;">批量封禁</el-button>
      </el-col>
      <el-col :span="3">
        <el-button type="warning" plain icon="Close" @click="handleClose" style="width: 100%;">关闭</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange"
      @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="用户名称" prop="userName" :show-overflow-tooltip="true" align="center" sortable="custom"
        :filters="[{ text: '张三', value: 'zhangsan' }, { text: '李四', value: 'lisi' }, { text: '王五', value: 'wangwu' }]"
        :filter-method="filterHandler" />
      <el-table-column label="状态" prop="status" :show-overflow-tooltip="true" align="center" sortable="custom"
        :filters="[{ text: '正常', value: '正常' }, { text: '禁言', value: '禁言' }, { text: '封禁中', value: '封禁中' }]"
        :filter-method="filterHandler">
        <template #default="scope">
          <el-tag :type="scope.row.status === '正常' ? 'success' : (scope.row.status === '禁言' ? 'warning' : 'danger')">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" prop="email" :show-overflow-tooltip="true" align="center" sortable="custom"
        :filters="[{ text: 'zhangsan@example.com', value: 'zhangsan@example.com' }, { text: 'lisi@example.com', value: 'lisi@example.com' }, { text: 'wangwu@example.com', value: 'wangwu@example.com' }]"
        :filter-method="filterHandler" />
      <el-table-column label="手机" prop="phonenumber" :show-overflow-tooltip="true" align="center" sortable="custom"
        :filters="[{ text: '13800138000', value: '13800138000' }, { text: '13800138001', value: '13800138001' }, { text: '13800138002', value: '13800138002' }]"
        :filter-method="filterHandler" />
      <el-table-column label="角色" align="center" prop="role"
        :filters="[{ text: '普通用户', value: '普通用户' }, { text: '管理员', value: '管理员' }, { text: '超级管理员', value: '超级管理员' }]"
        :filter-method="filterHandler">
        <template #default="scope">
          <el-tag :type="scope.row.role === '普通用户' ? 'info' : (scope.row.role === '管理员' ? 'warning' : 'success')">
            {{ scope.row.role }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" sortable="custom"
        :filters="[{ text: '2024-01-01', value: '2024-01-01' }]" :filter-method="filterHandler">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <role-manager :user="scope.row" v-if="scope.row" v-hasPermi="['system:role:modify']"></role-manager>
          <el-button link type="primary" @click="cancelAuthUser(scope.row)" v-hasPermi="['system:role:remove']">
            执行封禁
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 选择用户弹窗 -->
    <select-user ref="selectRef" :roleId="queryParams.roleId" @ok="handleQuery" />
  </div>
</template>


<script setup name="AuthUser">
import { ref, reactive, getCurrentInstance } from 'vue';
import { useRoute } from 'vue-router';
import { ElMessageBox, ElMessage } from 'element-plus';
import selectUser from "./selectUser.vue";
import RoleManager from './RoleManager.vue';

const selectRef = ref(null); 
const route = useRoute();
const { proxy } = getCurrentInstance();
const queryRef = ref(null);
const userList = ref([]);
const loading = ref(false);
const showSearch = ref(true);
const multiple = ref(true);
const total = ref(0);
const userIds = ref([]);

const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  roleId: route.params.roleId,
  searchQuery: '',
});

/** 模拟获取用户列表 */
function fetchMockUserList() {
  return new Promise((resolve) => {
    //模拟用户数据
    const allUsers = [
      { userId: 1, roleId: 0, role: '普通用户', userName: 'zhangsan', status: '正常', email: 'zhangsan@example.com', phonenumber: '13800138000', createTime: '2024-01-01 10:00:00', permissions: [{ name: '查看数据', enabled: false }, { name: '编辑数据', enabled: false }, { name: '删除数据', enabled: false }] },
      { userId: 2, roleId: 1, role: '管理员', userName: 'lisi', status: '禁言', email: 'lisi@example.com', phonenumber: '13800138001', createTime: '2024-01-01 10:00:00', permissions: [{ name: '查看数据', enabled: false }, { name: '编辑数据', enabled: false }, { name: '删除数据', enabled: false }] },
      { userId: 3, roleId: 2, role: '超级管理员', userName: 'wangwu', status: '封禁中', email: 'wangwu@example.com', phonenumber: '13800138002', createTime: '2024-01-01 10:00:00', permissions: [{ name: '查看数据', enabled: false }, { name: '编辑数据', enabled: false }, { name: '删除数据', enabled: false }] },
    ];
    // 模拟延迟
    setTimeout(() => {
      resolve(allUsers);
    }, 500);
  });
}
/** 查询授权用户列表 */
async function getList() {
  loading.value = true;

  try {
    const users = await fetchMockUserList();
    const query = queryParams.searchQuery.toLowerCase();
    userList.value = users.filter(user => {
      return (
        // 聚合搜索条件
        !query ||
        user.userName.toLowerCase().includes(query) ||
        user.phonenumber.includes(query) ||
        user.email.toLowerCase().includes(query) ||
        user.status.includes(query) ||
        user.role.includes(query)
      );
    });

    total.value = userList.value.length; // 更新总数
  } catch (error) {
    console.error("获取用户列表失败:", error);
  } finally {
    loading.value = false;
  }
}

/** 处理排序事件 */
function handleSortChange({ prop, order }) {
  if (order) {
    userList.value.sort((a, b) => {
      const valA = a[prop];
      const valB = b[prop];
      return order === 'ascending' ? (valA > valB ? 1 : -1) : (valA < valB ? -1 : 1);
    });
  } else {
    getList(); // 恢复默认排序
  }
}

/** 返回按钮操作 */
function handleClose() {
  proxy.$tab.closeOpenPage({ path: "/system/role" }); // 返回角色管理页面
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.pageNum = 1; // 重置页码为1
  getList(); // 获取用户列表
}

/** 重置按钮操作 */
function resetQuery() {
  queryRef.value?.resetFields(); // 安全地重置表单
  handleQuery(); // 重新查询
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  userIds.value = selection.map(item => item.userId); // 更新用户ID数组
  multiple.value = !selection.length; // 更新批量操作按钮的可用状态
}

/** 打开授权用户表弹窗 */
function openSelectUser() {
  selectRef.value.show(); // 调用 selectUser 的 show 方法
}


/** 取消授权按钮操作 */  
function cancelAuthUser(row) {
  ElMessageBox.confirm(`确认要封禁该用户"${row.userName}"角色吗？`, '提示', {
  confirmButtonText: '确定',
  cancelButtonText: '取消',
  type: 'warning',
}).then(() => {
  getList(); // 重新获取用户列表
  ElMessage.success("封禁成功"); // 显示成功提示
});
}

/** 批量取消授权按钮操作 */
function cancelAuthUserAll() {
  ElMessageBox.confirm("是否封禁选中用户数据项?", '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(() => {
    getList(); // 重新获取用户列表
    ElMessage.success("批量封禁成功"); // 显示成功提示
  });
}

/** 时间格式化函数 */
function parseTime(time) {
  if (!time) return '';
  return new Date(time).toLocaleString(); // 可以根据需要调整格式
}

// 筛选处理函数
function filterHandler(value, row, column) {
  const property = column['property'];
  return row[property] === value;
}

// 初始化获取用户列表
getList();
</script>
