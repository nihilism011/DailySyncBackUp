<template>
  <div class="account-left">
    <div class="account-fixed">
      <div class="account-divide">
        <div class="first">
          <div class="tit">소득</div>
          <div>{{ numToWon(accountStore.aaa.plusSumAmount) }}</div>
        </div>
        <div class="second">
          <div class="tit">지출</div>
          <div>{{ numToWon(accountStore.aaa.minusSumAmount) }}</div>
        </div>
      </div>
    </div>
    <div class="account-fixed">
      <div class="account-divide">
        <div class="first">
          <div class="tit">고정 소득</div>
          <div>{{ numToWon(fixedInfo.plusSumAmount) }}</div>
        </div>
        <div class="second">
          <div class="tit">고정 지출</div>
          <div>{{ numToWon(-fixedInfo.minusSumAmount) }}</div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
import { numToWon } from '@/lib/accountLib'
import { useAccountEventStore } from '@/stores/accountEventStore'
export default {
  setup() {
    const dateStore = useDateStore()
    const refreshStore = useRefreshStore()
    const accountStore = useAccountEventStore()
    return { dateStore, refreshStore, accountStore }
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
      // const url = `account/items/summary/${this.year}/${this.month}`
      // const { data } = await this.$axios.get(url)
      // this.info = data
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
