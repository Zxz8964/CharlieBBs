<template>
  <v-card>
    <div class="post-sort-container">
      <v-card-actions>
        <div
          :key="item"
          v-for="item in Object.keys(sorts)"
          :class="{ selected: sort === item }"
          @click="selectSort(item)"
        >
          <v-icon dense>{{ sorts[item] }}</v-icon>
          <v-card-text>{{ item }}</v-card-text>
        </div>
      </v-card-actions>
    </div>
  </v-card>
</template>

<script>
export default {
  props: {
    sort: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      sorts: {
        Best: 'keyboard_arrow_up',
        Hot: 'fireplace',
        New: 'fiber_new',
        Top: 'stars'
      }
    };
  },
  methods: {
    selectSort(sort) {
      this.$emit('selectSort', sort);
    }
  }
};
</script>

<style scoped lang="scss">
@import 'vuetify/styles';

.post-sort-container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 3px 0;
  margin-bottom: 20px;
}

.v-card__actions {
  > div {
    margin-right: 15px;
    display: flex;
    padding: 3px 9px 3px 6px;
    border-radius: 10px;
    cursor: pointer;
  }

  .selected {
    background-color: #dcdcdc;
  }
}

i {
  margin-right: 3px;
}

.v-card__text {
  padding: 0;
}

/* 使用常规的媒体查询 */
@media (max-width: 600px) {
  .v-card__actions {
    > div {
      margin-right: 5px;
    }
  }
}
</style>
