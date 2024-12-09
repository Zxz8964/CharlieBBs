<template>
  <el-container>
    <!-- 顶部导航栏 -->
    <el-header>
      <el-row>
        <el-col :span="12">
          <h2>论坛后台管理系统</h2>
        </el-col>
        <el-col :span="12" style="text-align: right;">
          <el-dropdown>
            <el-button type="primary">
              管理员 <i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>
      </el-row>
    </el-header>

    <!-- 侧边栏和主要内容 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="200px">
        <el-menu default-active="1" class="el-menu-vertical">
          <el-menu-item index="1">
            <i class="el-icon-s-home"></i>
            <span slot="title">仪表盘</span>
          </el-menu-item>
          <el-menu-item index="2">
            <i class="el-icon-user-solid"></i>
            <span slot="title">用户管理</span>
          </el-menu-item>
          <el-menu-item index="3">
            <i class="el-icon-document"></i>
            <span slot="title">帖子管理</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-message"></i>
            <span slot="title">反馈与通知</span>
          </el-menu-item>
        </el-menu>

        <!-- 快捷管理入口 -->
        <el-row style="margin-top: 20px; padding: 10px;">
          <el-col :span="24">
            <el-button type="primary" icon="el-icon-user-solid" @click="navigateTo('userManagement')" block>
              管理用户
            </el-button>
          </el-col>
          <el-col :span="24" style="margin-top: 10px;">
            <el-button type="primary" icon="el-icon-document" @click="navigateTo('postManagement')" block>
              管理帖子
            </el-button>
          </el-col>
          <el-col :span="24" style="margin-top: 10px;">
            <el-button type="primary" icon="el-icon-message" @click="navigateTo('feedbackManagement')" block>
              处理反馈
            </el-button>
          </el-col>
          <el-col :span="24" style="margin-top: 10px;">
            <el-button type="primary" icon="el-icon-setting" @click="navigateTo('systemSettings')" block>
              系统设置
            </el-button>
          </el-col>
        </el-row>
      </el-aside>

      <!-- 主要内容 -->
      <el-main>
        <!-- 统计信息 -->
        <el-row :gutter="20" style="margin-bottom: 20px;">
          <el-col :span="6">
            <el-card>
              <h3>总用户数</h3>
              <p>{{ totalUsers }}</p>
              <el-progress :percentage="userGrowth" status="success"></el-progress>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <h3>帖子总数</h3>
              <p>{{ totalPosts }}</p>
              <el-progress :percentage="postGrowth" status="warning"></el-progress>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <h3>活跃用户</h3>
              <p>{{ activeUsers }}</p>
              <el-progress :percentage="activeUserGrowth" status="info"></el-progress>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <h3>今日新增帖子</h3>
              <p>{{ newPostsToday }}</p>
              <el-progress :percentage="newPostGrowth" status="primary"></el-progress>
            </el-card>
          </el-col>
        </el-row>

        <!-- 图表区域 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-card>
              <h3>用户注册趋势</h3>
              <canvas ref="registrationChart"></canvas>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card>
              <h3>帖子发布趋势</h3>
              <canvas ref="postChart"></canvas>
            </el-card>
          </el-col>
        </el-row>

        <!-- 最近活动记录 -->
        <el-row :gutter="20" style="margin-top: 20px;">
          <el-col :span="24">
            <el-card>
              <h3>最近活动</h3>
              <el-table :data="recentActivities" style="width: 100%">
                <el-table-column prop="timestamp" label="时间" width="180"></el-table-column>
                <el-table-column prop="activity" label="活动"></el-table-column>
              </el-table>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { Chart, registerables } from 'chart.js';
import { ref, onMounted } from 'vue';

Chart.register(...registerables);

export default {
  data() {
    return {
      totalUsers: 5000,
      totalPosts: 15000,
      activeUsers: 500,
      newPostsToday: 75,
      userGrowth: 75,
      postGrowth: 60,
      activeUserGrowth: 40,
      newPostGrowth: 85,
      recentActivities: [
        { timestamp: '2024-10-23 10:00', activity: '用户 user1 发布了新帖子' },
        { timestamp: '2024-10-23 10:30', activity: '用户 user2 评论了帖子' },
        { timestamp: '2024-10-23 11:00', activity: '用户 user3 注册' },
      ],
    };
  },
  setup() {
    const registrationChart = ref(null);
    const postChart = ref(null);

    const renderRegistrationChart = () => {
      const ctx = registrationChart.value.getContext('2d');
      new Chart(ctx, {
        type: 'line',
        data: {
          labels: ['一月', '二月', '三月', '四月', '五月', '六月', '七月'],
          datasets: [{
            label: '注册用户数',
            data: [100, 150, 200, 300, 400, 500, 600],
            borderColor: 'rgba(75, 192, 192, 1)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            fill: true,
          }]
        },
        options: {
          responsive: true,
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    };

    const renderPostChart = () => {
      const ctx = postChart.value.getContext('2d');
      new Chart(ctx, {
        type: 'bar',
        data: {
          labels: ['一月', '二月', '三月', '四月', '五月', '六月', '七月'],
          datasets: [{
            label: '帖子发布数',
            data: [50, 75, 100, 200, 300, 350, 400],
            backgroundColor: 'rgba(153, 102, 255, 0.6)',
          }]
        },
        options: {
          responsive: true,
          scales: {
            y: {
              beginAtZero: true,
            },
          },
        },
      });
    };

    onMounted(() => {
      renderRegistrationChart();
      renderPostChart();
    });

    return {
      registrationChart,
      postChart,
    };
  },
  methods: {
    navigateTo(page) {
      console.log(`Navigating to ${page}`);
      // 使用 vue-router 进行页面跳转
      this.$router.push(`/${page}`);
    },
  },
};
</script>

<style scoped>
.el-header {
  background-color: #f5f7fa;
  padding: 10px 20px;
  border-bottom: 1px solid #ebeef5;
}
.el-aside {
  background-color: #f9fafc;
}
.el-button {
  width: 100%;
}

canvas {
  width: 100%;
  height: 300px;
}

h3 {
  margin-bottom: 10px;
}

.el-table th, .el-table td {
  text-align: center;
}
</style>
