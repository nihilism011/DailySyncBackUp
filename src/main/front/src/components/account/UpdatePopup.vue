<template>
  <div class="popup" @click="$emit('close')"></div>
  <div class="popup-contents">
    <div>
      <label for="category">카테고리</label>
      <select id="category" v-model="category">
        <option :value="c.key" v-for="c in categoryArray" :key="c">{{ c.name }}</option>
      </select>
    </div>
    <div>
      <select v-model="amountType">
        <option value="minus">지출</option>
        <option value="plus">수입</option>
      </select>
    </div>
    <div>
      <label for="amount">금액</label>
      <input type="number" id="amount" v-model="amount" />
    </div>
    <div>
      <label for="title">내역</label>
      <input type="text" id="title" v-model="title" />
    </div>
    <div>
      <label for="description">메모</label>
      <input type="text" id="description" v-model="description" />
    </div>
    <div>
      <button>저장</button>
      <button @click="$emit('close')">취소</button>
      <button @click="deleteItem">삭제</button>
    </div>
  </div>
</template>
<script>
import { categoryArray } from '@/constants/accountCategory'
import { deleteAccountItem } from '@/lib/accountLib'
export default {
  emits: ['refresh', 'close'],
  props: {
    account: Object,
  },
  data() {
    return {
      categoryArray,
      amountType: this.account.amount > 0 ? 'plus' : 'minus',
      category: this.account.category,
      amount: Math.abs(this.account.amount),
      title: this.account.title,
      description: this.account.description,
    }
  },
  methods: {
    deleteAccountItem,
    refresh() {
      this.$emit('refresh')
      this.$emit('close')
    },
    async deleteItem() {
      const success = await this.deleteAccountItem(this.account.id)
      if (success) {
        this.refresh()
      }
    },
  },
}
</script>
<style lang="scss" scoped>
.popup {
  position: absolute;
  top: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.1);
  width: 100%;
  height: 100%;
}
.popup-contents {
  position: absolute;
  background-color: white;
  border-radius: 20px;
  border: 3px solid black;
  width: 60%;
  height: 500px;
}
</style>
