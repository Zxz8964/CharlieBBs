<template>
  <v-container>
    <v-row>
      <v-col
        cols="12"
        :md="8"
      >
        <CreatePostHeader v-if="isAuthenticated" />
        <PostFilter
          :sort="sort"
          @selectSort="selectSort"
        />
        <PostList
          :posts="posts"
          :show-community="true"
          @vote="vote"
        />
      </v-col>
      <v-col
        v-if="showSidebar"
        :md="4"
      >
        <TopGrowing :communities="communities" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { ref, computed, onMounted } from 'vue';
import { useDisplay } from 'vuetify';
import TopGrowing from '@/components/Communities/Top.vue';
import CreatePostHeader from '@/components/Posts/CreatePostHeader.vue';
import PostFilter from '@/components/Posts/PostFilter.vue';
import PostList from '@/components/Posts/PostList.vue';
import communities from '@/assets/json/communities.json';
import axios from 'axios';
import { calculateVote } from '@/utils.js';
import store from '@/store'; // 确保从 store 导入

export default {
  name: 'Home',
  components: {
    CreatePostHeader,
    PostFilter,
    PostList,
    TopGrowing
  },
  setup() {
    const display = useDisplay();

    const sort = ref('Best');
    const posts = ref([null, null, null, null, null]);
    const isAuthenticated = computed(() => store.state.isAuthenticated);
    const communitiesList = ref(communities);

    const showSidebar = computed(() => display.mdAndUp.value);

    const selectSort = (sortType) => {
      sort.value = sortType;
      getPosts();
    };

    const vote = (data) => {
      calculateVote(posts.value.find(p => p._id === data.postId), data.type);
      axios.post(`/api/posts/${data.type}`, {
        postId: data.postId
      });
    };

    const getPosts = () => {
      posts.value = [null, null, null, null, null];
      axios.get('/api/posts', {
        params: {
          sort: sort.value
        }
      }).then(res => {
        if (res.data.success) {
          posts.value = res.data.posts.map(e => {
            const community = communitiesList.value.find(c => c.id === e.communityId);

            return {
              ...e,
              communityName: community.name
            };
          });
        }
      });
    };

    onMounted(() => {
      getPosts();
    });

    return {
      sort,
      posts,
      isAuthenticated,
      communitiesList,
      showSidebar,
      selectSort,
      vote
    };
  }
};
</script>
