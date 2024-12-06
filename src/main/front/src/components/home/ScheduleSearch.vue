<template>
  <div>
    <div>
      <select v-model="selectedYear">
        <option value="" disabled selected>연도</option>
        <option v-for="year in years" :key="year" :value="year">{{ year }}</option>
      </select>
      <select v-model="selectedMonth">
      <option value="" disabled selected>월</option>
        <option v-for="month in months" :key="month" :value="month">{{ month }}</option>
      </select>
      <input v-model="title" type="text" placeholder="일정"/>
      <button @click="fnClick">검색</button>
    </div>
    <div>
    <input v-model="startDate" type="date" placeholder="시작 날짜" />
    <input v-model="endDate" type="date" placeholder="끝 날짜" />
    <button @click="searchByDateRange">검색</button>
    </div> 
  <div>
  <h1>{{ schedule }}</h1>
  </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      selectedYear: '',  
      selectedMonth: '', 
      title: '',    
      startDate: '',  
      endDate: '',           
      years: [],                  
      months: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'], 
    };
  },
  methods: {
    fnClick() {
      if (this.selectedYear && this.selectedMonth) {
      } else if (this.selectedYear) {
      } else {
        alert('연도와 월을 모두 선택해주세요.');
      }
    },
    searchByDateRange() {
      if (this.startDate && this.endDate) {
        const start = new Date(this.startDate);
        const end = new Date(this.endDate);
        if (start > end) {
          alert('시작 날짜는 끝 날짜보다 이전이어야 합니다.');
        } else {
          this.schedule = `검색 범위: ${this.startDate} ~ ${this.endDate}`;
        }
      } else {
        alert('시작 날짜와 끝 날짜를 모두 선택해주세요.');
      }
    },
  },
  mounted() {
    for (let i = 1900; i <= 2025; i++) {
      this.years.push(i);  // years 배열에 연도를 추가
    }
  },
}
</script>
<style lang="scss" scoped></style>
