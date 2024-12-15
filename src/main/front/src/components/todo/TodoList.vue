<template>
  <div class="list-container">
    <p class="list-tit">List</p>
    <template v-if="list.length === 0" class="list-item" @click="openAddItemModal">
      <div class="title">
        <div style="color: blue;">+ 리스트 추가</div>
      </div>
    </template>
     
    <div v-for="groupId in groupIds" :key="groupId" class="group">
      <p class="list-stit">{{ getGroupTitle(groupId) }}</p>
      <template v-for="item in sortedItemsByGroupAndOrder(groupId, 0)"  :key="item.id">
        <div class="list-item active0">
          <div class="ip-chk-txt">
            <input
              type="checkbox"
              :id="item.id"
              :checked="item.checkedTime !== null"
              @change="fetchCheckByListID(item.id)"
            />
            <label :for="item.id">
              <span>{{ item.title }}</span>
            </label>
          </div>
          <div class="btn-box">
            <button @click="openEditModal(item)" class="edit-btn" title="수정">수정</button>
            <button @click="deleteGroup(item.id)" class="remove-btn" title="삭제">삭제</button>
        </div>
        </div>
      </template>
      <template v-for="order in [1, 2, 3]" :key="order">
        <div
          v-for="item in sortedItemsByGroupAndOrder(groupId, order)"
          :key="item.id"
          class="list-item"
          :class="'active'+order"
          @click="selectGroup(item.id)"
        >
          <div class="ip-chk-txt">
            <input
              type="checkbox"
              :id="item.id"
              :checked="item.checkedTime !== null"
              @change="fetchCheckByListID(item.id)"
            />
            <label :for="item.id">{{ item.title }}</label>
          </div>
          <div class="btn-box">
            <button @click="openEditModal(item)" class="edit-btn" title="수정">수정</button>
            <button @click="deleteGroup(item.id)" class="remove-btn" title="삭제">삭제</button>
          </div>
        </div>
      </template>
    </div>

    <p class="list-stit">추가한 일정</p>
    <div v-for="groupId in groupIds" :key="groupId" class="group">
      <div v-for="item in sortedItemsByGroupAndOrder(groupId, 4)" :key="item.id" class="list-item">
        <div class="ip-chk-txt">
          <input
            type="checkbox"
            :id="item.id"
            :checked="item.checkedTime !== null"
            @change="fetchCheckByListID(item.id)"
          />
          <label :for="item.id">{{ item.title }}</label>
        </div>
        <div class="date">{{ formatDate(item.createdAt) }} 생성</div>
        <div class="btn-box">
          <button @click="openEditModal(item)" class="edit-btn" title="수정">수정</button>
          <button @click="deleteGroup(item.id)" class="remove-btn" title="삭제">삭제</button>
        </div>
      </div>
    </div>

    <div v-if="list.length > 0" class="list-item center" @click="openAddItemModal">
        <div class="add">리스트 추가</div>
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
      console.log(data);
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

<style scoped></style>