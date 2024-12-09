<template>
   <div class="app-container">
      <el-row :gutter="20">
         <!-- 个人信息和基本资料交换位置 -->
         <el-col :span="18" :xs="24">
            <div class="flip-container" :class="{ flipped: showEditForm }">
               <!-- 个人信息正面 -->
               <div class="flipper front">
            <el-card class="box-card">
              <template v-slot:header>
                <div class="header-title">
                  <span>个人信息</span>
                </div>
              </template>
              <div class="user-info">
                <el-descriptions title="个人信息" direction="vertical" border>
                  <el-descriptions-item
                    :rowspan="2"
                    :width="140"
                    label="头像"
                    align="center"
                  >
                    <el-image
                      style="width: 100px; height: 100px"
                      src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
                    />
                  </el-descriptions-item>
                  <el-descriptions-item label="用户名称">{{ state.user.userName }}</el-descriptions-item>
                  <el-descriptions-item label="手机号码">{{ state.user.phonenumber }}</el-descriptions-item>
                  <el-descriptions-item label="邮箱">{{ state.user.email }}</el-descriptions-item>
                  <el-descriptions-item label="所属板块" v-if="state.user.dept">
                    {{ state.user.dept.deptName }} / {{ state.postGroup }}
                  </el-descriptions-item>
                  <el-descriptions-item label="所属角色">{{ state.roleGroup }}</el-descriptions-item>
                  <el-descriptions-item label="创建日期">{{ state.user.createTime }}</el-descriptions-item>
                </el-descriptions>
                <!-- 修改个人信息按钮 -->
                <div style="margin-top: 1%;">
                  <el-button type="primary" @click="toggleEdit">修改个人信息</el-button>
                </div>
              </div>
            </el-card>
          </div>

               <!-- 基本资料背面 -->
               <div class="flipper back">
                  <el-card class="box-card">
                     <template v-slot:header>
                       <div class="header-title">
                         <span>基本资料</span>
                       </div>
                     </template>
                     <el-tabs v-model="activeTab">
                        <el-tab-pane label="基本资料" name="userinfo">
                           <userInfo :user="state.user" />
                        </el-tab-pane>
                        <el-tab-pane label="修改密码" name="resetPwd">
                           <resetPwd />
                        </el-tab-pane>
                        <el-tab-pane label="修改头像" name="userAvatar">
                           <userAvatar />
                        </el-tab-pane>
                     </el-tabs>

                        <el-button type="primary" @click="toggleEdit">返回个人信息</el-button>

                  </el-card>
               </div>
            </div>
         </el-col>

         <!-- 保持宽度不变 -->
         <el-col :span="6" :xs="24">
            <el-card class="box-card">
               <template v-slot:header>
                 <div class="header-title">
                   <span>操作历史</span>
                 </div>
               </template>
               <div class="other-info">
                  <!-- 其他内容占位符，用户自行添加 -->
               </div>
            </el-card>
         </el-col>
      </el-row>
   </div>
</template>





<script setup>
import { ref, reactive } from 'vue';
import userAvatar from "./userAvatar.vue";
import userInfo from "./userInfo.vue";
import resetPwd from "./resetPwd.vue";

const activeTab = ref("userinfo");
const showEditForm = ref(false);  // 控制翻转效果的状态
const state = reactive({
  user: {
    userName: "用户名",
    phonenumber: "1234567890",
    email: "user@example.com",
    dept: { deptName: "技术板" },
    createTime: "2024-01-01"
  },
  roleGroup: "管理员",
  postGroup: "版主"
});

// 切换个人信息与基本资料
const toggleEdit = () => {
  showEditForm.value = !showEditForm.value;
};
</script>

<style scoped>
.male,
.saveAndclose{
  display: inline-block; 
}
</style>
