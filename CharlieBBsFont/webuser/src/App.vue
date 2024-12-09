<template>
  <v-app light color="blue">
    <Header />
    <v-main>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
import Header from '@/components/Layout/Header.vue'

export default {
  name: 'App',

  components: {
    Header
  }
}
</script>
