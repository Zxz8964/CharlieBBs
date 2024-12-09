<template>
  <!-- 授权用户弹窗 -->
  <el-dialog title="选择用户" v-model="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryRef" :inline="true">
      <!-- 聚合搜索框，支持用户名称/手机/昵称/邮箱 -->
      <el-form-item label="搜索" prop="search">
        <el-input
          v-model="queryParams.search"
          placeholder="请输入用户名称、手机号码、昵称或邮箱"
          clearable
          style="width: 300px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button> <!-- 搜索按钮 -->
        <el-button icon="Refresh" @click="resetQuery">重置</el-button> <!-- 重置按钮 -->
      </el-form-item>
    </el-form>
    <el-row>
      <!-- 用户列表表格 -->
      <el-table @row-click="clickRow" ref="refTable" :data="filteredUserList" @selection-change="handleSelectionChange" height="260px">
        <el-table-column type="selection" width="55"></el-table-column> <!-- 多选框列 -->
        <el-table-column label="用户名称" prop="userName" :show-overflow-tooltip="true" />
        <el-table-column label="用户昵称" prop="nickName" :show-overflow-tooltip="true" />
        <el-table-column label="邮箱" prop="email" :show-overflow-tooltip="true" />
        <el-table-column label="手机" prop="phonenumber" :show-overflow-tooltip="true" />
        <el-table-column label="状态" prop="status" align="center">
          <template #default="scope">
            <span>{{ scope.row.status }}</span> <!-- 状态显示 -->
          </template>
          <template #header>
            <el-select v-model="selectedStatus" placeholder="选择状态" @change="handleQuery" style="width: 100%;">
              <el-option v-for="status in statusOptions" :key="status.value" :label="status.label" :value="status.value" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template #default="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span> <!-- 格式化时间 -->
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList" 
      />
    </el-row>
    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="handleSelectUser">确 定</el-button> <!-- 确定按钮 -->
        <el-button @click="visible = false">取 消</el-button> <!-- 取消按钮 -->
      </div>
    </template>
  </el-dialog>
</template>

<script setup name="SelectUser">
import { ref, reactive, computed, getCurrentInstance } from 'vue'; // 引入 getCurrentInstance
import { ElMessage } from 'element-plus'; // 引入 ElMessage 提示组件

const props = defineProps({
  roleId: {
    type: [Number, String] // 接收的角色ID
  }
});

// 模拟的数据
const mockUserList = [
  { userId: 4, userName: '赵六', nickName: '六哥', email: 'zhaoliu@example.com', phonenumber: '12345678904', status: '正常', createTime: '2023-01-04 13:00:00' },
  { userId: 5, userName: '孙七', nickName: '七哥', email: 'sunqi@example.com', phonenumber: '12345678905', status: '正常', createTime: '2023-01-05 14:00:00' },
  { userId: 6, userName: '周八', nickName: '八哥', email: 'zhouba@example.com', phonenumber: '12345678906', status: '禁用', createTime: '2023-01-06 15:00:00' }
]; // 模拟的用户列表

const userList = ref([]); // 用户列表
const visible = ref(false); // 控制弹窗显示
const total = ref(0); // 用户总数
const userIds = ref([]); // 选中的用户ID列表

const statusOptions = ref([
  { label: '正常', value: '正常' },
  { label: '禁用', value: '禁用' }
]);

const selectedStatus = ref(null); // 选中的状态

/** 控制前端逻辑 */
/** 时间格式化函数 */
function parseTime(time) {
  if (!time) return '';
  const date = new Date(time);
  return date.toLocaleString(); // 根据需要格式化
}

/** 控制前端逻辑 */
/** 显示弹窗并初始化数据 */
function show() {
  queryParams.roleId = props.roleId; // 设置角色ID
  getList(); // 获取用户列表
  visible.value = true; // 显示弹窗
}

/** 控制前端逻辑 */
/** 选择行，切换行选择状态 */
function clickRow(row) {
  const table = refTable.value;
  table.toggleRowSelection(row);
}

/** 控制前端逻辑 */
/** 处理多选框选中数据，更新选中用户ID列表 */
function handleSelectionChange(selection) {
  userIds.value = selection.map(item => item.userId); // 更新用户ID数组
}

/** 连接后端 */
/** 查询表数据，调用后端API */
function getList() {
  // 模拟后端API调用
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        rows: mockUserList,
        total: mockUserList.length
      });
    }, 1000);
  }).then(res => {
    userList.value = res.rows; // 设置用户列表数据
    total.value = res.total; // 设置用户总数
  });
}

/** 连接后端 */
/** 选择授权用户操作，调用后端API进行用户授权 */
function handleSelectUser() {
  const roleId = queryParams.roleId; // 获取角色ID
  const uIds = userIds.value.join(","); // 获取选中的用户ID字符串
  if (uIds === "") {
    ElMessage.error("请选择要分配的用户"); // 如果没有选中用户，显示错误提示
    return;
  }
  // 模拟后端API调用
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        msg: "授权成功"
      });
    }, 1000);
  }).then(res => {
    ElMessage.success(res.msg); // 显示成功提示
    visible.value = false; // 关闭弹窗
    emit("ok"); // 触发确认事件
  });
}

/** 控制前端逻辑 */
/** 暴露show函数以供外部调用 */
defineExpose({
  show,
});
const queryParams = reactive({
  pageNum: 1, // 当前页码
  pageSize: 10, // 每页显示条目
  roleId: undefined, // 当前角色ID
  search: undefined // 聚合搜索字段
});

/** 计算过滤后的用户列表 */
const filteredUserList = computed(() => {
  return userList.value.filter(user => {
    const matchesSearch = queryParams.search 
      ? user.userName.includes(queryParams.search) || user.phonenumber.includes(queryParams.search) || user.nickName.includes(queryParams.search) || user.email.includes(queryParams.search)
      : true;
    const matchesStatus = selectedStatus.value ? user.status === selectedStatus.value : true;
    return matchesSearch && matchesStatus;
  });
});

/** 执行搜索操作 */
function handleQuery() {
  getList(); // 根据聚合搜索字段进行查询
}

/** 重置搜索条件 */
function resetQuery() {
  queryParams.search = ''; // 清空搜索字段
  selectedStatus.value = null; // 清空状态选择
  getList(); // 重新加载列表
}

</script>

