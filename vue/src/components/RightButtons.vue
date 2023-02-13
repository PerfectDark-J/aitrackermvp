<template>
  <div class="right-component">
    
    <div class="floating-buttons">
      <div class="top-button">
        <font-awesome-icon :icon="['fas', 'comment']" />
      </div>
      <div class="middle-button" @click="openPopup = true">
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


    
    <!-- MIDDLE BUTTON POPUP -->
    
    <v-dialog v-model="openPopup" max-height="90%">
      <template v-slot:activator="{ on }">
        <div v-on="on"></div>
      </template>
      <v-card style="height: 80vh;">
        <v-card-title>
          Add Entry
        </v-card-title>
        <v-card-text>
          <v-textarea
            label="Description"
            outlined
            style="height: 200px"
            v-model="description"
          ></v-textarea>
        </v-card-text>

        <v-card-actions class="d-flex flex-wrap" style="margin: 0; padding: 0;">
          <v-btn
            class="px-2 py-2"
            @click="selected = 'Journal'; showDateOption = false"
            :class="{ selected: selected === 'Journal' }"
          >
      
            Journal
          </v-btn>
          <v-btn
            class="px-2 py-2"
            @click="selected = 'Gratitude'; showDateOption = false"
            :class="{ selected: selected === 'Gratitude' }"
          >
            Gratitude
          </v-btn>
          <v-btn
            class="px-2 py-2"
            @click="selected = 'Meditation'; showDateOption = false"
            :class="{ selected: selected === 'Meditation' }"
          >
            Meditation
          </v-btn>
          <v-btn
            class="px-2 py-2"
            @click="selected = 'Story'; showDateOption = false"
            :class="{ selected: selected === 'Story' }"
          >
            Story
          </v-btn>
          <v-btn
            class="px-2 py-2"
            @click="selected = 'Business idea'; showDateOption = false"
            :class="{ selected: selected === 'Business idea' }"
          >
            Business idea
          </v-btn>
          <v-btn
            class="px-2 py-2"
            @click="selected = 'Quote'; showDateOption = false"
            :class="{ selected: selected === 'Quote' }"
          >
            Quote
          </v-btn>
          <v-btn
            class="px-2 py-2"
            @click="selected = 'Movie'; showDateOption = false"
            :class="{ selected: selected === 'Movie' }"
          >
           Movie
          </v-btn>
          <v-btn
            class="px-2 py-2"
            @click="selected = 'Idea'; showDateOption = false"
            :class="{ selected: selected === 'Idea' }"
          >
            Idea
          </v-btn>
      </v-card-actions>

      <template v-if="showDateOption">
      <TaskToggle v-bind:value="dateOption" @update:value="dateOption = $event" />
      </template>

      <v-card-actions style="justify-content: flex-end;">
      <v-btn color="primary" @click="saveEntry">Save</v-btn>

      </v-card-actions>
    </v-card>
    </v-dialog>

  </div>
</template>


<script>
import TaskToggle from './TaskToggle.vue'
import ServerService from '../services/ServerService'
// import { VButton, VTextField, VRadioGroup, VRadio, VSelect, VSelectList, VSelectItem, VCardActions, VCardText, VCard, VDialog, VCardTitle, VIcon, VRow, VCol } from 'vuetify/lib';

export default {
  name: 'right-buttons',
  components: {
    TaskToggle,
  },
  data() {
    return {
      openPopup: false,
      showTask: false,
      showWorkout: false, 
      showPopup: false,
      selected: '',
      showDateOption: false,
      dateOption: '',
      description: '',
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
        userid: 3, //change later
        taskisrecurring: this.isrecurring

      };
      console.log('AFTER object')
      console.log(task)
      ServerService.addTask(task).then(response => {
        // handle success
        console.log('TASK ADDED')
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
          
          let type = 'Workout'
          let content;

          if (this.selectedWorkout === 'Cardio') {
            content = `Cardio: Type: ${this.selectedCardioType} Duration: ${this.duration} minutes`;
          } else if (this.selectedWorkout === 'Bench') {
            content = `Bench: Weight: ${this.benchWeight} lbs Reps: ${this.benchReps}`;
          } else if (this.selectedWorkout === 'Abs') {
            content = `Abs: ${this.selectedAbs}`;
          } else if (this.selectedWorkout === 'Pullups') {
            content = `Pullups: ${this.pullupsReps} reps`;
          } else if (this.selectedWorkout === 'Pushups') {
            content = `Pushups: ${this.pushupsReps} reps`;
          } else if (this.selectedWorkout === 'Squats') {
            content = `Squats: ${this.squats} reps`;
          }

          const currentDate = new Date();
          const options = { timeZone: 'America/New_York' };
          const estDate = currentDate.toLocaleDateString('en-US', options);
          const estTime = currentDate.toLocaleTimeString('en-US', options);
          const date = `${estDate} ${estTime}`;

          const report = { content, type, date };
          ServerService.createReport(report).then(response => {
            console.log('success');
            console.log(report)
            }).catch(error => {
              console.log('failed');
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
      
        

      const content = this.description;
      const type = this.selected;
      const currentDate = new Date();
      const options = { timeZone: 'America/New_York' };
      const estDate = currentDate.toLocaleDateString('en-US', options);
      const estTime = currentDate.toLocaleTimeString('en-US', options);
      const date = `${estDate} ${estTime}`;

      const report = { content, type, date };
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