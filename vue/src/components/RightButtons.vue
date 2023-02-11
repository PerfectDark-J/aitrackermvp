<template>
  <div class="right-component">
    <div class="floating-buttons">
      <div class="top-button">
        <font-awesome-icon :icon="['fas', 'comment']" />
      </div>
      <div class="middle-button">
        <font-awesome-icon :icon="['fas', 'pen']" />
      </div>
      <div class="bottom-button">
        <font-awesome-icon :icon="['fas', 'plus']" />
      </div>
    </div>
  </div>
</template>


<script>
export default {
  name: 'right-buttons',
  created() {
    console.log('Right buttons component created');
  }
};
</script>



<style scoped>
  .right-component {
    display: flex;
    align-items: flex-end;
    padding-top: 50%;
  }

  .floating-buttons {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
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
    font-size: 30px;
    color: #ffffff !important; 
  }
</style>


