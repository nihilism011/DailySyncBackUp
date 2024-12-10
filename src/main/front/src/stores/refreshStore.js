// stores/refreshStore.js
import { defineStore } from 'pinia'

export const useRefreshStore = defineStore('refreshStore', {
  state: () => ({
    refreshState: 0,
  }),
  actions: {
    setRefresh() {
      this.refreshState += 1
    },
  },
})
