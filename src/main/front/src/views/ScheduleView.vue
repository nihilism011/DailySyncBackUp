<template>
  <div class="left left-container">
    <div class="left-top">
    </div>
    <div class="left-bottom">
      <ScheduleList @fnScheduleList="fnScheduleList" />
    </div>
  </div>
  <div class="right right-container">
    <ScheduleSearch/>
    <ScheduleCalendar :dailyList="dailyList" @fnScheduleList="fnScheduleList" @fnSchduleDayList="fnSchduleDayList"/>
  </div>
</template>
<script>
import ScheduleCalendar from '@/components/schedule/ScheduleCalendar.vue'
import ScheduleList from '@/components/schedule/ScheduleList.vue'
import ScheduleSearch from '@/components/schedule/ScheduleSearch.vue'
export default {
  components: {
    ScheduleList,
    ScheduleSearch,
    ScheduleCalendar,
  },
  data() {
    return {
      day : '',
      dailyList: [],
      selectedSchedule: null,
    };
  },

  methods: {
    async fnScheduleList(inputDay) {
      const userId = 6;
      let year = inputDay.split('-')[0]
      let month = inputDay.split('-')[1]
      const full = await this.$axios.get(`schedule/userId/${userId}/${year}/${month}`)
      if (full.status) {
        this.dailyList = full.data.map(item => ({
          id:item.id,
          title: item.title,
          start: item.startTime,
          end: item.endTime,
        }));
      } else {
        console.log(full.message)
      }
    },

    async fnSchduleDayList(selectedId){
      const userId = 6;
      const id = selectedId;
      console.log("id입니다.", id)
      const full = await this.$axios.get(`schedule/userId/id/${userId}/${id}`);
      if (full.status) {
        this.selectedSchedule = full.data;
        console.log("full.data입니다", full.data)
      } else {
        console.log(full.message);
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