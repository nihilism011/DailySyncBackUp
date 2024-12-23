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
import 'dayjs/locale/ko';  // 한국어 로케일 불러오기
import dayjs from 'dayjs';  // dayjs 불러오기
export default {
  props: {
      popupState: Boolean,
      schedule: Object,
  },
  watch: {
      schedule(newSchedule) {
          this.editedSchedule = { ...newSchedule };
          this.formatStartTime(); // 모달이 열릴 때, 시작 시간을 포맷
          this.formatEndTime();   // 모달이 열릴 때, 끝 시간을 포맷
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
    formatStartTime() {
    // 시작 시간을 한국어 AM/PM 형식으로 포맷
    if (this.editedSchedule.startTime) {
      this.editedSchedule.startTime = dayjs(this.editedSchedule.startTime)
        .locale('ko')  // 한국어 로케일
        .format('YYYY-MM-DD hh:mm A'); // 오전/오후 12시간 형식
    }
  },
  formatEndTime() {
    // 끝 시간을 한국어 AM/PM 형식으로 포맷
    if (this.editedSchedule.endTime) {
      this.editedSchedule.endTime = dayjs(this.editedSchedule.endTime)
        .locale('ko')
        .format('YYYY-MM-DD hh:mm A');
    }
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
      return this.$dayjs(date).locale('ko').format('YYYY-MM-DD hh:mm A'); 
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