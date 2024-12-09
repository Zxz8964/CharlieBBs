<template>
  <div class="pie-chart-container">
    <pie-chart
      :chart-data="chartData"
      :options="chartOptions"
    ></pie-chart>
  </div>
</template>

<script>
import { Pie } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, ArcElement } from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, ArcElement);

export default {
  components: {
    pieChart: Pie,
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
        labels: this.data.map(item => item.name),
        datasets: [
          {
            label: '分类分布',
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
            data: this.data.map(item => item.value),
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
            text: '分类分布',
          },
        },
      };
    },
  },
};
</script>

<style scoped>
.pie-chart-container {
  width: 100%;
  height: 300px; /* 设定高度以防止布局问题 */
}
</style>
