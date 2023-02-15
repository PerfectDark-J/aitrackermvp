<template>
  
   <!-- ******************************** Top ***************************************** -->

    <template class="top-component">
      <v-row class="text-left">
        <ol style="display: grid; grid-template-columns: 8fr 2fr; grid-gap: 10px;">
      <template v-for="(result, index) in searchData" :key="index">
          <li class="item" :class="{ 'lightblue-background': (index % 2 === 0) }" style="display: grid; grid-template-rows: 30px 40px; padding: 10px; cursor: pointer; height: 50px; font-size: larger;">
          {{ result.content }}
          </li>
    
      <div style="display: grid; grid-template-columns: 1fr 1fr; grid-gap: 10px;">
        <button style="height: 50px; width: 75px; background-color: red; color: white; border: none; border-radius: 5px;" @click="dialog = true">Delete</button>
      
          <template>
            <v-row>
              <v-col>
                <!-- Your other elements -->
                <v-dialog v-model="dialog" max-width="290">
                  <v-card>
                    <v-card-title class="headline">Delete Item</v-card-title>
                    <v-card-text>Are you sure you want to delete this item?</v-card-text>
                    <v-card-actions>
                      <v-btn color="green darken-1" text @click="dialog = false">Cancel</v-btn>
                      <v-btn color="red darken-1" text @click="deleteItem(result.id)">Delete</v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-col>
            </v-row>
          </template>
      
          <template v-if="result.type === 'Task'">
            <button style="height: 50px; width: 75px; background-color: blue; color: white; border: none; border-radius: 5px;" @click="confirm= true">Complete</button>
          
              <template>
                <v-row>
                  <v-col>
                    <!-- Your other elements -->
                    <v-dialog v-model="confirm" max-width="290">
                      <v-card>
                        <v-card-title class="headline">Finish Task</v-card-title>
                        <v-card-text>Are you sure you want to complete this task?</v-card-text>
                        <v-card-actions>
                          <v-btn color="green darken-1" text @click="confirm = false">Cancel</v-btn>
                          <v-btn color="red darken-1" text v-on:click="flipStatus(result.id, result.content, result.description, result.userid)">Complete</v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                   </v-col>
                  </v-row>
                </template>
              </template>
            </div>
          </template>
        </ol>

    <v-dialog v-model="dialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span style="font-size: larger;">{{ selectedResult.type }}</span><br>
          <span style="font-size: smaller;">From {{ formattedDate }}</span>
        </v-card-title>
        <v-card-text>
          <v-text-field label="Content" v-model="selectedResult.content"></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" @click="saveChanges">Save</v-btn>
          <v-btn color="error" @click="dialog = false">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>
  
  <!-- ****************************** BOTTOM ***************************************** -->
  
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
        <v-text-field label="Description" v-model="description"/>
        <v-text-field label="Exercise" v-model="exercise"/>
      </v-col>

 <!-- ****************************** BUTTONS ***************************************** -->

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
import ServerService from '../services/ServerService'

export default {
  methods: {
    deleteItem(logId) {
      ServerService.deleteLog(logId)
      .then(() => {
        // handle success
        //this.$router.go(0)
        this.dialog = false
        this.search()
      })
      .catch(error => {
        // handle error
      });
  },

    
  
    
    flipStatus(logid, title, description, userid) {
    ServerService.getTaskByTitle(title)
    .then(response => {
      console.log(response.data)
      
      ServerService.completeTask(response.data)
      ServerService.deleteLog(logid)
      location.reload();
     });
},

   

    
    async search() {
          try {
            let description = 'empty'
            let exercise = 'empty'
            
            //const response = await axios.get(`https://ai-tracker-1.fly.dev/logs?timeframe=${this.timeframe}&type=${this.type}&description=${description}&exercise=${exercise}`);
            const response = await axios.get(`http://localhost:9000/logs?timeframe=${this.timeframe}&type=${this.type}&description=${description}&exercise=${exercise}`);
            console.log(`http://localhost:9000/logs?timeframe=${this.timeframe}&type=${this.type}&description=${description}&exercise=${exercise}`)
            
            this.searchData = response.data;
            console.log(this.searchData)
          } catch (error) {
            console.error(error);
          }
        },
    openPopup(result) {
      this.selectedResult = result;
      this.dialog = true;
    },
    saveChanges() {
      // Save changes to the selectedResult object in the searchData array
      // ...
      this.dialog = false;
      }
  },
  data() {
    return {
      titleData: [],
      searchData: [],
      taskiscompleted: false,
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
      searchQuery: '',
      dialog: false,
      confirm: false, 
      selectedResult: {}
    };
  },
  computed: {
    formattedDate() {
      let options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
      };
      return new Intl.DateTimeFormat('en-US', options).format(new Date(this.selectedResult.date));
    }
  }
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
  overflow: auto;
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

.btnEComplete {
  margin-right: 5px;
  background-color: #335974;
  color: white;
  border-color: black;
  border-radius: 5px;
  padding: 5px;;
  }

/* li:nth-child(odd) {
    background-color: lightblue;
  } */

  .lightblue-background {
    background-color: lightblue;
    
  }

</style>
