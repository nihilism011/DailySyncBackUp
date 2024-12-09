<template>
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <div style="font-size: 30px;">아이템 추가</div>
        <button @click="closeModal">X</button>
      </div>
      <div class="modal-body">
        <input v-model="newItem.title" placeholder="제목 입력" />
        <div>
          <div>요일 선택</div>
          <label>
            <input type="checkbox" v-model="newItem.days" value="월" /> 월
          </label>
          <label>
            <input type="checkbox" v-model="newItem.days" value="화" /> 화
          </label>
          <label>
            <input type="checkbox" v-model="newItem.days" value="수" /> 수
          </label>
          <label>
            <input type="checkbox" v-model="newItem.days" value="목" /> 목
          </label>
          <label>
            <input type="checkbox" v-model="newItem.days" value="금" /> 금
          </label>
          <label>
            <input type="checkbox" v-model="newItem.days" value="토" /> 토
          </label>
          <label>
            <input type="checkbox" v-model="newItem.days" value="일" /> 일
          </label>
        </div>
        <div>
        <div>우선순위</div>
          <label>
            <input type="radio" v-model="newItem.itemOrder" value='1' /> 높음
          </label>
          <label>
            <input type="radio" v-model="newItem.itemOrder" value='2' /> 보통
          </label>
          <label>
            <input type="radio" v-model="newItem.itemOrder" value='3' /> 낮음
          </label>
        </div>
          <div>자동여부</div>
          <label>
            <input type="checkbox" v-model="newItem.isAuto" value='1' /> Auto
          </label>
        </div>
      <div class="modal-footer">
        <button @click="addItem">추가</button>
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
    groupId: {
      type: Number,
      required: true,  
    },
  },
  data() {
    return {
      newItem: {
        title: '',
        days: [],
        isAuto: false,
        itemOrder: null, // 기본값을 null로 설정
      }
    };
  },
  methods: {
    closeModal() {
      this.$emit('close'); 
    },
    async addItem() {
   
      
      try {
        console.log( "아이템 정보"  + this.newItem);
        const url = await this.$axios.post('todo/item', {
          title: this.newItem.title,
          day: this.newItem.days,
          isAuto: this.newItem.isAuto,
          groupId: this.groupId,  
          userId : '5',
          itemOrder: this.newItem.itemOrder,
          status : "new"
        });
        console.log('아이템 추가 성공:', url.data);
        this.$emit('add-item', url.data); 
        this.closeModal();
      } catch (error) {
        console.error('아이템 추가 실패:', error);
        alert('아이템 추가 실패');
      }
    },
  }
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
