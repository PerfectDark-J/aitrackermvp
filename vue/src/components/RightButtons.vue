<template>
  
    
    <div class="floating-buttons">
      <div class="top-button">
        <font-awesome-icon :icon="['fas', 'comment']" />
      </div>
      <div class="middle-button" @click="dialog = true">
        <font-awesome-icon :icon="['fas', 'pen']" />
      </div>
      <div class="bottom-button" @click="showPopup = true">
        <font-awesome-icon :icon="['fas', 'plus']" />
      </div>
    </div>

    <!-- PLUS BUTTON POPUP -->
    <v-dialog v-model="showPopup" max-width="500">
  <template v-slot:activator="{ on }">
    <div v-on="on"></div>
  </template>
  <v-card style="height: 90vh;">
    <v-card-title>
      What would you like to track?
    </v-card-title>
    <v-card-text>
      <v-row>
        <v-col cols="6" v-for="(item, index) in items" :key="index">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%;">
            <span style="display: flex; flex-direction: column; align-items: center;">
              <span>{{ item }}</span>
              <span style="display: flex;">
                <span @click="incrementCount(index, -1)">-</span>
                <span class="count">{{ counts[index] }}</span>
                <span @click="incrementCount(index, 1)"> +</span>
              </span>
            </span>
          </div>
        </v-col>
      </v-row>
    </v-card-text>
    
    <span style="display: flex; justify-content: center;">
  <v-btn color="primary" @click="showWorkout = true">Workout</v-btn>
  <v-btn color="secondary" @click="showTask = true">Task</v-btn>
    </span>

    <!-- WORKOUT POPUP -->

     <v-dialog v-model="showWorkout" max-width="500">
      <v-card>
        <v-card-title>
          Workout
        </v-card-title>
        <v-card-text>
          <v-form>
            <v-select
              v-model="selectedWorkout"
              :items="workouts"
              label="Workout"
            ></v-select>
          </v-form>
          <div v-if="selectedWorkout === 'Cardio'">
            <v-form>
              <v-select v-model="selectedCardioType" :items="cardioTypes" label="Cardio Type"/>
            </v-form>
            <v-text-field v-model="duration" label="Duration (minutes)" type="number"/>
          </div>
          <div v-if="selectedWorkout === 'Bench'">
            <v-form>
              <v-text-field
                label="Weight (in lbs)"
                v-model="benchWeight"
                type="number"
              />
              <v-text-field
                label="Reps"
                v-model="benchReps"
                type="number"
              />
            </v-form>
          </div>
          <div v-if="selectedWorkout === 'Abs'">
            <v-form>
              <v-select label="Select" v-model="selectedAbs" :items="['Basic Abs', 'Full Abs']"/>
            </v-form>
          </div>
          <div v-if="selectedWorkout === 'Pullups'">
            <v-text-field v-model="pullupsReps" label="Reps"></v-text-field>
          </div>
          <div v-if="selectedWorkout === 'Pushups'">
            <v-text-field v-model="pushupsReps" label="Reps"></v-text-field>
          </div>
          <div v-if="selectedWorkout === 'Squats'">
            <v-text-field v-model="squats" label="Reps"></v-text-field>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" @click="saveWorkout">Save</v-btn>
          <v-btn color="secondary" @click="showWorkout = false">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>



    <!-- TASK POPUP -->
      <v-dialog v-model="showTask" max-width="450">
      <v-card>
        <v-card-title>Task</v-card-title>
        <v-card-text>
          <v-text-field label="Task Name" v-model="tasktitle"></v-text-field>
          <v-row class="mx-2">
            <v-btn color="primary" @click="setTaskType('today')">Today</v-btn>
            <v-btn color="secondary" @click="setTaskType('daily')">Daily</v-btn>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-btn color="primary" @click="saveTask">Save</v-btn>
          <v-btn color="secondary" @click="showTask = false">Cancel</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- <span style="display: flex; justify-content: center;">
      <v-btn color="primary" @click="showWorkout = true">Workout</v-btn>
      <v-btn color="secondary" @click="showTask = true">Task</v-btn>
    </span> -->

        
        <v-card-actions>
          <v-btn color="secondary" @click="showPopup = false">Cancel</v-btn>
          <v-btn color="primary" @click="showPopup = false">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>


    
    <!-- PEN BUTTON POPUP -->
    <v-dialog v-model="dialog" max-width="500px">
    <v-card>
      <v-card-title class="headline">{{ title }}</v-card-title>

      <v-card-text>
        <v-text-field label="Title" v-model="title" single-line></v-text-field>
        <v-textarea label="Description" v-model="description"></v-textarea>
      </v-card-text>

      <v-card-actions class="d-flex flex-wrap justify-center">
        <v-btn text v-for="item in journalItems" :key="item" @click="selected = item">{{ item }}</v-btn>
      </v-card-actions>

      <v-btn color="transparent" class="fixed-bottom-right" @click="saveEntry" flat>
  <font-awesome-icon :icon="['fas', 'plus']" />
