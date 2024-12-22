<template>
    <div class="popup-box type-modal" v-if="popupState">
      <div class="popup-cont type2">
        <div class="schedule-info">
            <div class="info-item">
          <label for="title">제목</label>
          <input
            type="text"
            v-model="editedSchedule.title"
            id="title"
          />
        </div>
        <div class="info-item">
          <label for="startTime">시작 시간</label>
          <input
            type="text"
            v-model="editedSchedule.startTime"
            id="startTime"
          />
        </div>
        <div class="info-item">
          <label for="endTime">끝 시간</label>
          <input
            type="text"
            v-model="editedSchedule.endTime"
            id="endTime"
          />
        </div>
        <div class="info-item">
          <label for="description">설명</label>
          <input
            type="text"
            v-model="editedSchedule.description"
            id="description"
          />
          </div>
          <div>
            <div class="pop-btn-wrap">
              <button @click="saveSchedule" class="add-schedule-btn">저장</button>
              <button class="btn-default cancel" @click="closeModal">닫기</button>
            </div>
          </div>
        </div>
      </div>
      <div class="dimmed">dim</div>
    </div>
  </template>
  <script>
  export default {
    props: {
        popupState: Boolean,
        schedule: Object,
    },
    watch: {
        schedule(newSchedule) {
            this.editedSchedule = { ...newSchedule };
        },
    },
    data() {
        return {
            editedSchedule: { ...this.schedule  }, 
        };
    },
    methods: {
      closeModal() {
        this.$emit('closePopup')
      },
      async saveSchedule() {
        if (!this.editedSchedule.title.trim()) {
        alert('제목을 입력해주세요.');
        return; 
        }
        const startTime = new Date(this.editedSchedule.startTime);
        const endTime = new Date(this.editedSchedule.endTime);
        if (isNaN(startTime.getTime()) || isNaN(endTime.getTime())) {
            alert('시작 날짜 또는 끝 날짜가 올바르지 않습니다. 유효한 날짜 형식을 입력해 주세요.');
            return;
        }
        if (startTime > endTime) {
            alert('끝나는 시간이 시작 시간보다 이전입니다. 날짜를 확인해 주세요.');
            return;
        }
        const formattedSchedule = {
            ...this.editedSchedule,
            startTime: this.$dayjs(this.editedSchedule.startTime).format('YYYY-MM-DDTHH:mm:ss'),
            endTime: this.$dayjs(this.editedSchedule.endTime).format('YYYY-MM-DDTHH:mm:ss'),
        };

        const url = `schedule/update/${this.editedSchedule.id}`;
        const response = await this.$axios.patch(url, formattedSchedule);
        if (response.status) {
          alert('일정이 저장되었습니다.');
          this.$emit('saveSchedule', formattedSchedule.id)
          this.closeModal(); // 수정 후 모달 닫기
        } 
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