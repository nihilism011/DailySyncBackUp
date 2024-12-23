<template>
  <div class="popup-box type-modal">
    <div class="popup-cont type2">
      <div class="popup-tit-wrap">
        <div class="tit">{{ mode === 'create' ? '가계부 추가' : '수정' }}</div>
        <button
          v-if="mode === 'create'"
          class="favorite"
          :class="favorite ? 'active' : ''"
          title="즐겨찾기 추가"
          @click="favorite = !favorite"
        >
          즐겨찾기
        </button>
      </div>
      <div class="account-wrap">
        <div class="ip-list">
          <div class="tit-box">
            <label for="title" class="tit">소득/지출</label>
          </div>
          <div class="bot-box">
            <div class="ip-ra-txt">
              <input type="radio" id="plus" v-model="isPlus" :value="true" />
              <label for="plus">소득</label>
              <input type="radio" id="minus" v-model="isPlus" :value="false" />
              <label for="minus">지출</label>
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="title" class="tit">등록일자</label>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input type="date" class="date-picker" v-model="reqBody.accountDate" />
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="title" class="tit">카테고리</label>
          </div>
          <div class="bot-box">
            <div class="select-box">
              <select v-model="reqBody.category">
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
            <div class="ip-box">
              <input id="title" type="text" v-model="reqBody.title" ref="titleInput" />
            </div>
          </div>
        </div>
        <div class="ip-list">
          <div class="tit-box">
            <label for="amount" class="tit">가격</label>
          </div>
          <div class="bot-box">
            <div class="ip-box">
              <input id="amount" type="number" v-model="reqBody.amount" ref="amountInput" />
            </div>
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
        <button @click="updateItem" class="btn-default submit">
          {{ mode === 'create' ? '저장' : '수정' }}
        </button>
        <button @click="closePopup" class="btn-default cancel">취소</button>
      </div>
    </div>
    <div class="dimmed">dim</div>
  </div>
</template>
<script>
import { categoryArray } from '@/constants/accountCategory'
import { updateAccountItem, createAccountItem } from '@/lib/accountLib'
import { useRefreshStore } from '@/stores/refreshStore'
import { useDateStore } from '@/stores/dateStore'
export default {
  props: {
    account: {
      type: Object,
      default: () => ({}),
    },
    mode: String,
  },
  setup() {
    const refreshStore = useRefreshStore()
    const dateStore = useDateStore()
    return { refreshStore, dateStore }
  },
  data() {
    return {
      categoryArray,
      isPlus: this.account.amount > 0 ? true : false,
      reqBody: {
        ...this.account,
        category: this.account.category ?? 'FOOD',
        title: this.account.title ?? '',
        fixed: this.account.fixed ?? false,
        amount: this.account.amount ? Math.abs(this.account.amount) : 0,
        description: this.account.description ?? '',
        accountDate:
          this.account.accountDate ??
          this.dateStore.selectedDate ??
          this.$dayjs().format('YYYY-MM-DD'),
      },
      favorite: false,
    }
  },
  watch: {
    'reqBody.amount': function () {
      this.reqBody.amount = Math.abs(this.reqBody.amount)
    },
  },
  methods: {
    updateAccountItem,
    createAccountItem,
    async updateItem() {
      if (!this.reqBody.title) {
        alert('내역을 입력해 주세요.')
        this.$refs.titleInput.focus()
        return
      }
      if (this.reqBody.title.length < 2) {
        alert('항목 이름은 두글자 이상 입력해주세요.')
        this.$refs.titleInput.focus()
        return
      }
      if (!this.reqBody.amount) {
        alert('금액을 입력해 주세요.')
        this.$refs.amountInput.focus()
        return
      }
      if (this.reqBody.amount > 2000000000) {
        alert('20억 이상의 금액은 DailySync Ultimate 버전을 사용해주세요.')
        this.$refs.amountInput.focus()
        return
      }
      if (this.reqBody.amount <= 0) {
        alert('0이상 숫자를 입력해주세요.')
        this.$refs.amountInput.focus()
        return
      }
      const reqBody = {
        ...this.reqBody,
        amount: this.isPlus ? this.reqBody.amount : -this.reqBody.amount,
      }
      let resStatus
      if (this.mode === 'create') {
        resStatus = await this.createAccountItem(reqBody)
      } else {
        resStatus = await this.updateAccountItem(this.account.id, reqBody)
      }

      if (resStatus) {
        alert('저장되었습니다.')
        this.refreshStore.setRefresh()
        this.$emit('close')
      }
    },
    closePopup() {
      this.$emit('close')
    },
    handleKeyEvent(event) {
      if (event.key === 'Escape') {
        this.closePopup()
      }
      if (event.key === 'Enter') {
        this.updateItem()
      }
    },
  },
  mounted() {
    window.addEventListener('keydown', this.handleKeyEvent)
  },
  beforeUnmount() {
    window.removeEventListener('keydown', this.handleKeyEvent)
  },
}
</script>
<style lang="scss" scoped></style>
