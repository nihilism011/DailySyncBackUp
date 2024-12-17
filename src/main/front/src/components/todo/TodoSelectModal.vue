<template>

    <div v-if="isVisible" class="popup-box type-modal">
      <div class="popup-cont">
        <div class="popup-tit-wrap">
          <div class="tit">선택된 날짜: {{ selectedDate }}</div>
        </div>
        <div class="todo-items">
          <div class="item" v-for="(item, index) in items" :key="index">
            <div class="item-info">
              <span class="item-tit">{{ item.title }}</span>
              <span class="item-group-tit">({{ item.groupTitle || '직접 생성' }})</span>
            </div>
            <template v-if="item.status == 'new'">
              <span class="item-date">{{ formatTimeWithoutSeconds(item.checkedTime) }}</span>
            </template>
            <template v-else>
              <span class="item-date">
                <div> 생성일 : {{ formatDateTime(item.createdAt) }}</div>
                <div> 완료일 : {{ formatDateTime(item.checkedTime) }}</div>
              </span>
            </template>
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
      };
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
          this.fnMySelectList();
        }
      },
    },
    methods: {
      closeModal() {
        this.$emit("close");
      },
      async fnMySelectList() {
        if (!this.selectedDate) return; 
        const url = `todo/list/${this.selectedDate}`;
        try {
          const { data } = await this.$axios.get(url); 
          console.log(data); 
          this.items = data; 
        } catch (error) {
          console.error("데이터 가져오기 실패:", error);
        }
      },
    
      formatTimeWithoutSeconds(time) {
        if (!time) return "활동 안함";
        const date = new Date(time);
        return `${date.getHours()}시 ${date.getMinutes()}분`;
      },
      
      formatDateTime(dateTime) {
        if (!dateTime) return "활동 안함";
        const date = new Date(dateTime);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, "0"); 
        const day = String(date.getDate()).padStart(2, "0");
        const hours = String(date.getHours()).padStart(2, "0");
        const minutes = String(date.getMinutes()).padStart(2, "0");
        return `${year}-${month}-${day} ${hours}시${minutes}분`;
      },
    },
  };
  </script>
  
<style lang="scss" scoped>
.todo-items {
  margin-top: 20px;
  display: flex;
  flex-wrap: wrap;
  border-top: 1px solid var(--color-contrastyC8);
  border-bottom: 1px solid var(--color-contrastyC8);
  overflow-y: auto;
  height: 402px;
  .item {
    display: flex;
    width: 100%;
    border-bottom: 1px solid var(--color-contrastyC8);
    padding: 10px 10px;
    position: relative;
    min-height: 50px;
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
      right: 0; top: 10px;
    }
    &:last-child {
      border-bottom: 0;
    }
  }
}
</style>
  