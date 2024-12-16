<template>
  <div class="popup-box type-modal" v-if="popupState">
    <div class="modal-content">
      <button class="close-button" @click="closeModal">닫기</button>
      <div v-if="searchResults.length === 0">검색 결과가 없습니다.</div>
      <div v-else>
        <div v-for="(item, index) in searchResults" :key="index" class="result-item">
          <div class="result-title">
            <div class="title">{{ item.title }}</div>
            <div class="date-range">
              {{ formatDate(item.startTime) }} ~ {{ formatDate(item.endTime) }}
            </div>
          </div>
        </div>
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

<style scoped>
/* 모달 스타일 */
.popup-box {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.modal-content {
  background: white;
  padding: 20px;
  border-radius: 10px;
  max-width: 600px;
  width: 100%;
}
.close-button {
  position: absolute;
  bottom: 10px;
  right: 10px;
  background-color: white;
  color: black;
  border: 1px solid black;
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
}

.close-button:hover {
  background-color: #f0f0f0;
  border-color: #333;
}
</style>
