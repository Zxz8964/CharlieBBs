<template>
  <div class="community-header">
    <div v-if="border" class="border"></div>
    <div class="main">
      <v-container class="container">
        <img class="icon" :src="src" />
        <v-card>
          <v-card-title>{{ community.title }}</v-card-title>
          <v-card-text>r/{{ community.name }}</v-card-text>
        </v-card>
      </v-container>
    </div>
  </div>
</template>

<script>
// 预加载所有图像
const images = import.meta.glob('@/assets/images/community/*.png', { eager: true });

export default {
  props: ['border', 'community'],
  computed: {
    src() {
      const defaultImage = images['/src/assets/images/community/default.png'];
      const communityImage = images[`/src/assets/images/community/${this.community.name}.png`];
      return communityImage?.default ?? defaultImage.default;
    }
  }
};
</script>

<style lang="scss" scoped>
@import 'vuetify/styles';

.v-card {
  box-shadow: none !important;
}
.v-card__title {
  font-size: 24px;
  padding: 0;
  word-break: break-word;
}
.v-card__text {
  padding: 0;
}

.border {
  height: 80px;
  background: #33a8ff;
}
.main {
  background: white;
}
.container {
  min-height: 80px;
  display: flex;
  align-items: center;
  padding-top: 0;
  padding-bottom: 0;
}
.icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 20px;
  flex-shrink: 0;
}
span {
  color: var(--v-info-darken1);
  margin-right: 2px;
  font-weight: 500;
  font-size: 14px;
  cursor: pointer;
}

@media (max-width: 600px) {
  .v-card__title {
    font-size: 18px;
  }
}

@media (max-width: 400px) {
  .v-card__title {
    font-size: 16px;
  }
  .icon {
    width: 50px;
    height: 50px;
  }
}
</style>
