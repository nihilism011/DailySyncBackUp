<template>
  <div>
    <div class="list-container">
      List
      <div
        class="list-item" v-for="(item, index) in list"
        :key="index" @click="selectGroup(item.id)" >
        <div class="title">
          <input
            type="checkbox"
            style="margin-right: 10px;"
            :checked="item.checkedTime !== null" 
            @change="fetchCheckByListID(item.id)"
          />
          <div>{{ item.title }}</div>
        </div>
        <div class="actions">
          <button @click="openEditModal(item)" class="edit-btn">수정</button>
          <button @click="deleteGroup(item.id)" class="delete-btn" >삭제</button>
        </div>
      </div>
      <div class="list-item" @click="openAddItemModal">
        <div class="title">
          <div style="color: blue;" >+ 리스트 추가</div>
        </div>
      </div>
    </div>
    <!-- 리스트 추가 모달-->
    <Modal
      :isVisible="isModalVisible"
      :mode="'create'"
      @close="closeModal"
      @save-item="fetchListByUserId"
    />
    <Modal
      :isVisible="isEditModalVisible"
      :mode="'update'"
      :item="selectedItem"
      @close="closeEditModal"
      @save-item="fetchListByUserId"
    />
  </div>
</template>

<script>
import Modal from './ListCreateModal.vue';
export default {
  components: {
    Modal
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
    openEditModal(item) {
      this.selectedItem = item; 
      this.isEditModalVisible = true;
      console.log(item);
    },
    closeEditModal() {
      this.isEditModalVisible = false;
      this.selectedItem = null; 
    },
    async deleteGroup(id) {
      if(confirm("삭제할거임?")){
         const url = `todo/list/${id}`
        try {
          await this.$axios.delete(url);
          this.fetchListByUserId();
          alert("삭제됐음");
        } catch (error) {
          alert("삭제실패" + error);
        }
      }
    },
    openAddItemModal() {
      this.isModalVisible = true;
    },
    closeModal() {
      this.isModalVisible = false;
    },
    async fetchCheckByListID(id) {
      const url = `todo/list/update/check/${id}`;
      await this.$axios.put(url);
      await this.fetchListByUserId(); 
    },
    async fetchListByUserId() {
      const userId = 5;
      const url = `todo/list/today/${userId}`
      const { data } = await this.$axios.get(url);
      console.log("이거가져옴" + data); 
      this.list = data;
      
    },
  },
  mounted() {
    this.fetchListByUserId();
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
