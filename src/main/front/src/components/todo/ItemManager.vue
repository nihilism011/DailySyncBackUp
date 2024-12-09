<template>
    <div>
      <button @click="openAddItemModal">아이템 추가</button>
  
      <div v-if="items.length">
        <h3>아이템 목록</h3>
        <ul>
          <li v-for="(item, index) in items" :key="index">
            <strong>{{ item.title }}</strong> - {{ item.days.join(', ') }}
            <span v-if="item.isAuto == 1">{{item.day}}</span>
          </li>
        </ul>
      </div>
  
      <!-- AddItemModal 컴포넌트를 가져와서, isVisible이 true일 때 모달을 표시 -->
      <AddItemModal :isVisible="isModalVisible" @close="closeModal" @add-item="handleAddItem" />
    </div>
  </template>
  
  <script>
  import AddItemModal from './AddItemModal.vue';  // 모달 컴포넌트 임포트
  
  export default {
    components: {
      AddItemModal
    },
    data() {
      return {
        items: [],  // 아이템 목록
        isModalVisible: false,  // 모달 표시 여부
      };
    },
    methods: {
      openAddItemModal() {
        this.isModalVisible = true;  // 모달 열기
      },
      closeModal() {
        this.isModalVisible = false;  // 모달 닫기
      },
      handleAddItem(newItem) {
        this.items.push(newItem);  // 새 아이템 목록에 추가
      }
    }
  };
  </script>
  
  <style scoped>
  /* 스타일 */
  </style>
  