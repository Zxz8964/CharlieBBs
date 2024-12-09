<template>
  <div>
    <NotFound v-if="!community" resource="community" />
    <div v-if="community">
      <CommunityHeader :border="true" :community="community" />
      <v-container>
        <v-row>
          <v-col cols="12" :md="8">
            <CreatePostHeader v-if="isAuthenticated" :community="community.name" />
            <PostFilter :sort="sort" @selectSort="selectSort" />
            <PostList v-if="posts" :posts="posts" @vote="vote" />
            <!-- <Spinner v-if='!posts' /> -->
          </v-col>
          <v-col :md="4" v-if="isMdAndUp">
            <CommunityInfo :community="community" />
          </v-col>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script>
import CommunityInfo from '@/components/Communities/Info.vue';
import CommunityHeader from '@/components/Communities/Header.vue';
import CreatePostHeader from '@/components/Posts/CreatePostHeader.vue';
import PostFilter from '@/components/Posts/PostFilter.vue';
import PostList from '@/components/Posts/PostList.vue';
import communitiesData from '@/assets/json/communities.json';
import axios from 'axios';
import { calculateVote } from '@/utils.js';
import NotFound from '@/components/Core/NotFound.vue';
import { useDisplay } from 'vuetify';
import { useRoute } from 'vue-router';
import { defineComponent, ref, computed, onMounted, watch } from 'vue';
import { useStore } from 'vuex';

export default defineComponent({
  name: 'Community',
  components: {
    CreatePostHeader,
    PostFilter,
    PostList,
    CommunityInfo,
    CommunityHeader,
    NotFound,
  },
  setup() {
    const display = useDisplay();
    const route = useRoute();
    const store = useStore(); // 确保使用 useStore() 获取 Vuex store
    const communities = ref(communitiesData);
    const sort = ref('Best');
    const posts = ref([null, null, null, null, null]);
    const isAuthenticated = computed(() => store.state.isAuthenticated); // 通过 store.state 访问状态
    const community = computed(() => communities.value.find(e => e.name === route.params.community));

    const isMdAndUp = computed(() => display.mdAndUp?.value);

    const getPosts = () => {
      posts.value = [null, null, null, null, null];
      axios
        .get('/api/posts', {
          params: {
            communityId: community.value.id,
            sort: sort.value,
          },
        })
        .then(res => {
          if (res.data.success) {
            posts.value = res.data.posts.map(e => {
              const communityData = communities.value.find(c => c.id === e.communityId);
              return {
                ...e,
                communityName: communityData ? communityData.name : '',
              };
            });
          }
        });
    };

    const selectSort = (sortOption) => {
      sort.value = sortOption;
      getPosts();
    };

    const vote = (data) => {
      calculateVote(posts.value.find(p => p._id === data.postId), data.type);
      axios.post(`/api/posts/${data.type}`, { postId: data.postId });
    };

    watch(community, () => {
      if (community.value) {
        getPosts();
      }
    });

    onMounted(() => {
      if (community.value) {
        getPosts();
      }
    });

    return {
      communities,
      sort,
      posts,
      isAuthenticated,
      community,
      isMdAndUp,
      getPosts,
      selectSort,
      vote,
    };
  },
});
</script>
