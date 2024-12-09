<template>
   <div>
    <!-- v-calendar의 v-model을 selectedYear, selectedMonth, selectedDateRange와 바인딩 -->
    <v-calendar v-model="selectedYear" type="year" />
    <v-calendar v-model="selectedMonth" type="month" />
    <v-calendar v-model="selectedDateRange" type="range" />
    <!-- VCalendar에 하이라이트된 날짜 범위를 보여줌 -->
    <v-calendar :attributes="attributes" />
    <button @click="search">검색</button>
    <div>
      <input v-model="title" type="text" placeholder="일정"/>
      <button @click="fnClick">검색</button>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      selectedYear: new Date(),  // Date 객체로 초기화
      selectedMonth: new Date(),  // Date 객체로 초기화
      selectedDateRange: [],  // 날짜 범위는 배열로 초기화
      attributes: [], // 날짜 하이라이트 정보
      title: '',    
    };
  },
  watch: {
    // selectedDateRange가 변경되면 attributes를 업데이트
    selectedDateRange(newRange) {
      if (newRange.length === 2) {
        const [startDate, endDate] = newRange;
        this.attributes = [
          {
            highlight: 'blue', // 하이라이트 색상
            dates: [{ start: startDate, span: this.getDateSpan(startDate, endDate) }],
          },
        ];
      }
    },
  },
  methods: {
     search() {
      if (this.selectedYear) {
        alert(`선택한 연도: ${this.selectedYear}`);
      }
      if (this.selectedMonth) {
        alert(`선택한 월: ${this.selectedMonth}`);
      }
      if (this.selectedDateRange.length === 2) {
        alert(`선택한 날짜 범위: ${this.selectedDateRange[0]} ~ ${this.selectedDateRange[1]}`);
      }
    },
  
    fnClick() {
     
    },
  },
  mounted() {
    
  },
}
</script>
<style lang="scss" scoped></style>