</v-btn>
      
    </v-card>
  </v-dialog>

 
</template>


<script>
import TaskToggle from './TaskToggle.vue'
import ServerService from '../services/ServerService'
// import { User } from '@auth0/auth0-spa-js';
// import { useAuth0 } from '@auth0/auth0-vue';

// import { VButton, VTextField, VRadioGroup, VRadio, VSelect, VSelectList, VSelectItem, VCardActions, VCardText, VCard, VDialog, VCardTitle, VIcon, VRow, VCol } from 'vuetify/lib';

export default {
  name: 'right-buttons',
  props: {
    userId: {
      type: Number,
      required: true
    }
  },
  components: {
    TaskToggle,
  },
  data() {
    return {
      openPopup: false,
      showTask: false,
      showWorkout: false, 
      showPopup: false,
      dialog: false, 
      title: '',
      description: '',
      selected: '',
      showDateOption: false,
      dateOption: '',
      description: '',
      journalItems: ["Journal", "Gratitude", "Meditation", "Story", "Business Idea", "Quote", "Movie", "Idea"],
      items: ['Water', 'Poop', 'Sex', 'Porn', 'Night Vit', 'Coffee', 'Alcohol', 'Pot', 'Morning Vit', 'Gaming'],
      counts: Array(10).fill(0),
      tasktitle: '',
      isrecurring: false,
      selectedWorkout: null,
      workouts: ['Cardio', 'Bench', 'Squats', 'Abs', 'Pullups', 'Pushups'],
      selectedCardioType: '',
      duration: '',
      cardioTypes: [
        'Running',
        'Walking',
        'VR'
      ]
    }
  },
  methods: {
    incrementCount(index, amount) {
      this.counts[index] += amount;
    },
    setTaskType(type) {
    
    if(type === 'daily'){
      this.isrecurring = true;  
    }
    },
    saveTask() {
      console.log('INSIDE SAVETASK')
      const task = {
        tasktitle: this.tasktitle,
        taskcompletiondate: '',
        taskdescription: '',
        taskiscompleted: false,
        userid: this.userId,
        taskisrecurring: this.isrecurring

      };
      console.log('AFTER object')
      console.log(task)
      ServerService.addTask(task).then(response => {
        // handle success
        console.log('TASK ADDED')

        
      

        const date = new Date();
        console.log(date)
        let content = this.tasktitle
        console.log(content)

        let type = 'Task';
        
        let userid = this.userId;
        let description = 'Task';
        let exercise;
        let weight;
        let reps;
        let minutes; 
        const report = { content, type, date, userid, description, exercise, weight, reps, minutes };
        console.log(report)
        ServerService.createReport(report)




        this.showTask = false;
        this.showPopup = false; 
        window.location.reload()
      }).catch(error => {
        // handle error
        
        console.log('FAILED')
      });
      
      
      this.showTask = false;
    },
    saveWorkout(){
          console.log("inside workout")
          
          // const auth0 = useAuth0()

          // ServerService.verifyThroughEmail({ email: auth0.user.value.email})
      
          // ServerService.getUserByEmail(`${auth0.user.value.email}`)
          // .then(response => {
          // const { userid } = response.data;})


          // console.log(this.userid)
          
          let type = 'Workout'
          let content;
          let description; 
          let exercise; 
          let weight; 
          let reps;
          let minutes;
          // const userid = this.userId
          // console.log(userid)
          //console.log(this.userid)

          if (this.selectedWorkout === 'Cardio') {
            content = `Cardio: Type: ${this.selectedCardioType} Duration: ${this.duration} minutes`;
            description = 'Cardio'
            exercise =  `${this.selectedCardioType}`
            minutes = `${this.duration}`
          } else if (this.selectedWorkout === 'Bench') {
            description = 'Bench'
            content = `Bench: Weight: ${this.benchWeight} lbs Reps: ${this.benchReps}`;
            weight = `${this.benchWeight}`
            reps = `${this.benchReps}`
          } else if (this.selectedWorkout === 'Abs') {
            description = 'Abs'
            content = `Abs: ${this.selectedAbs}`;
            exercise = `${this.selectedAbs}`
          } else if (this.selectedWorkout === 'Pullups') {
            description = 'Pullups'
            content = `Pullups: ${this.pullupsReps} reps`;
            reps = `${this.pullupsReps}`
          } else if (this.selectedWorkout === 'Pushups') {
            description = 'Pushups'
            content = `Pushups: ${this.pushupsReps} reps`;
            reps = `${this.pushupsReps}`
          } else if (this.selectedWorkout === 'Squats') {
            description = 'Squats'
            content = `Squats: ${this.squats} reps`;
            reps = `${this.squats}`
          }

          const date = new Date();
          const userid = this.userId

          const report = { content, type, date, userid, description, exercise, weight, reps, minutes };
          ServerService.createReport(report).then(response => {
            console.log('success');
            console.log(report)
            }).catch(error => {
              console.log('failed');
              console.log(report)
            });

            this.successMessage = "Saved Successfully!";
            setTimeout(() => {
              this.successMessage = '';
            }, 2000);
            this.openPopup = false;
            location.reload();
        },



    saveEntry() {
      console.log('Saving entry with type: ', this.selected);
      console.log('Saving entry with date option: ', this.dateOption);
      // console.log(userid)
      // console.log(this.userid)
      
        

      const title = this.title; 
      const content = this.description;
      const type = this.selected;
      const date = new Date();
      const userid = this.userId; 

      let description; 
      let exercise; 
      let weight; 
      let reps;
      let minutes;

      const report = { title, content, type, date, userid, description, exercise, weight, reps, minutes };
      ServerService.createReport(report).then(response => {
        console.log('success');
      }).catch(error => {
        console.log('failed');
      });

      this.successMessage = "Saved Successfully!";
      setTimeout(() => {
        this.successMessage = '';
      }, 2000);
      this.openPopup = false;
      location.reload();
    }
  },
    

  created() {
    console.log('Right buttons component created');
    
  },
  computed: {
    dateOptionClass() {
      if (this.dateOption === 'Today') {
        return 'today-class';
      } else if (this.dateOption === 'Recurring') {
        return 'recurring-class';
      }
    }
  }
};
</script>



<style scoped>
 


  .floating-buttons {
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* added this */
  position: absolute;
  right: 15px; /* added this */
  z-index: 1;
  bottom: 100px;
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

/* .my-custom-spacing {
  margin-bottom: -40px;
} */

/* .new-plus {
    border-radius: 50%;
    width: 15px;
    height: 100px;
    background-color: red;
    padding: 0px;
  } */

v-dialog {
    height: 80vh;
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


</style>