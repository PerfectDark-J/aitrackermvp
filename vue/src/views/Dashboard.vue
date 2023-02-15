<template>
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
      userId: null
    };
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
  height: 118vh;
  overflow: hidden;
}

.chart-container {
  grid-row: 1 / 2;
  overflow: hidden;
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
</style>

