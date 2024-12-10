<template>
  <div @click="fixedAction" class="account-row" :class="{ fixedStyle: fixed }">
    <div>
      <CategoryBadge :category="account.category" :size="size" />
    </div>
    <div>
      <AmountBadge :amount="account.amount" :size="size" />
    </div>
    <div class="account-title" :class="{ title_fixedStyle: fixed }">
      <div>{{ account.title }}</div>
    </div>
    <div v-if="!fixed" class="account-description">
      <div>{{ account.description }}</div>
    </div>
    <div v-if="!fixed" class="account-btn-group">
      <button class="account-btn" @click="viewUpdatePopup = true">수정</button>
    </div>
    <div v-if="fixed" class="itemTextLayout tempCss">
      {{ $dayjs(account.accountDate).get('date') }}일
    </div>
  </div>
  <UpdatePopup
    v-if="viewUpdatePopup"
    :account="account"
    @close="viewUpdatePopup = false"
    action="update"
    :fixed="fixed"
  />
</template>
<script>
import CategoryBadge from '@/components/account/CategoryBadge.vue'
import AmountBadge from '@/components/account/AmountBadge.vue'
import UpdatePopup from '@/components/account/UpdatePopup.vue'
import { useRefreshStore } from '@/stores/refreshStore'
import { useDateStore } from '@/stores/dateStore'
export default {
  emits: ['refresh'],
  components: {
    CategoryBadge,
    AmountBadge,
    UpdatePopup,
  },
  props: {
    account: Object,
    fixed: {
      type: Boolean,
      default: false,
    },
    size: {
      type: String,
      default: 'medium',
      validator(value) {
        return ['small', 'medium', 'large'].includes(value)
      },
    },
  },
  setup() {
    const refreshStore = useRefreshStore()
    const dateStore = useDateStore()
    return { refreshStore, dateStore }
  },
  data() {
    return {
      viewUpdatePopup: false,
    }
  },
  methods: {
    fixedAction() {
      if (this.fixed) {
        this.dateStore.setSelectedDate(this.account.accountDate)
      }
    },
  },
}
</script>
<style lang="scss" scoped>
.account-row {
  display: flex;
  flex-direction: row;
}
.fixedStyle {
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.4s;
  &:hover {
    background-color: rgba(113, 113, 223, 0.247);
  }
}
.itemTextLayout {
  align-self: center;
  justify-items: center;
}
.account-title {
  display: flex;
  width: 200px;
  font-size: 20px;
  font-weight: bold;
  align-items: center;
  justify-content: center;
}
.title_fixedStyle {
  min-width: 80px;
  max-width: 100px;
  font-size: 16px;
}
.account-description {
  display: flex;
  width: 150px;
  align-items: center;
  justify-content: center;
}
.account-btn-group {
  display: flex;
  width: 150px;
  align-items: center;
  justify-content: center;
}
.account-btn {
  height: 40px;
  width: 100px;
  transition: all 0.5s;
  &:hover {
    background-color: rgba(66, 66, 247, 0.164);
  }
}
</style>
