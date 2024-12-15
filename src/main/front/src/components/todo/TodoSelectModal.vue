<template>
    <div v-if="isVisible" class="modal-overlay">
      <div class="modal-content">
        <div class="modal-header">
          <h3>선택된 날짜: {{ selectedDate }}</h3>
          <button @click="closeModal" class="close-btn">닫기</button>
        </div>
  
        <div class="modal-body">
          <div class="item" v-for="(item, index) in items" :key="index">
            <div class="item-content">
              <div>
                <span class="item-group-title">{{ item.groupTitle || '직접 생성' }}</span>
                <span class="item-title">{{ item.title }}</span>
              </div>
              <div v-if="item.status == 'new'">
                <span class="item-checked-time">{{ formatTimeWithoutSeconds(item.checkedTime) }}</span>
              </div>
              <div v-else>
                <span class="item-checked-time">
                  <div> 생성일 : {{ formatDateTime(item.createdAt) }}</div>
                  <div> 완료일 : {{ formatDateTime(item.checkedTime) }}</div>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
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
        const userId = 5; 
        const url = `todo/list/${userId}/${this.selectedDate}`;
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
    z-index: 1000;
  }
  
  .modal-content {
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    width: 800px;
    height: 700px;
    overflow-y: auto; /* 내용이 많아지면 스크롤 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  }
  
  .modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
    font-size: 18px;
    font-weight: bold;
  }
  
  .close-btn {
    padding: 8px 16px;
    background-color: #f44336;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .close-btn:hover {
    background-color: #e53935;
  }
  
  .modal-body {
    margin-bottom: 20px;
  }
  
  .item {
    background-color: #fafafa;
    margin: 10px 0;
    padding: 10px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .item-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 14px;
  }
  
  .item-group-title {
    font-weight: bold;
    color: #4caf50;
    margin-right: 10px;
  }
  
  .item-title {
    color: #2196f3;
  }
  
  .item-checked-time {
    color: #f44336;
    font-style: italic;
  }
  </style>
  