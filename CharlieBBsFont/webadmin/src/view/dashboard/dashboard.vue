<template>
    <el-container>
      <!-- 页面标题 -->
      <el-header>
        <h2>论坛仪表盘</h2>
      </el-header>
  
      <el-main>
        <!-- 统计信息 -->
        <el-row :gutter="20" style="margin-bottom: 20px;">
          <el-col :span="6">
            <el-card>
              <h3>总用户数</h3>
              <p>{{ totalUsers }}</p>
              <el-button @click="viewDetails('users')" size="mini" type="text">查看详情</el-button>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <h3>帖子总数</h3>
              <p>{{ totalPosts }}</p>
              <el-button @click="viewDetails('posts')" size="mini" type="text">查看详情</el-button>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <h3>今日活跃用户</h3>
              <p>{{ activeUsersToday }}</p>
              <el-button @click="viewDetails('activeToday')" size="mini" type="text">查看详情</el-button>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card>
              <h3>新注册用户</h3>
              <p>{{ newUsers }}</p>
              <el-button @click="viewDetails('newUsers')" size="mini" type="text">查看详情</el-button>
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
  
        <!-- 互动区域 -->
        <el-row :gutter="20" style="margin-top: 20px;">
          <el-col :span="12">
            <el-card>
              <h3>最新活动</h3>
              <el-table :data="recentActivities" style="width: 100%">
                <el-table-column prop="timestamp" label="时间" width="180"></el-table-column>
                <el-table-column prop="activity" label="活动"></el-table-column>
              </el-table>
              <el-button @click="loadMoreActivities" size="mini" type="text">加载更多</el-button>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card>
              <h3>热门帖子</h3>
              <el-table :data="popularPosts" style="width: 100%">
                <el-table-column prop="title" label="标题"></el-table-column>
                <el-table-column prop="author" label="作者" width="100"></el-table-column>
                <el-table-column prop="replies" label="回复数" width="100"></el-table-column>
              </el-table>
              <el-button @click="viewAllPosts" size="mini" type="text">查看所有</el-button>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </template>
  
  <script>
  import { Chart, registerables } from 'chart.js';
  import { ref, onMounted } from 'vue';
  
  Chart.register(...registerables);
  
  export default {
    data() {
      return {
        totalUsers: 1200,
        totalPosts: 3500,
        activeUsersToday: 150,
        newUsers: 25,
        recentActivities: [
          { timestamp: '2024-10-23 10:00', activity: '用户 user1 发布了新帖子' },
          { timestamp: '2024-10-23 10:30', activity: '用户 user2 评论了帖子' },
          { timestamp: '2024-10-23 11:00', activity: '用户 user3 注册' },
        ],
        popularPosts: [
          { title: '如何使用 Vue 3', author: 'user4', replies: 10 },
          { title: 'Element Plus 的使用指南', author: 'user5', replies: 8 },
          { title: '讨论论坛设计', author: 'user6', replies: 5 },
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
  
      const viewDetails = (type) => {
        // 根据不同类型查看详情的逻辑
        console.log(`查看 ${type} 详情`);
      };
  
      const loadMoreActivities = () => {
        // 加载更多活动的逻辑
        console.log('加载更多活动');
      };
  
      const viewAllPosts = () => {
        // 查看所有帖子逻辑
        console.log('查看所有帖子');
      };
  
      return {
        registrationChart,
        postChart,
        viewDetails,
        loadMoreActivities,
        viewAllPosts,
      };
    },
  };
  </script>
  
  <style scoped>
  .el-header {
    padding: 10px;
    background-color: #f5f7fa;
  }
  .el-card {
    text-align: center;
    padding: 20px;
  }
  .el-button {
    margin-top: 10px;
  }
  </style>
  