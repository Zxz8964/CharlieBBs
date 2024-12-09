<template>
  <div class="line-chart-container">
    <line-chart
      :chart-data="chartData"
      :options="chartOptions"
    ></line-chart>
  </div>
</template>

<script>
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale } from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, LineElement, PointElement, LinearScale, CategoryScale);

export default {
  components: {
    lineChart: Line,
  },
  props: {
    data: {
      type: Array,
      required: true,
    },
  },
  computed: {
    chartData() {
      return {
        labels: this.data.map(item => item.date),
        datasets: [
          {
            label: '活跃用户',
            backgroundColor: '#42A5F5',
            borderColor: '#1E88E5',
            data: this.data.map(item => item.activeUsers),
            fill: false,
          },
        ],
      };
    },
    chartOptions() {
      return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'top',
          },
          title: {
            display: true,
            text: '用户活跃趋势',
          },
        },
      };
    },
  },
};
</script>

<style scoped>
.line-chart-container {
  width: 100%;
  height: 300px; /* 设定高度以防止布局问题 */
}
</style>
