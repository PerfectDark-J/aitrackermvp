<template class="container">
  <v-row class="text-left">
    <ol
      v-auto-animate="{ duration: 300 }"
      style="
        display: grid;
        grid-template-columns: 8fr 2fr;
        grid-gap: 10px;
        height: 50px;
      "
    >
      <template v-for="(task, index) in displayedTasks" :key="index">
        <li
          style="
            display: flex;
            align-items: center;
            justify-content: flex-start;
            padding-left: 25px;
            height: 50px;
            background-color: white;
            border-radius: 10px;
            cursor: pointer;
          "
          @click="showTaskDetails(task)"
        >
          {{ task.tasktitle }}
        </li>
        <div
          style="display: grid; grid-template-columns: 1fr 1fr; grid-gap: 10px"
        >
          <v-btn
            :style="{
              height: '50px',
              width: '75px',
              backgroundColor: task.isroutine ? 'green' : 'blue',
              color: 'white',
              border: 'none',
              borderRadius: '5px',
              fontSize: '10px',
            }"
            @click="confirmCompletion(task.id)"
          >
            Complete
          </v-btn>
        </div>
      </template>

      <template>
        <v-dialog v-model="showConfirmationDialog" max-width="290">
          <v-card>
            <v-card-title class="headline">Finish Task</v-card-title>
            <v-card-text
              >Are you sure you want to complete this task?</v-card-text
            >
            <v-card-actions style="display: flex; justify-content: center">
              <v-btn
                color="green darken-1"
                text
                @click="showConfirmationDialog = false"
                >Cancel</v-btn
              >
              <v-btn
                color="red darken-1"
                text
                @click="completeTask(selectedTaskId)"
                >Complete</v-btn
              >
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
        <p>Comment:</p>
        <v-text-field v-model="selectedTask.comment"></v-text-field>
        <p v-if="selectedTask.isroutine">Tasks:</p>
        <ul v-if="selectedTask.isroutine" style="list-style: none">
          <li
            v-for="(task, index) in getSubTasks()"
            :key="index"
            @click="selectedTask.subtasks[index] = '<s>' + task + '</s>'"
            v-html="task"
          ></li>
        </ul>
      </v-card-text>
      <v-card-actions class="justify-end">
        <v-btn color="error" text @click="dialog = false">Cancel</v-btn>
        <v-btn color="primary" text @click="saveTaskChanges">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>

  <div
    v-if="showSuccess"
    class="success-message"
    :style="{ animation: growAndFade }"
  >
    {{ successMessage }}
  </div>
</template>
<script>
import Service from "../services/ServerService";
import { ref, onMounted } from "vue";
import autoAnimate from "@formkit/auto-animate";
import ServerService from "../services/ServerService";
import anime from "animejs";

export default {
  props: {
    taskList: {
      type: Array,
      required: true,
    },
  },

  data() {
    return {
      dialog: false,
      confirm: false,
      selectedResult: {},
      selectedTask: {},
      showConfirmationDialog: false,
      selectedTaskId: null,
      showSuccess: false,
      successMessage: "",
      subtasks: [],
    };
  },
  computed: {
    displayedTasks() {
      return this.taskList.filter((task) => !task.taskisCompleted);
    },
  },
  methods: {
    async getSubTasks() {
      try {
        const subTasks = await ServerService.getSubTasks(this.selectedTask.id);
        this.subtasks = subTasks.data;
        console.log(subTasks)
      } catch (error) {
        console.error(error);
      }
    },

    displaySuccess(message) {
      this.successMessage = message;
      this.showSuccess = true;
      anime({
        targets: ".success-message",
        scale: [1, 1.5],
        opacity: [1, 0],
        easing: "easeInQuad",
        duration: 1000,
        complete: () => {
          this.showSuccess = false;
          this.successMessage = "";
        },
      });
    },

    confirmCompletion(taskId) {
      this.selectedTaskId = taskId;
      //console.log('confirmCompletion ' + taskId + ' ' + task)
      //console.log(task)

      this.showConfirmationDialog = true;
      //this.displaySuccess('Completed')
    },

    updateTask() {
      // console.log('updateTask')
      // console.log(this.selectedTask.id)
      // console.log(this.selectedTask.comment)
      // console.log(this.selectedTask)
      // Add your code here to update the task in the database
      ServerService.updateTask(this.selectedTask);

      const log = {
        type: this.selectedTask.taskdescription,
        title: this.selectedTask.tasktitle,
        content: this.selectedTask.comment,
        userid: this.selectedTask.userid,
        date: new Date(),
        bounty: this.bounty,
        earnedpoints: this.earnedpoints,
      };

      ServerService.updateLog(log);
      this.displaySuccess("Updated");
      // For example, you could use axios to send a PUT request to the API endpoint for updating tasks
    },
    showTaskDetails(task) {
      this.selectedTask = task;
      this.dialog = true;
      console.log("showTaskDetails");
      console.log(task);
    },
    deleteItem(id) {
      // Code to delete item from taskList
    },

    completeTask(id) {
      this.taskList = this.taskList.filter((task) => {
        if (task.id === id) {
          task.taskisCompleted = true;
          Service.completeTask(id);

          Service.getLogByTaskId(id).then((response) => {
            //let log;
            const selectedResult = response.data;

            console.log(selectedResult);

            let log = {
              description: selectedResult.description,
              title: selectedResult.title,
              content: selectedResult.content,
              userid: selectedResult.userid,
              date: new Date(),
              bounty: selectedResult.bounty,
              earnedpoints: selectedResult.bounty,
              type: selectedResult.type,
              id: selectedResult.id,
              taskid: selectedResult.taskid,
              exercise: selectedResult.exercise,
              minutes: selectedResult.minutes,
              reps: selectedResult.reps,
              weight: selectedResult.weight,
            };
            //console.log(log)
            ServerService.updateLog(log);
            this.showConfirmationDialog = false;
            this.displaySuccess(`${selectedResult.bounty} Points`);
            setTimeout(() => {
              window.location.reload();
            }, 1000);
          });
        }
      });
    },

    //  .then(() => {
    //    this.taskList = this.taskList.map(task => {
    //      if (task.id === id) {
    //        task.taskiscompleted = true
    //      }
    //      return task
    //    })
    //  })

    //Service.deleteLog()
  },
};
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
