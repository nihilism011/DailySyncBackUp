<template>
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <div class="modal-header">
        <div style="font-size: 30px;">
          {{ mode === 'create' ? '아이템 추가' : '아이템 수정' }}
        </div>
        <button @click="closeModal">X</button>
      </div>
      <div class="modal-body">
        <input v-model="newItem.title" placeholder="제목 입력" />
        <div>
          <div>요일 선택</div>
          <label>
            <input type="checkbox" v-model="newItem.day" value="월" /> 월
          </label>
          <label>
            <input type="checkbox" v-model="newItem.day" value="화" /> 화
          </label>
          <label>
            <input type="checkbox" v-model="newItem.day" value="수" /> 수
          </label>
          <label>
            <input type="checkbox" v-model="newItem.day" value="목" /> 목
          </label>
          <label>
            <input type="checkbox" v-model="newItem.day" value="금" /> 금
          </label>
          <label>
            <input type="checkbox" v-model="newItem.day" value="토" /> 토
          </label>
          <label>
            <input type="checkbox" v-model="newItem.day" value="일" /> 일
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
        <div class="modal-footer">
          <button @click="saveItem">{{ mode === 'create' ? '추가' : '수정' }}</button>
          <button @click="closeModal">취소</button>
        </div>
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
    groupId: {
      type: Number,
      required: true,
    },
    item: {
      type: Object,
      default: () => ({
        id: '',
        itemOrder: '',
        title: '',
        isAuto: '',
        status: '',
        day: [], 
      }),
    },
  },
  data() {
    return {
      newItem: { ...this.item },
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
      if (!this.newItem.title || !this.newItem.day.length || !this.newItem.itemOrder) {
        alert('빈칸을 채워주세요');
        return;
      }

      try {
        let url;

        if (this.mode === 'create') {
          url = 'todo/item';  // 새 아이템 추가 URL
          await this.$axios.post(url, {
            title: this.newItem.title,
            day: this.newItem.day,
            isAuto: this.newItem.isAuto,
            groupId: this.groupId,  
            userId: '5',
            itemOrder: this.newItem.itemOrder,
            status: "new",
          });

        } else if (this.mode === 'update') {
          url = 'todo/item';  // 새 아이템 추가 URL
          await this.$axios.post(url, {
            title: this.newItem.title,
            day: this.newItem.day,
            isAuto: this.newItem.isAuto,
            groupId: this.groupId,  
            userId: '5',
            itemOrder: this.newItem.itemOrder,
            status: "new",
          });
          const statusUrl = `todo/item/update/status/${this.item.id}`;
          await this.$axios.put(statusUrl);
        }

        alert(`${this.mode === 'create' ? '아이템 추가' : '아이템 수정'} 성공`);
        this.$emit('save-item'); // 아이템 리스트를 갱신
        this.closeModal();
        
      } catch (error) {
        console.error('오류 발생:', error);
        alert(`${this.mode === 'create' ? '아이템 추가' : '아이템 수정'} 실패`);
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
