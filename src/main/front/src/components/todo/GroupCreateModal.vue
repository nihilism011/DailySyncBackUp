<template>
  <div v-if="isVisible" class="popup-box type-modal">
    <div class="popup-cont type2">
      <div class="popup-tit-wrap">
        <h2 class="tit">
          {{ mode === 'create' ? '그룹 추가' : '그룹 수정' }}
        </h2>
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
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">상세설명</p>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input type="text" v-model="newItem.description" placeholder="상세설명 입력" />
            </div>
          </div>
        </div>
      </div>
      <div class="pop-btn-wrap">
        <!-- 저장 버튼 (추가/수정) -->
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
      }),
    },
  },
  data() {
    return {
      newItem: { ...this.item }, // 부모로부터 받은 item을 복사하여 로컬 데이터로 설정
    };
  },
  watch: {
    // item이 바뀔 때마다 newItem을 갱신하여 동기화
    item(newVal) {
      this.newItem = { ...newVal };
    },
  },
  methods: {
    closeModal() {
      this.$emit('close'); // 모달 닫기
    },
    async saveItem() {
      if (!this.newItem.title || !this.newItem.description) {
        alert('빈칸을 채워주세요');
        return;
      }

      try {
        let url;
        let method;
        const data = {
          title: this.newItem.title,
          description: this.newItem.description, 
        };

        if (this.mode === 'create') {
          url = 'todo/group';
          method = 'post';
        } else if (this.mode === 'update') {
          url = `todo/group/update/${this.item.id}`;
          method = 'put';
        }

        await this.$axios[method](url, data);
        alert(`${this.mode === 'create' ? '그룹 추가' : '그룹 수정'} 성공`);
        
        // 추가/수정 후 fnMyGroup() 호출
        this.$emit('save-item'); // 부모 컴포넌트에 저장된 데이터 전달
        this.closeModal();
      } catch (error) {
        console.error('오류 발생:', error);
        alert(`${this.mode === 'create' ? '그룹 추가' : '그룹 수정'} 실패`);
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
