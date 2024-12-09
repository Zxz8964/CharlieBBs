<template>
  <v-tooltip bottom open-delay='400'>
    <span>{{ label || 'Icon' }}</span>
    <template v-slot:activator="{ on, attrs }">
      <v-icon v-bind="attrs" v-on="on" :large="display.smAndUp">{{ icon }}</v-icon>
    </template>
  </v-tooltip>
</template>

<script>
import { useDisplay } from 'vuetify';

export default {
  props: [
    'label',
    'icon'
  ],
  setup() {
    const display = useDisplay();

    return {
      display
    };
  }
}
</script>
