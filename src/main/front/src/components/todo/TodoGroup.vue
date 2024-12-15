<template>
  <div class="list-container">
    <p class="list-tit">Group</p>
    <div class="list-item" v-for="(item, index) in list" 
    :key="index" @click="selectGroup(item.id)">
      <div class="tit-box">
        <div class="description">{{ item.description }}</div>
        <div class="title">{{ item.title }}</div>
      </div>
      <div class="btn-box">
        <button @click="openEditModal(item)" class="edit-btn">수정</button>
        <button @click="deleteGroup(item.id)" class="remove-btn">삭제</button>
      </div>
    </div>
    <div class="list-item center" @click="openAddItemModal">
      <div class="add">그룹 추가</div>
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
      selectedItem: null, 
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
        const url = `todo/group/update/status/${id}`;
        try {
          await this.$axios.put(url);
          await this.fnMyGroup(); // 삭제 후 최신 목록을 가져옵니다.
          this.$emit('updateSelectedGroup', null);
          alert("그룹 삭제");
        } catch (error) {
          alert("삭제 실패: " + error);
        }
      } else {
        alert("삭제 취소");
      }
    },
  },
  mounted() {
    this.fnMyGroup(); 
  },
};
</script>

<style scoped></style>
