<template>
  <div class="nav-container">
    <div class="logo-container">
      <logo-image />
    </div>
    <list-all-project-report />
  </div>
</template>

<script>
import ListAllProjectReport from '../components/Reports/ListAllProjectReport.vue';
import LogoImage from '../components/LogoImage.vue';

export default {
    components: {
      LogoImage,
      ListAllProjectReport
    },
  }
</script>

<style>
  .nav-container {
    padding: 0 6% 6% 6%;
    display: flex;
    flex-direction: column;
    min-height: 100vh;
  }

  .logo-container {
    padding: 0 6% 6% 6%;
  }

  .logo-container {
    display: flex;
    justify-self: stretch;
    justify-content: space-around;
    flex-flow: row wrap;
    align-items: stretch;
    padding: 1em;
  }
</style>