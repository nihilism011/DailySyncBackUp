<template>
  <h1>{{ user }}</h1>
  <button @click="fnClick">클릭</button>
</template>
<script>
export default {
  data() {
    return {
      user: 'asdf',
    }
  },
  methods: {
    fnClick() {
      console.log('hi')
      this.user = 'hi'
    },
  },
  mounted() {},
}
</script>
<style lang="scss" scoped></style>
