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
    <ScheduleSearch/>
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
      selectedSchedule: {},
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

    async SelectedSchedule(id){
      const userId = 6;
      console.log("SelectedSchedule에 id가 들어왔습니다:", id); // ID 확인
      try {
        const response = await this.$axios.get(`schedule/userId/id/${userId}/${id}`);
        if (response.status) {
          console.log("서버에서 가져온 일정:", response.data);  // full.data 확인
          if (Array.isArray(response.data) && response.data.length > 0) {
        // 첫 번째 일정 선택
        this.selectedSchedule = response.data[0];  
        console.log("선택된 일정:", this.selectedSchedule);  // 선택된 일정 확인
      } else {
        console.log("일정이 없습니다.");
      }
    } else {
      console.log("서버에서 오류:", response.message);
    }
  } catch (error) {
    console.error("서버 요청 오류:", error);  // 오류가 발생한 경우 콘솔에 출력
  }
    },
     // console.log("id입니다.", id)
    //   const full = await this.$axios.get(`schedule/userId/id/${userId}/${id}`);
    //   if (full.status) {
    //     this.selectedSchedule = full.data;
    //     console.log("full.data입니다", full.data)
    //   } else {
    //     console.log(full.message);
    //   }
    // },
    // SelectedSchedule(id) {
    //   this.selectedSchedule = id;
    //   this.fnSchduleDayList(id);
    //   console.log("selectedSchedule ID:", this.selectedSchedule);
      
    // },
  },

  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD')
    this.fnScheduleList(this.day);
  },
}
</script>

<style>

</style>