<template>
    <div class="popup-box type-modal" v-if="popupState">
      <div class="popup-cont type2">
        <div class="schedule-info">
        <div class="info-item">
          <label for="title">제목</label>
          <input 
            type="text" 
            :value="inputSchedule.title" 
            id="title"
            readonly
          />
            </div>
            <div class="info-item">
            <label for="startTime">시작 시간</label>
            <input 
                type="text" 
                :value="formatDate(inputSchedule.startTime)"
                id="startTime"
                readonly
            />
            </div>
            <div class="info-item">
            <label for="endTime">끝 시간</label>
            <input 
                type="text" 
                :value="formatDate(inputSchedule.endTime)"
                id="endTime"
                readonly
            />
            </div>
            <div class="info-item">
            <label for="description">설명</label>
            <input 
                type="text" 
                :value="inputSchedule.description"
                id="description"
                readonly
            />
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
      selectSchedule(item) {
        this.$emit('selectSchedule', item); 
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
  