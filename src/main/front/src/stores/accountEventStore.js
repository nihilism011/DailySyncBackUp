import { defineStore } from 'pinia'

export const useAccountEventStore = defineStore('accountEventStore', {
  state: () => ({
    data: [],
  }),
  getters: {
    aaa() {
      let plusSumAmount = 0
      let minusSumAmount = 0
      this.data.forEach((el) => {
        plusSumAmount += el.plusSumAmount
        minusSumAmount += el.minusSumAmount
      })
      return { plusSumAmount, minusSumAmount }
    },
  },
  actions: {
    setData(data) {
      this.data = data
    },
  },
})
