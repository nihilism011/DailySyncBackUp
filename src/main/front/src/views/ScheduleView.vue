<template>
  <div class="left left-container">
    <div class="left-top">
      <DateSelector v-model="selectedDate" />
    </div>
    <div class="left-bottom">
    <ScheduleList/>
    </div>
  </div>
  <div class="right right-container">
    <ScheduleSearch/>
    <Calendar :dailyLsit="dailyLsit" @fnMealList="fnMealList" @fnDayList="fnDayList" />
  </div> 
</template>
<script>
import Calendar from '@/components/meal/Calendar.vue'
// import ScheduleList from '@/components/schedule/ScheduleList.vue'
// import ScheduleSearch from '@/components/schedule/ScheduleSearch.vue'
import DateSelector from '@/components/common/DateSelector.vue'
export default {
  components: {
    DateSelector,
    // ScheduleList,
    // ScheduleSearch,
    Calendar,
  },
  data() {
    return {
      selectedDate : ''
    };
  },
  
  methods: {
    async fnRequest() {
      console.log(this.selectedDate);
    },
  },
  mounted() {
    this.selectedDate = this.$dayjs().format('YYYY-MM-DD')
    this.fnRequest();
  },
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
