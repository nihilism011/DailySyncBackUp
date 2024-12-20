import { defineStore } from 'pinia'

export const useAccountStore = defineStore('accountStore', {
  state: () => ({
    accountList: [],
  }),
  getters: {
    aaa() {
      return 'asdf'
    },
  },
  actions: {
    setAccountList(list) {
      this.accountList = list
    },
  },
})
