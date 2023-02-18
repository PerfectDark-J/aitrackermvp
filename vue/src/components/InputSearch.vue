<template>
  
   <!-- ******************************** Top ***************************************** -->
     
    <h1 v-auto-animate="{ duration: 200 }"> {{ type || "Search" }} </h1>
  
   
    <template class="top-component">
      <v-row class="text-left">
        <ol v-auto-animate="{ duration: 200 }" style="display: grid; grid-template-columns: 8fr 2fr; grid-gap: 10px;">
      <template v-for="(result, index) in searchData" :key="index">
          <li class="item" :class="{ 'lightblue-background': (index % 2 === 0) }" style="display: grid; grid-template-rows: 30px 40px; padding: 10px; cursor: pointer; height: 50px; font-size: larger;" @click="dialog = true; selectedResult = result">
            <!-- @click="() => openUpdateDialog(result)" -->
            {{ result.title }}
          </li>

    
      <div style="display: grid; grid-template-columns: 1fr 1fr; grid-gap: 10px;">
        <button style="height: 50px; width: 75px; background-color: red; color: white; border: none; border-radius: 5px;" @click="deleteDialog = true; selectedResult = result">Delete</button>
      
          <template>
            <v-row>
              <v-col>
                <!-- Your other elements -->
                <v-dialog v-model="deleteDialog" max-width="290">
                  <v-card>
                    <v-card-title class="headline">Delete Item</v-card-title>
                    <v-card-text>Are you sure you want to delete this item?</v-card-text>
                    <v-card-actions>
                      <v-btn color="green darken-1" text @click="deleteDialog = false">Cancel</v-btn>
                      <v-btn color="red darken-1" text @click="deleteItem(selectedResult.id, result.taskid)">Delete</v-btn>
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
                          <v-btn color="red darken-1" text v-on:click="flipStatus(result.taskid)">Complete</v-btn>
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

<!-- **********************************UPDATE BY CLICKING*************************************** -->

      <v-dialog v-model="dialog" max-width="500px">
      <v-card >
        <v-card-title class="headline">{{ selectedResult.title }}</v-card-title> <v-card-text>
        
        <!-- Task specific options -->
        <template v-if="selectedResult.type === 'Task'">
            <v-text-field label="Title" v-model="selectedResult.title" single-line></v-text-field>
              <v-textarea label="Content" v-model="selectedResult.content"></v-textarea>
            <div style="display: flex; justify-content: flex-end; align-items: center;">
              <label for="isRecurring" style="margin-right: 10px; font-size: larger;">Is Recurring?</label>
              <input type="checkbox" id="isRecurring" v-model="selectedResult.taskisrecurring" style="transform: scale(1.5);"/>
            </div>
        </template>


       <!-- Workout specific options -->
        <template v-else-if="selectedResult.type === 'Workout'">
          <v-card>
            <!-- <v-card-title class="headline">{{ selectedResult.title }}</v-card-title> -->
            <v-card-text>
              <template v-if="selectedResult.description === 'Bench'">
                <v-text-field label="Weight (in lbs)" v-model="selectedResult.weight"></v-text-field>
                <v-text-field label="Reps" v-model="selectedResult.reps"></v-text-field>
              </template>
              <template v-else-if="selectedResult.description === 'Abs'">
                <v-form>
                  <v-select label="Abs Type" v-model="selectedResult.exercise" :items="['Basic Abs', 'Full Abs']"/>
                </v-form>
              </template>

              <template v-else-if="selectedResult.description === 'Cardio'">
                <v-form>
                  <v-select label="Cardio Type" v-model="selectedResult.exercise" :items="['Running', 'Walking', 'VR']"/>
                  <v-text-field label="Duration (minutes)" v-model="selectedResult.minutes"></v-text-field>
                </v-form>
              </template>
              <template v-else-if="selectedResult.description === 'Squats'">
                <v-text-field label="Reps" v-model="selectedResult.reps"></v-text-field>
              </template>
              <template v-else-if="selectedResult.description === 'Pullups'">
                <v-text-field label="Reps" v-model="selectedResult.reps"></v-text-field>
              </template>
              <template v-else-if="selectedResult.description === 'Pushups'">
                <v-text-field label="Reps" v-model="selectedResult.reps"></v-text-field>
              </template>
            </v-card-text>
          </v-card>
        </template>

          
        <!-- Anything Else  -->
          <template v-else>
          <v-text-field label="Title" v-model="selectedResult.title" single-line></v-text-field>
          <v-textarea label="Content" v-model="selectedResult.content"></v-textarea>
          </template>
          </v-card-text>

          <v-btn color="transparent" class="fixed-bottom-right" @click="saveEntry" flat>
            <font-awesome-icon :icon="['fas', 'plus']" />
          </v-btn>
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

