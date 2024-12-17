<template>
  <div class="left left-container">
    <div class="left-top"></div>
    <div class="left-bottom">
      <ScheduleList
        :fullList="dailyList"
        :day="day"
        :selectedSchedule="selectedSchedule"
        @fnDayList="fnScheduleList"
        />
    </div>
  </div>
  <div class="right right-container">
    <ScheduleSearch @searchResult="openModal"/>
    <ScheduleCalendar
    :dailyList="dailyList"
    @fnScheduleList="fnScheduleList"
    :selectedSchedule="selectedSchedule"
    @SelectedSchedule="SelectedSchedule"
  />
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
      day: '',
      dailyList: [],
      fullList: [],
      selectedDay: this.$dayjs().format('YYYY-MM-DD'),
      selectedSchedule: '',
      popupState: false,
      searchResults: [],
    }
  },
  methods: {
    async fnScheduleList(inputDay) {
      let year = inputDay.split('-')[0];
      let month = inputDay.split('-')[1];
      try {
        const full = await this.$axios.get(`schedule/userId/${year}/${month}`);
        if (full.status && full.data.length > 0) {
          this.dailyList = full.data.map(item => ({
            id: item.id,
            title: item.title,
            start: item.startTime,
            end: item.endTime,
            description: item.description,
          }));
          this.selectEarliestSchedule(inputDay);
      } else {
        console.log('해당 날짜에 일정이 없습니다.');
      }
    } catch (error) {
      console.error('일정을 불러오는 중 오류가 발생했습니다:', error);
    }
    },
    selectEarliestSchedule(todayDate) {
    if (this.dailyList.length === 0) {
      this.selectedSchedule = null;
      return;
    }
    const formattedTodayDate = this.$dayjs(todayDate).format('YYYY-MM-DD'); // todayDate 사용
    const startOfToday = this.$dayjs(formattedTodayDate).startOf('day');
    const endOfToday = this.$dayjs(formattedTodayDate).endOf('day');
    const todaySchedules = this.dailyList.filter(item => {
      const scheduleStart = this.$dayjs(item.start);
      const scheduleEnd = this.$dayjs(item.end);
      return (
        (scheduleStart.isBefore(endOfToday) && scheduleEnd.isAfter(startOfToday))
      );
    });
    const earliestSchedule = todaySchedules.reduce((earliest, current) => {
      const currentStart = new Date(current.start);
      const earliestStart = new Date(earliest.start);
      return currentStart < earliestStart ? current : earliest;
    });

    this.selectedSchedule = {
      ...earliestSchedule,
      startTime: this.$dayjs(earliestSchedule.start).format('YYYY-MM-DDTHH:mm'),
      endTime: this.$dayjs(earliestSchedule.end).format('YYYY-MM-DDTHH:mm')
    };
  },
    async SelectedSchedule(id) {
      const response = await this.$axios.get(`schedule/userId/id/${id}`);
      if (response.status) {
        this.selectedSchedule = response.data;
      }
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD')
    this.fnScheduleList(this.day)
  },
}
</script>

<style></style>
