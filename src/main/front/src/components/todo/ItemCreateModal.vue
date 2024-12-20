<template>
  <div v-if="isVisible" class="popup-box type-modal">
    <div class="popup-cont type2">
      <div class="popup-tit-wrap">
        <div class="tit">
          {{ mode === 'create' ? '아이템 추가' : '아이템 수정' }}
        </div>
      </div>
      <div class="todo-wrap">
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">제목</p>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input type="text" v-model="newItem.title" placeholder="제목 입력" />
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">요일선택</p>
          </div>
          <div class="bot-box">
            <div class="ip-chk-txt">
              <input type="checkbox" id="day7" v-model="newItem.day" value="일" />
              <label for="day7">일</label>
              <input type="checkbox" id="day1" v-model="newItem.day" value="월" />
              <label for="day1">월</label>
              <input type="checkbox" id="day2" v-model="newItem.day" value="화" />
              <label for="day2">화</label>
              <input type="checkbox" id="day3" v-model="newItem.day" value="수" />
              <label for="day3">수</label>
              <input type="checkbox" id="day4" v-model="newItem.day" value="목" />
              <label for="day4">목</label>
              <input type="checkbox" id="day5" v-model="newItem.day" value="금" />
              <label for="day5">금</label>
              <input type="checkbox" id="day6" v-model="newItem.day" value="토" />
              <label for="day6">토</label>
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">우선순위</p>
          </div>
          <div class="bot-box">
            <div class="ip-ra-txt">
              <input type="radio" id="order1" v-model="newItem.itemOrder" value="1" />
              <label for="order1">높음</label>
              <input type="radio" id="order2" v-model="newItem.itemOrder" value="2" />
              <label for="order2">보통</label>
              <input type="radio" id="order3" v-model="newItem.itemOrder" value="3" />
              <label for="order3">낮음</label>
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">자동여부</p>
          </div>
          <div class="bot-box">
            <div class="ip-chk-txt">
              <input type="checkbox" id="auto" v-model="newItem.isAuto" value="1" />
              <label for="auto">Auto</label>
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">오늘부터 적용</p>
          </div>
          <div class="bot-box">
            <div class="ip-chk-txt">
              <input type="checkbox" id="todayCreate" v-model="todayCreate" value="1" />
              <label for="todayCreate">적용</label>
            </div>
          </div>
        </div>
        <div class="pop-btn-wrap">
          <button @click="saveItem" class="btn-default submit">
            {{ mode === 'create' ? '추가' : '수정' }}
          </button>
          <button @click="closeModal" class="btn-default cancel">취소</button>
        </div>
      </div>
    </div>
    <div class="dimmed">dim</div>
  </div>
</template>

<script>
export default {
  props: {
    isVisible: {
      type: Boolean,
      default: false,
    },
    mode: {
      type: String,
      default: 'create',
    },
    groupId: {
      type: Number,
      required: true,
    },
    item: {
      type: Object,
      default: () => ({
        id: '',
        itemOrder: '',
        title: '',
        isAuto: '',
        status: '',
        day: [],
      }),
    },
  },
  data() {
    return {
      newItem: { ...this.item },
      todayCreate : 0
    }
  },
  watch: {
    item(newVal) {
      this.newItem = { ...newVal }
    },
  },
  methods: {
    closeModal() {
      this.todayCreate = 0
      this.$emit('close')
    },
    async saveItem() {
      if (!this.newItem.title || !this.newItem.day.length || !this.newItem.itemOrder) {
        alert('빈칸을 채워주세요')
        return
      }

      try {
        let url

        if (this.mode === 'create') {
          url = 'todo/item'
          await this.$axios.post(url, {
            title: this.newItem.title,
            day: this.newItem.day,
            isAuto: this.newItem.isAuto,
            groupId: this.groupId,
            itemOrder: this.newItem.itemOrder,
            status: 'new',
            todayCreate : this.todayCreate
          })
        } else if (this.mode === 'update') {
          url = 'todo/item'
          await this.$axios.post(url, {
            title: this.newItem.title,
            day: this.newItem.day,
            isAuto: this.newItem.isAuto,
            groupId: this.groupId,
            itemOrder: this.newItem.itemOrder,
            status: 'new',
            todayCreate : this.todayCreate
          })
          const statusUrl = `todo/item/update/status/${this.item.id}`
          await this.$axios.put(statusUrl)
        }

        alert(`${this.mode === 'create' ? '아이템 추가' : '아이템 수정'} 성공`)
        this.$emit('save-item')
        this.newItem.title = ''
        this.newItem.day = []
        this.newItem.isAuto = ''
        this.newItem.itemOrder = ''
        this.todayCreate = 0
        this.closeModal()
      } catch (error) {
        console.error('오류 발생:', error)
        alert(`${this.mode === 'create' ? '아이템 추가' : '아이템 수정'} 실패`)
      }
    },
  },
  
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 500px;
  height: 300px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.modal-footer {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}
</style>