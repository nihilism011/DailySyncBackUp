<template>
  <div class="account-right">
    <div class="account-fixed">
      <div>{{ monthString }} 고정 내역</div>
      <div v-if="list.length === 0">고정내역없음</div>
      <div v-else>
        <div class="list-item" v-for="(item, index) in list" :key="index">
          <div>{{ item.accountDate.split('-')[2] }}</div>
          <div>{{ categories[item.category].name }}</div>
          <div>{{ item.title }}</div>
          <div>{{ numToWon(item.amount) }}</div>
          <div class="btn-box">
            <button @click="viewInfoPopup(item)" class="edit-btn"></button>
            <button @click="deleteItem(item.id)" class="remove-btn"></button>
          </div>
          <AccountPopup
            v-if="viewUpdatePopup && account"
            :account="account"
            mode="update"
            @close="closeUpdatePopup"
          />
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { useDateStore } from '@/stores/dateStore'
import { useRefreshStore } from '@/stores/refreshStore'
import { numToWon } from '@/lib/accountLib'
import { categories } from '@/constants/accountCategory'
import AccountPopup from '../AccountPopup.vue'
import { deleteAccountItem } from '@/lib/accountLib'
export default {
  components: {
    AccountPopup,
  },
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
      categories,
      year: this.dateStore.selectedYear,
      month: this.dateStore.selectedMonth,
      viewUpdatePopup: false,
      account: null,
      list: [],
    }
  },
  methods: {
    numToWon,
    deleteAccountItem,
    async fetchData() {
      const url = `account/items/fixed/${this.year}/${this.month}`
      const { data } = await this.$axios.get(url)
      this.list = data
    },
    viewInfoPopup(item) {
      this.account = item
      this.viewUpdatePopup = true
    },
    closeUpdatePopup() {
      this.viewUpdatePopup = false
      this.account = false
    },
    async deleteItem(id) {
      if (confirm('삭제하시겠습니까?')) {
        await this.deleteAccountItem(id)
        this.refreshStore.setRefresh()
      }
    },
  },
  mounted() {
    this.fetchData()
  },
}
</script>
<style lang="scss" scoped></style>
