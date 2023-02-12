<template>
  <div class="right-component">
    <div class="floating-buttons">
      <div class="top-button">
        <font-awesome-icon :icon="['fas', 'comment']" />
      </div>
      <div class="middle-button" @click="openPopup = true">
        <font-awesome-icon :icon="['fas', 'pen']" />
      </div>
      <div class="bottom-button">
        <font-awesome-icon :icon="['fas', 'plus']" />
      </div>
    </div>
    
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
          ></v-textarea>
        </v-card-text>

        <v-card-actions class="d-flex flex-wrap" style="margin: 0; padding: 0;">
          <v-btn
      class="px-2 py-2"
      @click="selected = 'Task'; showDateOption = true"
      :class="{ selected: selected === 'Task', [dateOptionClass]: true }"
    >
      Task
    </v-btn>
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
            @click="selected = 'Thought'; showDateOption = false"
            :class="{ selected: selected === 'Thought' }"
          >
            Thought
          </v-btn>
          <v-btn
            class="px-2 py-2"
            @click="selected = 'Movie'; showDateOption = false"
            :class="{ selected: selected === 'Movie' }"
          >
           Movie
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

export default {
  name: 'right-buttons',
  components: {
    TaskToggle
  },
  data() {
    return {
      openPopup: false,
      selected: '',
      showDateOption: false,
      dateOption: ''
    }
  },
  methods: {
    saveEntry() {
      console.log('Saving entry with type: ', this.selected);
      console.log('Saving entry with date option: ', this.dateOption);
      const content = 'test';
      const type = this.selected;
      const date = 'today';
      const report = { content, type, date };
      ServerService.createReport(report).then(response => {
        // handle success
        if (type === 'Task') {
          const tasktitle = 'test';
          const taskdescription = 'test';
          const taskiscompleted = false; 
          const taskcompletiondate = '02/12/2023'
          const userid = 1; 
          
          const task = {tasktitle, taskdescription, taskiscompleted, taskcompletiondate, userid}; // Create task object with necessary information
          ServerService.addTask(task).then(response => {
            // Handle success
            this.openPopup = false;
            location.reload();
          }).catch(error => {
            // Handle error
          });
        }
        console.log('success')
      }).catch(error => {
        console.log('failed')
        // handle error
      });
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
    align-items: right;
    position: right;
    z-index: 1;
  }

  .top-button,
  .middle-button,
  .bottom-button {
    background-color: #b53c3c;
    border: 1px solid #ffffff;
    border-radius: 50%;
    width: 75px;
    height: 75px;
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






</style>


