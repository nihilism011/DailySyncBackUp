<template>
  <div>
    <p class="list-tit">
      <AccountItem v-for="(account, index) in list" :key="index" :account="account" />
    </p>
  </div>
</template>
<script>
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
import AccountItem from '../AccountItem.vue'
export default {
  emits: ['refresh'],
  components: {
    AccountItem,
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
