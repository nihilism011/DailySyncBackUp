<script setup lang="ts">
import AccountPopup from '../AccountPopup.vue'
import { ref } from 'vue'
import { useAccountStore } from '@/stores/account/accountStore'
import { categories } from '@/constants/accountCategory'
import { formatCurrencyKRW } from '@/lib/account/accountLib'

const accountStore = useAccountStore()
const account = ref(null)
const viewUpdatePopup = ref(false)
async function deleteItem(id) {
  if (confirm('삭제하시겠습니까?')) {
    await accountStore.deleteItem(id)
  }
}
function openPopup(acc) {
  account.value = acc
  viewUpdatePopup.value = true
}
function closePopup() {
  account.value = null
  viewUpdatePopup.value = false
}
</script>
<template>
  <div class="account-right">
    <div class="account-fixed">
      <div class="account-tit">고정 내역</div>
      <div class="account-list">
        <div v-if="accountStore.fixedList.length === 0" class="account-none">
          <span>고정내역없음</span>
        </div>
        <template v-else>
          <div class="account-item" v-for="(item, index) in accountStore.fixedList" :key="index">
            <div class="date">{{ item.accountDate.split('-')[2] }}</div>
            <div class="category" :style="{ color: categories[item.category].color }">
              {{ categories[item.category].name }}
            </div>
            <div class="tit">{{ item.title }}</div>
            <div class="amount">{{ formatCurrencyKRW(item.amount) }}</div>
            <div class="btn-box">
              <button @click="openPopup(item)" class="edit-btn"></button>
              <button @click="deleteItem(item.id)" class="remove-btn"></button>
            </div>
            <AccountPopup
              v-if="viewUpdatePopup && account"
              :account="account"
              mode="update"
              @close="closePopup"
            />
          </div>
        </template>
      </div>
    </div>
  </div>
</template>
