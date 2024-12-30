<template>
  <button :disabled="isUpdate" @click="fnAdd" class="btn-default">일정등록</button>
  <div class="schedule-wrap">
    <template v-if="isAdd || isUpdate">
      <div class="ip-list">
        <div class="tit-box">
          <label class="tit" for="title">제목</label>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input
              type="text"
              v-model="newSchedule.title"
              id="title"
              placeholder="제목을 입력해주세요"
            />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <label class="tit" for="startTime">시작 일정</label>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input type="datetime-local" v-model="newSchedule.startTime" id="startTime" />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <label class="tit" for="endTime">종료 일정</label>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input type="datetime-local" v-model="newSchedule.endTime" id="endTime" />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <label class="tit" for="description">일정 설명</label>
        </div>
        <div class="bot-box">
          <div class="text-box">
            <textarea
              v-model="newSchedule.description"
              id="description"
              placeholder="일정 설명"
            ></textarea>
          </div>
        </div>
      </div>
      <div class="btn-box">
        <button @click="fnSave()" class="submit">저장</button>
        <button @click="fnCancle()" class="cancel">취소</button>
      </div>
    </template>
    <template v-else>
      <div class="ip-list">
        <div class="tit-box">
          <label class="tit" for="title">제목</label>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input type="text" :value="inputSchedule.title" id="title" readonly />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <label class="tit" for="startTime">시작 일정</label>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input
              type="text"
              :value="formatDate(inputSchedule.startTime)"
              id="startTime"
              readonly
            />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <label class="tit" for="endTime">종료 일정</label>
        </div>
        <div class="bot-box">
          <div class="ip-box">
            <input type="text" :value="formatDate(inputSchedule.endTime)" id="endTime" readonly />
          </div>
        </div>
      </div>
      <div class="ip-list">
        <div class="tit-box">
          <label class="tit" for="description">일정 설명</label>
        </div>
        <div class="bot-box">
          <div class="text-box">
            <textarea
              id="description"
              placeholder="일정 설명"
              :value="inputSchedule.description"
              readonly
            ></textarea>
          </div>
        </div>
      </div>
      <div class="btn-box">
        <button v-if="inputSchedule.id" @click="fnUpdate" class="submit">수정</button>
        <button v-if="inputSchedule.id" @click="fnRemove" class="remove">삭제</button>
      </div>
    </template>
  </div>
</template>
<script>
export default {
  props: {
    dailyList: Array,
    schedule: Object,
    day: String,
    inputSchedule: Object,
  },
  watch: {
    dailyList(newSchedule) {
      console.log('일정 리스트:', newSchedule)
      this.newSchedule = [...newSchedule]
    },
  },
  emits: ['fnScheduleList', 'fnDayList', 'inputedSchedule'],
  data() {
    return {
      year: new Date().getFullYear(),
      month: new Date().getMonth() + 1,
      isUpdate: false,
      isAdd: false,
      newSchedule: {
        title: '',
        startTime: '',
        endTime: '',
        description: '',
      },
    }
  },
  methods: {
    formatDate(date) {
       return date ? this.$dayjs(date).locale('ko').format('YYYY-MM-DD A hh:mm') : ''
    },
    fnUpdate() {
      this.isUpdate = true
      this.newSchedule = { ...this.inputSchedule }
    },
    async inputedSchedule(id) {
      console.log('inputedSchedule 호출', id)
      const response = await this.$axios.get(`schedule/userId/id/${id}`)
      if (response) {
        this.newSchedule = {
          ...response.data,
        }
      } else {
        console.log('일정이 없습니다.')
      }
    },
    fnCancle() {
      this.isAdd = false
      this.isUpdate = false
      this.newSchedule = { title: '', startTime: '', endTime: '', description: '' }
    },
    async fnRemove() {
      if (confirm('일정을 삭제하시겠습니까?')) {
        const id = this.inputSchedule.id
        const response = await this.$axios.delete(`schedule/delete/${id}`)
        if (response) {
          this.$emit('fnScheduleList', this.day)
          this.$emit('fnDayList', this.day)
          this.$emit('inputedSchedule', null) // 화면에서 해당 일정 정보 지움
        }
      }
    },
    fnAdd() {
      this.isAdd = true
      this.newSchedule = { title: '', startTime: '', endTime: '', description: '' }
    },
    async fnSave() {
      if (!this.newSchedule.title.trim()) {
        alert('제목을 입력해주세요.')
        return
      }

      const startTime = new Date(this.newSchedule.startTime).toISOString();
      const endTime = new Date(this.newSchedule.endTime).toISOString();

      if (isNaN(startTime.getTime()) || isNaN(endTime.getTime())) {
        alert('시작 날짜 또는 끝 날짜가 올바르지 않습니다. 유효한 날짜 형식을 입력해 주세요.')
        return
      }

      if (startTime > endTime) {
        alert('끝나는 시간이 시작 시간보다 이전입니다. 날짜를 확인해 주세요.')
        return
      }
      const schedule = this.isAdd ? this.newSchedule : this.newSchedule
      const formattedSchedule = {
        ...schedule,
        startTime: this.$dayjs(schedule.startTime).format('YYYY-MM-DDTHH:mm:ss'),
        endTime: this.$dayjs(schedule.endTime).format('YYYY-MM-DDTHH:mm:ss'),
      }

      let url
      let method
      if (this.isAdd) {
        url = 'schedule/add'
        method = 'post'
      } else if (this.isUpdate) {
        url = `schedule/update/${schedule.id}`
        method = 'patch'
      }

      try {
        const response = await this.$axios[method](url, formattedSchedule)
        console.log()
        if (response.status) {
          alert('일정이 저장되었습니다.')
          this.isAdd = false
          this.isUpdate = false
          this.$emit('fnScheduleList', this.day)
          this.$emit('fnDayList', this.day)
          this.$emit('inputedSchedule', formattedSchedule.id)
        } else {
          alert(response.message)
        }
      } catch (error) {
        console.error(error)
        alert('오류가 발생했습니다.')
      }
    },
  },
  mounted() {},
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
