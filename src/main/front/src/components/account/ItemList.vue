<template>
  <div>
    <div class="list-container">
      <div class="list-item" v-for="(item, index) in list" :key="index">
        <div class="category">
          <CategoryBadge :category="item.category" size="large" />
        </div>
        <div class="amountType">
          <div v-if="item.id != updateRowNum" :class="item.amount > 0 ? 'plus' : 'minus'">
            {{ item.amount > 0 ? '수입' : '지출' }}
          </div>
          <select v-if="item.id === updateRowNum" v-model="updateForm.amountValue">
            <option value="plus">수입</option>
            <option value="minus">지출</option>
          </select>
        </div>
        <div class="amount">
          <div>{{ fnToWon(item.amount) }}</div>
        </div>
        <div class="title">
          <div>{{ item.title }}</div>
        </div>
        <div class="buttonBox">
          <button v-if="item.id != updateRowNum" class="updateBtn" @click="fnUpdateInit(item)">
            수정
          </button>
          <button v-if="item.id === updateRowNum" class="updateBtn" @click="fnUpdate(item)">
            저장
          </button>
          <button v-if="item.id === updateRowNum" class="updateBtn" @click="fnUpdateCancel()">
            취소
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import CategoryBadge from './CategoryBadge.vue'

export default {
  components: {
    CategoryBadge,
  },
  props: {
    date: String,
  },
  data() {
    return {
      updateRowNum: null,
      updateItemId: null,
      updateForm: { amountValue: '' },
      list: [],
    }
  },
  watch: {
    date(newDate) {
      this.fnGetItemListByDate(newDate)
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
      this.updateForm.amountValue = item.amount > 0 ? 'plus' : 'minus'
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
    .amountType {
      width: 80px;
      align-content: center;
      justify-items: center;
      font-weight: bold;
      .plus {
        color: red;
      }
      .minus {
        color: blue;
      }
    }
    .amount {
      width: 150px;
      align-content: center;
      justify-items: start;
      font-weight: bold;
      padding-left: 30px;
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
        background-color: #3498db;
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
