<template class="container">
  <v-row class="text-left">
    <ol v-auto-animate="{ duration: 300 }" style="display: grid; grid-template-columns: 8fr 2fr; grid-gap: 10px; height: 50px;">

      <template  v-for="(task, index) in displayedTasks" :key="index">
        <li  style="display: flex; align-items: center; justify-content: flex-start; padding-left: 25px; height: 50px; background-color: white; border-radius: 10px; cursor: pointer;" @click="showTaskDetails(task)">
          {{ task.tasktitle }}
        </li>
        <div style="display: grid; grid-template-columns: 1fr 1fr; grid-gap: 10px;">
          <v-btn style="height: 50px; width: 75px; background-color: blue; color: white; border: none; border-radius: 5px; font-size: 10px;" @click="confirmCompletion(task.id)">Complete</v-btn>

        </div>
      </template>

      <template>
  <v-dialog v-model="showConfirmationDialog" max-width="290">
    <v-card>
      <v-card-title class="headline">Finish Task</v-card-title>
          <v-card-text>Are you sure you want to complete this task?</v-card-text>
      <v-card-actions style="display: flex; justify-content: center;">
  <v-btn color="green darken-1" text @click="showConfirmationDialog = false">Cancel</v-btn>
  <v-btn color="red darken-1" text @click="completeTask(selectedTaskId)">Complete</v-btn>
</v-card-actions>

    </v-card>
  </v-dialog>
</template>
    </ol>
  </v-row>


          <v-dialog v-model="dialog" max-width="600px">
            <v-card>
              <v-card-title>
                <span class="headline">{{ selectedTask.tasktitle }}</span>
              </v-card-title>
              <v-card-text>
                <p>{{ selectedTask.taskcontent }}</p>
                <p>{{ selectedTask.taskdescription }}</p>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="primary" text @click="dialog = false">Close</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
</template>
<script>
import Service from '../services/ServerService'
import { ref, onMounted } from "vue"
import autoAnimate from "@formkit/auto-animate"

export default {
  
  
  props: {
    taskList: {
      type: Array,
      required: true
    }
  },
  
  data() {
    return {
      dialog: false,
      confirm: false,
      selectedResult: {},
      showConfirmationDialog: false,
      selectedTaskId: null
    }
  },
  computed: {
    displayedTasks() {
      return this.taskList.filter(task => !task.taskisCompleted)
    }
  },
  methods: {
    confirmCompletion(taskId) {
      this.selectedTaskId = taskId;
      this.showConfirmationDialog = true;
      },
    showTaskDetails(task) {
      this.selectedTask = task
      this.dialog = true
    },
    deleteItem(id) {
      // Code to delete item from taskList
    },
    completeTask(id) {
      // Code to flip the status of task
      Service.completeTask(id).then(() => {
        this.taskList = this.taskList.map(task => {
          if (task.id === id) {
            task.taskisCompleted = true
          }
          return task
        })
      })
      this.showConfirmationDialog = false;
      //Service.deleteLog()
    },
    saveChanges() {
      // Code to save changes to the task
    }
  }
}
</script>

<style scoped>
.container {
  background-color: white;
  height: 100vh;
  width: 100vw;
  z-index: 1; /* add this */
}

.v-list-item__content {
  padding-left: 20px;
}

.lightblue-background {
  background-color: lightblue;
}

.background-white {
  background-color: white;
}





</style>
