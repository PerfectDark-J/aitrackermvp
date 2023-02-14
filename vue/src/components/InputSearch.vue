<template>
  
  <!-- TOP -->
  
  

    <template class="top-component">
      <v-row class="text-left">
        <template v-for="(result, index) in searchData" :key="index">
          <p>{{ index + 1 }}. {{ result.content }}</p><br>
        </template>
      </v-row>
    </template>
  
  
  
  <!-- BOTTOM -->
  
  <v-container>
    <v-row>
      <v-col cols="12">
        <v-text-field label="Keyword" v-model="searchQuery"/>
        <v-row>
          <v-col cols="5">
            <v-select
              :items="timeframes"
              label="Days"
              v-model="timeframe"
              color="white"
            />
          </v-col>
          <v-col cols="7">
            <v-select
              :items="types"
              label="Type"
              v-model="type"
              color="white"
            />
          </v-col>
        </v-row>
      </v-col>

<!-- BUTTONS START HERE -->

      <v-col cols="1">
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
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  methods: {
      async search() {
      try {
        const response = await axios.get("https://ai-tracker-1.fly.dev/logs?timeframe=7&type=Workout&description=&exercise=");
        this.searchData = response.data;
        console.log(this.searchData)
      } catch (error) {
        console.error(error);
      }
    },
  },
  data() {
    return {
      searchData: [],
      timeframes: [
        "Today",
        "7",
        "30",
        "365",
        "All"
      ],
      types: [
        "Journal",
        "Gratitude",
        "Meditation",
        "Story",
        "Business",
        "Quote",
        "Movie",
        "Idea",
        "Workout",
        "Task"
      ],
      timeframe: 0,
      type: "Task",
      searchQuery: ''
    };
  },
};
</script>


<style scoped>
.v-container {
    background-color: white !important;
    width: 80%;
    margin: 20px 0; 
    height: 600px !important;
    border-radius: 0px;
    margin-top: 20px;
    margin-left: 0;
    text-align: left;
}

.right-component {
  display: flex;
  align-items: flex-start;
  padding-top: 5%;
}

.floating-buttons {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  bottom: 120px; 
  position: absolute;
  right: 15px;
  z-index: 1;
}

.top-button,
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

.item {
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
  align-items: center;
  justify-content: center;
}

.top-component {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  background-color: white !important;
  border-radius: 20px;
  padding: 20px;
  height: 50vh;
}


.search-display {
  width: 100%;

  
}

.text-left {
  text-align: left;
  margin: 0;
  padding: 0;
}

p {
  margin: 0;
  padding: 0;
  line-height: 1;
}


</style>
