<template>
  <div class="nav-container">
    <v-card>
      <table>
        <thead>
          <tr>
            <th>Tasks</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="task in topFiveTasks"
            v-bind:key="task.id"
            v-bind:class="{ disabled: task.isCompleted === false }"
          >
            <td>{{ task.tasktitle }}</td>
            <td>
              <button
                v-on:click="flipStatus(task.id)"
                class="btnEComplete"
                v-if="!task.tasksscompleted"
              >
                Complete
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </v-card>
    <div style="overflow: auto; height: calc(100% - 300px);">
      <table>
        <tbody>
          <tr
            v-for="task in restOfTasks"
            v-bind:key="task.id"
            v-bind:class="{ disabled: task.isCompleted === false }"
          >
            <td>{{ task.tasktitle }}</td>
            <td>
              <button
                v-on:click="flipStatus(task.id)"
                class="btnEComplete"
                v-if="!task.tasksscompleted"
              >
                Complete
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <!-- <add-task/> -->
  </div>
</template>


<script>
import TaskTile from '../components/Dashboard/TaskTile.vue';
import { useAuth0 } from '@auth0/auth0-vue';
import ServerService from '../services/ServerService.js';
import AddTask from '../components/AddTask.vue'


export default {
  name: "home-view",
  props: ['taskList'],
  data() {
    return {
      filter: {
        taskIsCompleted: false,
        DueDate:''
      },
      timeFilter: null,
      displayLimit: 5,
    }
  },    
  methods: {
    flipStatus(id) {
    this.taskList.forEach((task) => {
        if (task.id == id) {
            if (!task.tasksscompleted) {
                task.tasksscompleted = true;
                task.taskcompletiondate = new Date().toLocaleDateString();
                ServerService.updateTask(task)
                    .then(() => {
                        location.reload();
                    });
            }
        }
    });
},

    },  
  computed: {
  topFiveTasks() {
  return this.taskList.filter(task => !task.tasksscompleted).slice(0, 5);
},

  restOfTasks() {
    return this.taskList.slice(5);
  }
},
  components: {
    TaskTile,
    AddTask
  },
};
</script>

<style>
  table {
width: 120%; /* Increase this value */
font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
background-color: white;
margin-top: 0px;
}


.nav-container {
height: 120%; /* Increase this value */
}

/* To increase the font size of the text inside the table, you can increase the font-size property of the "td" and "th" selectors: */

th {
text-transform: uppercase;
font-size: 16px; 
padding: 0px;
}
td {
padding: 0px;
font-size: 14px; 
}
  .btnEComplete {
  margin-right: 5px;
  background-color: #335974;
  color: white;
  border-color: black;
  border-radius: 5px;
  padding: 5px;;
  }
</style>