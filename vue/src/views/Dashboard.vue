<template>
   <span style="color: white; font-size: smaller;">
        <div style="display: grid; grid-template-columns: repeat(4, 1fr); grid-template-rows: auto auto;">
      <h1 style="grid-column: 1 / 4;">{{ dayOfWeek }}</h1>
      <p style="grid-column: 4 / 5;">{{ formattedDate }} {{ formattedTime }}</p>
      <!-- <p style="grid-column: 4 / 5;">{{ formattedTime }}</p> -->
    </div>
  </span>
  <div class="container">
    <div class="chart-container">
      <!-- Your chart goes here -->
      <chart :filter="filter"/> 
    </div>
    
    <div class="bottom-half">
  
      <div class ="left-side">
      <!-- Your left component goes here -->
      <tasks v-bind:taskList="taskList" />
      </div>
  
      <div class ="right-side">
      <!-- Your right component goes here -->
      <right-buttons :userId="userId" />
      </div>

    </div>
</div>
</template>

<script lang="ts">
import { useAuth0 } from '@auth0/auth0-vue';
import ServerService from '../services/ServerService.js';
import RightButtons from '../components/RightButtons.vue';
import Chart from '../components/Chart.vue'; 
import Tasks from '../views/TaskPage.vue'


export default {
  name: "Dashboard",
  components: {
    Chart,
    Tasks, 
    RightButtons
  },
  
  data() {
    return {
      taskList: [],
      filter: null,
      userId: null,
      dayOfWeek: '',
      currentDate: new Date()
    };
  },
  methods: {
    getDayOfWeek() {
      const daysOfWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
      const today = new Date();
      return daysOfWeek[today.getUTCDay()];
    }
  },
  mounted() {
    this.dayOfWeek = this.getDayOfWeek();
     // Update the time every second
    setInterval(() => {
      this.currentDate = new Date();
    }, 1000);
  },

  computed: {
    formattedDate() {
      return this.currentDate.toLocaleDateString("en-US", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit"
      });
    },
    formattedTime() {
      return this.currentDate.toLocaleTimeString("en-US", {
        hour: "numeric",
        minute: "2-digit",
        hour12: true
      });
    }
  },
  created() {
      const auth0 = useAuth0()

      ServerService.verifyThroughEmail({ email: auth0.user.value.email})
      
      ServerService.getUserByEmail(`${auth0.user.value.email}`)
      .then(response => {
      const { ismanager, isactivated, userid } = response.data;
      if(response.data == null){
        window.location.reload();
      }

      this.userId = userid
      
      console.log(response.data)
      console.log(`isManager: ${ismanager}`);
      console.log(`isActivated: ${isactivated}`);
      console.log(`userId: ${userid}`);
      
      if (!isactivated) {
        console.log("User is not activated, logging out");
        auth0.logout({ logoutParams: {
                returnTo: window.location.origin
                }})
      } 
      
      if (!ismanager) {
          
          //Get Tasks for User
          ServerService.getAllTasksByUserId(`${userid}`).then(response => {
          this.taskList = response.data; 
          console.log(response.data)
          })
          

      } else {
        
          //Get All Tasks
          console.log('Getting ALL Tasks')
          ServerService.getAllTasks().then(response => {
          this.taskList = response.data;
          console.log(response.data);
          
          })
          .catch(error => {
          console.error(error);
          });
      }
      })
      .catch(error => {
      console.error(error);
      });
  },
}
</script>

<style lang="css" scoped>
.container {
  display: grid;
  grid-template-columns: 100%;
  grid-template-rows: 40% 60%;
  height: 100vh;
  overflow: hidden;
  border-radius: 25px;
}

.chart-container {
  grid-row: 1 / 2;
  height: 90%;
  overflow: hidden;
  background-color: #F8F8F8;
  border-radius: 25px;
}


.bottom-half {
  grid-row: 2 / 3;
  display: grid;
  grid-template-columns: 80% 20%;
  overflow: hidden;
  
}

.left-side {
  display: flex;
  flex-direction: column;
  width: 100%;
  padding-top: 10px;
  height: 230px; /* add this */
  overflow: auto;
  border-radius: 10px; /* add this */
  
}



.right-side {
  display: flex;
  align-items: center;
  justify-content: center;
}

h1 {
  margin-left: 15px;
  color: white;
}
</style>

