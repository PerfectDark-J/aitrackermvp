<template>
  <div class="radio-buttons-container">
    <div class="radio-button-column">
      <v-radio-group v-model="localValue" class="primary">
        <v-radio label="Today" @click="emitUpdate('Today')"></v-radio>
      </v-radio-group>
    </div>
    <div class="radio-button-column">
      <v-radio-group v-model="localValue" class="primary">
        <v-radio label="Recurring" @click="emitUpdate('Recurring')"></v-radio>
      </v-radio-group>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TaskToggle',
  props: ['value'],
  data() {
    return {
      localValue: this.value
    }
  },
  methods: {
    emitUpdate(value) {
      this.$emit('update:value', value);
      console.log(value)
    }
  }
}
</script>
<style>
  .radio-buttons-container {
    display: flex;
    flex-direction: row;
    justify-content: center;
  }

  .radio-button-column {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .custom-radio .v-input--selection-controls {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 1.5em;
    height: 1.5em;
    border-radius: 50%;
    border: 2px solid var(--v-input-border-color);
    margin-right: 0.5em;
  }

  .custom-radio .v-input--selection-controls input[type="checkbox"],
  .custom-radio .v-input--selection-controls input[type="radio"] {
    opacity: 0;
  }

  .custom-radio .v-input--selection-controls .v-icon {
    font-size: 1.25em;
    color: var(--v-input-icon-color);
  }

  .custom-radio.v-input--is-active .v-input--selection-controls {
    border-color: var(--v-input-active-color);
  }

  .custom-radio.v-input--is-active .v-input--selection-controls .v-icon {
    color: var(--v-input-active-color);
  }

  .v-input--selection-controls {
  display: flex;
  align-items: center;
}

.v-input--selection-controls .v-icon {
  font-size: 20px;
}

.v-radio + .v-radio {
  margin-left: 1rem;
}

</style>
