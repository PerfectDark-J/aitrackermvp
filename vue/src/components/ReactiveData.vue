<template>
  <Bar
    id="my-chart-id"
    :options="chartOptions"
    :data="chartData"
  />
</template>

<script>
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
import ServerService from './ServerService'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: 'BarChart',
  components: { Bar },
  data() {
    return {
      chartData: {
        labels: [],
        datasets: [ { data: [] } ]
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    async fetchData() {
      const points = await ServerService.getPoints()
      this.chartData.labels = points.map(p => p.team)
      this.chartData.datasets[0].data = points.map(p => p.points)
    }
  }
}
</script>
