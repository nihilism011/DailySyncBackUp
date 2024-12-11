<template>
  <div>
    <div class="list-container">
      <AccountItemRow
        v-for="(account, index) in list"
        :key="index"
        :account="account"
        size="large"
      />
    </div>
  </div>
</template>
<script>
import AccountItemRow from './AccountItemRow.vue'
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
export default {
  emits: ['refresh'],
  components: {
    AccountItemRow,
  },
  data() {
    const dateStore = useDateStore()
    const refreshStore = useRefreshStore()
    return {
      refreshStore,
      dateStore,
      list: [],
    }
  },
  watch: {
    'dateStore.selectedDate': 'fetchDataList',
    'refreshStore.refreshState': 'fetchDataList',
  },
  methods: {
    async fetchDataList() {
      const date = this.dateStore.selectedDate || this.$dayjs().format('YYYY-MM-DD')
      const url = `account/items/date/${date}`
      const { data } = await this.$axios.get(url)
      this.list = data
    },
  },
  mounted() {
    this.fetchDataList()
  },
}
</script>
<style scoped></style>
