<template>
  <div class="about">
  <ButtonSearch />
  </div>
</template>
<script>
import ButtonSearch from '@/components/home/ScheduleSearch.vue'
export default {
  components: {
    ButtonSearch,
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
