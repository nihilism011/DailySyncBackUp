<template>
  <div v-if="isVisible" class="popup-box type-modal">
    <div class="popup-cont">
      <div class="popup-tit-wrap">
        <div class="tit">선택된 날짜: {{ selectedDate }}</div>
      </div>
      <div class="todo-items">
        <div v-if="items.length">
          <div
            class="item"
            v-for="(item, index) in items"
            :key="index"
            :class="item.checkedTime != null ? 'checked' : ''"
          >
            <div class="item-info">
              <span class="item-tit">{{ item.title }}</span>
              <div v-if="item.groupTitle">
                <span class="item-group-tit">({{ item.groupTitle }})</span>
              </div>
            </div>
            <template v-if="item.status == 'new'">
              <span class="item-date">{{ formatTimeWithoutSeconds(item.checkedTime) }}</span>
            </template>
            <template v-else>
              <span class="item-date" style="text-align: right">
                <div>작성일 : {{ formatDate(item.createdAt) }}</div>
                <div>완료 : {{ formatTime(item.checkedTime) }}</div>
              </span>
            </template>
          </div>
        </div> 
        <div v-else>
        태환씨 여기 부탁드립니다!!
        </div> 
      </div>
      <div class="pop-btn-wrap">
        <button @click="closeModal" class="btn-default cancel">닫기</button>
      </div>
    </div>
    <div class="dimmed">dim</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [],
    }
  },
  props: {
    isVisible: {
      type: Boolean,
      default: false,
    },
    selectedDate: {
      type: String,
      default: null,
    },
  },
  watch: {
    isVisible(newVal) {
      if (newVal) {
        this.fnMySelectList()
      }
    },
  },
  methods: {
    closeModal() {
      this.$emit('close')
    },
    async fnMySelectList() {
      if (!this.selectedDate) return
      const url = `todo/list/${this.selectedDate}`
      try {
        const { data } = await this.$axios.get(url)
        console.log(data)
        this.items = data
      } catch (error) {
        console.error('데이터 가져오기 실패:', error)
      }
    },
    // 날짜만 반환 (YYYY-MM-DD)
    formatDate(dateTime) {
      if (!dateTime) return ''
      const date = new Date(dateTime)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    },
    // 시간만 반환 (HH시 MM분)
    formatTime(dateTime) {
      if (!dateTime) return ''
      const date = new Date(dateTime)
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      return `${hours}시 ${minutes}분`
    },
    formatTimeWithoutSeconds(time) {
      if (!time) return ''
      const date = new Date(time)
      return `${date.getHours()}시 ${date.getMinutes()}분`
    },
  },
}
</script>

<style lang="scss" scoped>
.todo-items {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  border-top: 1px solid var(--color-contrastyC8);
  border-bottom: 1px solid var(--color-contrastyC8);
  overflow-y: auto;
  height: 402px;
  box-sizing: border-box;
  .item {
    display: flex;
    width: 100%;
    border-bottom: 1px dashed var(--color-contrastyC8);
    padding: 10px 10px 10px 24px;
    position: relative;
    min-height: 50px;
    background: url('@/assets/images/ico/ico_done1_2.png') left center / 18px no-repeat;
    &.checked {
      background-image: url('@/assets/images/ico/ico_done.png');
    }
    &-info {
      display: flex;
      align-items: center;
      width: calc(100% - 180px);
      .item-tit {
        font-size: 14px;
        font-weight: bold;
      }
      .item-group-tit {
        font-size: 12px;
        color: var(--color-contrastyA);
        margin-left: 10px;
      }
    }
    &-date {
      font-size: 12px;
      color: var(--color-contrastyA);
      position: absolute;
      right: 0;
      top: 10px;
    }
    &:last-child {
      border-bottom: 0;
    }
  }
}
</style>
