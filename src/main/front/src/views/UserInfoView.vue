<template>
  <div class="left left-container">
    <UserInfoSet />
  </div>
  <div class="right right-container"></div>
</template>
<script>
import UserInfoSet from '@/components/user/UserInfoSet.vue'
export default {
  components: {
    UserInfoSet,
  },
}
</script>
<style lang="scss" scoped>
.left-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.left-record {
  display: flex;
  input {
    width: 100px;
  }
}
.record-title {
  width: 150px;
}
.title {
  margin-bottom: 20px;
}
</style>
