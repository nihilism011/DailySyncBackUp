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
              {{ formatDate(item.startTime) }} ~ {{ formatDate(item.endTime) }}
            </div>
          </div>
        </div>
      </div>
      <div class="pop-btn-wrap">
        <button class="btn-default cancel" @click="closeModal">닫기</button>
      </div>
    </div>
    <div class="dimmed">dim</div>
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
      this.$emit('closePopup')
    },
    formatDate(date) {
      if (!date) return ''
      const formattedDate = new Date(date)
      return formattedDate.toLocaleDateString()
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
