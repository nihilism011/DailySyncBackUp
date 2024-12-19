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
          <option value="plus">소득</option>
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

      <div class="button-group" v-if="mode != 'favor'">
        <button @click="saveItem" class="button save-btn">저장</button>
        <button @click="$emit('close')" class="button cancel-btn">취소</button>
        <button v-if="mode === 'update'" @click="deleteItem" class="button delete-btn">삭제</button>
      </div>
      <div class="button-group" v-if="mode === 'favor'">
        <button @click="saveItem" class="button save-btn">즐찾등록</button>
        <button v-if="requestBody.id != null" @click="updateFavor" class="button save-btn">
          즐찾수정
        </button>
        <button @click="deleteFavor" v-if="requestBody.id != null" class="button delete-btn">
          즐찾삭제
        </button>
        <button @click="$emit('close')" class="button cancel-btn">취소</button>
      </div>
      <div>
        <label for="isFixed">고정 내역 등록</label>
        <input id="isFixed" type="checkbox" v-model="requestBody.fixed" />
      </div>
    </div>
    <div class="favor-contents">
      <div>
        즐겨찾기
        <button class="button favor-btn" @click="changeFavorMode" v-if="mode != 'favor'">
          즐겨찾기 추가/제거
        </button>
      </div>
      <div class="form-group">
        <select v-model="favorSearchCategory" class="input-field">
          <option value="ALL">카테고리</option>
          <option :value="c.key" v-for="c in categoryArray" :key="c">{{ c.name }}</option>
        </select>
      </div>
      <input type="text" v-model="favorSearch" />
      <hr />
      <div>
        <div
          v-for="(item, index) in favorList"
          :key="index"
          class="favorListItem"
          @click="replaceFavorItem(item)"
        >
          <AmountBadge :amount="item.amount" />
          <div>{{ item.title }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { categoryArray } from '@/constants/accountCategory'
import {
  deleteAccountItem,
  updateAccountItem,
  createAccountItem,
  isValid,
  numToWon,
} from '@/lib/accountLib'
import AmountBadge from './AmountBadge.vue'
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
export default {
  components: {
    AmountBadge,
  },
  emits: ['close'],
  props: {
    account: {
      type: Object,
      default() {
        return {
          id: null,
          title: '',
          category: 'OTHER',
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
        return ['update', 'create', 'favor'].includes(value)
      },
    },
  },
  setup() {
    const refreshStore = useRefreshStore()
    const dateStore = useDateStore()
    return { dateStore, refreshStore }
  },
  watch: {
    favorSearchCategory() {
      this.fetchFavorList()
    },
    favorSearch() {
      this.fetchFavorList()
    },
    'refreshStore.refreshState': function () {
      this.fetchFavorList()
    },
  },
  data() {
    return {
      mode: this.action,
      favorSearch: '',
      favorSearchCategory: 'ALL',
      favorList: [],
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
    numToWon,
    makeRequestBody() {
      const data = { ...this.requestBody }
      if (
        !isValid(data.title, 'title') ||
        !isValid(data.amount, 'amount') ||
        !isValid(data.description, 'description')
      ) {
        return false
      }
      data.amount = this.amountType === 'plus' ? this.requestBody.amount : -this.requestBody.amount

      return data
    },
    async saveItem() {
      const requestBody = this.makeRequestBody()
      if (!requestBody) return
      let res = false
      if (this.mode === 'create') {
        const createReqBody = {
          ...requestBody,
          accountDate: this.dateStore.selectedDate,
        }
        res = await createAccountItem(createReqBody)
      }
      if (this.mode === 'update') {
        res = await this.updateAccountItem(this.account.id, requestBody)
      }
      if (this.mode === 'favor') {
        res = await this.$axios.post('account/items/favor', requestBody)
        alert('즐겨찾기 등록 되었습니다.')
      }
      if (res) {
        this.refreshStore.setRefresh()
        this.$emit('close')
      }
    },
    replaceFavorItem(item) {
      this.amountType = item.amount > 0 ? 'plus' : 'minus'
      this.requestBody = {
        id: item.id,
        category: item.category,
        amount: Math.abs(item.amount),
        title: item.title,
        description: item.description,
        fixed: false,
      }
    },
    async updateFavor() {
      const requestBody = this.makeRequestBody()
      if (!requestBody) return
      const { data } = await this.$axios.patch(
        `account/items/favor/${this.requestBody.id}`,
        this.requestBody,
      )
      if (data) {
        this.refreshStore.setRefresh()
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
    async deleteFavor() {
      if (!confirm('이 항목을 삭제 하시겠습니까?')) {
        return
      }
      const { data } = await this.$axios.delete(`account/items/favor/${this.requestBody.id}`)
      if (data) {
        this.refreshStore.setRefresh()
      }
    },
    async fetchFavorList() {
      const url = `account/items/favor/${this.favorSearchCategory}`
      const { data: favorList } = await this.$axios.get(url)
      const filteredList = favorList.filter(
        (item) =>
          item.title.includes(this.favorSearch) || item.description.includes(this.favorSearch),
      )
      this.favorList = filteredList
    },
    changeFavorMode() {
      this.mode = 'favor'
    },
  },
  mounted() {
    this.fetchFavorList()
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
  z-index: 9999;

  .popup-contents {
    background-color: white;
    border-radius: 10px;
    border: none;
    width: 80%;
    max-width: 500px;
    padding: 30px;
    height: 500px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1); /* 그림자 추가 */
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    gap: 15px;
  }
  .favor-contents {
    background-color: rgb(179, 179, 247);
    border-radius: 10px;
    border: none;
    width: 30%;
    max-width: 500px;
    height: 500px;
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
  .favor-btn {
    background-color: #ffffff;
    color: black;
    &:hover {
      background-color: #ffffff9c;
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
  .favorListItem {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    border-radius: 8px;
    height: 33px;
    cursor: pointer;
    &:hover {
      background-color: rgba(255, 255, 255, 0.308);
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
