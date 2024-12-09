<template>
  <div>
    <div v-if="!selectedGroup">
      <div>그룹 선택ㄱㄱ</div>
    </div>

    <div v-if="selectedGroup">
      <div class="item-container">

        <div class="item" v-for="(item, index) in items" :key="index">
          <div class="title" >{{ item.title }}</div>
          <div class="actions">
            <button @click="editTodoItem(item.id)" class="edit-btn">수정</button>
            <button @click="deleteTodoItem({ id: item.id, groupId: item.groupId })" class="delete-btn">삭제</button>

          </div>
        </div>
        <div class="item">
          <div class="title" style="color: blue;"> + 아이템 추가 </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    selectedGroup: Number,
  },
  data() {
    return {
      items: [],
    };
  },
  watch: {
    selectedGroup(newGroupId) {
      if (newGroupId) {
        this.fetchItemsByGroup(newGroupId);
      }
    },
  },
  methods: {
    async fetchItemsByGroup(groupId) {
      const userId = 5;
      const url = `todo/item/${userId}/${groupId}`;
      const { data } = await this.$axios.get(url);
      console.log("이거가져옴" + data); 
      this.items = data;
      
    },

    async editTodoItem(itemId) {
      console.log("아이템 수정", itemId);
    },

    deleteTodoItem(item) {
      if (confirm("정말 삭제할거냐? 되돌릴 수 없다!")) {
        const url = `todo/item/update/status/${item.id}`;
        this.$axios.put(url).then(() => {
          this.fetchItemsByGroup(item.groupId);
          alert("아이템 삭제: ");
          }).catch((error) => {
          alert("삭제 실패: " + error);
          });
        } else {
          alert("삭제 취소");
        }
    }

  },
};
</script>

<style scoped>
.item-container {
  margin-top: 20px;
}

.item {
  display: flex;
  margin: 10px 0;
  height: 45px;
  border-bottom: 1px solid #eee;
  box-shadow: 5px 5px 3px #666;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 8px;
  position: relative;
  transition: background-color 0.3s ease;
}

.item:hover {
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

.item:hover .actions {
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