<!-- Success Message -->

<div v-if="showSuccess" class="success-message" :style="{ animation: growAndFade }">
    {{ successMessage }}
  </div>

  

</template>

<script>
import axios from 'axios'; 
import ServerService from '../services/ServerService'
import anime from 'animejs'



export default {
  components: {

  },
  methods: {
      //   openUpdateDialog(result) {
      // this.selectedResult = result;
      // if (this.selectedResult.type === 'Task') {
      //   this.updateTask();
      // } else {
      //   this.updateLog();
      // }
    // },
    updateTask() {
      //This was coppied nd pasted from task page
      // console.log('updateTask')
      // console.log(this.selectedTask.id)
      // console.log(this.selectedTask.comment)
      // console.log(this.selectedTask)
      // Add your code here to update the task in the database
      ServerService.updateTask(this.selectedTask)
      
      const log = {
        type: this.selectedTask.taskdescription,
        title: this.selectedTask.tasktitle,
        content: this.selectedTask.comment,
        userid: this.selectedTask.userid,
        date: new Date(),
       // id: 
      };
      
      ServerService.updateLog(log)
      // For example, you could use axios to send a PUT request to the API endpoint for updating tasks
    },
    showTaskDetails(task) {
      //this was copied and pasted from taskpage
      
      this.selectedTask = task
      this.dialog = true
      console.log('showTaskDetails')
      console.log(task)
    },
    updateLog() {
      // Add your code here to open the log update dialog
      this.dialog = true; 
      console.log('updateLog')
    },
    deleteItem(logId, taskId) {

      console.log(logId)
      console.log(taskId)
      
      ServerService.deleteLog(logId)
      .then(() => {
        // handle success
        //this.$router.go(0)
        this.deleteDialog = false
        location.reload();
        
      })
      ServerService.deleteTask(taskId)
      //this.search()
      .catch(error => {
        // handle error
      });
  },

    
  
    
    flipStatus(taskid) {
    //console.log(taskid)
    ServerService.completeTask(taskid)
    this.confirm = false; 
    location.reload();
    
    // ServerService.getTaskByTitle(title)
    // .then(response => {
    //   console.log(response.data)
      
    //   ServerService.completeTask(response.data)
    //   ServerService.deleteLog(logid)
    //   location.reload();
    //  });
},

    async search() {
    try {
      let description = 'empty'
      let exercise = 'empty'
      
      const response = await axios.get(`https://ai-tracker-1.fly.dev/logs?timeframe=${this.timeframe}&type=${this.type}&description=${description}&exercise=${exercise}`);
      //const response = await axios.get(`http://localhost:9000/logs?timeframe=${this.timeframe}&type=${this.type}&description=${description}&exercise=${exercise}`);
      //console.log(`http://localhost:9000/logs?timeframe=${this.timeframe}&type=${this.type}&description=${description}&exercise=${exercise}`)
      
      // Store the response data in searchData
      this.searchData = response.data;

      // Filter the searchData to only contain tasks that are not completed
      this.searchData = await Promise.all(this.searchData.map(async task => {
        const taskStatus = await ServerService.isTaskCompleted(task.taskid);
        if (!taskStatus.data) {
          return task;
        }
      }));

      // Filter out any undefined values from the filtered searchData
      this.searchData = this.searchData.filter(task => task);
                } catch (error) {
                  console.error(error);
                }
              },



  

    openPopup(result) {
      this.selectedResult = result;
      this.dialog = true;
    },

    displaySuccess(message) {
          this.successMessage = message;
          this.showSuccess = true;
            anime({
            targets: '.success-message',
            scale: [1, 1.5],
            opacity: [1, 0],
            easing: 'easeInQuad',
            duration: 1000,
            complete: () => {
            this.showSuccess = false;
            this.successMessage = "";
          }
          });
          },

    saveEntry() {
      // Save changes to the selectedResult object in the searchData array
      if (this.selectedResult.type === 'Task') {
        console.log('save task')
        // Update log
        const taskLog = {
              id: this.selectedResult.id,
              userid: this.selectedResult.userid,
              content: this.selectedResult.content,
              type: this.selectedResult.type,
              date: this.selectedResult.date,
              description: this.selectedResult.description,
              title: this.selectedResult.title,
              taskid: this.selectedResult.taskid
              }

        ServerService.updateLog(taskLog)

        // Update task
        const task = {
            id: this.selectedResult.taskid,
            tasktitle: this.selectedResult.title,
            taskdescription: 'Task',
            taskiscompleted: 'False',
            taskcompletiondate: this.selectedResult.completiondate,
            taskisrecurring: this.selectedResult.taskisrecurring,
            userid: this.selectedResult.userid,
            comment: this.selectedResult.content
          };
          ServerService.updateTask(task)
          this.displaySuccess('Updated')

      } else if (this.selectedResult.type === 'Workout') {
        console.log('save workout')
        // Workout specific code
        // Update log

        const workoutLog = {
                id: this.selectedResult.id,
                userid: this.selectedResult.userid,
                date: this.selectedResult.date,
                reps: this.selectedResult.reps,
                weight: this.selectedResult.weight,
                minutes: this.selectedResult.minutes,
                content: this.selectedResult.content,
                type: 'Workout',
                title: this.selectedResult.title,
                description: this.selectedResult.description,
                exercise: this.selectedResult.exercise
                };

              console.log(workoutLog)
              ServerService.updateLog(workoutLog)

      } else {
        // Code for anything else
        console.log('save anything else')
        // Update log
        const entryLog = {
                id: this.selectedResult.id,
                userid: this.selectedResult.userid,
                date: this.selectedResult.date,
                content: this.selectedResult.content,
                type: this.selectedResult.type,
                title: this.selectedResult.title,
                };
          console.log(entryLog)
          ServerService.updateLog(entryLog)
          this.displaySuccess('Success!')
      


      }
      this.dialog = false;
      }

  },
  data() {
    return {
      showFireworks: false,
      taskDialog: false, 
      showSuccess: false,
      successMessage: '',
      deleteDialog: false, 
      isRecurring: false,
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
    height: 180px !important;
    border-radius: 25px;
    margin-top: 70px;
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
  height: 40vh;
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

  h1 {
    color: white;
  }

  v-card-actions {
  display: flex;
  justify-content: space-between;
}

  v-dialog {
  height: 80vh;
}

.justify-center {
  display: flex;
  justify-content: center;
}

.fixed-bottom-right {
    left: 220px; 
    bottom:10px;
    
  }

input[type="checkbox"] {
  width: 15px;
  height: 15px;
  -webkit-appearance: none;
  background-color: white;
  border: 2px solid gray;
  border-radius: 25px;
}

input[type="checkbox"]:checked {
  background-color: green;
}


.fade-enter-active,
.fade-leave-active {
  transition: opacity 1.5s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.success-message {
  padding: 10px;
  border-radius: 5px;
  background-color: green;
  color: white;
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%) scale(1);
  animation: growAndFade 1s ease-in-out forwards;
}

@keyframes growAndFade {
  0% {
    transform: translate(-50%, -50%) scale(1);
    opacity: 0;
  }
  50% {
    transform: translate(-50%, -50%) scale(1.5);
    opacity: 1;
  }
  100% {
    transform: translate(-50%, -50%) scale(3);
    opacity: 0;
  }
}


</style>
