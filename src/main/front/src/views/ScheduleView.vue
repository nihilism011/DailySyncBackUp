<template>
  <div class="left left-container">
    <div class="left-top">
    </div>
    <div class="left-bottom">
    <ScheduleList/>
    </div>
  </div>
  <div class="right right-container">
    <ScheduleSearch/>
    <Calendar :dailyLsit="dailyLsit" @fnScheduleList="fnScheduleList" @fnDayList="fnDayList"/>
  </div>
</template>
<script>
import Calendar from '@/components/schedule/ScheduleCalendar.vue'
import ScheduleList from '@/components/schedule/ScheduleList.vue'
import ScheduleSearch from '@/components/schedule/ScheduleSearch.vue'
import DateSelector from '@/components/common/DateSelector.vue'
export default {
  components: {
    DateSelector,
    ScheduleList,
    ScheduleSearch,
    Calendar,
  },
  data() {
    return {
      day : '',
      dailyLsit: [],
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
      await this.fnDayClick(year, month, day)
    },
    async fnDayList(inputDay) {
      console.log(inputDay);
      const userId = 6;
      let year = inputDay.split('-')[0]
      let month = inputDay.split('-')[1]
      const daily = await this.$axios.get(`schedule/userId/${userId}/${year}/${month}`)
      if (daily.status) {
        this.dailyLsit = daily.data.map((item) => ({
          title: item.title,
          start: item.startTime,
          end: item.endTime
        }))
      } else {
        console.log(daily.message)
      }
    },
    async fnDayClick(year, month, day) {
      let firstDay = new Date(year, month - 1, 1).getDay()
      let lastDay = new Date(year, month, 0).getDate()
      let toDay = new Date(year, month - 1, day).getDate()
      let thisWeek = parseInt((firstDay - 1 + toDay) / 7)
      this.week = new Array(7).fill(0)
      for (let i = 0; i < 7; i++) {
        let check = thisWeek === 0 ? i - (firstDay - 1) : i + 7 * thisWeek - firstDay + 1
        this.week[i] =
          check > lastDay || check < 1
            ? { 0: 0 }
            : { [`${year}-${month}-${check.toString().padStart(2, '0')}`]: check } // 날짜가 유효하지 않으면 0 처리
      }
      this.day = `${year}-${month}-${day}`
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
