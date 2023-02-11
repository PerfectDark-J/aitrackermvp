<template>
  <div class="container">
    <div class="chart-container">
      <!-- Your chart goes here -->
      <chart :filter="filter"/> 
    </div>
    <div class="bottom-half">
  <div class="left-component">
    <!-- Your left component goes here -->
    <tasks />
  </div>
  <div class="right-component">
    <!-- Your right component goes here -->
    <right-buttons />
  </div>
</div>
</div>
</template>
<script lang="ts">
import { useAuth0 } from '@auth0/auth0-vue';
import ServerService from '../services/ServerService.js';
import RightButtons from '../components/RightButtons.vue';
import Chart from '../components/Chart.vue'; 
import Tasks from '../views/TaskPage.vue'


export default {
  name: "home-view",
  components: {
    Chart,
    Tasks, 
    RightButtons
  },
  data() {
    return {
      filter: null
    };
  },
  created() {
    const auth0 = useAuth0();

    ServerService.verifyThroughEmail({ email: auth0.user.value.email });
    ServerService.getUserByEmail(`${auth0.user.value.email}`);
  }
}
</script>
<style lang="css" scoped>
 .container {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 50% 50%;
  height: 100vh;
}

.chart-container {
  grid-row: 1 / 2;
}

.bottom-half {
  grid-row: 2 / 3;
  display: grid;
  grid-template-columns: 75% 25%;
}

.left-component {
  grid-column: 1 / 2;
}

.right-component {
  grid-column: 2 / 3;
}

</style>