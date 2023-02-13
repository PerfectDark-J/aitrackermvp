<template>
  <div class="filter-buttons">
    <button @click="setFilter('today')">Today</button>
    <button @click="setFilter('week')">Week</button>
    <button @click="setFilter('month')">Month</button>
    <button @click="setFilter('year')">Year</button>
  </div>
</template>
<script>
export default {
  name: "FilterButtons",
  props: ['selectedFilter'],
  methods: {
    setFilter(timeFrame) {
      this.selectedFilter = timeFrame;
    }
  }
};
</script>
<style scoped>
.filter-buttons {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-top: 0;
}

button {
  width: 25%;
  padding: 10px;
  background-color: lightgray;
  border: 1px solid gray;
  border-radius: 5px;
  font-size: 16px;
  text-align: center;
  cursor: pointer;
}
</style>