<template>
  <div class="list-item">
    <div class="tit-box">
      <div :style="{ color: categories[account.category].color }" class="title">
        {{ categories[account.category].name }}
      </div>
    </div>
    <div>{{ account.title }}</div>
    <div>{{ account.description }}</div>
    <div>{{ numToWon(account.amount) }}</div>
    <div class="btn-box">
      <button @click="viewUpdatePopup = true" class="edit-btn"></button>
      <button @click="deleteItem" class="remove-btn"></button>
    </div>
  </div>
  <AccountPopup v-if="viewUpdatePopup" :account="account" mode="update" @close="closeUpdatePopup" />
</template>
<script>
import { useRefreshStore } from '@/stores/refreshStore'
import { useDateStore } from '@/stores/dateStore'
import { categories } from '@/constants/accountCategory'
import { numToWon } from '@/lib/accountLib'
import { deleteAccountItem } from '@/lib/accountLib'
import AccountPopup from '@/components/account/AccountPopup.vue'
export default {
  components: {
    AccountPopup,
  },
  setup() {
    const refreshStore = useRefreshStore()
    const dateStore = useDateStore()
    return { refreshStore, dateStore }
  },
  props: {
    account: Object,
  },
  data() {
    return {
      categories,
      viewUpdatePopup: false,
    }
  },
  methods: {
    numToWon,
    deleteAccountItem,
    closeUpdatePopup() {
      this.viewUpdatePopup = false
    },
    async deleteItem() {
      if (!confirm('정말 삭제하시겠습니까?')) {
        return
      }
      const reqStatus = await this.deleteAccountItem(this.account.id)
      if (reqStatus) {
        this.refreshStore.setRefresh()
      }
    },
  },
}
</script>
<style lang="scss" scoped></style>
