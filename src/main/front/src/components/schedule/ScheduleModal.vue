<template>
  <div class="popup-box type-modal" v-if="popupState">
    <div class="popup-cont type2">
      <div class="popup-tit-wrap">
        <h2 class="tit">검색결과</h2>
      </div>
      <div class="search-list">
        <div v-if="searchResults.length === 0">검색 결과가 없습니다.</div>
        <div v-else>
          <div v-for="(item, index) in searchResults" :key="index" class="list-item">
            <div class="tit">{{ item.title }}</div>
            <div class="date-range">
              {{ getDateRange(item.startTime, item.endTime) }}
            </div>
            <button  @click="selectSchedule(item)">수정</button>
            <button @click="fnDelete">삭제</button>
          </div>
        </div>
      </div>
      <div class="pop-btn-wrap">
        <button class="btn-default cancel" @click="closeModal">닫기</button>
      </div>
    </div>
    <div class="dimmed">dim</div>
  </div>
  <!-- <ScheduleInfoModal 
   
  /> -->
</template>

<script>
//import ScheduleInfoModal from './ScheduleInfoModal.vue'
export default {
  // components: {
  //   ScheduleInfoModal,
  // },
  props: {
    popupState: Boolean,
    searchResults: Array,
  },
  methods: {
   
    closeModal() {
      this.$emit('closePopup'); // 부모로 모달 닫기 이벤트 전송
    },
    selectSchedule(item) {
      this.$emit('selectSchedule', item); // 수정할 일정 선택
      this.closeModal(); 
    },
    formatDate(date) {
      if (!date) return ''
      const formattedDate = new Date(date)
      return formattedDate.toLocaleDateString('ko-KR');
      },
      getDateRange(startTime, endTime) {
        const startFormatted = this.formatDate(startTime);
        const endFormatted = this.formatDate(endTime);

        // startTime이 endTime보다 클 경우 순서를 바꿔서 출력
        if (new Date(startTime) > new Date(endTime)) {
          return `${endFormatted} ~ ${startFormatted}`;
        }
        return `${startFormatted} ~ ${endFormatted}`;
      },
  },
}
</script>
<style lang="scss" scoped>
.search {
  &-list {
    padding: 10px;
    margin-top: 10px;
    overflow-y: auto;
    height: 360px;
  }
}
</style>