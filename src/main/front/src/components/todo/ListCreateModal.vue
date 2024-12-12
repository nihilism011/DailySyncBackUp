<template>
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <div style="font-size: 30px;">
          {{ mode === 'create' ? '리스트 추가' : '리스트 수정' }}
        </div>
        <button @click="closeModal">X</button>
      </div>
      <div v-if = "mode === 'update' && newItem.listOrder !=4 ">
        <span style="margin-right: 20px;">중요도 : </span> 
        <label style="margin-right: 20px;">긴급  
        <input type="radio" v-model="newItem.listOrder" value='0'/>
        </label>
        <label style="margin-right: 20px;"> 높음  
        <input type="radio" v-model="newItem.listOrder" value='1'/>
        </label>
        <label style="margin-right: 20px;"> 보통  
        <input type="radio" v-model="newItem.listOrder" value='2'/>
        </label>
        <label> 낮음   
        <input type="radio" v-model="newItem.listOrder" value='3'/>
        </label>
      </div>
      <div class="modal-body" style="margin-top: 20px;">
        <span style="margin-right: 20px;">제목: </span> 
        <input v-model="newItem.title" placeholder="제목 입력" />
      </div>
      <div class="modal-footer">
        <button @click="saveItem">{{ mode === 'create' ? '추가' : '수정' }}</button>
        <button @click="closeModal">취소</button>
      </div>
    </div>
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
      const userId = 5; 
      if (!this.newItem.title) {
        alert('제목을 입력하세요');
        return;
      }

      try {
        let url;
        if (this.mode === 'create') {
          url = `todo/list/change/${userId}`;  // 새 아이템 추가 URL
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
