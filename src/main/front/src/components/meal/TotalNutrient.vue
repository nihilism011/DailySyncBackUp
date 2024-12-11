<template>
  <!-- {{ todayNutrient }} -->
  <div class="nutrient-wrap">
    <span class="nutrient-box" v-for="(val, key) in Object(todayNutrient)">
      {{ key }} : {{ val }} </span
    >&nbsp;
  </div>
</template>
<script>
export default {
  props: {
    todayNutrient: {
      type: Object,
    },
  },
  data() {
    return {}
  },
  methods: {},
  mounted() {},
}
</script>
<style lang="scss" scoped></style>
