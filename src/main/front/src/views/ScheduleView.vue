<template>
  <div class="left left-container">
    <NewInput
      :dailyList="dailyList"
      :day="day"
      :inputSchedule="inputSchedule"
      @fnDayList="fnScheduleList"
      @inputedSchedule="inputedSchedule"
    />
  </div>
  <div class="right right-container">
    <ScheduleSearch 
      @searchResult="openModal"
      :searchResults="searchResults"
    />
    <ScheduleCalendar
      :dailyList="dailyList"
      :inputSchedule="inputSchedule"
      @fnScheduleList="fnScheduleList"
      @inputSchedule="inputedSchedule"
      :isAdd="isAdd"
      :isUpdate="isUpdate"
    />
    <ScheduleModal
      v-if="popupState"       
      :popupState="popupState"
      :searchResults="searchResults"
      @closePopup="popupState = false"
      @deleteSchedule="handleDeleteSchedule" 
      @editSchedule="openEditModal" 
    />
    <ScheduleInfoModal
      v-if="editModalState"
      :popupState="editModalState"
      :schedule="selectedSchedule"
      @closePopup="editModalState = false"
      @saveSchedule="handleSaveSchedule"
    />
  </div>

</template>
<script>
import NewInput from '@/components/schedule/NewInput.vue';
import ScheduleCalendar from '@/components/schedule/ScheduleCalendar.vue'
import ScheduleSearch from '@/components/schedule/ScheduleSearch.vue'
import ScheduleModal from '@/components/schedule/ScheduleModal.vue';
import ScheduleInfoModal from '@/components/schedule/ScheduleInfoModal.vue';
export default {
  components: {
    NewInput,
    ScheduleSearch,
    ScheduleCalendar,
    ScheduleModal,
    ScheduleInfoModal
  },
  data() {
    return {
      day: '',
      dailyList: [],
      fullList: [],
      isAdd: false, 
      isUpdate: false, 
      inputSchedule: {
        title: '',
        description: '',
        startTime: '',  
        endTime: '', 
        searchResults: [],  
      },
      popupState: false, 
      selectedSchedule: '',
      editModalState: false,  
      searchResults: [],
    }
  },
  methods: {
    async fnScheduleList(inputDay) {
      let year = inputDay.split('-')[0];
      let month = inputDay.split('-')[1];
      const startOfMonth = `${year}-${month}-01T00:00:00`; 
      const endOfMonth = `${year}-${month}-01T23:59:59`;   
      try {
      const full = await this.$axios.get(`schedule/userId/scheduleList/${year}/${month}`, {
        params: { startOfMonth, endOfMonth } 
      });
        if (full.status && full.data.length > 0) {
          this.dailyList = full.data.map(item => ({
            id: item.id,
            title: item.title,
            start: item.startTime,
            end: item.endTime,
            description: item.description,
          }));
          console.log("Server response: ", full.data);
        } else {
          console.log('해당 날짜에 일정이 없습니다.');
          this.dailyList = [];
        }
      } catch (error) {
        console.error('일정을 불러오는 중 오류가 발생했습니다:', error);
        this.dailyList = []; 
      }
    },
    async inputedSchedule(id) {
      if (id) {
        const response = await this.$axios.get(`schedule/userId/id/${id}`);
        if (response.status) {
          console.log("response.data", response.data);
          this.inputSchedule = response.data;  
        } else {
          console.log("일정이 없습니다.");
        }
      } else {
        this.inputSchedule = { title: '', startTime: '', endTime: '', description: '' };
      }
    },
    openModal(searchResults) {
      this.searchResults = searchResults;  
      this.popupState = true;  
      console.log("Search Results:", searchResults);
    },
    handleDeleteSchedule(item) {
      this.searchResults = this.searchResults.filter(schedule => schedule.id !== item.id);
      this.dailyList = this.dailyList.filter(schedule => schedule.id !== item.id);
    },
    openEditModal(schedule) {
      this.selectedSchedule = { ...schedule };  
      this.editModalState = true;  
    },
    handleSaveSchedule(updatedSchedule) {
      this.searchResults = this.searchResults.map(schedule =>
        schedule.id === updatedSchedule.id ? updatedSchedule : schedule
      );
      this.dailyList = this.dailyList.map(schedule =>
        schedule.id === updatedSchedule.id ? updatedSchedule : schedule
      );
      this.editModalState = false;  
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD')
    this.fnScheduleList(this.day)
  },
};
</script> 