<template>
  <div class="about">
    <!-- <h1>This is an about page</h1> -->
    <ButtonA />
  </div>
</template>
<script>
import ButtonA from '@/components/home/ButtonA.vue'
export default {
  components: {
    ButtonA,
  },
  data() {
    return {}
  },
  methods: {},
  mounted() {},
}
</script>
<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
