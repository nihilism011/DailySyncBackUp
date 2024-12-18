<script setup>
import { ref } from 'vue' // ref import
import DateScore from '../leftView/DateScore.vue'
import AccountItemList from '../leftView/AccountItemList.vue'
import AccountPopup from '../AccountPopup.vue'

const viewUpdatePopup = ref(false)
</script>
<template>
  <div>
    <DateScore />
    <button @click="viewUpdatePopup = true">작성</button>
    <AccountItemList />
  </div>
  <AccountPopup v-if="viewUpdatePopup" mode="create" @close="viewUpdatePopup = false" />
</template>
