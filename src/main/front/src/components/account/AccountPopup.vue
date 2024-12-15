<template>
  <div class="popup-box type-modal">
    <div class="popup-cont type2">
      <div class="popup-tit-wrap">
        <div class="tit">{{ mode === 'create' ? '가계부 추가' : '수정' }}</div>
      </div>
      <div>
        <input type="radio" id="plus" v-model="isPlus" :value="true" />
        <label for="plus">수입</label>
        <input type="radio" id="minus" v-model="isPlus" :value="false" />
        <label for="minus">지출</label>
      </div>
      <div>
        <select>
          <option v-for="(item, index) in categoryArray" :key="index" :value="item.key">
            {{ item.name }}
          </option>
        </select>
      </div>
      <div>
        <input id="title" type="text" v-model="reqBody.title" />
        <label for="title">title</label>
        <input id="amount" type="number" v-model="reqBody.amount" />
        <label for="amount">amount</label>
        <input id="description" type="text" v-model="reqBody.description" />
        <label for="description">description</label>
        <input id="fixed" type="checkbox" v-model="reqBody.fixed" />
        <label for="fixed">fixed</label>
      </div>
    </div>
    <div class="btn-box">
      <button v-if="mode === 'create'">저장</button>
      <button v-if="mode === 'update'" @click="updateItem">수정</button>
      <button @click="closePopup">취소</button>
    </div>
  </div>
</template>
<script>
import { categoryArray } from '@/constants/accountCategory'
import { updateAccountItem } from '@/lib/accountLib'
import { useRefreshStore } from '@/stores/refreshStore'
export default {
  props: {
    account: Object,
    mode: String,
  },
  setup() {
    const refreshStore = useRefreshStore()
    return { refreshStore }
  },
  data() {
    return {
      categoryArray,
      isPlus: this.account.amount > 0 ? true : false,
      reqBody: {
        ...this.account,
        amount: Math.abs(this.account.amount),
        description: this.account.description ?? '',
      },
    }
  },
  watch: {
    'reqBody.amount': function () {
      this.reqBody.amount = Math.abs(this.reqBody.amount)
    },
  },
  methods: {
    updateAccountItem,
    async updateItem() {
      if (!this.reqBody.title) {
        alert('내역역을 입력해 주세요.')
        return
      }
      if (!this.reqBody.amount) {
        alert('금액을 입력해 주세요.')
        return
      }
      if (this.reqBody.amount <= 0) {
        alert('0이상 숫자를 입력해주세요.')
        return
      }
      const reqBody = {
        ...this.reqBody,
        amount: this.isPlus ? this.reqBody.amount : -this.reqBody.amount,
      }
      const resStatus = await this.updateAccountItem(this.account.id, reqBody)
      if (resStatus) {
        alert('수정되었습니다.')
        this.refreshStore.setRefresh()
        this.$emit('close')
      }
    },
    closePopup() {
      this.$emit('close')
    },
  },
}
</script>
<style lang="scss" scoped></style>
