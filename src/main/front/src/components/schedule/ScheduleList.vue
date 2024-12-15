<template>
  <div>
    <div v-if="isUpdate">
      <div>
        <label for="editTitle">제목</label>
        <input type="text" v-model="selectedSchedule.title" id="updateTitle" />
      </div>
      <div>
        <label for="editStartTime">시작 시간</label>
        <input type="datetime-local" v-model="selectedSchedule.startTime" id="updateStartTime" />
      </div>
      <div>
        <label for="editEndTime">끝 시간</label>
        <input type="datetime-local" v-model="selectedSchedule.endTime" id="updateEndTime" />
      </div>
      <div>
        <label for="editDescription">설명</label>
        <textarea v-model="selectedSchedule.description" id="updateDescription"></textarea>
      </div>

      <button @click="fnSaveUpdate">저장</button>
      <button @click="fnCancle">취소</button>
    </div>

    <div v-else>
      <div>
        <div class="schedule-info">
        <div class="info-item">
          <div class="info-value">{{ selectedSchedule.title }}</div>
        </div>
        <div class="info-item">
          <div class="info-value">{{ selectedSchedule.startTime }}</div>
        </div>
        <div class="info-item">
          <div class="info-value">{{ selectedSchedule.endTime }}</div>
        </div>
        <div class="info-item">
          <div class="info-value">{{ selectedSchedule.description }}</div>
        </div>
        <div v-if="!isUpdate && selectedSchedule">
          <button @click="fnUpdate">수정</button>
          <button @click="fnRemove">삭제</button>
        </div>
      </div>
      </div>
    </div>
  </div>
  <button v-if="!isAdd && !isUpdate" @click="fnAdd" class="add-schedule-btn">일정등록</button>
  <div v-if="isAdd" class="add-schedule-form">
    <div>
      <label for="newTitle">제목</label>
      <input type="text" v-model="newSchedule.title" id="newTitle" placeholder="일정 제목" />
    </div>
    <div>
      <label for="newStartTime">일정 시작</label>
      <input type="datetime-local" v-model="newSchedule.startTime" id="newStartTime" />
    </div>
    <div>
      <label for="newEndTime">일정 마침</label>
      <input type="datetime-local" v-model="newSchedule.endTime" id="newEndTime" />
    </div>
    <div>
      <label for="newDescription">설명</label>
      <textarea v-model="newSchedule.description" id="newDescription" placeholder="일정 설명"></textarea>
    </div>
    <button @click="fnSaveNewSchedule">저장</button>
    <button @click="fnCancelAdd">취소</button>
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
    }
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
        description: ''
      },
    };
  },
  methods: {
    selectEarliestSchedule() {
    if (this.fullList.length === 0) return;

    const earliestSchedule = this.fullList.reduce((earliest, current) => {
      const currentStart = new Date(current.startTime);
      const earliestStart = new Date(earliest.startTime);
      return currentStart < earliestStart ? current : earliest;
    });

    // 부모로 selectedSchedule을 전달
    this.$emit('SelectedSchedule', earliestSchedule.id);
  },

    async SelectedSchedule(id) {
      console.log('SelectedSchedule 메서드 호출', id);
      const userId = 6;
        const response = await this.$axios.get(`schedule/userId/id/${userId}/${id}`);
        if (response.status) {
         (Array.isArray(response.data) && response.data.length > 0) 
            this.selectedSchedule = response.data[0];  
          } else {
            console.log("일정이 없습니다.");
          }
    },
    fnUpdate() {
      this.isUpdate = true; 
    },
    fnCancle() {
      this.isUpdate = false; 
    },
    async fnSaveUpdate() {
      if (new Date(this.newSchedule.startTime) > new Date(this.newSchedule.endTime)) {
        alert('마치는 시간이 시작 시간보다 앞설 수 없습니다.');
        return;  
      }
      const updatedSchedule = this.selectedSchedule;
      const id = updatedSchedule.id;
      const response = await this.$axios.patch(`schedule/update/${id}`, updatedSchedule);  
      if (response.status) {
        alert('수정 되었습니다.');
        this.isUpdate = false;   
        this.$emit('fnScheduleList', this.day);
        this.$emit('fnDayList', this.day);
      } else {
        alert(response.message)
      }
    },
    async fnRemove() {
      if(confirm('일정을 삭제하시겠습니까?')){
          const id = this.selectedSchedule.id;
          const response = await this.$axios.delete(`schedule/delete/${id}`);
          if(response.status){
            this.$emit('fnScheduleList', this.day);
            this.$emit('fnDayList', this.day);
          }
        }
        this.selectedSchedule = null; 
    },
 
   fnAdd() {
      this.isAdd = true;  
    },

    fnCancelAdd() {
      this.isAdd = false;  
      this.newSchedule = { title: '', startTime: '', endTime: '', description: '' }; 
    },

    async fnSaveNewSchedule() {
       if (new Date(this.newSchedule.startTime) > new Date(this.newSchedule.endTime)) {
        alert('마치는 시간이 시작 시간보다 앞설 수 없습니다.');
        return;  
      }
      const response = await this.$axios.post('schedule/add', this.newSchedule);
      if (response.status) {
        alert('일정이 등록되었습니다.');
        this.isAdd = false;
        this.$emit('fnScheduleList', this.day);
        this.$emit('fnDayList', this.day);
      } else {
        alert(response.message);
      }
    },
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DDTHH:mm:ss');
    
  },
};
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
  border: 1px solid black; 
  padding: 10px;
  
}

.info-value {
  flex-grow: 1;
  padding-left: 10px;
  padding-right: 10px;
}

.button-container {
  display: flex;
  justify-content: flex-end;
  gap: 20px; /* 버튼 사이 간격을 20px로 설정 */
}


button {
  padding: 8px 12px;
  background-color: white;
  color: black;
  border: 1px solid black; 
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #f0f0f0;  
  border-color: #333; 
}

.add-schedule-btn {
  margin-top: 20px;
  padding: 8px 12px;
  background-color: white;
  color: black;
  border: 1px solid black; 
  border-radius: 5px;
  cursor: pointer;
}
</style>
