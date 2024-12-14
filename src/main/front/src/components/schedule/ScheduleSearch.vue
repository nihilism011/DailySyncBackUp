<template>
  <div>
    <div class="form-container">
      <div>
        <select v-model="form.searchType">
          <option value="">선택해주세요</option>
          <option value="year">연도</option>
          <option value="yearMonth">월</option>
          <option value="title">제목</option>
          <option value="range">기간설정</option>
        </select>
        <div v-if="form.searchType === 'year'">
          <select v-model="form.year">
            <option v-for="(year, index) in years" :key="index" :value="year">{{ year }}</option>
          </select>
        </div>
        <div v-if="form.searchType === 'yearMonth'">
          <select v-model="form.year">
            <option v-for="(year, index) in years" :key="index" :value="year">{{ year }}</option>
          </select>
          <select v-model="form.month">
            <option v-for="(month, index) in months" :value="month" :key="index">{{ month }}</option>
          </select>
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
        year: '2024',      
        month: '12',     
        startTime: '', 
        endTime: '',    
      },
      list: [], 
      years:[],
      months:[
        1,2,3,4,5,6,7,8,9,10,11,12
      ],
      
    };
    
  },
  methods: {
    initYears(){
      const currentYear = new Date().getFullYear();
      const startYear = currentYear - 14;
      for(let year = startYear; year <= currentYear; year++){
        this.years.push(year);
      }
    },
    async fnSearch() {
      let url = ''; 
      if (this.form.searchType === 'year' && this.form.year) {
        url = `schedule/date/${this.form.year}`;
      }
      else if (this.form.searchType === 'yearMonth'  && this.form.year && this.form.month) {
        url = `schedule/date/${this.form.year}/${this.form.month}`;
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
          this.$emit('searchResult', data);  // 검색 결과 부모로 전달
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
  mounted() {
    this.initYears();
  },
};
</script>

<style scoped></style>