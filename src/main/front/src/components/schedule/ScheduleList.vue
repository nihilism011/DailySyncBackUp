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
        <div class="today-schedule">
        <h3>오늘의 첫 번째 일정</h3>
        <div v-if="firstTodaySchedule">
          <div>{{ firstTodaySchedule.title }}</div>
          <div>
            {{ firstTodaySchedule.startTime }} ~ {{ firstTodaySchedule.endTime }}
          </div>
          <div>{{ firstTodaySchedule.description }}</div>
        </div>
        <div v-else>
          <p>오늘의 일정이 없습니다.</p>
        </div>
      </div>
       <!-- 선택한 일정의 상세 정보 -->
      <div v-if="selectedSchedule" class="schedule-detail">
        <h3>상세 정보</h3>
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

      <button @click="fnSave">수정 저장</button>
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
</template>

<script>
export default {
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
    };
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

    async fnSave() {
      const updatedSchedule = this.selectedSchedule;
      const id = updatedSchedule.id;
      const response = await this.$axios.patch(`schedule/update/${id}`, updatedSchedule);  
      if (response.status) {
        alert('수정 되었습니다.');
        this.isUpdate = false;  
        this.fnListByUserId();  
      } else {
        alert(response.message)
      }
    },

    async fnRemove() {
      if(confirm('일정을 삭제하시겠습니까?')){
          const id = this.selectedSchedule.id;
          const response = await this.$axios.delete(`schedule/delete/${id}`);
          if(response.status){
            this.fnListByUserId()
          }
        }
        this.selectedSchedule = null; 
    },

    formatDate(date) {
    return this.$dayjs(date).format('YYYY-MM-DD');  
}

  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DDTHH:mm:ss');
    this.fnListByUserId();
  },
};
</script>

<style scoped>
.list-item {
  cursor: pointer;
}</style>
