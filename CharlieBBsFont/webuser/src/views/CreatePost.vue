<template>
  <v-container>
    <v-row>
      <v-col cols="12" :md="8">
        <CreatePost :communities="communities" />
      </v-col>
      <v-col :md="4" v-if="display.mdAndUp">
        <div>
          <PostingRules v-if="!community" />
          <CommunityInfo v-if="community" :community="community" />
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { defineComponent } from 'vue';
import { useDisplay } from 'vuetify'; 
import CreatePost from '@/components/Posts/CreatePost.vue';
import PostingRules from '@/components/Posts/PostingRules.vue';
import communities from '@/assets/json/communities.json';
import CommunityInfo from '@/components/Communities/Info.vue';

export default defineComponent({
  components: {
    CreatePost,
    PostingRules,
    CommunityInfo
  },
  setup() {
    const display = useDisplay(); // 使用 useDisplay

    return {
      display
    };
  },
  data() {
    return {
      communities
    };
  },
  computed: {
    community() {
      if (!this.$route.params.community) return null;
      return communities.find(e => e.name === this.$route.params.community);
    }
  }
});
</script>

