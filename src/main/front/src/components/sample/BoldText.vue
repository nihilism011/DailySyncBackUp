<template>
  <div>
    <div class="bold">value에 넣어준 값 : {{ value }}</div>
    <div>some에 넣어준 값 : {{ some }}</div>
  </div>
</template>
<script>
export default {
  props: {
    value: {
      type: String,
      default: '기본값입니다.',
    },
    some: {
      type: Object,
      default: () => ({}),
    },
  },
}
</script>
<style>
.bold {
  font-weight: bold;
}
</style>
