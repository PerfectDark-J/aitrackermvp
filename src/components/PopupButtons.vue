<template>
  <v-card-actions class="d-flex flex-wrap" style="margin: 0; padding: 0;">
          
    <v-btn
      class="px-2 py-2"
      @click="selected = 'Journal'; showDateOption = false"
      :class="{ selected: selected === 'Journal' }"
    >
      Journal
    </v-btn>
    <v-btn
      class="px-2 py-2"
      @click="selected = 'Gratitude'; showDateOption = false"
      :class="{ selected: selected === 'Gratitude' }"
    >
      Gratitude
    </v-btn>
    <v-btn
      class="px-2 py-2"
      @click="selected = 'Meditation'; showDateOption = false"
      :class="{ selected: selected === 'Meditation' }"
    >
      Meditation
    </v-btn>
    <v-btn
      class="px-2 py-2"
      @click="selected = 'Story'; showDateOption = false"
      :class="{ selected: selected === 'Story' }"
    >
      Story
    </v-btn>
    <v-btn
      class="px-2 py-2"
      @click="selected = 'Business idea'; showDateOption = false"
      :class="{ selected: selected === 'Business idea' }"
    >
      Business idea
    </v-btn>
    <v-btn
      class="px-2 py-2"
      @click="selected = 'Thought'; showDateOption = false"
      :class="{ selected: selected === 'Thought' }"
    >
      Thought
    </v-btn>
    <v-btn
      class="px-2 py-2"
      @click="selected = 'Movie'; showDateOption = false"
      :class="{ selected: selected === 'Movie' }"
    >
      Movie
    </v-btn>
  </v-card-actions>
  <v-card-actions style="justify-content: flex-end;">
    <v-btn color="primary" @click="saveEntry">Save</v-btn>
  </v-card-actions>
</template>

<script>
export default {
  name: 'popupButtons',
  props: ['selected', 'showDateOption'],
  data() {
    return {
      description: ''
    }
  },
  methods: {
    saveEntry() {
      console.log('Saving entry with type: ', this.selected);
      console.log('Saving entry with date option: ', this.dateOption);
      
      const content = this.description;
      const type = this.selected;
      const currentDate = new Date();
      const options = { timeZone: 'America/New_York' };
      const estDate = currentDate.toLocaleDateString('en-US', options);
      const estTime = currentDate.toLocaleTimeString('en-US', options);
      const date = `${estDate} ${estTime}`;

      const report = { content, type, date };
      ServerService.createReport(report).then(response => {
        // handle success
        
    console.log('success')

      }).catch(error => {
        console.log('failed')
        // handle error
      });
    this.successMessage = "Saved Successfully!";
      setTimeout(() => {
        this.successMessage = '';
      }, 2000);
    this.openPopup = false;
             location.reload();
    }
  },
  created() {
    console.log('Right buttons component created');
  },
  computed: {
    
  }
};
