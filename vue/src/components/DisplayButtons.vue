<template>
  <div class="right-component">
    
    <div class="floating-buttons">
      <div class="top-button">
        <font-awesome-icon :icon="['fas', 'comment']" />
      </div>
      <div class="bottom-button" @click="search">
        <font-awesome-icon :icon="['fas', 'search']" />
      </div>

    </div>

  </div>
</template>

<script>
import InputSearch from './InputSearch.vue'
import store from '../store/store'

export default {
  name: 'display-buttons',
  components: {
    InputSearch
  },
  methods: {
    search() {
      store.dispatch('updateSearchClicked', true)
      console.log("sent")
    }
  }
};
</script>









<style scoped>
  .right-component {
    display: flex;
    align-items: flex-start; /* change this */
    padding-top: 5%;
  }

  .floating-buttons {
  display: flex;
  flex-direction: column;
  align-items: flex-end; /* added this */
  position: absolute;
  right: 15px; /* added this */
  z-index: 1;
}


  .top-button,
  .middle-button,
  .bottom-button {
    background-color: #b53c3c;
    border: 1px solid #ffffff;
    border-radius: 50%;
    width: 60px;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;
  }

  i {
    font-size: 130px;
    color: #ffffff !important; 
  }

    .v-card-actions {
    display: flex;
    justify-content: center;
  }

  .v-btn {
    width: 100px;
    margin: 10px;
  }

  item {
  display: flex;
  align-items: center;
  font-size: 8px;
  padding: 4px 0;
}



.count {
  margin: 0 10px;
}

.fa-minus,
.fa-plus {
  font-size: 20px;
  cursor: pointer;
}

span {
  /* display: flex; */
  align-items: center;
  justify-content: center;
}


</style>