<template>
  <div>
    <div id="app">
      <div class="container flex-grow-1">
        <error />
        <div class="mt-5"> 
          <router-view />
          <footer-nav-bar />
        </div>   
      </div>    
    </div>
  </div>
  
</template>

<script lang="ts">
import { useAuth0 } from '@auth0/auth0-vue';
import FooterNavBar from './components/FooterNavBar.vue';
import { useRouter } from 'vue-router';

export default {
  components: {
    FooterNavBar
  },
  setup() {
    const auth0 = useAuth0();
    const router = useRouter();
    
    if (auth0.isAuthenticated) {
      //change to push to dash
      router.push('/dash');
    } 
  }
};
</script>

<style>
body, html {
  height: 100%;
  overflow: hidden;
  margin: 0;
}

#app {
  height: 100vh;
  overflow: auto;
  background-color: #2f719d;
}

.mt-5 {
  margin-top: 0%;
  /* height: 100vh; */
} 
</style>