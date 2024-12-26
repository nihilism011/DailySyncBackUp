<template>
  <div class="popup-box type-modal" v-if="popupState">
    <div class="popup-cont type2">
      <div class="popup-tit-wrap">
        <div class="tit">수정</div>
      </div>
      <div class="schedule-info">
        <div class="ip-list">
          <div class="tit-box">
            <label for="" class="tit">제목</label>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input
                type="text"
                v-model="editedSchedule.title"
                id="title"
              />
              </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="" class="tit">시작 시간</label>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input
                type="text"
                v-model="formattedStartTime"
                id="startTime"
              />
             </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="" class="tit">끝 시간</label>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input
                type="text"
                v-model="formattedEndTime"
                id="endTime"
              />
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="" class="tit">설명</label>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input
              type="text"
              v-model="editedSchedule.description"
              id="description"
              />
            </div>
          </div>
        </div>
        <div>
          <div class="pop-btn-wrap">
            <button @click="saveSchedule" class="btn-default submit">저장</button>
            <button class="btn-default cancel" @click="closeModal">닫기</button>
          </div>
        </div>
      </div>
    </div>
    <div class="dimmed">dim</div>
  </div>
</template>
<script>
import 'dayjs/locale/ko';
export default {
  props: {
      popupState: Boolean,
      schedule: Object,
  },
  watch: {
    dailyList(newSchedule) {
      if (newSchedule && Array.isArray(newSchedule)) {
        this.$set(this.calendarOptions, 'events', newSchedule);  // 배열을 새로 갱신
      }
    },
  },
  emits: ['fnScheduleList', 'fnDayList', 'saveSchedule', 'closePopup'],
  data() {
      return {
          editedSchedule: { ...this.schedule  },
      };
  },
  computed: {
    // 시작 시간을 포맷된 형태로 반환
    formattedStartTime: {
      get() {
        return this.editedSchedule.startTime
          ? this.formatDate(this.editedSchedule.startTime)
          : '';
      },
      set(value) {
        // 사용자가 입력한 값을 dayjs로 변환하여 저장
        const parsedDate = this.$dayjs(value, 'YYYY-MM-DD A HH:mm');
        if (parsedDate.isValid()) {
          this.editedSchedule.startTime = parsedDate.toISOString();
        }
      }
    },
    formattedEndTime: {
      get() {
        return this.editedSchedule.endTime
          ? this.formatDate(this.editedSchedule.endTime)
          : '';
      },
      set(value) {
        const parsedDate = this.$dayjs(value, 'YYYY-MM-DD A HH:mm');
        if (parsedDate.isValid()) {
          this.editedSchedule.endTime = parsedDate.toISOString();
        }
      }
    }
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
            start: this.$dayjs(this.editedSchedule.startTime).format('YYYY-MM-DDTHH:mm:ss'),
            end: this.$dayjs(this.editedSchedule.endTime).format('YYYY-MM-DDTHH:mm:ss'),
        };
        const url = `schedule/update/${this.editedSchedule.id}`;
        const response = await this.$axios.patch(url, formattedSchedule);
        if (response.status) {
          alert('일정이 저장되었습니다.');
          this.$emit('saveSchedule', formattedSchedule)
          this.$emit('fnScheduleList', this.day);
          this.$emit('fnDayList', this.day);
          this.closeModal(); 
      }
  },
    formatDate(date) {
      return date ? this.$dayjs(date).locale('ko').format('YYYY-MM-DD A HH:mm') : '';
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
 .btn-default {
  margin-bottom: 16px;
}
.schedule {
  &-wrap {
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0px 0px 10px 0 rgba(0, 0, 0, 0.3);
    height: 700px;
    .btn-box {
      display: flex;
      align-items: center;
      gap: 0 10px;
      color: var(--base-white);
      margin-top: 20px;
      button {
        border: 0;
        width: 100%;
        height: 36px;
        border-radius: 4px;
        &.submit {
          background-color: var(--btn-blue);
        }
        &.cancel,
        &.remove {
          background-color: var(--btn-gray3);
        }
      }
    }
  }
}
</style>