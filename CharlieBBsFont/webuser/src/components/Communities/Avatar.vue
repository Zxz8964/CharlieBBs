<template>
  <img class="icon" :src="src" />
</template>

<script>
import randomcolor from 'random-color';

// 预加载所有图像
const images = import.meta.glob('@/assets/images/community/*.png', { eager: true });

export default {
  props: [
    'word',
    'large',
    'communityName'
  ],
  data() {
    return {
      src: ''
    };
  },
  created() {
    this.loadImage();
  },
  methods: {
    loadImage() {
      const defaultImage = images['/src/assets/images/community/default.png'];
      const communityImage = images[`/src/assets/images/community/${this.communityName}.png`];
      
      // 确保 communityImage 正确赋值
      this.src = communityImage?.default ?? defaultImage.default;
    }
  },
  computed: {
    letter() {
      return this.word.charAt(0).toUpperCase();
    },
    color() {
      return randomcolor().hexString();
    }
  }
};
</script>

<style lang="scss" scoped>
div {
  display: inline-block;
}
.icon {
  background: rgb(154, 188, 206);
  height: 25px;
  width: 25px;
  text-align: center;
  border-radius: 50%;
  display: inline-block;
  margin-right: 10px;
}
</style>
