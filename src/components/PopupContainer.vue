<template>
  <v-dialog :value="openPopup" max-height="90%">
    <template v-slot:activator="{ on }">
      <div v-on="on"></div>
    </template>
    <v-card style="height: 80vh;">
      <v-card-title>
        Add Entry
      </v-card-title>
      <v-card-text>
        <v-textarea
          label="Description"
          outlined
          style="height: 200px"
          v-model="description"
        ></v-textarea>
      </v-card-text>
      <!-- Other content for the popup goes here -->

      <v-card-actions style="justify-content: flex-end;">
        <v-btn color="primary" @click="updateOpenPopup(false)">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState, mapActions } from 'vuex';

export default {
  name: 'PopupContainer',
  computed: {
    ...mapState(['openPopup']),
  },
  methods: {
    ...mapActions(['updateOpenPopup']),
  },
};
</script>

