<template>
  <div>
    <div class="form-container">
      <div>
        <select v-model="form.searchType">
          <option value="year">연도</option>
          <option value="yearMonth">월</option>
          <option value="title">제목</option>
          <option value="range">기간설정</option>
        </select>
        <div v-if="form.searchType === 'year'">
          <input type="number" v-model="form.year" placeholder="예) 2024 입력" />
        </div>
        <div v-if="form.searchType === 'yearMonth'">
          <input type="number" v-model="form.month" placeholder="예) 2024.01 입력" />
        </div>
        <div v-if="form.searchType === 'range'">
          <input type="date" v-model="form.startTime" placeholder="시작일을 입력하세요" />
          <input type="date" v-model="form.endTime" placeholder="종료일을 입력하세요" />
        </div>
        <div v-if="form.searchType === 'title'">
          <input id="title" type="text" v-model="form.title" placeholder="제목을 입력하세요" />
        </div>
        <button class="search-btn" @click="fnSearch">검색</button>
      </div>
      <div class="list-container">
        <div v-if="list.length === 0">검색 결과가 없습니다.</div>
        <div v-else>
          <div class="list-item" v-for="(item, index) in list" :key="index">
            <div class="title">
              <div>
                {{ item.title }}
                {{ formatDate(item.startTime) }} ~ {{ formatDate(item.endTime) }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        searchType: '', 
        title: '',      
        year: '',      
        month: '',     
        startTime: '', 
        endTime: '',    
      },
      list: [], 
    };
  },
  methods: {
    async fnSearch() {
      let url = ''; 
      if (this.form.searchType === 'year' && this.form.year) {
        url = `schedule/date/${this.form.year}`;
      }
      else if (this.form.searchType === 'yearMonth' && this.form.month) {
        const monthStr = String(this.form.month);
        const [year, month] = monthStr.split('.'); 
        this.form.year = year;  
        this.form.month = month;  
        url = `schedule/date/${year}/${month}`;
      }
      else if (this.form.searchType === 'range' && this.form.startTime && this.form.endTime) {
        const startTime = this.form.startTime;
        const endTime = this.form.endTime;
        url = `schedule/date/range?startTime=${startTime}&endTime=${endTime}`;
      }
      else if (this.form.searchType === 'title' && this.form.title) {
        url = `schedule/title/${this.form.title}`;
      }

      if (url) {
        try {
          const { data } = await this.$axios.get(url);
          this.list = data; 
        } catch (error) {
          console.error("오류 :", error);
        }
      } else {
        console.log("검색할 조건을 입력하세요.");
      }
    },

      formatDate(date) {
      if (!date) return ''; 
      const formattedDate = new Date(date);
      return formattedDate.toLocaleDateString(); 
    },
  },
  mounted() {},
};
</script>

<style scoped></style>