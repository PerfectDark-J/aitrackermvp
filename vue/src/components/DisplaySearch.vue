<template>
  <v-container class="search-display" >
    <v-row>
      <!-- Display data from get requests here -->
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      // data from get requests
    };
  },
  created() {
    // make get request to retrieve data
  }
};
</script>

<style scoped>
.search-display {
  background-color: white !important;
  border-radius: 20px;
  padding: 20px;
  height: 35vh;
}
</style>
