<template>
    <el-container>
      <!-- 页面标题 -->
      <el-header>
        <h2>高级主题设置</h2>
      </el-header>
  
      <el-main>
        <el-tabs v-model="activeTab">
          <!-- 基础设置 Tab -->
          <el-tab-pane label="基础设置" name="basic">
            <el-form :model="basicSettings" label-width="120px">
              <!-- 颜色设置 -->
              <el-form-item label="主色调">
                <el-color-picker v-model="basicSettings.primaryColor"></el-color-picker>
              </el-form-item>
              <el-form-item label="副色调">
                <el-color-picker v-model="basicSettings.secondaryColor"></el-color-picker>
              </el-form-item>
              <el-form-item label="按钮颜色">
                <el-color-picker v-model="basicSettings.buttonColor"></el-color-picker>
              </el-form-item>
              <el-form-item label="链接颜色">
                <el-color-picker v-model="basicSettings.linkColor"></el-color-picker>
              </el-form-item>
              <el-form-item label="字体">
                <el-input v-model="basicSettings.fontFamily" placeholder="例如 Arial, sans-serif"></el-input>
              </el-form-item>
              <el-form-item label="字体大小">
                <el-input-number v-model="basicSettings.fontSize" :min="12" :max="24"></el-input-number>
              </el-form-item>
              <el-form-item label="边距 (px)">
                <el-input-number v-model="basicSettings.margin" :min="0" :max="50"></el-input-number>
              </el-form-item>
              <el-form-item label="填充间距 (px)">
                <el-input-number v-model="basicSettings.padding" :min="0" :max="50"></el-input-number>
              </el-form-item>
            </el-form>
          </el-tab-pane>
  
          <!-- 自定义CSS Tab -->
          <el-tab-pane label="自定义CSS" name="css">
            <el-form label-width="120px">
              <el-form-item label="自定义CSS">
                <el-input
                  type="textarea"
                  v-model="customCss"
                  rows="8"
                  placeholder="在这里输入自定义CSS..."
                ></el-input>
              </el-form-item>
            </el-form>
          </el-tab-pane>
  
          <!-- 图片管理 Tab -->
          <el-tab-pane label="图片管理" name="images">
            <el-upload
              class="upload-demo"
              drag
              action="/upload"
              multiple
              :on-success="handleImageUpload"
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">拖拽文件上传，或点击上传</div>
              <div class="el-upload__tip" slot="tip">支持jpg, png, gif等图片格式</div>
            </el-upload>
          </el-tab-pane>
  
          <!-- 预览 Tab -->
          <el-tab-pane label="实时预览" name="preview">
            <div class="theme-preview" :style="previewStyles">
              <p>实时预览区域，将根据您的设置变化。</p>
            </div>
          </el-tab-pane>
        </el-tabs>
  
        <!-- 操作按钮 -->
        <el-row style="margin-top: 20px; text-align: right;">
          <el-button @click="restoreDefault">恢复默认</el-button>
          <el-button type="primary" @click="saveTheme">保存设置</el-button>
        </el-row>
      </el-main>
    </el-container>
  </template>
  
  <script>
  export default {
    data() {
      return {
        activeTab: 'basic', // 当前选中的Tab
        basicSettings: {
          primaryColor: '#409EFF',
          secondaryColor: '#67C23A',
          buttonColor: '#E6A23C',
          linkColor: '#1E9FFF',
          fontFamily: 'Arial, sans-serif',
          fontSize: 16,
          margin: 10,
          padding: 10,
        },
        customCss: '', // 自定义CSS内容
      };
    },
    computed: {
      previewStyles() {
        return {
          color: this.basicSettings.primaryColor,
          backgroundColor: this.basicSettings.secondaryColor,
          fontSize: `${this.basicSettings.fontSize}px`,
          margin: `${this.basicSettings.margin}px`,
          padding: `${this.basicSettings.padding}px`,
          fontFamily: this.basicSettings.fontFamily,
        };
      },
    },
    methods: {
      handleImageUpload(response) {
        this.$message.success('图片上传成功');
        // 在这里处理图片上传逻辑
      },
      saveTheme() {
        // 保存主题设置，调用API或本地存储
        this.$message.success('主题设置已保存');
      },
      restoreDefault() {
        this.basicSettings = {
          primaryColor: '#409EFF',
          secondaryColor: '#67C23A',
          buttonColor: '#E6A23C',
          linkColor: '#1E9FFF',
          fontFamily: 'Arial, sans-serif',
          fontSize: 16,
          margin: 10,
          padding: 10,
        };
        this.customCss = '';
        this.$message.info('已恢复默认设置');
      },
    },
  };
  </script>
  
  <style scoped>
  .theme-preview {
    border: 1px solid #dcdcdc;
    padding: 20px;
    border-radius: 5px;
    text-align: center;
    background-color: #fff;
  }
  </style>
  