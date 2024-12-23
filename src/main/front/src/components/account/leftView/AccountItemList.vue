<template>
  <div class="account-list-wrap">
    <div class="item top">
      <div class="tit">지출 내역</div>
      <div class="list">
        <template v-if="minusList.length > 0">
          <AccountItem v-for="(account, index) in minusList" :key="index" :account="account" />
        </template>
        <div v-else class="none"><span>내역이 없습니다.</span></div>
      </div>
    </div>
    <div class="item bottom">
      <div class="tit">소득 내역</div>
      <template v-if="plusList.length > 0">
        <AccountItem v-for="(account, index) in plusList" :key="index" :account="account" />
      </template>
      <div v-else class="none"><span>내역이 없습니다.</span></div>
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
<style lang="scss" scoped>
.account {
  &-list-wrap {
    display: flex;
    flex-direction: column;
    gap: 10px 0;
    flex-grow: 1;
    width: 100%;
    .item {
      height: 50%;
      .tit {
        font-weight: bold;
        margin-bottom: 10px;
        line-height: 1;
      }
      .list {
        height: calc(100% - 26px);
        overflow-y: auto;
        padding: 10px;
      }
      .none {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 100%;
        span {
          padding-top: 60px;
          background: url('@/assets/images/no_list_icon.png') center top / 50px no-repeat;
          font-weight: bold;
          color: var(--color-contrastyA);
        }
      }
    }
  }
}
</style>
