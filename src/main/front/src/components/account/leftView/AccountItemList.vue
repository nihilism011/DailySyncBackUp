<template>
  <div>
    <div>
      <div class="title">지출 내역</div>
      <div class="list">
        <template v-if="minusList.length > 0">
          <AccountItem v-for="(account, index) in minusList" :key="index" :account="account" />
        </template>
        <div v-else>내역이 없습니다.</div>
      </div>
    </div>
    <div>
      <div class="title">소득 내역</div>
      <template v-if="plusList.length > 0">
        <AccountItem v-for="(account, index) in plusList" :key="index" :account="account" />
      </template>
      <div v-else>내역이 없습니다.</div>
    </div>
  </div>
</template>
<script>
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
import AccountItem from '@/components/account/leftView/AccountItem.vue'
export default {
  props: {
    propDate: String,
  },
  components: {
    AccountItem,
  },
  data() {
    const dateStore = useDateStore()
    const refreshStore = useRefreshStore()
    return {
      refreshStore,
      dateStore,
      plusList: [],
      minusList: [],
      list: [],
    }
  },
  watch: {
    propDate() {
      this.fetchDataList()
    },
    'dateStore.selectedDate': 'fetchDataList',
    'refreshStore.refreshState': 'fetchDataList',
  },
  methods: {
    async fetchDataList() {
      const date = this.propDate ?? this.dateStore.selectedDate
      const url = `account/items/date/${date}`
      const { data } = await this.$axios.get(url)
      const plusList = []
      const minusList = []
      for (let account of data) {
        if (account.amount > 0) {
          plusList.push(account)
        } else {
          minusList.push(account)
        }
      }
      this.plusList = plusList
      this.minusList = minusList
    },
  },
  mounted() {
    this.fetchDataList()
  },
}
</script>
<style scoped></style>
