<template>
  <div>
    <div>
      <div v-if="isAdd || isUpdate">
        <div>
          <label for="title">제목</label>
          <input 
            type="text" 
            v-model="selectedSchedule.title" 
            id="title" 
            placeholder="일정 제목" 
            :readonly="!isAdd && !isUpdate" 
          />
        </div>
        <div>
          <label for="startTime">시작 시간</label>
          <input 
            type="datetime-local" 
            v-model="selectedSchedule.startTime" 
            id="startTime" 
            :readonly="!isAdd && !isUpdate"
          />
        </div>
        <div>
          <label for="endTime">끝 시간</label>
          <input 
            type="datetime-local" 
            v-model="selectedSchedule.endTime" 
            id="endTime" 
            :readonly="!isAdd && !isUpdate"
          />
        </div>
        <div>
          <label for="description">설명</label>
          <input 
            type="text" 
            v-model="selectedSchedule.description" 
            id="description" 
            placeholder="일정 설명" 
            :readonly="!isAdd && !isUpdate"
          />
        </div>
        <button @click="isAdd ? fnSaveNewSchedule() : fnSaveUpdate()">
          저장
        </button>
        <button @click="isAdd ? fnCancelAdd() : fnCancle()">취소</button>
      </div>
      <div v-else>
        <div class="schedule-info">
          <div class="info-item">
            <label for="title">제목</label>
            <input 
              type="text" 
              :value="selectedSchedule.title" 
              id="title" 
              readonly
            />
          </div>
          <div class="info-item">
            <label for="startTime">시작 시간</label>
            <input 
              type="text" 
              :value="formatDate(selectedSchedule.startTime)" 
              id="startTime" 
              readonly
            />
          </div>
          <div class="info-item">
            <label for="endTime">끝 시간</label>
            <input 
              type="text" 
              :value="formatDate(selectedSchedule.endTime)" 
              id="endTime" 
              readonly
            />
          </div>
          <div class="info-item">
            <label for="description">설명</label>
            <input 
              type="text" 
              :value="selectedSchedule.description" 
              id="description" 
              readonly
            />
          </div>
          <div>

            <button @click="fnUpdate">수정</button>
            <button @click="fnRemove">삭제</button>
          </div>
        </div>
      </div>
    </div>
    <button v-if="!isAdd && !isUpdate" @click="fnAdd" class="add-schedule-btn">일정등록</button>
  </div>
</template>


<script>
export default {
  props: {
    fullList: {
      type: Array,
    },
    selectedSchedule: {
      type: Object,
      default: null,
    },
    day: {
      type: String,
    },
  },
  emits: ['fnScheduleList', 'fnDayList'],
  data() {
    return {
      day: '',
      year: new Date().getFullYear(),
      month: new Date().getMonth() + 1,
      isUpdate: false,
      isAdd: false,
      newSchedule: {
        title: '',
        startTime: '',
        endTime: '',
        description: '',
      },
    }
  },
  methods: {
    formatDate(date) {
    return this.$dayjs(date).format('YYYY-MM-DD HH:mm');
  },
    async SelectedSchedule(id) {
      console.log('SelectedSchedule 메서드 호출', id);
        const response = await this.$axios.get(`schedule/userId/id/${id}`);
        if (response.status) {
         (response.data) 
            this.selectedSchedule = response.data;  
          } else {
            console.log("일정이 없습니다.");
          }
    },
    selectEarliestSchedule() {
      if (this.fullList && Array.isArray(this.fullList) && this.fullList.length > 0) {
    // 날짜 비교를 dayjs를 이용해 처리
    const earliestSchedule = this.fullList.reduce((earliest, current) => {
      const earliestStart = this.$dayjs(earliest.start);
      const currentStart = this.$dayjs(current.start);
      if (earliestStart.isValid() && currentStart.isValid()) {
        return currentStart.isBefore(earliestStart) ? current : earliest;
      } else {
        console.error('Invalid date format detected:', earliest.start, current.start);
        return earliest;
      }
    });
    this.selectedSchedule = earliestSchedule;
  } else {
    console.log('오늘의 일정에 오류가 있다.');
  }
    },
    fnUpdate() {
      this.isUpdate = true
    },
    fnCancle() {
      this.isUpdate = false
    },
    async fnSaveUpdate() {
      if (new Date(this.newSchedule.startTime) > new Date(this.newSchedule.endTime)) {
        alert('마치는 시간이 시작 시간보다 앞설 수 없습니다.')
        return
      }
      const updatedSchedule = this.selectedSchedule
      const id = updatedSchedule.id
      const response = await this.$axios.patch(`schedule/update/${id}`, updatedSchedule)
      if (response.status) {
        alert('수정 되었습니다.')
        this.isUpdate = false
        this.$emit('fnScheduleList', this.day)
        this.$emit('fnDayList', this.day)
      } else {
        alert(response.message)
      }
    },
    async fnRemove() {
      if(confirm('일정을 삭제하시겠습니까?')){
        const id = this.selectedSchedule.id;
        const response = await this.$axios.delete(`schedule/delete/${id}`);
        if(response){
          this.$emit('fnScheduleList', this.day);
          this.$emit('fnDayList', this.day);
        }
      }
    },
   fnAdd() {
      this.isAdd = true;  
      this.selectedSchedule  = { title: '', startTime: '', endTime: '', description: '' }; 

    },
    fnCancelAdd() {

      this.isAdd = false;  

    },
    async fnSaveNewSchedule() {
      if (new Date(this.newSchedule.startTime) > new Date(this.newSchedule.endTime)) {
        alert('마치는 시간이 시작 시간보다 앞설 수 없습니다.')
        return
      }
      const response = await this.$axios.post('schedule/add', this.newSchedule)
      if (response.status) {
        alert('일정이 등록되었습니다.')
        this.isAdd = false
        this.$emit('fnScheduleList', this.day)
        this.$emit('fnDayList', this.day)
      } else {
        alert(response.message)
      }
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DDTHH:mm:ss');
    this.selectEarliestSchedule();
  },
}
</script>

<style scoped>
.schedule-info {
  display: grid;
  grid-template-columns: 1fr;
  gap: 15px;
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;
  border-radius: 5px;
  background-color: white;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 10px;
}

.info-value {
  flex-grow: 1;
  padding-left: 10px;
  padding-right: 10px;
}

button {
  padding: 8px 12px;
  background-color: transparent;  
  color: black;
  border: 1px solid black;  
  border-radius: 5px;
  cursor: pointer;
  outline: none; 
}

button:hover {
  background-color: #f0f0f0;  
}

.add-schedule-btn {
  margin-top: 20px;
  padding: 8px 12px;
  background-color: transparent; 
  color: black;
  border: 1px solid black;  
  border-radius: 5px;
  cursor: pointer;
  outline: none; 
}

input, textarea {
  padding: 8px 12px;
  background-color: transparent;  
  color: black;
  border: 1px solid black;  
  border-radius: 5px;
  width: 100%;
  outline: none; 
}

</style>
