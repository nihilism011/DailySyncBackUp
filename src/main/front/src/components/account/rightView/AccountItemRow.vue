<template>
  <div class="account-row">
    <div>
      <CategoryBadge :category="account.category" size="large" />
    </div>
    <div>
      <AmountBadge :amount="account.amount" size="large" />
    </div>
    <div>
      <div>{{ account.title }}</div>
    </div>
    <div>
      <div>{{ account.description }}</div>
    </div>
    <div>
      <button @click="viewUpdatePopup = true">수정</button>
    </div>
  </div>
  <UpdatePopup
    v-if="viewUpdatePopup"
    :account="account"
    @refresh="refresh"
    @close="viewUpdatePopup = false"
    :update="true"
  />
</template>
<script>
import CategoryBadge from '@/components/account/CategoryBadge.vue'
import AmountBadge from '@/components/account/AmountBadge.vue'
import UpdatePopup from '@/components/account/UpdatePopup.vue'
export default {
  emits: ['refresh'],
  components: {
    CategoryBadge,
    AmountBadge,
    UpdatePopup,
  },
  props: {
    account: Object,
  },
  data() {
    return {
      viewUpdatePopup: false,
    }
  },
  methods: {
    refresh() {
      this.$emit('refresh')
      this.viewUpdatePopup = false
    },
  },
}
</script>
<style lang="scss" scoped>
.account-row {
  display: flex;
  flex-direction: row;
}
</style>
