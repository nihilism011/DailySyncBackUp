<template>
  <div class="left left-container">
    <div class="left-top">
    </div>
    <div class="left-bottom">
      <ScheduleList 
        :fullList="dailyList" 
        :day="day" 
        :selectedSchedule="selectedSchedule" 
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
      day : '',
      dailyList: [],
      selectedDay: this.$dayjs().format('YYYY-MM-DD'), 
      selectedSchedule: '',
      popupState: false,
      searchResults: [],
    };
  },

  methods: {
    async fnScheduleList(inputDay) {
    const userId = 6;
    let year = inputDay.split('-')[0];
    let month = inputDay.split('-')[1];

    try {
      const full = await this.$axios.get(`schedule/userId/${userId}/${year}/${month}`);
      if (full.status && full.data.length > 0) {
        this.dailyList = full.data.map(item => ({
          id: item.id,
          title: item.title,
          startTime: item.startTime,
          endTime: item.endTime,
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
      const todaySchedules = this.dailyList.filter(item => {
        const startDate = new Date(item.startTime);
        const today = new Date(todayDate);
        return startDate.getFullYear() === today.getFullYear() &&
              startDate.getMonth() === today.getMonth() &&
              startDate.getDate() === today.getDate();
      });
      if (todaySchedules.length === 0) {
        console.log("오늘 일정이 없습니다.");
        return;
      }
      const earliestSchedule = todaySchedules.reduce((earliest, current) => {
        const currentStart = new Date(current.startTime);  
        const earliestStart = new Date(earliest.startTime);
        return currentStart < earliestStart ? current : earliest;
      });
      this.selectedSchedule = earliestSchedule;
      console.log("오늘 가장 빠른 일정: ", this.selectedSchedule);  
    },
 
    async SelectedSchedule(id){
      const userId = 6;
      console.log("SelectedSchedule에 id가 들어왔습니다:", id); 
      const response = await this.$axios.get(`schedule/userId/id/${userId}/${id}`);
        if (response.status) {
          this.selectedSchedule = response.data[0];  
      } else {
        console.log("일정이 없습니다.");
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