<template>
  <template v-if="!selectedGroup">
    <p class="list-tit">Select Group</p>
  </template>
  <template v-else>
    <p class="list-tit">Item</p>
  </template>
  <div class="list-container">
    <template v-if="selectedGroup">
      <div class="list-item" v-for="(item, index) in items" :key="index">
        <div class="tit-box">
          <div class="title">{{ item.title }}</div>
          <div class="day">
            <span>반복요일: </span>
            <template v-if="item.day && Array.isArray(item.day)">
              <span v-for="(day, i) in sortedDays(item.day)" :key="i" :class="getDayClass(day)"
                >{{ day }}<span v-if="i < sortedDays(item.day).length - 1">, </span></span
              >
            </template>
          </div>
          <div class="auto" title="자동여부">{{ item.isAuto == 1 ? 'A' : '' }}</div>
        </div>
        <div class="btn-box">
          <button @click="openEditModal(item)" class="edit-btn">수정</button>
          <button @click="deleteTodoItem(item)" class="remove-btn">삭제</button>
        </div>
      </div>
      <div class="list-item center" v-if="selectedGroup" @click="openAddItemModal">
        <div class="add">아이템 추가</div>
      </div>
    </template>

    <!-- 아이템 추가 모달 -->
    <Modal
      :isVisible="isModalVisible"
      :mode="'create'"
      :groupId="selectedGroup"
      @close="closeModal"
      @save-item="fetchItemsByGroup(selectedGroup)"
    />

    <!-- 아이템 수정 모달 -->
    <Modal
      :isVisible="isEditModalVisible"
      :mode="'update'"
      :item="selectedItem"
      :groupId="selectedGroup"
      @close="closeEditModal"
      @save-item="fetchItemsByGroup(selectedGroup)"
    />
  </div>
</template>

<script>
import Modal from './ItemCreateModal.vue'

export default {
  components: {
    Modal,
  },
  props: {
    selectedGroup: Number,
  },
  data() {
    return {
      items: [],
      isModalVisible: false,
      isEditModalVisible: false,
      selectedItem: null,
    }
  },
  watch: {
    selectedGroup(newGroupId) {
      if (newGroupId) {
        this.fetchItemsByGroup(newGroupId)
      }
    },
  },
  methods: {
    openAddItemModal() {
      this.isModalVisible = true
    },
    closeModal() {
      this.isModalVisible = false
    },
    openEditModal(item) {
      this.selectedItem = item
      this.isEditModalVisible = true
    },
    closeEditModal() {
      this.isEditModalVisible = false
      this.selectedItem = null
    },
    async fetchItemsByGroup(groupId) {
      const url = `todo/item/${groupId}`
      const { data } = await this.$axios.get(url)
      console.log('아이템 목록:', data)
      this.items = data
    },
    deleteTodoItem(item) {
      if (confirm('정말 삭제할거냐? 되돌릴 수 없다!')) {
        const url = `todo/item/update/status/${item.id}`
        this.$axios
          .put(url)
          .then(() => {
            this.fetchItemsByGroup(item.groupId)
            alert('아이템 삭제')
          })
          .catch((error) => {
            alert('삭제 실패: ' + error)
          })
      } else {
        alert('삭제 취소')
      }
    },
    sortedDays(dayArray) {
      const order = ['일', '월', '화', '수', '목', '금', '토']
      return dayArray.sort((a, b) => order.indexOf(a) - order.indexOf(b))
    },
    getDayClass(day) {
      if (day === '일') return 'sunday'
      if (day === '토') return 'saturday'
      return ''
    },
  },
}
</script>

<style scoped></style>
