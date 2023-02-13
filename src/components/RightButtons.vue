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
    <v-dialog v-model="showPopup" max-width="450">
    <template v-slot:activator="{ on }">
      <div v-on="on"></div>
    </template>
    <v-card style="height: 80vh;">
      <v-card-title>
        What would you like to track?
      </v-card-title>
      <v-card-text>
        <v-row>
          <v-col cols="6" v-for="(item, index) in items" :key="index">
            {{ item }}
          </v-col>
        </v-row>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" @click="showPopup = false">Save</v-btn>
        <v-btn color="secondary" @click="showPopup = false">Cancel</v-btn>
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
    // VButton,
    // VTextField,
    // VRadioGroup,
    // VRadio,
    // VSelect,
    // VSelectList,
    // VSelectItem,
    // VCardActions,
    // VCardText,
    // VCard,
    // VDialog,
    // VCardTitle,
    // VIcon,
    // VRow,
    // VCol,
  },
  data() {
    return {
      openPopup: false,
      showPopup: false,
      selected: '',
      showDateOption: false,
      dateOption: '',
      description: '',
      items: ['Water', 'Poop', 'Sex', 'Morning Vitamins', 'Nightly Vitamins', 'Coffee', 'Alcohol', 'Pot', 'Porn', 'Gaming'],
      counts: Array(10).fill(0)
    }
  },
  methods: {
    incrementCount(index) {
      this.counts[index] = this.counts[index] > 0 ? this.counts[index] - 1 : this.counts[index] + 1;
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
        // handle success
        if (type === 'Task') {
          const taskisrecurring = this.dateOption === 'Today' ? false : true;
          console.log(task)
          const tasktitle = 'test';
          const taskdescription = 'test';
          const taskiscompleted = false;
          const taskcompletiondate = '02/12/2023'
          const userid = 1;
          console.log(task);      
          const task = { tasktitle, taskdescription, taskiscompleted, taskcompletiondate, userid}; // Create task object with necessary information
        ServerService.addTask(task).then(response => {
        // Handle success
  
        this.openPopup = false;
        location.reload();
        console.log(task)
      }).catch(error => {
        // Handle error
      });
    }
    console.log('success')

      }).catch(error => {
        console.log('failed')
        // handle error
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

  item {
  display: flex;
  align-items: center;
  font-size: 12px;
  padding: 8px 0;
}

.icon {
  margin-left: 4px;
}

</style>