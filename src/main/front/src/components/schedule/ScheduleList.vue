<template>
  <div>
    <div class="list-container">
       list : {{list}}
      <div class="list-item" v-for="(item, index) in list" :key="index">
          <div @click="scheduleDetails(item)">
            {{ item.scheduleId }}
            {{ item.title }} 
          </div>
      </div>
    </div>
    <!-- 오늘의 첫 번째 일정 -->
    <div v-if="firstTodaySchedule">
      <div>{{ firstTodaySchedule.title }}</div>
      <div>
        {{ firstTodaySchedule.startTime }} ~ {{ firstTodaySchedule.endTime }}
      </div>
      <div>{{ firstTodaySchedule.description }}</div>
    </div>
    <!-- 선택한 일정의 상세 정보 -->
    <div v-if="selectedSchedule" class="schedule-detail">
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
        <div>{{ selectedSchedule.title }}</div>
        <div>{{ formatDate(selectedSchedule.startTime) }} ~ {{ formatDate(selectedSchedule.endTime) }}</div>
        <div>{{ selectedSchedule.description }}</div>
      </div>

      <button @click="fnUpdate">수정</button>
      <button @click="fnRemove">삭제</button>
      </div>
  </div>
  <button @click="fnAdd">일정등록</button>
  <div v-if="isAdd" class="add-schedule-form">
    <div>
      <label for="newTitle">제목</label>
      <input type="text" v-model="newSchedule.title" id="newTitle" placeholder="일정 제목" />
    </div>
    <div>
      <label for="newStartTime">시작 시간</label>
      <input type="datetime-local" v-model="newSchedule.startTime" id="newStartTime" />
    </div>
    <div>
      <label for="newEndTime">끝 시간</label>
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
      type: Object,
    },
    day: {
      type: String,
    }
  },
  emits: ['fnScheduleList', 'fnDayList'],
  data() {
    return {
      list: [],
      todayList: [],
      firstTodaySchedule: null, 
      day: '',  
      year: new Date().getFullYear(), 
      month: new Date().getMonth() + 1, 
      selectedSchedule: null,  
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
  watch: {
    fullList(newList) {
      if (newList && typeof newList === 'object') {
        this.list = Object.values(newList);  // 객체의 값을 배열로 변환
        this.todaySchedule();
      }
    }
  },
  methods: {
    async fnListByUserId() {
      const userId = 6; 
      try {
        const response = await this.$axios.get(`schedule/userId/${userId}/${this.year}/${this.month}`);
        if (response.status) {
          this.list = response.data;
          this.todaySchedule();
        } else {
          console.log(response.message);
        }
      } catch (error) {
        console.error("일정을 가져오는 중 오류 발생: ", error);
      }
    },
    todaySchedule() {
      const today = this.$dayjs().format('YYYY-MM-DD');  
      this.todayList = this.list.filter(item => {
        const itemDate = this.$dayjs(item.startTime).format('YYYY-MM-DD');
        return itemDate === today;
      });

      // 오늘 일정이 있을 경우, 가장 첫 번째 일정을 선택
      if (this.todayList.length > 0) {
        this.firstTodaySchedule = this.todayList
          .sort((a, b) => this.$dayjs(a.startTime).isBefore(this.$dayjs(b.startTime)) ? -1 : 1)[0];
      } else {
        this.firstTodaySchedule = null; 
      }
    },

    scheduleDetails(item) {
      this.selectedSchedule = item;
      this.isUpdate = false; 
    },
   
    fnUpdate() {
      this.isUpdate = true; 
    },

    fnCancle() {
      this.isUpdate = false; 
      this.scheduleDetails(this.selectedSchedule); 
    },

    async fnSaveUpdate() {
      const updatedSchedule = this.selectedSchedule;
      const id = updatedSchedule.id;
      const response = await this.$axios.patch(`schedule/update/${id}`, updatedSchedule);  
      if (response.status) {
        alert('수정 되었습니다.');
        this.isUpdate = false;  
        this.fnListByUserId();  
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
            this.fnListByUserId();
            this.$emit('fnScheduleList', this.day);
            this.$emit('fnDayList', this.day);
          }
        }
        this.selectedSchedule = null; 
    },

    formatDate(date) {
    return this.$dayjs(date).format('YYYY-MM-DD');  
  },
 
   fnAdd() {
      this.isAdd = true;  
    },

    fnCancelAdd() {
      this.isAdd = false;  
      this.newSchedule = { title: '', startTime: '', endTime: '', description: '' }; 
    },

    async fnSaveNewSchedule() {
      const response = await this.$axios.post('schedule/add', this.newSchedule);
      if (response.status) {
        alert('일정이 등록되었습니다.');
        this.isAdd = false;
        this.fnListByUserId(); 
        this.$emit('fnScheduleList', this.day);
        this.$emit('fnDayList', this.day);
        
      } else {
        alert(response.message);
      }
    },
    handleScheduleClick(item) {
      this.$emit('selectSchedule', item);  // 부모에게 클릭된 일정 전달
    }
  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DD');
    this.fnListByUserId();
  },
};
</script>

<style scoped>
.list-item {
  cursor: pointer;
}</style>
