<template>
  <v-container>
    <v-row>
      <v-col>
        <h3>Create a post</h3>
        <v-divider />
        <v-select
          :items="communities"
          item-text="name"
          item-value="id"
          label="Choose a community"
          outlined
          dense
          color="grey"
          v-model="communityId"
          class="mb-4"
        ></v-select>
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        <v-tabs fixed-tabs background-color="indigo" dark v-model="tab">
          <v-tab v-for="(item, index) in items" :key="index">
            {{ item }}
          </v-tab>
        </v-tabs>
        <v-card class="tabs-items-wrapper mt-4">
          <v-tabs-items v-model="tab">
            <v-tab-item v-for="(item, index) in items" :key="index">
              <div v-if="index === tab">
                <v-text-field
                  outlined
                  dense
                  counter="300"
                  placeholder="Title"
                  v-model="title"
                  class="mb-4"
                ></v-text-field>

                <TextField v-if="index === 0" :value="text" @onChange="textOnChange" :dense="true" placeholder="Text (optional)" :area="true" />
                <DropZone v-if="index === 1" @addImage="addImage" />
                <TextField v-if="index === 2" :value="link" @onChange="linkOnChange" :dense="true" placeholder="Url" :area="false" />

                <v-card-text class="error-text" v-if="error">{{ error }}</v-card-text>

                <v-card-actions class="action-buttons">
                  <v-btn width="100" @click="back()">
                    Cancel
                  </v-btn>
                  <v-btn color="primary" width="80" @click="submit" :disabled="disabled">
                    Post
                  </v-btn>
                </v-card-actions>
              </div>
            </v-tab-item>
          </v-tabs-items>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>


<script>
import { VTabs, VTab} from 'vuetify/components';
import { defineComponent, computed, ref, watch } from 'vue';
import { useDisplay } from 'vuetify';
import TextField from '@/components/Core/TextField.vue';
import DropZone from '@/components/Core/DropZone.vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';

export default defineComponent({
  props: ['communities'],
  components: {
    TextField,
    DropZone,
    VTabs,
    VTab
  },
  setup(props) {
    const display = useDisplay();
    const route = useRoute();
    const router = useRouter();

    const communityId = computed(() => {
      return route.params.community ? props.communities.find(e => e.name === route.params.community).id : null;
    });

    const tab = ref(0);
    const items = ['Post', 'Image & Video', 'Link'];
    const title = ref('');
    const text = ref('');
    const image = ref(null);
    const link = ref('');
    const error = ref(null);

    const disabled = computed(() => {
      if (tab.value === 0) {
        return !communityId.value || !title.value;
      }
      if (tab.value === 1) {
        return !communityId.value || !title.value || !image.value;
      }
      if (tab.value === 2) {
        return !communityId.value || !title.value || !link.value;
      }
      return false;
    });

    function back() {
      if (window.history.length > 2) {
        router.back();
      } else {
        router.push('/');
      }
    }

    function submit() {
      let route, fields;
      if (tab.value === 0) {
        route = 'text';
        fields = { text: text.value };
      } else if (tab.value === 1) {
        route = 'image';
        fields = { image: image.value };
      } else if (tab.value === 2) {
        route = 'link';
        fields = { link: link.value };
      }

      axios.post(`/api/posts/${route}`, {
        title: title.value,
        communityId: communityId.value,
        ...fields
      })
      .then(res => {
        if (res.data.success) {
          window.location.href = `/post/${res.data.id}`;
        } else {
          error.value = res.data.message;
        }
      });
    }

    function textOnChange(e) {
      text.value = e;
    }

    function addImage(imageFile) {
      const reader = new FileReader();
      reader.readAsDataURL(imageFile);
      reader.onload = (event) => {
        image.value = event.target.result;
      };
    }

    function linkOnChange(e) {
      link.value = e;
    }

    watch(communityId, (id) => {
      const name = props.communities.find(e => e.id === id)?.name;
      if (name) {
        router.push(`/posts/create/${name}`);
      }
    });

    return {
      communityId,
      tab,
      items,
      title,
      text,
      image,
      link,
      error,
      disabled,
      back,
      submit,
      textOnChange,
      addImage,
      linkOnChange,
      display
    };
  }
});
</script>

<style scoped lang="scss">
.v-tabs {
  border-radius: 3px;
}
.v-select {
  max-width: 300px;
}
.create-post-title {
  margin-bottom: 10px;
}
h3 {
  margin-bottom: 10px;
}
.action-buttons {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
  button {
    margin-left: 15px !important;
  }
}
.error-text {
  color: red !important;
  padding-left: 0;
}
.v-card {
  padding: 16px;
  margin-top: 20px;
}
</style>
