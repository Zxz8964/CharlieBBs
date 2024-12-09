<template>
   <div class="form-container">
     <el-form ref="userRef" :model="form" :rules="rules" label-width="80px">
       <el-form-item label="用户昵称" prop="nickName">
         <el-input v-model="form.nickName" maxlength="15" show-word-limit />
       </el-form-item>
       <el-form-item label="手机号码" prop="phonenumber">
         <el-input v-model="form.phonenumber" maxlength="11" show-word-limit />
       </el-form-item>
       <el-form-item label="邮箱" prop="email">
         <el-input v-model="form.email" maxlength="50" show-word-limit />
       </el-form-item>
       <div class="gender-save-container">
        <el-form-item label="性别" class="male">
         <el-radio-group v-model="form.sex">
           <el-radio value="0">男</el-radio>
           <el-radio value="1">女</el-radio>
           <el-radio value="2">不愿透露</el-radio>
         </el-radio-group>
       </el-form-item>
       <el-form-item class="saveAndclose">
         <el-button type="primary" @click="submit" >保存</el-button>
         <el-button type="danger" @click="close" >关闭</el-button>
       </el-form-item>
       </div>
       
     </el-form>
   </div>
 </template>
 
 <script setup>
 // import { updateUserProfile } from "@/api/system/user"; // 暂时注释掉
 import { ref, reactive, getCurrentInstance, watch } from 'vue';
 const props = defineProps({
  user: {
    type: Object
  }
 });
 
 const { proxy } = getCurrentInstance();
 
 const form = ref({});
 const rules = ref({
  nickName: [{ required: true, message: "用户昵称不能为空", trigger: "blur" }],
  email: [{ required: true, message: "邮箱地址不能为空", trigger: "blur" }, { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] }],
  phonenumber: [{ required: true, message: "手机号码不能为空", trigger: "blur" }, { pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/, message: "请输入正确的手机号码", trigger: "blur" }],
 });
 
 /** 提交按钮 */
 function submit() {
  proxy.$refs.userRef.validate(valid => {
    if (valid) {
      // 暂时注释掉 updateUserProfile 调用部分
      /*
      updateUserProfile(form.value).then(response => {
        proxy.$modal.msgSuccess("修改成功");
        props.user.phonenumber = form.value.phonenumber;
        props.user.email = form.value.email;
      });
      */
      proxy.$modal.msgSuccess("修改功能暂时不可用"); // 添加提示信息
    }
  });
 };
 
 /** 关闭按钮 */
 function close() {
  proxy.$tab.closePage();
 };
 
 // 回显当前登录用户信息
 watch(() => props.user, user => {
  if (user) {
    form.value = { nickName: user.nickName, phonenumber: user.phonenumber, email: user.email, sex: user.sex };
  }
 },{ immediate: true });
 </script>
 

 