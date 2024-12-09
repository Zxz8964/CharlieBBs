<template>
  <div class="post-list-container">
    <div v-for="(post, i) in posts" :key="post ? post.id : i">
      <router-link v-if="post" :to="postUrl(post.communityId, post._id)">
        <v-card>
          <PostPreview
            :post="post"
            :show-community="showCommunity"
            v-on="$attrs" 
            :postUrl="postUrl(post.communityId, post._id)"
          />
        </v-card>
      </router-link>
      <ContentLoader v-if="!post" />
    </div>
  </div>
</template>

<script>
import { defineComponent, toRefs } from 'vue';
import PostPreview from '@/components/Posts/PostPreview.vue';
import ContentLoader from '@/components/Layout/ContentLoader.vue';
import communitiesJSON from '@/assets/json/communities.json';

export default defineComponent({
  name: 'PostList',
  components: {
    PostPreview,
    ContentLoader
  },
  props: {
    posts: {
      type: Array,
      required: true
    },
    showCommunity: {
      type: Boolean,
      default: false
    }
  },
  setup(props) {
    const { posts, showCommunity } = toRefs(props);

    const postUrl = (communityId, postId) => {
      const community = communitiesJSON.find(c => c.id === communityId);
      return community ? `/r/${community.name}/${postId}` : '#';
    };

    return {
      posts,
      showCommunity,
      postUrl
    };
  }
});
</script>

<style scoped lang="scss">
.post-list-container a {
  text-decoration: none;
}
</style>
