<template>
  <div>
    <div v-if="isAdd || isUpdate">
      <div>
        <label for="title">제목</label>
        <input 
          type="text" 
          v-model="inputSchedule.title" 
          id="title"
          placeholder="일정 제목"
          :readonly="!isAdd && !isUpdate"
        />
      </div>
      <div>
        <label for="startTime">시작 시간</label>
        <input 
          type="datetime-local" 
          v-model="inputSchedule.startTime" 
          id="startTime"
          :readonly="!isAdd && !isUpdate"
        />
      </div>
      <div>
        <label for="endTime">끝 시간</label>
        <input 
          type="datetime-local" 
          v-model="inputSchedule.endTime" 
          id="endTime"
          :readonly="!isAdd && !isUpdate"
        />
      </div>
      <div>
        <label for="description">설명</label>
        <input 
          type="text" 
          v-model="inputSchedule.description" 
          id="description"
          placeholder="일정 설명"
          :readonly="!isAdd && !isUpdate"
        />
      </div>
      <button @click="fnSave()">저장</button>
      <button @click="fnCancle()">취소</button>
    </div>
    <div v-else>
      <div class="schedule-info">
        <div class="info-item">
          <label for="title">제목</label>
          <input 
            type="text" 
            v-model="inputSchedule.title" 
            id="title"
            readonly
          />
        </div>
        <div class="info-item">
          <label for="startTime">시작 시간</label>
          <input 
            type="text" 
            :value="formatDate(inputSchedule.startTime)"
            id="startTime"
            readonly
          />
        </div>
        <div class="info-item">
          <label for="endTime">끝 시간</label>
          <input 
            type="text" 
            :value="formatDate(inputSchedule.endTime)"
            id="endTime"
            readonly
          />
        </div>
        <div class="info-item">
          <label for="description">설명</label>
          <input 
            type="text" 
            :value="inputSchedule.description"
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
    <button v-if="!isAdd && !isUpdate" @click="fnAdd" class="add-schedule-btn">일정등록</button>
  </div>
</template>
<script>
export default {
  props: {
    fullList: Array,
    schedule: Object,
    day: String,
    inputSchedule: Object 
  },
  watch: {
    fullList(newVal) {
      this.newItem = [ ...newVal ];
    },
  },
  emits: ['fnScheduleList', 'fnDayList'],
  data() {
    return {
      year: new Date().getFullYear(),
      month: new Date().getMonth() + 1,
      isUpdate: false,
      isAdd: false,
      // inputSchedule: {
      //   id: '',
      //   title: '',
      //   description: '',
      //   startDate: '',
      //   endDate: '',
      // },
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
      return date ? this.$dayjs(date).format('YYYY-MM-DDTHH:mm') : '';  
    },
    fnUpdate() {
      this.isUpdate = true
    },
    async inputedSchedule(id) {
      console.log('inputedSchedule 호출', id);
        const response = await this.$axios.get(`schedule/userId/id/${id}`);
        if (response.status) {
          console.log("일정 데이터", response.data);
          //this.inputSchedule = response.data;
          this.$emit('updateInputSchedule', response.data);
            } else {
              console.log("일정이 없습니다.");
            }
    },
    fnCancle() {
      if (this.isAdd) {
      this.isAdd = false;  
    } else if (this.isUpdate) {
      this.isUpdate = false;  
    }
    },
    async fnRemove() {
      if(confirm('일정을 삭제하시겠습니까?')){
        const id = this.inputSchedule.id;
        const response = await this.$axios.delete(`schedule/delete/${id}`);
        if(response){
          this.$emit('fnScheduleList', this.day);
          this.$emit('fnDayList', this.day);
        }
      }
    },
    fnAdd() {
      this.isAdd = true;
      this.$emit('updateInputSchedule', { title: '', startTime: '', endTime: '', description: '' });
      //this.inputSchedule  = { title: '', startTime: '', endTime: '', description: '' };
    },
    async fnSave() {
      const schedule = this.isAdd ? this.newSchedule : this.inputSchedule;
      console.log("서버로 보내는 데이터:", schedule);
      const formattedSchedule = {
        ...schedule,
        startTime: this.$dayjs(schedule.startDate).format('YYYY-MM-DDTHH:mm:ss'), // ISO 형식
        endTime: this.$dayjs(schedule.endDate).format('YYYY-MM-DDTHH:mm:ss'), // ISO 형식
      };
      let url;
      let method;

      if (formattedSchedule.id) {
        url = `schedule/update/${formattedSchedule.id}`;
        method = 'patch';
      } else {
        url = 'schedule/add';
        method = 'post';
      }
      try {
        const response = await this.$axios[method](url, formattedSchedule);  
        console.log("API 응답:", response);
        if (response.status) {
          alert('작업이 완료되었습니다.');
          this.isAdd = false;  
          this.isUpdate = false;  
          this.$emit('fnScheduleList', this.day);  
          this.$emit('fnDayList', this.day); 
          // 부모에게 inputSchedule 업데이트 요청
        this.$emit('updateInputSchedule', response.data);
        } else {
          alert(response.message);  
        }
      } catch (error) {
        console.error(error);
        alert('오류가 발생했습니다.');
      }
    }
  },
  mounted() {
    if (!this.inputSchedule.startTime) {
      this.inputSchedule.startTime = '';  
    }
    if (!this.inputSchedule.endTime) {
      this.inputSchedule.endTime = '';  
    }
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
