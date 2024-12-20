import { defineStore } from 'pinia'

const groupByDate = (accountList) => {
  return accountList.reduce((acc, { accountDate, amount }) => {
    if (!acc[accountDate]) {
      acc[accountDate] = { plusSum: 0, minusSum: 0 }
    }
    if (amount > 0) {
      acc[accountDate].plusSum += amount
    } else {
      acc[accountDate].minusSum += amount
    }
    return acc
  }, {})
}

const formatGroupedData = (groupedData) => {
  return Object.entries(groupedData).flatMap(([date, { plusSum, minusSum }]) => {
    const result = []
    if (plusSum > 0) result.push({ start: date, title: plusSum, color: '#ff334b' })
    if (minusSum < 0) result.push({ start: date, title: minusSum, color: '#4f7eff' })
    return result
  })
}
export const useAccountStore = defineStore('accountStore', {
  state: () => ({
    accountList: [],
  }),

  getters: {
    calendarList() {
      const grouped = groupByDate(this.accountList)
      return formatGroupedData(grouped)
    },
  },

  actions: {
    setAccountList(list) {
      this.accountList = list
    },
  },
})
