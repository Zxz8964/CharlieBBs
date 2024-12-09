<template>
  <div>
    <el-button link type="primary" @click="openDialog">
      修改授权
    </el-button>
    <el-dialog title="用户角色设置" v-model="modifyAuthUser" width="40%" @close="handleClose"
      :style="{ borderRadius: '10px' }" append-to-body>
      <div>
        <!-- 用户名和角色 -->
        <div class="user-info">
          <span>{{ formData.username }} / {{ roles[formData.role].name }}</span>
        </div>

        <!-- 角色选择（色块代替滑块） -->
        <el-form-item label="选择角色">
          <div class="color-blocks">
            <div v-for="(role, index) in roles" :key="index" class="color-block"
              :style="{ backgroundColor: getColor(index), opacity: getOpacity(index) }"
              :class="{ active: formData.role === index, first: index === 0, last: index === roles.length - 1 }"
              @click="selectRole(index)"></div>
          </div>
          <div class="role-label">{{ roles[formData.role].name }}</div>
        </el-form-item>

        <!-- 权限设置（Switch 开关） -->
        <el-form-item label="权限">
          <div class="permission-switches">
            <div v-for="(perm, index) in currentPermissions" :key="index" class="permission-item">
              <span>{{ perm.name }}</span>
              <el-switch v-model="perm.enabled" active-color="#409EFF" inactive-color="#C0CCDA"></el-switch>
            </div>
          </div>
        </el-form-item>
      </div>

      <!-- 底部操作按钮 -->
      <template #footer>
        <el-button @click="handleCancel">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, defineProps } from 'vue';
const props = defineProps(['user']);
const user = props.user;
const modifyAuthUser = ref(false);
const savedPermissions = ref({});
// 模拟角色数据
const roles = [
  {
    name: '普通用户',
    permissions: [
      { name: '查看数据', enabled: true },
      { name: '编辑数据', enabled: false },
      { name: '删除数据', enabled: false },
    ],
  },
  {
    name: '管理员',
    permissions: [
      { name: '查看数据', enabled: true },
      { name: '编辑数据', enabled: true },
      { name: '删除数据', enabled: true },
    ],
  },
  {
    name: '超级管理员',
    permissions: [
      { name: '查看数据', enabled: true },
      { name: '编辑数据', enabled: true },
      { name: '删除数据', enabled: true },
    ],
  },
  {
    name: '特殊管理员',
    permissions: [
      { name: '查看数据', enabled: true },
      { name: '编辑数据', enabled: true },
      { name: '审核数据', enabled: true },
    ],
  },
];

// 表单数据
const formData = reactive({
  username: props.user.userName || '',
  role: props.user.roleId,
});

// 当前权限
const currentPermissions = reactive([...user.permissions.map(perm => ({ ...perm }))]);

// 更新权限，当角色切换时触发
const updatePermissions = () => {
  if (roles[formData.role]) {
    if (savedPermissions.value[formData.role]) {
      currentPermissions.splice(0, currentPermissions.length, ...savedPermissions.value[formData.role]);
    } else {
      const selectedRolePermissions = roles[formData.role].permissions.map((perm) => {
        const userPermission = user.permissions.find(p => p.name === perm.name);
        return { ...perm, enabled: userPermission ? userPermission.enabled : perm.enabled };
      });
      currentPermissions.splice(0, currentPermissions.length, ...selectedRolePermissions);
    }
  } else {
    console.error("角色数据未定义");
  }
};

// 选择角色
const selectRole = (index) => {
  // 保存当前角色的权限状态
  savedPermissions.value[formData.role] = currentPermissions.map(perm => ({ ...perm }));

  // 切换角色
  formData.role = index;

  // 恢复新角色的权限状态
  if (savedPermissions.value[formData.role]) {
    currentPermissions.splice(0, currentPermissions.length, ...savedPermissions.value[formData.role]);
  } else {
    updatePermissions(); // 如果没有保存的状态，则初始化权限
  }
};

// 根据角色索引获取颜色
const getColor = (index) => {
  const colors = ['#87CEEB', '#FFA500', '#FF4500', '#8A2BE2'];
  return colors[index % colors.length];
};

// 根据角色索引获取透明度
const getOpacity = (index) => {
  return index <= formData.role ? 1 : 0.5; // 当前选中的角色完全不透明，其他角色透明度降低
};

// 保存处理
const handleSave = () => {
  console.log('保存的数据:', formData, currentPermissions);
  modifyAuthUser.value = false;
  savedPermissions.value = {}; // 清除保存的权限状态
};

// 取消处理
const handleCancel = () => {
  modifyAuthUser.value = false;
  resetFormData(); // 重置表单数据
  savedPermissions.value = {}; // 清除保存的权限状态
};

// 打开弹窗
const openDialog = () => {
  modifyAuthUser.value = true;
  formData.role = user.roleId; // 初始化角色
  formData.username = user.userName; // 初始化用户名
  updatePermissions(); // 打开时更新权限
};

// 重置表单数据
const resetFormData = () => {
  formData.role = user.roleId; // 重置为初始角色
  formData.username = user.userName; // 重置用户名
  currentPermissions.splice(0, currentPermissions.length, ...user.permissions.map(perm => ({ ...perm }))); // 重置权限
};
</script>
