<template>
  <div class="left left-container">
    <div class="left-top">
    </div>
    <div class="left-bottom">
      <ScheduleList />
    </div>
  </div>
  <div class="right right-container">
    <ScheduleSearch/>
    <Calendar :dailyList="dailyList" @fnScheduleList="fnScheduleList" @fnDayList="fnDayList" />
  </div>
</template>
<script>
import Calendar from '@/components/schedule/ScheduleCalendar.vue'
import ScheduleList from '@/components/schedule/ScheduleList.vue'
import ScheduleSearch from '@/components/schedule/ScheduleSearch.vue'
export default {
  components: {
    ScheduleList,
    ScheduleSearch,
    Calendar,
  },
  data() {
    return {
      day : '',
      dailyList: [],
      fullList: [], 
    };
  },

  methods: {
    async fnScheduleList(inputDay) {
      const userId = 6;
      console.log(inputDay);
      let year = inputDay.split('-')[0]
      let month = inputDay.split('-')[1]
      let day = inputDay.split('-')[2]
      const full = await this.$axios.get(`schedule/userId/${userId}/${year}/${month}`)
      if (full.status) {
        this.fullList = full.data.scheduleList
      } else {
        console.log(full.message)
      }
      await this.fnDayClick(year, month, day);  
    },
    async fnDayList(inputDay) {
      console.log(inputDay);
      const userId = 6;
      let year = inputDay.split('-')[0]
      let month = inputDay.split('-')[1]
      const daily = await this.$axios.get(`schedule/userId/${userId}/${year}/${month}`)
      if (daily.status) {
        this.dailyList = daily.data.map(item => ({
          title: item.title,
          start: item.startTime,
          end: item.endTime,
        }));
      } else {
        console.log(daily.message)
      }
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD')
    this.fnScheduleList(this.day);
  },
}
</script>

<style>

</style>