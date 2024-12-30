<script setup>
import { ref } from 'vue'
import DateScore from '@/components/account/leftView/DateScore.vue'
import AccountItemList from '@/components/account/leftView/AccountItemList.vue'
import AccountPopup from '@/components/account/AccountPopup.vue'

const props = defineProps({
  selectedDate: {
    type: String,
    default: '',
  },
})
const viewUpdatePopup = ref(false)
</script>
<template>
  <div class="dash-wrap">
    <DateScore :prop-date="selectedDate" />
    <!-- <button @click="viewUpdatePopup = true">+</button> -->
    <AccountItemList :prop-date="selectedDate" />
    <RouterLink to="/account" class="dash-link" title="가계부 이동">Account</RouterLink>
  </div>
  <!-- <AccountPopup
    v-if="viewUpdatePopup"
    mode="create"
    :account="{ accountDate: selectedDate }"
    @close="viewUpdatePopup = false"
  /> -->
</template>
