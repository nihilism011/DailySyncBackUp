<template>
  <div>
    <div>{{ monthString }}</div>
    <div>
      <div>수입</div>
      <div>{{ numToWon(info.plusSumAmount) }}</div>
      <div>지출</div>
      <div>{{ numToWon(info.minusSumAmount) }}</div>
    </div>
  </div>
  <div>
    <div>고정 수입</div>
    <div>{{ numToWon(fixedInfo.plusSumAmount) }}</div>
    <div>고정 지출</div>
    <div>{{ numToWon(fixedInfo.minusSumAmount) }}</div>
  </div>
</template>
<script>
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
import { numToWon } from '@/lib/accountLib'
export default {
  setup() {
    const dateStore = useDateStore()
    const refreshStore = useRefreshStore()
    return { dateStore, refreshStore }
  },
  watch: {
    'dateStore.selectedMonth': function () {
      this.month = this.dateStore.selectedMonth
      this.fetchData()
    },
    'dateStore.selectedYear': function () {
      this.year = this.dateStore.selectedYear
      this.fetchData()
    },
    'refreshStore.refreshState': 'fetchData',
  },
  computed: {
    monthString() {
      return `${this.year}년 ${this.month}월`
    },
  },
  data() {
    return {
      year: this.dateStore.selectedYear,
      month: this.dateStore.selectedMonth,
      info: {},
      fixedInfo: {},
    }
  },
  methods: {
    numToWon,
    async fetchData() {
      const url = `account/items/summary/${this.year}/${this.month}`
      const { data } = await this.$axios.get(url)
      this.info = data
      const fixedUrl = `account/items/summary/fixed/${this.year}/${this.month}`
      const { data: fixedData } = await this.$axios.get(fixedUrl)
      this.fixedInfo = fixedData
    },
  },
  mounted() {
    this.fetchData()
  },
}
</script>
<style lang="scss" scoped></style>
