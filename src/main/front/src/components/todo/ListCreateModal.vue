<template>
  <div v-if="isVisible" class="popup-box type-modal">
    <div class="popup-cont type2">
      <div class="popup-tit-wrap">
        <div class="tit">
          {{ mode === 'create' ? '리스트 추가' : '리스트 수정' }}
        </div>
      </div>
      <div class="todo-wrap" v-if = "mode === 'update' && newItem.listOrder !=4 ">
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">중요도</p>
          </div>
          <div class="bot-box">
            <div class="ip-ra-txt">
              <input type="radio" id="ra1" v-model="newItem.listOrder" value='0'/>
              <label for="ra1">긴급</label>
              <input type="radio" id="ra2" v-model="newItem.listOrder" value='1'/>
              <label for="ra2">높음</label>
              <input type="radio" id="ra3" v-model="newItem.listOrder" value='2'/>
              <label for="ra3">보통</label>
              <input type="radio" id="ra4" v-model="newItem.listOrder" value='3'/>
              <label for="ra4">낮음</label>
            </div>
          </div>
        </div>
      </div>
      <div class="todo-wrap">
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">제목</p>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input type="text" v-model="newItem.title" placeholder="제목 입력" />
            </div>
          </div>
        </div>
      </div>
      <div class="pop-btn-wrap">
        <button @click="saveItem" class="btn-default submit">{{ mode === 'create' ? '추가' : '수정' }}</button>
        <button @click="closeModal" class="btn-default cancel">취소</button>
      </div>
    </div>
    <div class="dimmed">dim</div>
  </div>
</template>

<script>
export default {
  props: {
    isVisible: {
      type: Boolean,
      default: false,
    },
    mode: {
      type: String,
      default: 'create',
    },
    item: {
      type: Object,
      default: () => ({
        title: '', 
        description: '', 
        listOrder: ''
      }),
    },
    // userId: {
    //   type: Number,
    //   required: true,
    // },
  },
  data() {
    return {
      newItem: {
      ...this.item
    },
    };
  },
  watch: {
    item(newVal) {
      this.newItem = { ...newVal };
    },
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    async saveItem() {
      if (!this.newItem.title) {
        alert('제목을 입력하세요');
        return;
      }

      try {
        let url;
        if (this.mode === 'create') {
          url = `todo/list/change`;  // 새 아이템 추가 URL
          await this.$axios.post(url, {
            title : this.newItem.title,
          });
        } else if (this.mode === 'update') {
          url = `todo/list/update/${this.item.id}`;  // 새 아이템 추가 URL
          await this.$axios.put(url, {
            title : this.newItem.title,
            listOrder: this.newItem.listOrder,
          });
        }

        alert(`${this.mode === 'create' ? '리스트 추가' : '리스트 수정'} 성공`);
        this.$emit('save-item'); 
        this.closeModal();
        
      } catch (error) {
        console.error('오류 발생:', error);
        alert(`${this.mode === 'create' ? '리스트 추가' : '리스트 수정'} 실패`);
      }
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
  z-index: 9999; 
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  width: 500px;
  height: 300px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.modal-footer {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}
</style>
