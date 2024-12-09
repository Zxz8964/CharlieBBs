<template>
  <el-table :data="filterTableData" style="width: 100%" @sort-change="handleSortChange">
    <el-table-column type="selection" :selectable="selectable" width="55" />
    <el-table-column label="更新时间" prop="updatedate" width="150" sortable />
    <el-table-column label="权限名" prop="name" width="200" sortable :filters="nameFilters" :filter-method="filterHandler" />
    <el-table-column label="所属角色" prop="rolebyboard" width="150" sortable :filters="roleFilters" :filter-method="filterHandler" />
    <el-table-column label="所属板块" prop="board" sortable :filters="boardFilters" :filter-method="filterHandler" />
    <el-table-column type="expand">
      <template #default="props">
        <div class="expand-content">
          <div class="expand-header">
            <p><strong>描述：</strong>{{ props.row.descripition }}</p>
          </div>
          <div class="expand-body">
            <p><strong>拥有权限的用户：</strong></p>
            <div class="user-tags">
              <el-tag
                v-for="user in props.row.usernames"
                :key="user"
                :type="getUserTagColor(user)"  
                class="user-tag"
              >
                {{ user }}
              </el-tag>
            </div>
          </div>
        </div>
      </template>
    </el-table-column>
    <el-table-column align="right">
      <template #header>
        <el-input v-model="search" size="small" placeholder="Type to search" />
      </template>
      <template #default="scope">
        <div class="action-buttons">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">
            修改
          </el-button>
          <el-switch 
            size="large"
            v-model="scope.row.enabled" 
            class="ml" 
            inline-prompt
            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" 
            active-text="启用"
            inactive-text="禁用"
            @change="handleSwitchChange(scope.row.permId, scope.row.enabled)" />
        </div>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import { computed, ref } from 'vue'

// 定义接口 User
interface User {
  id: number
  permId: number
  updatedate: string
  name: string
  rolebyboard: string
  board: string
  descripition: string
  usernames: string[]
  enabled: boolean
}

const selectable = (row: User) => ![0].includes(row.id)
const search = ref('')

// 安全的聚合搜索
const filterTableData = computed(() =>
  tableData.filter(
    (data) => {
      const searchLower = search.value.toLowerCase()
      return (
        !search.value || 
        (data.name && data.name.toLowerCase().includes(searchLower)) || 
        (data.board && data.board.toLowerCase().includes(searchLower)) || 
        (data.descripition && data.descripition.toLowerCase().includes(searchLower)) || 
        (data.rolebyboard && data.rolebyboard.toLowerCase().includes(searchLower))
      )
    }
  )
)

// 获取用户标签颜色
const getUserTagColor = (user: string) => {
  const colors = ['success', 'info', 'warning', 'danger']
  return colors[user.length % colors.length]
}

// 编辑操作
const handleEdit = (index: number, row: User) => {
  console.log('编辑行数据:', index, row)
}

// 处理开关状态改变的操作
const handleSwitchChange = (permId: number, enabled: boolean) => {
  console.log(`权限ID ${permId} 的开关状态改变为: ${enabled ? '启用' : '禁用'}`)
  // 在这里可以添加实际的权限状态修改逻辑，例如发送请求到后台
}

// 初始化表格数据
const tableData: User[] = [
  {
    id: 1,
    permId: 0,
    updatedate: '2016-05-03',
    name: 'POST_CREATE',
    rolebyboard: '超级管理员',
    board: '管理',
    descripition: '超级管理员可以创建和管理审计报告',
    usernames: ['Lisa', 'Victor'],
    enabled: true,
  },  {
    id:2,
    permId: 1,
    updatedate: '2016-05-02',
    name: 'POST_REPLY',
    rolebyboard: '超级管理员',
    board: '管理',
    descripition: '超级管理员可以修改系统设置',
    usernames:['Lisa','Victor','Bill','kite'],
    enabled: false, // 开关初始状态
  },
  {
    id:3,
    permId: 2,
    updatedate: '2016-05-04',
    name: 'POST_EDIT_OWN',
    rolebyboard: '红牌楼管理员',
    board: '红牌楼',
    descripition: '管理员可以调整系统配置',
    usernames:['Lisa','Victor','Bill','kite'],
    enabled: true, // 开关初始状态
  },
  {
    id:4,
    permId: 3,
    updatedate: '2016-05-01',
    name: 'POST_DELETE_OWN',
    rolebyboard: '普通用户',
    board: 'null',
    descripition: '管理员可以设置系统公告和通知',
    usernames:['Lisa','Victor','Bill','kite'],
    enabled: false, // 开关初始状态
  },
]

// 排序处理
const handleSortChange = ({ column, prop, order }) => {
  console.log(`排序列: ${prop}, 排序顺序: ${order}`)
  // 在这里可以添加实际的排序逻辑
}

// 筛选处理
const nameFilters = [
  { text: 'POST_CREATE', value: 'POST_CREATE' },
  { text: 'POST_EDIT', value: 'POST_EDIT' },
]

const roleFilters = [
  { text: '超级管理员', value: '超级管理员' },
  { text: '普通用户', value: '普通用户' },
]

const boardFilters = [
  { text: '管理', value: '管理' },
  { text: '用户', value: '用户' },
]

const filterHandler = (value, row, column) => {
  const property = column['property']
  return row[property] === value
}
</script>
