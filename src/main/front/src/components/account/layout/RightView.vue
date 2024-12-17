<template>
  <div class="account-right-container">
    <div class="right-top-container">
      <div class="account-fixed-wrap">
        <MonthScore />
      </div>
      <div class="account-fixed-wrap">
        <MonthFixedList />
      </div>
    </div>
    <Calendar />
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
  gap: 20px 0;
}
.right-top-container {
  display: flex;
  gap: 0 20px;
}
</style>
