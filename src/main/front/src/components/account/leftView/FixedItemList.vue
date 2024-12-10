<template>
  <div>{{ $dayjs(dateStore.selectedDate).format('YYYY-MM') }} 고정 지출 목록</div>
  <hr />
  <div class="list-container">
    <AccountItemRow
      v-for="(account, index) in list"
      :key="index"
      :account="account"
      size="small"
      :fixed="true"
    />
  </div>
</template>
<script>
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
import AccountItemRow from '../rightView/AccountItemRow.vue'
export default {
  components: {
    AccountItemRow,
  },
  setup() {
    const dateStore = useDateStore()
    const refreshStore = useRefreshStore()
    return { dateStore, refreshStore }
  },
  data() {
    return {
      list: [],
    }
  },
  watch: {
    'dateStore.selectedDate': 'fetchDataList',
    'refreshStore.refreshState': 'fetchDataList',
  },
  methods: {
    async fetchDataList() {
      const date = this.$dayjs(this.dateStore.selectedDate)
      const year = date.get('year')
      const month = date.get('month') + 1
      const url = `account/items/fixed/${year}/${month}`
      const { data } = await this.$axios.get(url)
      this.list = data
    },
  },
  mounted() {
    this.fetchDataList()
  },
}
</script>
<style lang="scss" scoped>
.list-container {
  display: flex;
  flex-direction: column;
}
</style>
