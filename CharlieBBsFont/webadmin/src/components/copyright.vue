<template>
  <el-row justify="center" class="copyright">
    <el-col>
      <span>Copyright © {{ year }} charlie-bbs</span>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: 'copyRight',
  computed: {
    year() {
      return new Date().getFullYear();
    }
  }
}
</script>

<style lang="scss" scoped>
.copyright {
  text-align: center;
}
</style>
