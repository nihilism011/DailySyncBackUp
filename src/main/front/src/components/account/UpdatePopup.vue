<template>
  <div class="popup">
    <div class="popup-contents">
      <div class="form-group">
        <label for="category">카테고리</label>
        <select id="category" v-model="requestBody.category" class="input-field">
          <option :value="c.key" v-for="c in categoryArray" :key="c">{{ c.name }}</option>
        </select>
      </div>

      <div class="form-group">
        <select v-model="amountType" class="input-field">
          <option value="minus">지출</option>
          <option value="plus">수입</option>
        </select>
      </div>

      <div class="form-group">
        <label for="amount">금액</label>
        <input type="number" id="amount" v-model="requestBody.amount" class="input-field" />
      </div>

      <div class="form-group">
        <label for="title">내역</label>
        <input type="text" id="title" v-model="requestBody.title" class="input-field" />
      </div>

      <div class="form-group">
        <label for="description">메모</label>
        <input type="text" id="description" v-model="requestBody.description" class="input-field" />
      </div>

      <div class="button-group">
        <button @click="saveItem" class="button save-btn">저장</button>
        <button @click="$emit('close')" class="button cancel-btn">취소</button>
        <button v-if="action === 'update'" @click="deleteItem" class="button delete-btn">
          삭제
        </button>
      </div>
      <div>
        <label for="isFixed">고정 내역</label>
        <input id="isFixed" type="checkbox" v-model="requestBody.fixed" />
      </div>
    </div>
  </div>
</template>

<script>
import { categoryArray } from '@/constants/accountCategory'
import { deleteAccountItem, updateAccountItem, createAccountItem, isValid } from '@/lib/accountLib'
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
export default {
  emits: ['close'],
  props: {
    account: {
      type: Object,
      default() {
        return {
          title: '',
          category: 'FOOD',
          amount: 0,
          description: '',
          fixed: false,
        }
      },
    },
    fixed: {
      type: Boolean,
      default: false,
    },
    action: {
      type: String,
      required: true,
      validator(value) {
        return ['update', 'create'].includes(value)
      },
    },
  },
  setup() {
    const refreshStore = useRefreshStore()
    const dateStore = useDateStore()
    return { dateStore, refreshStore }
  },
  data() {
    return {
      categoryArray,
      amountType: this.account.amount > 0 ? 'plus' : 'minus',
      requestBody: {
        category: this.account.category,
        amount: Math.abs(this.account.amount),
        title: this.account.title,
        description: this.account.description,
        fixed: this.account.fixed,
      },
    }
  },
  methods: {
    createAccountItem,
    deleteAccountItem,
    updateAccountItem,
    isValid,
    async saveItem() {
      const requestBody = { ...this.requestBody }
      //todo 유효성겁사 alert로 구성되어 있는데 isValid 수정해서 좀더 괜찮은 디자인으로 하면 좋을듯.
      if (
        !isValid(requestBody.title, 'title') ||
        !isValid(requestBody.amount, 'amount') ||
        !isValid(requestBody.description, 'description')
      ) {
        return
      }
      requestBody.amount =
        this.amountType === 'plus' ? this.requestBody.amount : -this.requestBody.amount
      let res = false
      if (this.action === 'create') {
        const createReqBody = {
          ...requestBody,
          accountDate: this.dateStore.selectedDate,
        }
        res = await createAccountItem(createReqBody)
      }
      if (this.action === 'update') {
        res = await this.updateAccountItem(this.account.id, requestBody)
      }
      if (res) {
        this.refreshStore.setRefresh()
        this.$emit('close')
      }
    },
    async deleteItem() {
      //todo 삭제 확인 메세지 confirm으로 구성되어 있는데 좀더 괜찮게 개선 할 수 있을지도?
      if (!confirm('이 항목을 삭제 하시겠습니까?')) {
        return
      }
      const success = await this.deleteAccountItem(this.account.id)
      if (success) {
        this.refreshStore.setRefresh()
        this.$emit('close')
      }
    },
  },
}
</script>

<style lang="scss" scoped>
.popup {
  position: fixed;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5); /* 어두운 배경 */
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center; /* 팝업을 화면 중앙에 배치 */

  .popup-contents {
    background-color: white;
    border-radius: 10px;
    border: none;
    width: 80%;
    max-width: 500px;
    padding: 30px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    gap: 15px;
  }

  .form-group {
    display: flex;
    flex-direction: column;
    gap: 5px;
  }

  .input-field {
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 14px;
    outline: none;
    transition: all 0.3s ease;

    &:focus {
      border-color: #007bff;
      box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
    }
  }

  .button-group {
    display: flex;
    justify-content: space-between;
    gap: 10px;
  }

  .button {
    padding: 10px 20px;
    font-size: 16px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.3s ease;
  }

  .save-btn {
    background-color: #28a745;
    color: white;
    &:hover {
      background-color: #218838;
    }
  }

  .cancel-btn {
    background-color: #6c757d;
    color: white;
    &:hover {
      background-color: #5a6268;
    }
  }

  .delete-btn {
    background-color: #dc3545;
    color: white;
    &:hover {
      background-color: #c82333;
    }
  }

  @media (max-width: 768px) {
    .popup-contents {
      width: 90%;
      padding: 20px;
    }
  }
}
</style>
