<template>
  <div class="account-right-container">
    <Calendar />
    <div class="right-bottom-container">
      <MonthScore />
      <MonthFixedList />
    </div>
  </div>
</template>
<script>
import Calendar from '../rightView/AccountCalendar.vue'
import MonthScore from '../rightView/MonthScore.vue'
import MonthFixedList from '../rightView/MonthFixedList.vue'
export default {
  components: {
    Calendar,
    MonthScore,
    MonthFixedList,
  },
  data() {
    return {
      year: null,
      month: null,
    }
  },
  mounted() {},
}
</script>
<style lang="scss" scoped>
.account-right-container {
  display: flex;
  flex-direction: column;
}
.right-bottom-container {
  display: flex;
  flex-direction: row;
}
</style>
