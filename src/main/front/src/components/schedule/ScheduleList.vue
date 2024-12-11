<template>
  <div>
    <div class="list-container">
       list : {{list}}
      <div class="list-item" v-for="(item, index) in list" :key="index">
          <div>{{ item.title }}</div>
      </div>
    </div>
        <!-- 오늘의 첫 번째 일정만 별도로 표시 -->
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [],
      todayList: [],
      firstTodaySchedule: null, // 가장 먼저 시작하는 오늘 일정
      day: '',  
      year: new Date().getFullYear(), 
      month: new Date().getMonth() + 1, 
    };
  },
  methods: {
    async fetchListByUserId() {
      const userId = 6;
      const url = `schedule/userId/${userId}/${this.year}/${this.month}`
      const { data } = await this.$axios.get(url);
      console.log("이거가져옴" + data); 
      this.list = data;
      this.todaySchedule();  
      
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
        this.firstTodaySchedule = null; // 오늘 일정이 없을 경우
      }
    },

  },
  mounted() {
    this.day = this.$dayjs().format('YYYY-MM-DDTHH:mm:ss');
    this.fetchListByUserId();
  },
};
</script>

<style scoped></style>
