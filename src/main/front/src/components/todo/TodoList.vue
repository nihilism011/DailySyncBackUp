<template>
  <div class="list-container">
    List
    <div v-if="list.length === 0" class="list-item" @click="openAddItemModal">
      <div class="title">
        <div style="color: blue;">+ 리스트 추가</div>
      </div>
    </div>
    <div>
   
      <div v-for="groupId in groupIds" :key="groupId" class="group">
    
        <h1>{{ getGroupTitle(groupId) }}</h1>

        <div v-for="item in sortedItemsByGroupAndOrder(groupId, 0)"  :key="item.id" class="list-item urgent">
          <div class="title">
            <input
              type="checkbox"
              style="margin-right: 10px; width: 20px;"
              :checked="item.checkedTime !== null"
              @change="fetchCheckByListID(item.id)"
            />
            <div style="font-size: 12px; margin-right: 2px;">{{ item.groupTitle }}</div>
            <div>{{ item.title }}</div>
          </div>
          <div class="actions">
            <button @click="openEditModal(item)" class="edit-btn">수정</button>
            <button @click="deleteGroup(item.id)" class="delete-btn">삭제</button>
          </div>
        </div>

       
        <div v-for="order in [1, 2, 3]" :key="order" class="order-group">
          <div
            v-for="item in sortedItemsByGroupAndOrder(groupId, order)"
            :key="item.id"
            class="list-item"
            @click="selectGroup(item.id)"
          >
            <div class="title">
              <input
                type="checkbox"
                style="margin-right: 10px; width: 20px;"
                :checked="item.checkedTime !== null"
                @change="fetchCheckByListID(item.id)"
              />
              <div style="font-size: 12px; margin-right: 2px;">{{ item.groupTitle }}</div>
              <div>{{ item.title }}</div>
              
            </div>
            <div class="actions">
              <button @click="openEditModal(item)" class="edit-btn">수정</button>
              <button @click="deleteGroup(item.id)" class="delete-btn">삭제</button>
            </div>
          </div>
        </div>
      </div>
      <h1>추가한 일정</h1>
      <div v-for="groupId in groupIds" :key="groupId" class="group">
        <div v-for="item in sortedItemsByGroupAndOrder(groupId, 4)" :key="item.id" class="list-item">
          <div class="title">
            <input
              type="checkbox"
              style="margin-right: 10px; width: 20px;"
              :checked="item.checkedTime !== null"
              @change="fetchCheckByListID(item.id)"
            />
            <div style="font-size: 12px; margin-right: 2px;">{{ item.groupTitle }}</div>
            <div>{{ item.title }} </div>
          </div>
          <div class="created-at">{{ formatDate(item.createdAt) }} 생성</div>
          <div class="actions">
            <button @click="openEditModal(item)" class="edit-btn">수정</button>
            <button @click="deleteGroup(item.id)" class="delete-btn">삭제</button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="list.length > 0" class="list-item" @click="openAddItemModal">
      <div class="title">
        <div style="color: blue;">+ 리스트 추가</div>
      </div>
    </div>
  </div>

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
  computed: {
    groupIds() {
      return [...new Set(this.list.map(item => item.groupId))];
    }
  },
  methods: {
    getGroupTitle(groupId) {
      const group = this.list.find(item => item.groupId === groupId);
      return group ? group.groupTitle : '이름없음';  
    },
    sortedItemsByGroupAndOrder(groupId, order) {
      return this.list
        .filter(item => item.groupId === groupId && item.listOrder === order)
        .sort((a, b) => a.listOrder - b.listOrder);  
    },

    openEditModal(item) {
      this.selectedItem = item;
      this.isEditModalVisible = true;
    },
    closeEditModal() {
      this.isEditModalVisible = false;
      this.selectedItem = null;
    },
    async deleteGroup(id) {
      if(confirm("삭제할거임?")){
        const url = `todo/list/${id}`;
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
      const url = `todo/list/today/${userId}`;
      const { data } = await this.$axios.get(url);
      console.log("이거가져옴" + data);
      this.list = data;
    },
    formatDate(dateString) {
      const date = new Date(dateString);
      const month = date.getMonth() + 1; // 월은 0부터 시작하므로 +1
      const day = date.getDate();
      return `${month}월 ${day}일`;
    }
  },
  mounted() {
    this.fetchListByUserId();
  }
};
</script>

<style scoped>
.list-container {
  margin-top: 20px;
}

.list-item {
  display: flex;
  margin: 10px 0;
  height: 40px;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 8px;
  position: relative;
}

.list-item:hover {
  background-color: #faf7a3;
}

.urgent {
  background-color: red; /* 긴급 항목은 빨간색 배경 */
  color: white;
}

.title {
  width: 600px;
  font-size: 20px;
  align-items: center;
  justify-content: start;
  display: flex;
}

.created-at {
  margin-left: auto;
  font-size: 14px;
  color: gray;
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