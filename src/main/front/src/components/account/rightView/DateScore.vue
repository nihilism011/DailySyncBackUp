<template>
  <div class="date-score-container">
    <div>수입 :</div>
    <div>{{ moneyFormatted.plus }}</div>
    <div>지출 :</div>
    <div>{{ moneyFormatted.minus }}</div>
  </div>
</template>

<script>
import { numToWon } from '@/lib/accountLib'
import { useDateStore } from '@/stores/dateStore'

export default {
  data() {
    const dateStore = useDateStore()
    return {
      dateStore,
      money: { plus: 0, minus: 0 },
    }
  },
  watch: {
    'dateStore.selectedDate': 'fetchData',
  },
  computed: {
    selectedDate() {
      return this.dateStore.selectedDate
    },
    moneyFormatted() {
      return {
        plus: numToWon(this.money.plus),
        minus: numToWon(this.money.minus),
      }
    },
  },
  methods: {
    async fetchData() {
      const date = this.selectedDate || this.$dayjs().format('YYYY-MM-DD')
      const url = `account/items/sum/${date}`
      const { data } = await this.$axios.get(url)
      this.money.plus = data.plusSumAmount
      this.money.minus = data.minusSumAmount
    },
  },
  mounted() {
    this.fetchData()
  },
}
</script>

<style lang="scss">
.date-score-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.date-score-container div {
  font-size: 16px;
  font-weight: 600;
}

.date-score-container div:nth-child(odd) {
  color: #333;
}

.date-score-container div:nth-child(even) {
  font-size: 18px;
  color: #2f8fe8;
}

.date-score-container .income,
.date-score-container .expense {
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  padding: 8px 12px;
  border-radius: 8px;
  width: 100px;
}

.date-score-container .income {
  background-color: #d1f7d7;
  color: #4caf50;
}

.date-score-container .expense {
  background-color: #ffebee;
  color: #e57373;
}

@media (max-width: 600px) {
  .date-score-container {
    flex-direction: column;
    align-items: flex-start;
    padding: 15px;
  }

  .date-score-container div {
    font-size: 14px;
  }

  .date-score-container .income,
  .date-score-container .expense {
    width: 100%;
    font-size: 18px;
    margin-top: 5px;
  }
}
</style>
