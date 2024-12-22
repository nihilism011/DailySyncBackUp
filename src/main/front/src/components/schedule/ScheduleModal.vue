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
            <button  @click="selectSchedule(item)" class="edit-btn">수정</button>
            <button @click="fnDelete(item, index)" class="remove-btn">삭제</button>
          </div>
        </div>
      </div>
      <div class="pop-btn-wrap">
        <button class="btn-default cancel" @click="closeModal">닫기</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    popupState: Boolean,
    searchResults: Array,
  },
  methods: {
    closeModal() {
      this.$emit('closePopup'); 
    },
    selectSchedule(item) {
      this.$emit('editSchedule', item);  // 수정할 일정 아이템을 부모에게 전달
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
      async fnDelete(item) {
      if (confirm('일정을 삭제하시겠습니까?')) {
        const id = item.id;
        const response = await this.$axios.delete(`schedule/delete/${id}`);
        if (response) {
          this.$emit('deleteSchedule', item);  // 삭제된 일정 정보를 부모에게 전달
          this.$emit('closePopup');  // 모달 닫기
        }
      }
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