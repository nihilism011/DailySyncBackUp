<template>
  <div class="popup-box type-modal">
    <div class="popup-cont type2">
      <div class="popup-tit-wrap">
        <div class="tit">{{ mode === 'create' ? '가계부 추가' : '수정' }}</div>
      </div>
      <div class="account-wrap">
        <div class="ip-list">
          <div class="tit-box">
            <label for="title" class="tit">수입/지출</label>
          </div>
          <div class="bot-box">
            <div class="ip-ra-txt">
              <input type="radio" id="plus" v-model="isPlus" :value="true" />
              <label for="plus">수입</label>
              <input type="radio" id="minus" v-model="isPlus" :value="false" />
              <label for="minus">지출</label>
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="title" class="tit">카테고리</label>
          </div>
          <div class="bot-box">
            <div class="select-box">
              <select>
                <option v-for="(item, index) in categoryArray" :key="index" :value="item.key">
                  {{ item.name }}
                </option>
              </select>
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="title" class="tit">제목</label>
          </div>
          <div class="bot-box">
            <div class="ip-box"><input id="title" type="text" v-model="reqBody.title" /></div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="amount" class="tit">가격</label>
          </div>
          <div class="bot-box">
            <div class="ip-box"><input id="amount" type="number" v-model="reqBody.amount" /></div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="description" class="tit">메모</label>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input id="description" type="text" v-model="reqBody.description" />
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <p class="tit">고정 여부</p>
          </div>
          <div class="bot-box">
            <div class="ip-chk-txt">
              <input id="fixed" type="checkbox" v-model="reqBody.fixed" />
              <label for="fixed">fixed</label>
            </div>
          </div>
        </div>
      </div>
      <div class="pop-btn-wrap">
        <button v-if="mode === 'create'" class="btn-default submit">저장</button>
        <button v-if="mode === 'update'" @click="updateItem" class="btn-default submit">
          수정
        </button>
        <button @click="closePopup" class="btn-default cancel">취소</button>
      </div>
    </div>
    <div class="dimmed">dim</div>
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
