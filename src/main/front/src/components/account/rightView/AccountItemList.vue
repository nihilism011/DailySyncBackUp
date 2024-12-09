<template>
  <div>
    <div class="list-container">
      <AccountItemRow
        v-for="(account, index) in list"
        :key="index"
        :account="account"
        @refresh="$emit('refresh')"
      />
    </div>
  </div>
</template>
<script>
import AccountItemRow from './AccountItemRow.vue'
import { useDateStore } from '@/stores/dateStore'
export default {
  emits: ['refresh'],
  components: {
    AccountItemRow,
  },
  data() {
    const dateStore = useDateStore()
    return {
      dateStore,
      list: [],
    }
  },
  watch: {
    'dateStore.selectedDate': 'fetchDataList',
  },
  computed: {
    selectedDate() {
      return this.dateStore.selectedDate
    },
  },
  methods: {
    async fetchDataList() {
      const date = this.selectedDate || this.$dayjs().format('YYYY-MM-DD')
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
