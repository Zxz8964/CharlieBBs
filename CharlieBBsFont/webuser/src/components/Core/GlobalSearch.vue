<template>
  <v-autocomplete
    dense
    label="Search"
    solo
    flat
    hide-details
    hide-no-data
    prepend-inner-icon="search"
    :items="communities"
    item-text="name"
    item-value="name"
    :menu-props="{ maxHeight: 38 * 5, closeOnContentClick: true }"
    @change="select"
  >
    <template v-slot:item="data">
      <div class="menuitem">
        <v-avatar size="20">
          <img :src="src(data.item.name)">
        </v-avatar>
        <v-list-item>
          <v-list-item-title>{{ data.item.name }}</v-list-item-title>
          <v-list-item-subtitle>{{ data.item.members }} members</v-list-item-subtitle>
        </v-list-item>
      </div>
    </template>
  </v-autocomplete>
</template>

<script>
import communities from '@/assets/json/communities.json';
import { VAvatar, VListItem, VListItemTitle, VListItemSubtitle } from 'vuetify/components';

export default {
  components: {
    VAvatar,
    VListItem,
    VListItemTitle,
    VListItemSubtitle
  },
  data() {
    return {
      communities
    };
  },
  computed: {
    communityNames() {
      return communities.map(e => e.name);
    }
  },
  methods: {
    async select(name) {
      this.$router.push(`/r/${name}`).catch(err => {
        // Ignore the error regarding navigating to the page they are already on
        if (err.name !== 'NavigationDuplicated' && !err.message.includes('Avoided redundant navigation to current location')) {
          console.error(err);
        }
      });
      document.querySelector('.v-autocomplete input').blur(); // Unfocus the autocomplete element
    },
    async src(name) {
      let img;
      try {
        img = await import(`@/assets/images/community/${name}.png`);
      } catch (e) {
        img = await import('@/assets/images/community/default.png');
      }
      return img.default;
    }
  }
};
</script>

<style lang="scss" scoped>
  @import 'vuetify/styles';
  .v-autocomplete {
    max-width: 400px;
  }

  .v-list-item__subtitle {
    font-size: 11px !important;
  }

  .menuitem {
    display: flex;
  }

  @media (max-width: 576px) {
    .v-input {
      max-width: 200px;
      position: relative;
      top: 4px;
    }
  }
</style>
