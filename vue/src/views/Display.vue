<template>
  <div class="container">
    
    <div class="bottom-component">
        <div>
    <!-- Your left component goes here -->
        <input-search />
        </div>
        <!-- <div class="right-component">
    
        </div> -->
    </div>
  </div>
</template>

<script>
//import DisplayButtons from '../components/DisplayButtons.vue'
import InputSearch from '../components/InputSearch.vue'
//import SearchDisplay from '../components/DisplaySearch.vue'
import store from '../store/store'

export default {
  components: { 
    // SearchDisplay,
    //DisplayButtons,
    InputSearch
  }, 
  name: "display",
  store
}

</script>


<style lang="css" scoped>
/* .container {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 50% 50%;
  height: 100vh;
    overflow: hidden;
} */

/* .display {
  grid-row: 1 / 2;
  height: 100%;
  overflow: hidden;
} */
/* 
.bottom-component {
  grid-row: 2 / 3;
  height: 100%;
  width: 75%; 
  overflow: hidden;
  padding: 10px 10px; /* added this */

 


/* .right-component {
  grid-column: 2 / 3;
  display: flex;
  align-items: flex-start;
  justify-content: center;
  height: 100%;
  overflow: hidden;
} */

/* h1 {
  color: white;
}

p {
  color: white;
} */


</style>
