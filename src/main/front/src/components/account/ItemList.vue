<template>
  <div>
    <div class="list-container">
      <div class="list-item" v-for="(item, index) in list" :key="index">
        <div class="category">
          <CategoryBadge v-if="updateRowNum != item.id" :category="item.category" size="large" />
          <select v-else v-model="updateForm.category">
            <option v-for="(category, i) in categoriesAsArray" :value="category.key" :key="i">
              {{ category.name }}
            </option>
          </select>
        </div>
        <div class="amount">
          <AmountBadge v-if="updateRowNum != item.id" :amount="item.amount" size="large" />
          <div style="display: flex; width: 100%">
            <select
              style="width: 25%"
              v-if="updateRowNum === item.id"
              v-model="updateForm.amountValue"
            >
              <option value="plus">수입</option>
              <option value="minus">지출</option>
            </select>
            <input
              style="width: 60%"
              v-if="updateRowNum === item.id"
              v-model="updateForm.amount"
              type="text"
            />
          </div>
        </div>
        <div class="title">
          <div v-if="updateRowNum != item.id">{{ item.title }}</div>
          <input v-else v-model="updateForm.title" type="text" />
        </div>
        <div class="buttonBox">
          <button
            v-if="item.id != updateRowNum"
            class="updateBtn"
            style="background-color: #515ee8"
            @click="fnUpdateInit(item)"
          >
            수정
          </button>
          <button
            v-if="item.id != updateRowNum"
            class="updateBtn"
            style="background-color: #e86351"
            @click="fnDeleteItem(item.id)"
          >
            삭제
          </button>
          <button
            v-if="item.id === updateRowNum"
            class="updateBtn"
            style="background-color: #51e85e"
            @click="fnUpdate(item)"
          >
            저장
          </button>
          <button
            v-if="item.id === updateRowNum"
            class="updateBtn"
            style="background-color: #e8b451"
            @click="fnUpdateCancel()"
          >
            취소
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import AmountBadge from '@/components/account/AmountBadge.vue'
import CategoryBadge from '@/components/account/CategoryBadge.vue'
import { categories } from '@/constants/accountCategory.js'
export default {
  components: {
    CategoryBadge,
    AmountBadge,
  },
  props: {
    date: String,
  },
  data() {
    return {
      updateRowNum: null,
      updateForm: {},
      list: [],
    }
  },
  watch: {
    date(newDate) {
      this.fnGetItemListByDate(newDate)
    },
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
    fnToWon(money) {
      let won = new Intl.NumberFormat('ko-KR', {
        style: 'currency',
        currency: 'KRW',
      })
      return won.format(Math.abs(money))
    },
    async fnGetItemListByDate(date) {
      const url = `account/items/date/${date}`
      const { data } = await this.$axios.get(url)
      this.list = data
    },
    fnUpdateInit(item) {
      this.updateRowNum = item.id
      this.updateForm = {
        category: item.category,
        title: item.title,
        amount: Math.abs(item.amount),
        description: item.description,
        amountValue: item.amount > 0 ? 'plus' : 'minus',
      }
    },
    async fnUpdate() {
      const url = `account/items/${this.updateRowNum}`
      const form = this.updateForm
      const body = {
        category: form.category,
        title: form.title,
        description: form.description,
        amount: form.amountValue === 'plus' ? form.amount : -form.amount,
      }
      const { data } = await this.$axios.patch(url, body)
      if (data) {
        this.updateRowNum = null
        this.updateForm = null
        this.fnGetItemListByDate(this.date)
      }
    },
    async fnDeleteItem(itemId) {
      if (!confirm('진짜 삭제?')) {
        return
      }
      const url = `account/items/${itemId}`
      const { data } = await this.$axios.delete(url)
      if (data) {
        this.fnGetItemListByDate(this.date)
      } else {
        alert('뭔가 잘못되었으니 수정해라')
      }
    },
    fnUpdateCancel() {
      this.updateRowNum = null
      this.updateForm.amountValue = ''
    },
  },
  mounted() {
    this.fnGetItemListByDate(this.date)
  },
}
</script>
<style scoped>
.list-container {
  .list-item {
    display: flex;
    margin: 10px 0;
    height: 70px;
    border-bottom: 1px solid #eee;
    box-shadow: 5px 5px 3px #666;
    .category {
      width: 120px;
      align-content: center;
      justify-items: center;
    }
    .amount {
      width: 220px;
      align-content: center;
      justify-items: start;
      font-weight: bold;
    }
    .title {
      width: 200px;
      font-size: 20px;
      align-content: center;
      justify-items: start;
    }
    .buttonBox {
      align-content: center;
      justify-items: center;
      .updateBtn {
        color: #fff;
        border: none;
        border-radius: 4px;
        padding: 10px 20px;
        font-size: 16px;
        cursor: pointer;
        transition:
          background-color 0.3s ease,
          transform 0.2s ease,
          box-shadow 0.2s ease;
        height: 40px;
        width: 100px;

        /* 기본 그림자 효과 */
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);

        &:hover {
          background-color: #2980b9;
          box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
        }

        &:active {
          background-color: #1c6ba0;
          transform: translateY(2px);
          box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
        }

        &:focus {
          outline: none;
        }
      }
    }
  }
}
</style>
