<template>
   <div class="app-container">
      <!-- 基本信息部分 -->
      <h4 class="form-header h4">基本信息</h4>
      <el-form :model="form" label-width="80px">
         <el-row>
            <el-col :span="6" :offset="1">
               <el-form-item label="用户昵称" prop="nickName">
                  <el-input v-model="form.nickName" disabled />
               </el-form-item>
            </el-col>
            <el-col :span="6">
               <el-form-item label="登录账号" prop="userName">
                  <el-input v-model="form.userName" disabled />
               </el-form-item>
            </el-col>
            <el-col :span="5">
               <el-form-item label="当前角色" prop="currentRole">
                  <el-input v-model="form.currentRole" disabled />
               </el-form-item>
            </el-col>
            <el-col :span="5">
               <el-form-item label="用户权限" prop="userPermissions">
                  <el-input v-model="form.userPermissions" disabled />
               </el-form-item>
            </el-col>
         </el-row>
      </el-form>

      <h4 class="form-header h4">角色信息</h4>

      <!-- 搜索和重置筛选按钮 -->
      <el-form :inline="true" label-width="80px">
         <el-form-item label="聚合搜索">
            <el-input v-model="filters.roleName" placeholder="请输入角色名" />
         </el-form-item>
         <el-form-item>
            <el-button type="primary" @click="handleSearch">搜索</el-button>
         </el-form-item>
         <el-button @click="clearAllFilters">重置筛选</el-button> <!-- 一键重置按钮 -->
      </el-form>

      <!-- 角色表格 -->
      <el-table ref="tableRef" :data="filteredRoles" style="width: 100%; overflow-y: auto;" highlight-current-row
         @expand-change="handleExpandChange">
         <el-table-column type="selection" width="55" />
         <el-table-column type="index" label="序号" width="50" align="center" />
         <el-table-column prop="roleId" label="角色编号" align="center" />
         <el-table-column prop="roleName" label="角色名称" align="center" :filters="roleNameFilters"
            :filter-method="filterRoleName" sortable>
            <template #default="scope">
               <el-tooltip class="item" effect="dark" :content="scope.row.roleName" placement="top-start">
                  <span class="hover-cell">{{ scope.row.roleName }}</span>
               </el-tooltip>
            </template>
         </el-table-column>
         <el-table-column label="权限字符" align="center" prop="roleKey" />
         <el-table-column label="状态" align="center" prop="status" :filters="statusFilters" :filter-method="filterStatus"
            sortable>
            <template #default="scope">
               <el-tag :type="scope.row.status === 'enabled' ? 'success' : 'danger'">
                  {{ scope.row.status === 'enabled' ? '启用' : '禁用' }}
               </el-tag>
            </template>
         </el-table-column>
         <el-table-column label="创建时间" align="center" prop="createTime" sortable>
            <template #default="scope">
               <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
         </el-table-column>

         <el-table-column label="操作" align="center" fixed="right" width="150">
            <template #default="scope">
               <el-button size="small" @click="handleEdit(scope.row)">
                  编辑
               </el-button>
               <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">
                  删除
               </el-button>
            </template>
         </el-table-column>

         <el-table-column type="expand">
            <template #default="scope">
               <el-row>
                  <el-col :span="24">
                     <p><strong>描述:</strong> {{ scope.row.description }}</p>
                     <el-row>
                        <el-col :span="24">
                           <div class="permission-tags">
                              <div class="flex gap-2">
                                 <el-tag v-for="(permission, index) in scope.row.permissions" :key="permission.name"
                                    closable :type="permission.type" @close="removePermission(scope.row, index)">
                                    {{ permission.name }}
                                 </el-tag>
                                 <el-input v-if="inputVisible" ref="InputRef" v-model="inputValue" class="w-20"
                                    size="small" @keyup.enter="handleInputConfirm(scope.row)"
                                    @blur="handleInputConfirm(scope.row)" />
                                 <el-button v-else class="button-new-tag" size="small" @click="showInput">
                                    + New Permission
                                 </el-button>
                              </div>
                           </div>
                        </el-col>
                     </el-row>
                  </el-col>
               </el-row>
            </template>
         </el-table-column>
      </el-table>

      <el-pagination v-show="filteredRoles.length > 0" layout="prev, pager, next" :total="total" :page-size="pageSize"
         v-model:current-page="currentPage" @current-change="handlePageChange" />

      <!-- 提交与返回按钮 -->
      <el-form label-width="100px">
         <div style="text-align: center; margin-top: 30px;">
            <el-button type="primary" @click="submitForm()">提交</el-button>
            <el-button @click="close()">返回</el-button>
         </div>
      </el-form>
   </div>
</template>

<script setup>
import { ref, computed } from 'vue';
const tableRef = ref(null) // 定义 tableRef
const form = ref({
   nickName: '星晴萍',
   userName: 'xingjingpinghaochilanzuo',
   currentRole: '管理员',
   userPermissions: '查看, 编辑',
   userId: 1
});

const roles = ref([
   {
      roleId: 1, roleName: '管理员', roleKey: 'admin', createTime: '2024-10-01 10:00:00', status: 'enabled', description: '这是管理员角色。',
      permissions: [
         { name: '查看用户', type: 'primary' },
         { name: '编辑用户', type: 'success' },
         { name: '删除用户', type: 'danger' },
         { name: '添加用户', type: 'info' },
         { name: '查看角色', type: 'warning' },
         { name: '编辑角色', type: 'success' },
         { name: '管理权限', type: 'primary' }
      ],
   },
   {
      roleId: 2, roleName: '用户', roleKey: 'user', createTime: '2024-10-02 11:00:00', status: 'disabled', description: '这是用户角色。',
      permissions: [
         { name: '查看个人信息', type: 'info' },
         { name: '编辑个人信息', type: 'success' },
         { name: '查看内容', type: 'primary' },
         { name: '评论内容', type: 'warning' }
      ],
   },
   // 更多角色...
]);

const filters = ref({
   roleName: '',
   status: ''
});

const roleNameFilters = computed(() => [
   { text: '管理员', value: '管理员' },
   { text: '用户', value: '用户' }
]);

const statusFilters = [
   { text: '启用', value: 'enabled' },
   { text: '禁用', value: 'disabled' }
];

const filterRoleName = (value, row) => {
   return row.roleName.toLowerCase().includes(value.toLowerCase());
};

const filterStatus = (value, row) => {
   return row.status === value;
};

const pageSize = ref(10);
const currentPage = ref(1);
const total = ref(roles.value.length);

const filteredRoles = computed(() => {
   let filtered = roles.value.filter(role => {
      return (
         (filters.value.roleName ? role.roleName.toLowerCase().includes(filters.value.roleName.toLowerCase()) : true) &&
         (filters.value.status ? role.status === filters.value.status : true)
      );
   });

   total.value = filtered.length;

   const start = (currentPage.value - 1) * pageSize.value;
   return filtered.slice(start, start + pageSize.value);
});

const handleSearch = () => {
   currentPage.value = 1; // 搜索后重置到第一页
};

// 一键重置所有筛选条件
const clearAllFilters = () => {
   filters.value.roleName = ''; // 清空角色名筛选
   filters.value.status = '';   // 清空状态筛选
   currentPage.value = 1;       // 重置到第一页
   tableRef.value.clearFilter()
};

const submitForm = () => {
   console.log('Form submitted');
};

const close = () => {
   console.log('Form closed');
};

const handlePageChange = (page) => {
   currentPage.value = page;
};

function parseTime(dateString) {
   return new Date(dateString).toLocaleString('zh-CN');
}
</script>
