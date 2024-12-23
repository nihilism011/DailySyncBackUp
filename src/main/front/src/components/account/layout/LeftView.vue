<script setup>
import { ref } from 'vue' // ref import
import DateScore from '../leftView/DateScore.vue'
import { useDateStore } from '@/stores/dateStore'
import AccountItemList from '../leftView/AccountItemList.vue'
import AccountPopup from '../AccountPopup.vue'
const dateStore = useDateStore()
const viewUpdatePopup = ref(false)
</script>
<template>
  <div class="date">{{ dateStore.selectedDate }}</div>
  <div class="account-wrap">
    <DateScore />
    <button @click="viewUpdatePopup = true" class="add-btn">가계부 작성</button>
    <AccountItemList />
  </div>
  <AccountPopup v-if="viewUpdatePopup" mode="create" @close="viewUpdatePopup = false" />
</template>

<style lang="scss" scoped>
.date {
  font-size: 28px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}
.add-btn {
  height: 36px;
  padding: 0 20px;
  width: auto;
  border-radius: 4px;
  background-color: var(--color-blue);
  color: var(--base-white);
  font-weight: bold;
  font-size: 14px;
  transition: all 0.3s;
  &:hover {
    background-color: var(--color-contrasty3);
  }
}
.account {
  &-wrap {
    height: 100%;
    border-radius: 10px;
    box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.3);
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    gap: 15px 0;
  }
}
</style>
