<template>
  <div>
    <div class="projTile-container">
      <router-link v-bind:to="{name: 'project', params: {id: project.id}}" v-for="project in filteredList" v-bind:key="project.id">
          <div class="projTile" 
          > <!-- need to add v-on:click -->
              <h5>{{project.projectTitle}}</h5>
              <p>Due date: {{ new Date(project.projectDueDate).toLocaleDateString() }}</p>

          </div>
      </router-link>
      <!-- <form>
        {{projCard}}<br>
        Proj No.: <input type="text" v-model="projCard.projectID" /><br>
        Proj Title.: <input type="text" v-model="projCard.projectTitle" /><br>
        Due Date: <input type="text" v-model="projCard.projectDueDate" />
      </form> -->
  </div>
  </div>
</template>

<script>
import service from '../../services/ServerService.js'

export default {
  name: "projTile",
  props: ['sort'],
  data() {
    return {
      projCard:[] 
    }
  },
  created () {
    service.getAllProjects().then(
      (response) => {
        this.projCard = response.data;
        console.log(response.data);
      }
    )
  },
  computed:{
    filteredList() {
            let filteredProjects = this.projCard;
            if(this.sort===1){
              filteredProjects = filteredProjects.filter( (project) => 
                 !project.projectIsCompleted

              )
            }
            if(this.sort===2){
              filteredProjects = filteredProjects.filter( (project) => 
                project.projectIsCompleted
              )
            }

            return filteredProjects;
        }
  }
}
</script>

<style lang="css" scoped>

.projTile-container {
  display: flex;
  flex-wrap: nowrap;
  overflow-x: auto;

}

.projTile-container::-webkit-scrollbar {
  display: none;

} 

/* .projTile-container::-webkit-scrollbar {width: 0;} */


.projTile {
  background-color: #335974;
  color: #FFFFFF;
  display: inline-flex;
  flex-wrap: wrap;
  padding: 10px;
  margin: 5px;
  border: 5px;
  border-style: solid;
  border-color: #78B2C6;
  border-radius: 10%;
  flex: 0 0 auto;
  width: 150px;
  height: 150px;

}

#titleText {
  color: #78B2C6;
}

</style>