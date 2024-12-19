<template>
  <div class="form-container">
    <div class="select-container">
      <label for="category">카테고리</label>
      <select id="category" v-model="form.category">
        <option v-for="(category, i) in categoriesAsArray" :value="category.key" :key="i">
          {{ category.name }}
        </option>
      </select>
      <label for="amountType">소득/지출</label>
      <select id="amountType" v-model="amountType">
        <option value="plus">소득</option>
        <option value="minus">지출</option>
      </select>
    </div>
    <div class="amount-title-container">
      <label for="amount">금액</label>
      <input id="amount" type="number" v-model="form.amount" />
      <label for="title">타이틀</label>
      <input id="title" type="text" v-model="form.title" />
    </div>
    <div class="description">
      <label for="description">내용</label>
      <input id="description" type="text" v-model="form.description" />
    </div>
    <button class="save-btn" @click="fnSave">저장</button>
  </div>
</template>
<script>
import { categories } from '@/constants/accountCategory.js'
export default {
  components: {},
  props: {
    date: String,
  },
  data() {
    return {
      amountType: 'minus',
      form: {
        title: '',
        accountDate: this.date,
        category: 'FOOD',
        amount: 0,
        description: '',
        fixed: false,
      },
    }
  },
  computed: {
    categoriesAsArray() {
      return Object.entries(categories).map(([key, value]) => ({
        ...value,
        key: key.toUpperCase(),
      }))
    },
  },
  methods: {
    async fnSave() {
      const url = `account/items`
      const form = this.form
      const body = {
        ...form,
        amount: this.amountType === 'plus' ? form.amount : -form.amount,
      }
      console.log(body)
      if (form.amount === null || form.amount === 0) {
        alert('금액 입력 해라.')
        return
      }
      if (form.title === null || form.title === '') {
        alert('타이틀 입력 해라')
        return
      }
      const { data } = await this.$axios.post(url, body)
      if (data) {
        this.form = {
          title: '',
          accountDate: '2024-12-08',
          category: 'FOOD',
          amount: 0,
          description: '',
          fixed: false,
        }
        this.$emit('refresh')
      } else {
        alert('뭔가 잘못되었으니 확인해라')
      }
    },
  },
}
</script>
<style lang="scss">
.form-container {
  display: flex;
  flex-direction: column;
  margin: 10px 0;
  height: 200px;
  border-bottom: 1px solid #eee;
  box-shadow: 5px 5px 3px #666;
  .select-container {
    display: flex;
    padding: 10px;
    font-size: 20px;

    select {
      margin: 0 15px;
      width: 100px;
      height: 30px;
      border-radius: 10px;
      font-size: 15px;
      font-weight: bold;
      text-align: center;
    }
  }
  .amount-title-container,
  .description {
    display: flex;
    padding: 10px;
    font-size: 20px;
    input {
      margin: 0 15px;
      width: 150px;
      &:focus {
        border-color: rgb(63, 63, 240);
      }
    }
  }
}
.save-btn {
  align-self: center;
  margin: 10px;
  width: 500px;
  font-size: 20px;
  font-weight: bold;
  background-color: white;
  border: 3px solid black;
  height: 50px;
  border-radius: 10px;
  transition:
    background-color 0.3s ease,
    color 0.3s ease,
    transform 0.3s ease,
    box-shadow 0.2s ease;
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.1);
  &:hover {
    background-color: black;
    color: white;
    border-color: rgb(26, 26, 25);
    box-shadow: 0 6px 10px rgba(0, 0, 0, 0.3);
  }
}
</style>
