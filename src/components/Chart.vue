<template>
  
  <div class="chart-container">
    <h2>Dashboard</h2>
    <!-- <img src="src\assets\Line-Graph.webp"> -->
    <filter-buttons @updateFilter="filter = $event"/>
    <!-- <canvas ref="chart"></canvas> -->
  </div>
</template>

<script>
// import FilterButtons from '../components/FilterButtons.vue'

export default {
components: {
    // FilterButtons
}
}
</script>


<style scoped>
.chart-container {
  width: 100%;
  height: 500px;
  margin: 0;
  text-align: left;
}

.filter-buttons {
  display: flex;
  justify-content: flex-start;
  width: 100%;
  margin-left: 0;
}

button {
  width: 20%;
  padding: 10px;
  background-color: lightgray;
  border: 1px solid gray;
  border-radius: 5px;
  font-size: 16px;
  text-align: center;
  cursor: pointer;
}

.chart-container img {
  height: 60%;
  width: 100%;
  margin: 0 auto;
}

h2 {
  text-align: left;
  margin-bottom: 20px;
  color: white;
}

canvas {
  width: 90%;
  height: 90%;
  margin: 0 auto;
}
</style>



