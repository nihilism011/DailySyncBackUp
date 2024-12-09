<template>
  <div>
    <div class="list-container">
      Group
      <div class="list-item" v-for="(item, index) in list" 
      :key="index" @click="selectGroup(item.id)">
        <div class="title">{{ item.title }}</div>
        <div class="actions">
          <button @click="openEditModal(item)" class="edit-btn">수정</button>
          <button @click="deleteGroup(item.id)" class="delete-btn">삭제</button>
        </div>
      </div>
      <div class="list-item">
        <div class="title">
          <div style="color: blue;" @click="openAddItemModal">+ 그룹 추가</div>
        </div>
      </div>
    </div>

    <!-- 그룹 추가 모달 -->
    <Modal
      :isVisible="isModalVisible"
      :mode="'create'"
      @close="closeModal"
      @save-item="fnMyGroup"
    />

    <!-- 그룹 수정 모달 -->
    <Modal
      :isVisible="isEditModalVisible"
      :mode="'update'"
      :item="selectedItem"
      @close="closeEditModal"
      @save-item="fnMyGroup"
    />
  </div>
</template>

<script>
import Modal from './GroupCreateModal.vue';

export default {
  components: {
    Modal,
  },
  data() {
    return {
      list: [],
      isModalVisible: false,
      isEditModalVisible: false,
      selectedItem: null, // 선택된 그룹을 저장
    };
  },
  methods: {
    openAddItemModal() {
      this.isModalVisible = true;
    },
    closeModal() {
      this.isModalVisible = false;
    },
    openEditModal(item) {
      this.selectedItem = item; 
      this.isEditModalVisible = true;
    },
    closeEditModal() {
      this.isEditModalVisible = false;
      this.selectedItem = null; 
    },
    async fnMyGroup() {
      const userId = 5;
      const url = `todo/group/${userId}`;
      const { data } = await this.$axios.get(url);
      this.list = data; 
    },
    selectGroup(groupId) {
      this.$emit('updateSelectedGroup', groupId);
    },
    async deleteGroup(id) {
      if (confirm("정말 삭제할거냐? 되돌릴 수 없다!")) {
        const url = `todo/group/${id}`;
        try {
          await this.$axios.delete(url);
          this.fnMyGroup(); // 삭제 후 최신 목록을 가져옵니다.
          alert("아이템 삭제");
        } catch (error) {
          alert("삭제 실패: " + error);
        }
      } else {
        alert("삭제 취소");
      }
    },
  },
  mounted() {
    this.fnMyGroup(); // 페이지 로드 시 그룹 목록을 불러옵니다.
  },
};
</script>

<style scoped>
.list-container {
  margin-top: 20px;
}

.list-item {
  display: flex;
  margin: 10px 0;
  height: 70px;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 8px;
  position: relative;
  transition: background-color 0.3s ease;
}

.list-item:hover {
  background-color: #faf7a3;
}

.title {
  width: 200px;
  font-size: 20px;
  align-items: center;
  justify-content: start;
  display: flex;
}

.actions {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  display: none;
}

.list-item:hover .actions {
  display: block;
}

.edit-btn,
.delete-btn {
  margin-left: 10px;
  padding: 5px 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  background-color: #f0f0f0;
  transition: background-color 0.3s;
}

.edit-btn:hover {
  background-color: #4CAF50;
  color: white;
}

.delete-btn:hover {
  background-color: #f44336;
  color: white;
}
</style>
