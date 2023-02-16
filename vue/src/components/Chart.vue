<template>
  
  <div class="chart-container">
   <test-auto-animate />

  </div>
</template>

<script>
import FilterButtons from '../components/FilterButtons.vue'
import test from '../components/TestAutoAnimate.vue'


export default {
components: {
    FilterButtons,
    test
   
}
}
</script>


<style scoped>
.chart-container {
  width: 100%;
  height: 300px;
  margin: 0;
  text-align: left;
  border-radius: 25;
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

/* .chart-container img {
  height: 60%;
  width: 100%;
  margin: 0 auto;
} */

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



