<template>
  <div class="left left-container">
    <div class="left-top">
      <DateSelector v-model="selectedDate" />
    </div>
    <div class="left-bottom">
      {{ selectedDate }}
    </div>
  </div>
  <div class="right">우측메뉴</div>
</template>
<script>
import DateSelector from '@/components/common/DateSelector.vue'
export default {
  components: {
    DateSelector,
  },
  data() {
    return {
      selectedDate: this.$dayjs().format('YYYY-MM-DD'),
    }
  },
  methods: {},
  mounted() {},
}
</script>
<style scoped>
.left-container {
  display: flex;
  flex-direction: column;
  .left-top {
    align-content: center;
    justify-items: center;
    height: 50%;
    padding: 20px;
  }
  .left-bottom {
    height: 50%;
    padding: 20px;
  }
}
</style>
